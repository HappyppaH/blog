import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function Navbar() {
  const { user, logout, isAdmin } = useAuth()
  const navigate = useNavigate()

  const handleLogout = () => {
    logout()
    navigate('/')
  }

  return (
    <nav className="navbar">
      <Link to="/" className="logo">Blog</Link>
      <div className="nav-links">
        <Link to="/">首页</Link>
        {user ? (
          <>
            <Link to="/admin">后台</Link>
            <span className="username">{user.username}</span>
            <button onClick={handleLogout}>退出</button>
          </>
        ) : (
          <>
            <Link to="/login">登录</Link>
            <Link to="/register">注册</Link>
          </>
        )}
      </div>
    </nav>
  )
}
