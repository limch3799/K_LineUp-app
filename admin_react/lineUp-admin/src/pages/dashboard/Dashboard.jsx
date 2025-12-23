//src/pages/dashboard/Dashboard.jsx
import { useEffect } from 'react';
import { collection, getDocs } from 'firebase/firestore';
import { db } from '../../firebase';

const Dashboard = () => {
  useEffect(() => {
    const testFirebase = async () => {
      try {
        const querySnapshot = await getDocs(collection(db, 'players'));
        console.log('✅ Firebase 연결 성공!', querySnapshot.size);
      } catch (error) {
        console.error('❌ Firebase 연결 실패:', error);
      }
    };
    
    testFirebase();
  }, []);
  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h1 style={styles.title}>홈 대시보드</h1>
      </div>

      <div style={styles.content}>
        {/* 통계 카드들 */}
        <div style={styles.statsGrid}>
          <div style={styles.statCard}>
            <p style={styles.statLabel}>전체 사용자</p>
            <p style={styles.statValue}>1,234</p>
          </div>
          <div style={styles.statCard}>
            <p style={styles.statLabel}>오늘 경기</p>
            <p style={styles.statValue}>5</p>
          </div>
          <div style={styles.statCard}>
            <p style={styles.statLabel}>발송된 알림</p>
            <p style={styles.statValue}>890</p>
          </div>
          <div style={styles.statCard}>
            <p style={styles.statLabel}>활성 선수</p>
            <p style={styles.statValue}>28</p>
          </div>
        </div>
      </div>
    </div>
  );
};

const styles = {
  container: {
    padding: '24px',
  },
  header: {
    marginBottom: '24px',
  },
  title: {
    fontSize: '24px',
    fontWeight: '700',
    color: '#111',
    margin: 0,
  },
  content: {
    backgroundColor: '#fff',
    borderRadius: '8px',
    padding: '24px',
  },
  statsGrid: {
    display: 'grid',
    gridTemplateColumns: 'repeat(4, 1fr)',
    gap: '16px',
  },
  statCard: {
    backgroundColor: '#F8F9FA',
    padding: '20px',
    borderRadius: '8px',
  },
  statLabel: {
    fontSize: '14px',
    color: '#6C757D',
    margin: '0 0 8px 0',
  },
  statValue: {
    fontSize: '32px',
    fontWeight: '700',
    color: '#111',
    margin: 0,
  },
};

export default Dashboard;