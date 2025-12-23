// src/pages/stats/Stats.jsx
import { useState } from 'react';
import RegisterStatModal from './components/RegisterStatModal';
import StatsList from './components/StatsList';

const Stats = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [editingStat, setEditingStat] = useState(null);
  const [refreshKey, setRefreshKey] = useState(0);

  const handleStatRegistered = () => {
    setRefreshKey(prev => prev + 1);
    setIsModalOpen(false);
    setEditingStat(null);
  };

  const handleEdit = (stat) => {
    setEditingStat(stat);
    setIsModalOpen(true);
  };

  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h1 style={styles.title}>기록 관리</h1>
        <button
          style={styles.addButton}
          onClick={() => {
            setEditingStat(null);
            setIsModalOpen(true);
          }}
        >
          + 선수 등록
        </button>
      </div>

      <div style={styles.content}>
        <StatsList 
          key={refreshKey}
          onEdit={handleEdit}
        />
      </div>

      <RegisterStatModal
        isOpen={isModalOpen}
        onClose={() => {
          setIsModalOpen(false);
          setEditingStat(null);
        }}
        onComplete={handleStatRegistered}
        editData={editingStat}
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

export default Stats;