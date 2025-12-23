// src/pages/match/components/RegisterPlayerModal.jsx
import { useState } from 'react';
import PlayerList from './PlayerList';
import RegisterForm from './RegisterForm';

const RegisterPlayerModal = ({ isOpen, onClose }) => {
  const [view, setView] = useState('list'); // 'list' or 'form'

  if (!isOpen) return null;

  const handleRegisterComplete = () => {
    setView('list'); // 등록 완료 후 리스트로 돌아감
  };

  const handleClose = () => {
    setView('list'); // 모달 닫을 때 리스트로 초기화
    onClose();
  };

  return (
    <div style={styles.overlay} onClick={handleClose}>
      <div style={styles.modal} onClick={(e) => e.stopPropagation()}>
        {/* 헤더 */}
        <div style={styles.header}>
          <h2 style={styles.title}>
            {view === 'list' ? '현재 등록된 선수' : '선수 등록'}
          </h2>
          <div style={styles.headerButtons}>
            {view === 'list' && (
              <button
                style={styles.registerButton}
                onClick={() => setView('form')}
              >
                + 선수 등록
              </button>
            )}
            <button style={styles.closeButton} onClick={handleClose}>
              ✕
            </button>
          </div>
        </div>

        {/* 내용 */}
        <div style={styles.content}>
          {view === 'list' ? (
            <PlayerList />
          ) : (
            <RegisterForm onComplete={handleRegisterComplete} />
          )}
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
    maxWidth: '600px',
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
  headerButtons: {
    display: 'flex',
    gap: '12px',
    alignItems: 'center',
  },
  registerButton: {
    padding: '8px 16px',
    backgroundColor: '#1C1E29',
    color: '#fff',
    border: 'none',
    borderRadius: '6px',
    fontSize: '14px',
    fontWeight: '600',
    cursor: 'pointer',
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
};

export default RegisterPlayerModal;