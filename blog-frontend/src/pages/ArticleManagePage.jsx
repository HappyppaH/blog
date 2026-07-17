import { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import request from '../utils/request'

export default function ArticleManagePage() {
  const [articles, setArticles] = useState([])
  const [loading, setLoading] = useState(true)
  const navigate = useNavigate()

  useEffect(() => {
    (async () => {
      setLoading(true)
      try {
        const data = await request.get('/articles/all')
        setArticles(data || [])
      } catch { /* interceptor handles */ }
      setLoading(false)
    })()
  }, [])

  const handleDelete = async (id, title) => {
    if (!confirm(`确定删除文章「${title}」？`)) return
    try {
      await request.delete(`/articles/${id}`)
      setArticles(articles.filter(a => a.id !== id))
    } catch { /* interceptor handles */ }
  }

  const fmtDate = (d) => d ? new Date(d).toLocaleDateString('zh-CN') : ''

  if (loading) return <div className="loading">加载中...</div>

  return (
    <div>
      <div className="page-header">
        <h1>文章管理</h1>
        <Link to="/admin/articles/new" className="btn btn-primary">写文章</Link>
      </div>

      {articles.length === 0 ? (
        <div className="empty">暂无文章，<Link to="/admin/articles/new" style={{ color: '#3d5a7c' }}>写一篇</Link></div>
      ) : (
        <div className="card" style={{ padding: 0 }}>
          <div className="table-wrap">
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>标题</th>
                  <th>分类</th>
                  <th>创建时间</th>
                  <th>操作</th>
                </tr>
              </thead>
              <tbody>
                {articles.map(a => (
                  <tr key={a.id}>
                    <td>{a.id}</td>
                    <td style={{ maxWidth: 260, overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                      {a.title}
                    </td>
                    <td>{a.category || '-'}</td>
                    <td>{fmtDate(a.createTime)}</td>
                    <td className="actions">
                      <button className="btn btn-ghost btn-sm" onClick={() => navigate(`/admin/articles/${a.id}/edit`)}>
                        编辑
                      </button>
                      <button className="btn btn-danger btn-sm" onClick={() => handleDelete(a.id, a.title)}>
                        删除
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      )}
    </div>
  )
}
