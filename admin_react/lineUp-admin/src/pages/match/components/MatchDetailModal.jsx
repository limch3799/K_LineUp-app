// src/pages/match/components/MatchDetailModal.jsx
const MatchDetailModal = ({ isOpen, onClose, match }) => {
  if (!isOpen || !match) return null;

  const formatMatchTime = (timestamp) => {
    if (!timestamp) return '';
    const date = timestamp.toDate ? timestamp.toDate() : new Date(timestamp);
    return date.toLocaleString('ko-KR', {
      year: 'numeric',
      month: '2-digit',
      day: '2-digit',
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  const getStatusLabel = (status) => {
    const statusMap = {
      scheduled: '예정',
      lineup_announced: '라인업 발표',
      finished: '종료',
    };
    return statusMap[status] || status;
  };

  return (
    <div style={styles.overlay} onClick={onClose}>
      <div style={styles.modal} onClick={(e) => e.stopPropagation()}>
        {/* 헤더 */}
        <div style={styles.header}>
          <h2 style={styles.title}>경기 상세 정보</h2>
          <button style={styles.closeButton} onClick={onClose}>
            ✕
          </button>
        </div>

        {/* 내용 */}
        <div style={styles.content}>
          <div style={styles.matchTitle}>
            {match.homeTeam} vs {match.awayTeam}
          </div>

          <div style={styles.infoSection}>
            <div style={styles.infoRow}>
              <span style={styles.infoLabel}>대회:</span>
              <span style={styles.infoValue}>{match.competition}</span>
            </div>
            
            <div style={styles.infoRow}>
              <span style={styles.infoLabel}>경기 시간:</span>
              <span style={styles.infoValue}>
                {formatMatchTime(match.matchTime)}
              </span>
            </div>

            {match.stadium && (
              <div style={styles.infoRow}>
                <span style={styles.infoLabel}>경기장:</span>
                <span style={styles.infoValue}>{match.stadium}</span>
              </div>
            )}

            <div style={styles.infoRow}>
              <span style={styles.infoLabel}>상태:</span>
              <span style={styles.infoValue}>{getStatusLabel(match.status)}</span>
            </div>

            {match.koreanPlayerIds && match.koreanPlayerIds.length > 0 && (
              <div style={styles.infoRow}>
                <span style={styles.infoLabel}>한국인 선수:</span>
                <span style={styles.infoValue}>
                  {match.koreanPlayerIds.length}명 (ID: {match.koreanPlayerIds.join(', ')})
                </span>
              </div>
            )}
          </div>

          <div style={styles.placeholder}>
            라인업 및 추가 정보는 추후 구현 예정입니다.
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
  matchTitle: {
    fontSize: '24px',
    fontWeight: '700',
    color: '#111',
    marginBottom: '24px',
    textAlign: 'center',
  },
  infoSection: {
    display: 'flex',
    flexDirection: 'column',
    gap: '16px',
    marginBottom: '24px',
  },
  infoRow: {
    display: 'flex',
    alignItems: 'flex-start',
    gap: '12px',
  },
  infoLabel: {
    fontSize: '14px',
    fontWeight: '600',
    color: '#6C757D',
    minWidth: '100px',
  },
  infoValue: {
    fontSize: '14px',
    color: '#111',
    flex: 1,
  },
  placeholder: {
    textAlign: 'center',
    padding: '40px 20px',
    color: '#999',
    fontSize: '14px',
    backgroundColor: '#F8F9FA',
    borderRadius: '8px',
  },
};

export default MatchDetailModal;