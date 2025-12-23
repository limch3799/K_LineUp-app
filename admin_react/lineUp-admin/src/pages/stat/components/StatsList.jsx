// src/pages/stats/components/StatsList.jsx
import { useState, useEffect } from 'react';
import { collection, getDocs, query, orderBy } from 'firebase/firestore';
import { db } from '../../../firebase';

const StatsList = ({ onEdit }) => {
  const [stats, setStats] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchStats();
  }, []);

  const fetchStats = async () => {
    try {
      setLoading(true);
      const q = query(
        collection(db, 'stats'),
        orderBy('updatedAt', 'desc')
      );
      const querySnapshot = await getDocs(q);
      const statsList = querySnapshot.docs.map((doc) => ({
        id: doc.id,
        ...doc.data(),
      }));
      setStats(statsList);
    } catch (error) {
      console.error('기록 목록 조회 실패:', error);
      alert('기록 목록을 불러오는데 실패했습니다.');
    } finally {
      setLoading(false);
    }
  };

  const formatDate = (timestamp) => {
    if (!timestamp) return '-';
    const date = timestamp.toDate ? timestamp.toDate() : new Date(timestamp);
    return date.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
    });
  };

  if (loading) {
    return <div style={styles.loading}>로딩 중...</div>;
  }

  if (stats.length === 0) {
    return <div style={styles.empty}>등록된 기록이 없습니다.</div>;
  }

  return (
    <div style={styles.tableContainer}>
      <table style={styles.table}>
        <thead>
          <tr style={styles.headerRow}>
            <th style={styles.headerCell}>이름</th>
            <th style={styles.headerCell}>소속팀</th>
            <th style={styles.headerCell}>리그</th>
            <th style={styles.headerCell}>경기수</th>
            <th style={styles.headerCell}>득점</th>
            <th style={styles.headerCell}>도움</th>
            <th style={styles.headerCell}>파울</th>
            <th style={styles.headerCell}>유효슈팅</th>
            <th style={styles.headerCell}>출전시간</th>
            <th style={styles.headerCell}>최근 업데이트</th>
            <th style={styles.headerCell}>관리</th>
          </tr>
        </thead>
        <tbody>
          {stats.map((stat) => (
            <tr key={stat.id} style={styles.row}>
              <td style={styles.cell}>{stat.name}</td>
              <td style={styles.cell}>{stat.currentTeam}</td>
              <td style={styles.cell}>{stat.league}</td>
              <td style={styles.cell}>{stat.matches}</td>
              <td style={styles.cell}>{stat.goals}</td>
              <td style={styles.cell}>{stat.assists}</td>
              <td style={styles.cell}>{stat.fouls}</td>
              <td style={styles.cell}>{stat.shotsOnTarget}</td>
              <td style={styles.cell}>{stat.playTime}분</td>
              <td style={styles.cell}>{formatDate(stat.updatedAt)}</td>
              <td style={styles.cell}>
                <button
                  onClick={() => onEdit(stat)}
                  style={styles.updateButton}
                >
                  업데이트
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

const styles = {
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
  tableContainer: {
    overflowX: 'auto',
    border: '1px solid #DEE2E6',
    borderRadius: '8px',
  },
  table: {
    width: '100%',
    borderCollapse: 'collapse',
    backgroundColor: '#fff',
  },
  headerRow: {
    backgroundColor: '#F8F9FA',
    borderBottom: '2px solid #DEE2E6',
  },
  headerCell: {
    padding: '12px 16px',
    textAlign: 'left',
    fontSize: '14px',
    fontWeight: '700',
    color: '#495057',
    whiteSpace: 'nowrap',
  },
  row: {
    borderBottom: '1px solid #DEE2E6',
    transition: 'background-color 0.2s',
  },
  cell: {
    padding: '12px 16px',
    fontSize: '14px',
    color: '#212529',
    whiteSpace: 'nowrap',
  },
  updateButton: {
    padding: '6px 12px',
    backgroundColor: '#1C1E29',
    color: '#fff',
    border: 'none',
    borderRadius: '4px',
    fontSize: '13px',
    fontWeight: '600',
    cursor: 'pointer',
    transition: 'background-color 0.2s',
  },
};

if (typeof document !== 'undefined') {
  const style = document.createElement('style');
  style.textContent = `
    tr:hover {
      background-color: #F8F9FA !important;
    }
  `;
  document.head.appendChild(style);
}

export default StatsList;