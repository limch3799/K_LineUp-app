// src/pages/match/components/PlayList.jsx
import { useState, useEffect } from 'react';
import { collection, getDocs, query, orderBy } from 'firebase/firestore';
import { db } from '../../../firebase';

const PlayerList = () => {
  const [players, setPlayers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchPlayers();
  }, []);

  const fetchPlayers = async () => {
    try {
      setLoading(true);
      const q = query(
        collection(db, 'players'),
        orderBy('createdAt', 'desc')
      );
      const querySnapshot = await getDocs(q);
      const playerList = querySnapshot.docs.map((doc) => ({
        id: doc.id,
        ...doc.data(),
      }));
      setPlayers(playerList);
    } catch (error) {
      console.error('선수 목록 조회 실패:', error);
      alert('선수 목록을 불러오는데 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div style={styles.loading}>로딩 중...</div>;
  }

  if (players.length === 0) {
    return <div style={styles.empty}>등록된 선수가 없습니다.</div>;
  }

  return (
    <div style={styles.container}>
      {players.map((player) => (
        <div key={player.id} style={styles.playerCard}>
          <div style={styles.playerInfo}>
            <div style={styles.playerName}>{player.name}</div>
            <div style={styles.playerDetails}>
              {player.currentTeam} · {player.league} · {player.position}
            </div>
          </div>
          <div
            style={{
              ...styles.badge,
              ...(player.isActive ? styles.activeBadge : styles.inactiveBadge),
            }}
          >
            {player.isActive ? '활성' : '비활성'}
          </div>
        </div>
      ))}
    </div>
  );
};

const styles = {
  container: {
    display: 'flex',
    flexDirection: 'column',
    gap: '12px',
  },
  loading: {
    textAlign: 'center',
    padding: '40px',
    color: '#666',
    fontSize: '16px',
  },
  empty: {
    textAlign: 'center',
    padding: '40px',
    color: '#999',
    fontSize: '16px',
  },
  playerCard: {
    display: 'flex',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: '16px',
    backgroundColor: '#F8F9FA',
    borderRadius: '8px',
    border: '1px solid #E9ECEF',
  },
  playerInfo: {
    flex: 1,
  },
  playerName: {
    fontSize: '16px',
    fontWeight: '600',
    color: '#111',
    marginBottom: '4px',
  },
  playerDetails: {
    fontSize: '14px',
    color: '#6C757D',
  },
  badge: {
    padding: '4px 12px',
    borderRadius: '12px',
    fontSize: '12px',
    fontWeight: '600',
  },
  activeBadge: {
    backgroundColor: '#D4EDDA',
    color: '#155724',
  },
  inactiveBadge: {
    backgroundColor: '#F8D7DA',
    color: '#721C24',
  },
};

export default PlayerList;