// src/pages/notification/Notifications.jsx
const Notifications = () => {
  return (
    <div style={styles.container}>
      <div style={styles.header}>
        <h1 style={styles.title}>알림 발신</h1>
      </div>

      <div style={styles.content}>
        <p style={styles.placeholder}>알림 발송 기능이 여기에 표시됩니다.</p>
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
    minHeight: '400px',
  },
  placeholder: {
    color: '#6C757D',
    fontSize: '16px',
  },
};

export default Notifications;