import { useState, useEffect } from 'react'
import request from '../utils/request'

export default function UserManagePage() {
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(true)

  const fetchUsers = async () => {
    setLoading(true)
    try {
      const data = await request.get('/users')
      setUsers(data || [])
    } catch { /* interceptor handles */ }
    setLoading(false)
  }

  useEffect(() => { fetchUsers() }, [])

  const handleDelete = async (id, username) => {
    if (!confirm(`确定删除用户「${username}」？`)) return
    try {
      await request.delete(`/users/${id}`)
      setUsers(users.filter(u => u.id !== id))
    } catch { /* interceptor handles */ }
  }

  if (loading) return <div className="loading">加载中...</div>

  return (
    <div>
      <div className="page-header"><h1>用户管理</h1></div>
      <div className="card" style={{ padding: 0 }}>
        <div className="table-wrap">
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>用户名</th>
                <th>邮箱</th>
                <th>角色</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              {users.map(u => (
                <tr key={u.id}>
                  <td>{u.id}</td>
                  <td>{u.username}</td>
                  <td>{u.email || '-'}</td>
                  <td><span className="btn btn-sm btn-ghost">{u.role || 'user'}</span></td>
                  <td>
                    <button className="btn btn-danger btn-sm" onClick={() => handleDelete(u.id, u.username)}>
                      删除
                    </button>
                  </td>
                </tr>
              ))}
              {users.length === 0 && (
                <tr><td colSpan={5} style={{ textAlign: 'center', color: '#aaa' }}>暂无用户</td></tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </div>
  )
}
