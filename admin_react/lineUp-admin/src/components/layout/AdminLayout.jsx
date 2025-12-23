import { Outlet, useNavigate, useLocation } from 'react-router-dom';
import {
  LayoutGrid,
  Users,
  Calendar,
  Bell,
  BarChart3, // 추가
} from 'lucide-react';
import './AdminLayout.css';

const AdminLayout = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const menuItems = [
    { icon: LayoutGrid, label: '홈 대시보드', path: '/dashboard' },
    { icon: Users, label: '사용자 관리', path: '/users' },
    { icon: Calendar, label: '경기 리스트/등록', path: '/matches' },
    { icon: BarChart3, label: '기록 관리', path: '/stats' }, // 추가
    { icon: Bell, label: '알림 발신', path: '/notifications' },
  ];

  return (
    <div className="admin-container">
      <aside className="admin-sidebar">
        <div className="admin-logo-container">
          <h1 className="admin-logo-text">LineUp Admin</h1>
        </div>

        <nav className="admin-nav">
          {menuItems.map((item) => {
            const Icon = item.icon;
            const isActive = location.pathname === item.path;

            return (
              <button
                key={item.path}
                onClick={() => navigate(item.path)}
                className={`admin-menu-button ${isActive ? 'active' : ''}`}
              >
                <Icon className="admin-menu-icon" />
                <span className="admin-menu-label">{item.label}</span>
              </button>
            );
          })}
        </nav>
      </aside>

      <main className="admin-main">
        <Outlet />
      </main>
    </div>
  );
};

export default AdminLayout;