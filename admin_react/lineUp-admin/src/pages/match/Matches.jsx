// src/pages/match/Matches.jsx
import { useState } from 'react';
import RegisterPlayerModal from './components/RegisterPlayerModal';
import RegisterMatchModal from './components/RegisterMatchModal';
import MatchList from './components/MatchList';
import MatchDetailModal from './components/MatchDetailModal';

const Matches = () => {
  const [isPlayerModalOpen, setIsPlayerModalOpen] = useState(false);
  const [isMatchModalOpen, setIsMatchModalOpen] = useState(false);
  const [isDetailModalOpen, setIsDetailModalOpen] = useState(false);
  const [selectedMatch, setSelectedMatch] = useState(null);
  const [refreshKey, setRefreshKey] = useState(0);

  const handleMatchClick = (match) => {
    setSelectedMatch(match);
    setIsDetailModalOpen(true);
  };

  const handleMatchRegistered = () => {
    // 경기 목록 새로고침
    setRefreshKey(prev => prev + 1);
  };

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h1 style={styles.title}>경기 리스트/등록</h1>
        <div style={styles.buttonGroup}>
          <button
            style={styles.addButton}
            onClick={() => setIsPlayerModalOpen(true)}
          >
            + 선수 등록
          </button>
          <button 
            style={styles.addButton}
            onClick={() => setIsMatchModalOpen(true)}
          >
            + 경기 등록
          </button>
        </div>
      </div>

      <div style={styles.content}>
        <MatchList 
          key={refreshKey}
          onMatchClick={handleMatchClick} 
        />
      </div>

      {/* 선수 등록 모달 */}
      <RegisterPlayerModal
        isOpen={isPlayerModalOpen}
        onClose={() => setIsPlayerModalOpen(false)}
      />

      {/* 경기 등록 모달 */}
      <RegisterMatchModal
        isOpen={isMatchModalOpen}
        onClose={() => setIsMatchModalOpen(false)}
        onComplete={handleMatchRegistered}
      />

      {/* 경기 상세 모달 */}
      <MatchDetailModal
        isOpen={isDetailModalOpen}
        onClose={() => {
          setIsDetailModalOpen(false);
          setSelectedMatch(null);
        }}
        match={selectedMatch}
      />
    </div>
  );
};

const styles = {
  container: {
    padding: '24px',
  },
  header: {
    marginBottom: '24px',
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  title: {
    fontSize: '24px',
    fontWeight: '700',
    color: '#111',
    margin: 0,
  },
  buttonGroup: {
    display: 'flex',
    gap: '12px',
  },
  addButton: {
    padding: '10px 20px',
    backgroundColor: '#1C1E29',
    color: '#fff',
    border: 'none',
    borderRadius: '6px',
    fontSize: '14px',
    fontWeight: '600',
    cursor: 'pointer',
    transition: 'background-color 0.2s',
  },
  content: {
    backgroundColor: '#fff',
    borderRadius: '8px',
    padding: '24px',
    minHeight: '400px',
  },
};

export default Matches;