import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function LoginPage() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const { login } = useAuth()
  const navigate = useNavigate()

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!username || !password) return setError('请填写用户名和密码')
    setLoading(true)
    setError('')
    try {
      await login(username, password)
      navigate('/admin')
    } catch (err) {
      setError(typeof err === 'string' ? err : '登录失败，请检查用户名和密码')
    }
    setLoading(false)
  }

  return (
    <div className="auth-page">
      <div className="card">
        <h2>登录</h2>
        {error && <div className="alert alert-error">{error}</div>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>用户名</label>
            <input value={username} onChange={(e) => setUsername(e.target.value)} placeholder="请输入用户名" />
          </div>
          <div className="form-group">
            <label>密码</label>
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="请输入密码" />
          </div>
          <button className="btn btn-primary" style={{ width: '100%' }} disabled={loading}>
            {loading ? '登录中...' : '登录'}
          </button>
        </form>
        <div className="footer-link">
          还没有账号？<Link to="/register">立即注册</Link>
        </div>
      </div>
    </div>
  )
}
