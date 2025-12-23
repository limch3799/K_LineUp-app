import { useState } from 'react';
import { collection, addDoc, serverTimestamp, query, orderBy, limit, getDocs } from 'firebase/firestore';
import { db } from '../../../firebase';

const RegisterForm = ({ onComplete }) => {
  const [formData, setFormData] = useState({
    name: '',
    currentTeam: '',
    league: '',
    position: '',
    isActive: true,
  });
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  // 다음 playerId 가져오기
  const getNextPlayerId = async () => {
    try {
      // playerId 기준으로 내림차순 정렬해서 가장 큰 값 가져오기
      const q = query(
        collection(db, 'players'),
        orderBy('playerId', 'desc'),
        limit(1)
      );
      
      const snapshot = await getDocs(q);
      
      if (snapshot.empty) {
        return 1; // 첫 번째 선수
      }
      
      const lastPlayer = snapshot.docs[0].data();
      return (lastPlayer.playerId || 0) + 1;
    } catch (error) {
      console.error('playerId 조회 실패:', error);
      // 에러 시 현재 시간 기반 ID 생성 (충돌 방지)
      return Date.now();
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // 유효성 검사
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
    if (!formData.position.trim()) {
      alert('포지션을 입력해주세요.');
      return;
    }

    try {
      setLoading(true);

      // 다음 playerId 가져오기
      const nextPlayerId = await getNextPlayerId();

      // 문서 추가 (Firestore 자동 ID + playerId 필드)
      await addDoc(collection(db, 'players'), {
        playerId: nextPlayerId, // 숫자 ID 추가
        name: formData.name.trim(),
        currentTeam: formData.currentTeam.trim(),
        league: formData.league.trim(),
        position: formData.position.trim(),
        photoUrl: '',
        isActive: formData.isActive,
        subscriberCount: 0,
        createdAt: serverTimestamp(),
        updatedAt: serverTimestamp(),
      });

      alert(`선수가 등록되었습니다. (ID: ${nextPlayerId})`);
      
      // 폼 초기화
      setFormData({
        name: '',
        currentTeam: '',
        league: '',
        position: '',
        isActive: true,
      });

      // 리스트 화면으로 돌아가기
      onComplete();
    } catch (error) {
      console.error('선수 등록 실패:', error);
      alert('선수 등록에 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit} style={styles.form}>
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

      <div style={styles.formGroup}>
        <label style={styles.label}>포지션 *</label>
        <input
          type="text"
          name="position"
          value={formData.position}
          onChange={handleChange}
          placeholder="예: FW, MF, DF, GK"
          style={styles.input}
          disabled={loading}
        />
      </div>

      <div style={styles.checkboxGroup}>
        <label style={styles.checkboxLabel}>
          <input
            type="checkbox"
            name="isActive"
            checked={formData.isActive}
            onChange={handleChange}
            style={styles.checkbox}
            disabled={loading}
          />
          <span>활성 상태</span>
        </label>
      </div>

      <button
        type="submit"
        style={{
          ...styles.submitButton,
          ...(loading && styles.submitButtonDisabled),
        }}
        disabled={loading}
      >
        {loading ? '등록 중...' : '선수 등록'}
      </button>
    </form>
  );
};

const styles = {
  form: {
    display: 'flex',
    flexDirection: 'column',
    gap: '20px',
  },
  formGroup: {
    display: 'flex',
    flexDirection: 'column',
    gap: '8px',
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
  checkboxGroup: {
    display: 'flex',
    alignItems: 'center',
    gap: '8px',
  },
  checkboxLabel: {
    display: 'flex',
    alignItems: 'center',
    gap: '8px',
    fontSize: '14px',
    color: '#333',
    cursor: 'pointer',
  },
  checkbox: {
    width: '18px',
    height: '18px',
    cursor: 'pointer',
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

export default RegisterForm;