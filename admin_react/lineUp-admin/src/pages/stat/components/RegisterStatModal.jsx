// src/pages/stats/components/RegisterStatModal.jsx
import { useState, useEffect } from 'react';
import { X } from 'lucide-react';
import { collection, addDoc, doc, updateDoc, serverTimestamp } from 'firebase/firestore';
import { db } from '../../../firebase';

const RegisterStatModal = ({ isOpen, onClose, onComplete, editData }) => {
  const [formData, setFormData] = useState({
    name: '',
    currentTeam: '',
    league: '',
    matches: 0,
    goals: 0,
    assists: 0,
    fouls: 0,
    shotsOnTarget: 0,
    playTime: 0,
  });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (editData) {
      setFormData({
        name: editData.name || '',
        currentTeam: editData.currentTeam || '',
        league: editData.league || '',
        matches: editData.matches || 0,
        goals: editData.goals || 0,
        assists: editData.assists || 0,
        fouls: editData.fouls || 0,
        shotsOnTarget: editData.shotsOnTarget || 0,
        playTime: editData.playTime || 0,
      });
    } else {
      setFormData({
        name: '',
        currentTeam: '',
        league: '',
        matches: 0,
        goals: 0,
        assists: 0,
        fouls: 0,
        shotsOnTarget: 0,
        playTime: 0,
      });
    }
  }, [editData, isOpen]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: ['matches', 'goals', 'assists', 'fouls', 'shotsOnTarget', 'playTime'].includes(name)
        ? Number(value)
        : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.name.trim()) {
      alert('선수 이름을 입력해주세요.');
      return;
    }
    if (!formData.currentTeam.trim()) {
      alert('소속 팀을 입력해주세요.');
      return;
    }
    if (!formData.league.trim()) {
      alert('리그를 입력해주세요.');
      return;
    }

    try {
      setLoading(true);

      const statData = {
        name: formData.name.trim(),
        currentTeam: formData.currentTeam.trim(),
        league: formData.league.trim(),
        matches: formData.matches,
        goals: formData.goals,
        assists: formData.assists,
        fouls: formData.fouls,
        shotsOnTarget: formData.shotsOnTarget,
        playTime: formData.playTime,
        updatedAt: serverTimestamp(),
      };

      if (editData) {
        await updateDoc(doc(db, 'stats', editData.id), statData);
        alert('기록이 업데이트되었습니다.');
      } else {
        await addDoc(collection(db, 'stats'), {
          ...statData,
          createdAt: serverTimestamp(),
        });
        alert('기록이 등록되었습니다.');
      }

      onComplete();
    } catch (error) {
      console.error('기록 저장 실패:', error);
      alert('기록 저장에 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };

  if (!isOpen) return null;

  return (
    <div style={styles.modalOverlay} onClick={onClose}>
      <div style={styles.modalContent} onClick={(e) => e.stopPropagation()}>
        <div style={styles.modalHeader}>
          <h2 style={styles.modalTitle}>
            {editData ? '기록 수정' : '선수 등록'}
          </h2>
          <button onClick={onClose} style={styles.closeButton}>
            <X size={24} />
          </button>
        </div>

        <div style={styles.formContainer}>
          <div style={styles.formGroup}>
            <label style={styles.label}>선수 이름 *</label>
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              placeholder="예: 손흥민"
              style={styles.input}
              disabled={loading}
            />
          </div>

          <div style={styles.formGroup}>
            <label style={styles.label}>소속 팀 *</label>
            <input
              type="text"
              name="currentTeam"
              value={formData.currentTeam}
              onChange={handleChange}
              placeholder="예: 토트넘"
              style={styles.input}
              disabled={loading}
            />
          </div>

          <div style={styles.formGroup}>
            <label style={styles.label}>리그 *</label>
            <input
              type="text"
              name="league"
              value={formData.league}
              onChange={handleChange}
              placeholder="예: 프리미어리그"
              style={styles.input}
              disabled={loading}
            />
          </div>

          <div style={styles.formRow}>
            <div style={styles.formGroup}>
              <label style={styles.label}>경기 수</label>
              <input
                type="number"
                name="matches"
                value={formData.matches}
                onChange={handleChange}
                min="0"
                style={styles.input}
                disabled={loading}
              />
            </div>

            <div style={styles.formGroup}>
              <label style={styles.label}>득점</label>
              <input
                type="number"
                name="goals"
                value={formData.goals}
                onChange={handleChange}
                min="0"
                style={styles.input}
                disabled={loading}
              />
            </div>
          </div>

          <div style={styles.formRow}>
            <div style={styles.formGroup}>
              <label style={styles.label}>도움</label>
              <input
                type="number"
                name="assists"
                value={formData.assists}
                onChange={handleChange}
                min="0"
                style={styles.input}
                disabled={loading}
              />
            </div>

            <div style={styles.formGroup}>
              <label style={styles.label}>파울</label>
              <input
                type="number"
                name="fouls"
                value={formData.fouls}
                onChange={handleChange}
                min="0"
                style={styles.input}
                disabled={loading}
              />
            </div>
          </div>

          <div style={styles.formRow}>
            <div style={styles.formGroup}>
              <label style={styles.label}>유효 슈팅</label>
              <input
                type="number"
                name="shotsOnTarget"
                value={formData.shotsOnTarget}
                onChange={handleChange}
                min="0"
                style={styles.input}
                disabled={loading}
              />
            </div>

            <div style={styles.formGroup}>
              <label style={styles.label}>출전 시간 (분)</label>
              <input
                type="number"
                name="playTime"
                value={formData.playTime}
                onChange={handleChange}
                min="0"
                style={styles.input}
                disabled={loading}
              />
            </div>
          </div>

          <button
            onClick={handleSubmit}
            style={{
              ...styles.submitButton,
              ...(loading && styles.submitButtonDisabled),
            }}
            disabled={loading}
          >
            {loading ? (editData ? '수정 중...' : '등록 중...') : (editData ? '수정하기' : '등록하기')}
          </button>
        </div>
      </div>
    </div>
  );
};

const styles = {
  modalOverlay: {
    position: 'fixed',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    zIndex: 1000,
  },
  modalContent: {
    backgroundColor: '#fff',
    borderRadius: '12px',
    width: '90%',
    maxWidth: '600px',
    maxHeight: '90vh',
    overflow: 'auto',
    boxShadow: '0 20px 25px -5px rgba(0, 0, 0, 0.1)',
  },
  modalHeader: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '24px',
    borderBottom: '1px solid #DEE2E6',
  },
  modalTitle: {
    fontSize: '20px',
    fontWeight: '700',
    color: '#111',
    margin: 0,
  },
  closeButton: {
    backgroundColor: 'transparent',
    border: 'none',
    cursor: 'pointer',
    padding: '4px',
    color: '#666',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  formContainer: {
    padding: '24px',
    display: 'flex',
    flexDirection: 'column',
    gap: '20px',
  },
  formGroup: {
    display: 'flex',
    flexDirection: 'column',
    gap: '8px',
    flex: 1,
  },
  formRow: {
    display: 'flex',
    gap: '16px',
  },
  label: {
    fontSize: '14px',
    fontWeight: '600',
    color: '#333',
  },
  input: {
    padding: '12px',
    border: '1px solid #ddd',
    borderRadius: '6px',
    fontSize: '14px',
    transition: 'border-color 0.2s',
    outline: 'none',
  },
  submitButton: {
    padding: '14px',
    backgroundColor: '#1C1E29',
    color: '#fff',
    border: 'none',
    borderRadius: '6px',
    fontSize: '16px',
    fontWeight: '600',
    cursor: 'pointer',
    marginTop: '12px',
    transition: 'background-color 0.2s',
  },
  submitButtonDisabled: {
    backgroundColor: '#999',
    cursor: 'not-allowed',
  },
};

export default RegisterStatModal;