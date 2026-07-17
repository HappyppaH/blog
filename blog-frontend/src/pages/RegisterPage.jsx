import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function RegisterPage() {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [email, setEmail] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')
  const { register } = useAuth()
  const navigate = useNavigate()

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!username || !password) return setError('请填写用户名和密码')
    if (password.length < 6) return setError('密码长度至少6位')
    setLoading(true)
    setError('')
    try {
      await register(username, password, email)
      alert('注册成功，请登录')
      navigate('/login')
    } catch (err) {
      setError(typeof err === 'string' ? err : '注册失败')
    }
    setLoading(false)
  }

  return (
    <div className="auth-page">
      <div className="card">
        <h2>注册</h2>
        {error && <div className="alert alert-error">{error}</div>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>用户名</label>
            <input value={username} onChange={(e) => setUsername(e.target.value)} placeholder="请输入用户名" />
          </div>
          <div className="form-group">
            <label>密码</label>
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="密码至少6位" />
          </div>
          <div className="form-group">
            <label>邮箱</label>
            <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="选填" />
          </div>
          <button className="btn btn-primary" style={{ width: '100%' }} disabled={loading}>
            {loading ? '注册中...' : '注册'}
          </button>
        </form>
        <div className="footer-link">
          已有账号？<Link to="/login">立即登录</Link>
        </div>
      </div>
    </div>
  )
}
