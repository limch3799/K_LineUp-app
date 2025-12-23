// src/pages/match/components/RegisterMatchModal.jsx
import { useState, useRef } from 'react';
import { collection, addDoc, serverTimestamp, writeBatch, doc } from 'firebase/firestore';
import { db } from '../../../firebase';

const RegisterMatchModal = ({ isOpen, onClose, onComplete }) => {
  const [loading, setLoading] = useState(false);
  const [uploadStatus, setUploadStatus] = useState('');
  const fileInputRef = useRef(null);

  if (!isOpen) return null;

  const parseCSV = (text) => {
    const lines = text.split('\n').filter(line => line.trim());
    const matches = [];

    for (const line of lines) {
      // CSV 파싱 (쉼표로 분리, 빈 값 처리)
      const values = [];
      let current = '';
      let inQuotes = false;

      for (let i = 0; i < line.length; i++) {
        const char = line[i];
        
        if (char === '"') {
          inQuotes = !inQuotes;
        } else if (char === ',' && !inQuotes) {
          values.push(current.trim());
          current = '';
        } else {
          current += char;
        }
      }
      values.push(current.trim());

      // 값 추출
      const [homeTeam, awayTeam, competition, matchTimeStr, stadium, playerIdsStr] = values;

      // 필수 필드 확인
      if (!homeTeam || !awayTeam || !competition || !matchTimeStr) {
        console.warn('필수 필드 누락:', line);
        continue;
      }

      // matchTime 파싱 (202512091430 형식)
      const year = matchTimeStr.substring(0, 4);
      const month = matchTimeStr.substring(4, 6);
      const day = matchTimeStr.substring(6, 8);
      const hour = matchTimeStr.substring(8, 10);
      const minute = matchTimeStr.substring(10, 12);
      const matchTime = new Date(`${year}-${month}-${day}T${hour}:${minute}:00`);

      // 선수 ID 파싱
      let koreanPlayerIds = [];
      if (playerIdsStr && playerIdsStr.trim()) {
        koreanPlayerIds = playerIdsStr.split(',')
          .map(id => id.trim())
          .filter(id => id);
      }

      matches.push({
        homeTeam: homeTeam.trim(),
        awayTeam: awayTeam.trim(),
        competition: competition.trim(),
        matchTime,
        stadium: stadium && stadium.trim() ? stadium.trim() : null,
        koreanPlayerIds,
        status: 'scheduled',
        lineup: null,
        koreanPlayersStatus: null,
        notificationSent: false,
        notificationSentAt: null,
        subscriberCount: 0,
      });
    }

    return matches;
  };

  const handleFileUpload = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    if (!file.name.endsWith('.csv')) {
      alert('CSV 파일만 업로드 가능합니다.');
      return;
    }

    try {
      setLoading(true);
      setUploadStatus('파일 읽는 중...');

      const text = await file.text();
      const matches = parseCSV(text);

      if (matches.length === 0) {
        alert('유효한 경기 데이터가 없습니다.');
        setLoading(false);
        setUploadStatus('');
        return;
      }

      setUploadStatus(`${matches.length}개 경기 등록 중...`);

      // Batch로 한 번에 등록 (최대 500개까지)
      const batch = writeBatch(db);
      let batchCount = 0;

      for (const match of matches) {
        const docRef = doc(collection(db, 'matches'));
        batch.set(docRef, {
          ...match,
          createdAt: serverTimestamp(),
          updatedAt: serverTimestamp(),
        });
        batchCount++;

        // 500개마다 batch commit
        if (batchCount >= 500) {
          await batch.commit();
          batchCount = 0;
        }
      }

      // 남은 batch commit
      if (batchCount > 0) {
        await batch.commit();
      }

      alert(`${matches.length}개의 경기가 등록되었습니다.`);
      setUploadStatus('');
      
      // 파일 입력 초기화
      if (fileInputRef.current) {
        fileInputRef.current.value = '';
      }

      onComplete();
      onClose();
    } catch (error) {
      console.error('CSV 업로드 실패:', error);
      alert('경기 등록에 실패했습니다.');
      setUploadStatus('');
    } finally {
      setLoading(false);
    }
  };

  const handleClose = () => {
    if (!loading) {
      setUploadStatus('');
      if (fileInputRef.current) {
        fileInputRef.current.value = '';
      }
      onClose();
    }
  };

  return (
    <div style={styles.overlay} onClick={handleClose}>
      <div style={styles.modal} onClick={(e) => e.stopPropagation()}>
        {/* 헤더 */}
        <div style={styles.header}>
          <h2 style={styles.title}>경기 등록</h2>
          <button 
            style={styles.closeButton} 
            onClick={handleClose}
            disabled={loading}
          >
            ✕
          </button>
        </div>

        {/* 내용 */}
        <div style={styles.content}>
          <div style={styles.description}>
            CSV 파일을 업로드하여 경기를 일괄 등록할 수 있습니다.
          </div>

          <div style={styles.formatInfo}>
            <div style={styles.formatTitle}>CSV 파일 형식:</div>
            <code style={styles.code}>
              홈팀,원정팀,대회명,경기시간,경기장,선수ID목록
            </code>
            <div style={styles.formatExample}>
              <div style={styles.exampleTitle}>예시:</div>
              <code style={styles.code}>
                토트넘,첼시,프리미어리그,202512091430,,&quot;1,2&quot;
              </code>
              <code style={styles.code}>
                토트넘,첼시,프리미어리그,202512091700,토트넘스타디움,1
              </code>
            </div>
            <div style={styles.note}>
              * 경기시간 형식: YYYYMMDDHHMM (예: 202512091430)<br />
              * 경기장이 없으면 비워두세요<br />
              * 선수 ID는 쉼표로 구분 (여러 명일 경우 따옴표로 감싸기)
            </div>
          </div>

          <div style={styles.uploadSection}>
            <input
              ref={fileInputRef}
              type="file"
              accept=".csv"
              onChange={handleFileUpload}
              style={styles.fileInput}
              disabled={loading}
            />
            <button
              onClick={() => fileInputRef.current?.click()}
              style={{
                ...styles.uploadButton,
                ...(loading && styles.uploadButtonDisabled),
              }}
              disabled={loading}
            >
              {loading ? uploadStatus : 'CSV 파일 선택'}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

const styles = {
  overlay: {
    position: 'fixed',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    zIndex: 1000,
  },
  modal: {
    backgroundColor: '#fff',
    borderRadius: '12px',
    width: '90%',
    maxWidth: '700px',
    maxHeight: '80vh',
    display: 'flex',
    flexDirection: 'column',
    boxShadow: '0 4px 20px rgba(0, 0, 0, 0.15)',
  },
  header: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '20px 24px',
    borderBottom: '1px solid #eee',
  },
  title: {
    fontSize: '20px',
    fontWeight: '700',
    color: '#111',
    margin: 0,
  },
  closeButton: {
    padding: '4px 8px',
    backgroundColor: 'transparent',
    border: 'none',
    fontSize: '24px',
    color: '#666',
    cursor: 'pointer',
    lineHeight: 1,
  },
  content: {
    padding: '24px',
    overflowY: 'auto',
    flex: 1,
  },
  description: {
    fontSize: '14px',
    color: '#6C757D',
    marginBottom: '20px',
  },
  formatInfo: {
    backgroundColor: '#F8F9FA',
    padding: '16px',
    borderRadius: '8px',
    marginBottom: '24px',
  },
  formatTitle: {
    fontSize: '14px',
    fontWeight: '600',
    color: '#111',
    marginBottom: '8px',
  },
  code: {
    display: 'block',
    backgroundColor: '#fff',
    padding: '8px 12px',
    borderRadius: '4px',
    fontSize: '13px',
    fontFamily: 'monospace',
    color: '#495057',
    marginBottom: '8px',
    border: '1px solid #DEE2E6',
  },
  formatExample: {
    marginTop: '12px',
  },
  exampleTitle: {
    fontSize: '13px',
    fontWeight: '600',
    color: '#495057',
    marginBottom: '8px',
  },
  note: {
    fontSize: '12px',
    color: '#6C757D',
    marginTop: '12px',
    lineHeight: '1.6',
  },
  uploadSection: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    gap: '12px',
  },
  fileInput: {
    display: 'none',
  },
  uploadButton: {
    padding: '14px 32px',
    backgroundColor: '#1C1E29',
    color: '#fff',
    border: 'none',
    borderRadius: '6px',
    fontSize: '16px',
    fontWeight: '600',
    cursor: 'pointer',
    transition: 'background-color 0.2s',
    width: '100%',
    maxWidth: '300px',
  },
  uploadButtonDisabled: {
    backgroundColor: '#999',
    cursor: 'not-allowed',
  },
};

export default RegisterMatchModal;