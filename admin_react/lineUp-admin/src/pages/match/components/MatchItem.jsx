import { doc, updateDoc } from 'firebase/firestore';
import { db } from '../../../firebase';

const MatchItem = ({ match, onMatchClick, getPlayerNames, onRefresh }) => {
  const handleStatusChange = async (matchId, newStatus) => {
    try {
      await updateDoc(doc(db, 'matches', matchId), {
        status: newStatus,
        updatedAt: new Date(),
      });

      onRefresh();
    } catch (error) {
      console.error('상태 변경 실패:', error);
      alert('상태 변경에 실패했습니다.');
    }
  };

  // ⬇ 날짜 포맷 + 요일 추가 기능
  const formatMatchTime = (timestamp) => {
    if (!timestamp) return '';

    const date = timestamp.toDate ? timestamp.toDate() : new Date(timestamp);

    const week = ['일', '월', '화', '수', '목', '금', '토'];
    const dayName = week[date.getDay()]; // 요일 가져오기

    // 날짜 부분
    const datePart = date.toLocaleDateString('ko-KR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
    });

    // 시간 부분
    const timePart = date.toLocaleTimeString('ko-KR', {
      hour: '2-digit',
      minute: '2-digit',
      hour12: true,
    });

    return `${datePart} (${dayName}) ${timePart}`;
  };

  const getStatusStyle = (status) => {
    const statusStyles = {
      scheduled: { backgroundColor: '#D1ECF1', color: '#0C5460' },
      lineup_announced: { backgroundColor: '#FFF3CD', color: '#856404' },
      finished: { backgroundColor: '#D4EDDA', color: '#155724' },
    };
    return statusStyles[status] || {};
  };

  // 경기장 5글자 + ... 처리
  const trimStadiumName = (name) => {
    if (!name) return "-";
    return name.length > 5 ? name.slice(0, 5) + "..." : name;
  };

  return (
    <tr
      style={styles.row}
      onClick={() => onMatchClick(match)}
    >
      <td style={styles.cell}>{formatMatchTime(match.matchTime)}</td>
      <td style={styles.cell}>{match.competition}</td>
      <td style={styles.cell}>{match.homeTeam}</td>
      <td style={styles.cell}>{match.awayTeam}</td>

      {/* 경기장 (5글자 + ...) */}
      <td style={styles.cell}>
        {trimStadiumName(match.stadium)}
      </td>

      <td style={styles.cell}>{getPlayerNames(match.koreanPlayerIds)}</td>

      <td style={styles.cell} onClick={(e) => e.stopPropagation()}>
        <select
          value={match.status}
          onChange={(e) => {
            e.stopPropagation();
            handleStatusChange(match.id, e.target.value);
          }}
          style={{
            ...styles.statusSelect,
            ...getStatusStyle(match.status),
          }}
          onClick={(e) => e.stopPropagation()}
        >
          <option value="scheduled">예정</option>
          <option value="lineup_announced">라인업 발표</option>
          <option value="finished">종료</option>
        </select>
      </td>
    </tr>
  );
};

const styles = {
  row: {
    borderBottom: '1px solid #DEE2E6',
    cursor: 'pointer',
    transition: 'background-color 0.2s',
  },
  cell: {
    padding: '12px 16px',
    fontSize: '14px',
    color: '#212529',
    whiteSpace: 'nowrap',
  },
  statusSelect: {
    padding: '6px 12px',
    borderRadius: '12px',
    fontSize: '13px',
    fontWeight: '600',
    border: 'none',
    cursor: 'pointer',
    outline: 'none',
  },
};

// hover 스타일 추가
if (typeof document !== 'undefined') {
  const style = document.createElement('style');
  style.textContent = `
    tr:hover {
      background-color: #F8F9FA !important;
    }
  `;
  document.head.appendChild(style);
}

export default MatchItem;
