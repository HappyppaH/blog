import { useState, useEffect } from 'react'
import { useParams, Link } from 'react-router-dom'
import request from '../utils/request'
import { useAuth } from '../context/AuthContext'

export default function ArticleDetailPage() {
  const { id } = useParams()
  const { token, user } = useAuth()
  const [article, setArticle] = useState(null)
  const [comments, setComments] = useState([])
  const [loading, setLoading] = useState(true)
  const [commentText, setCommentText] = useState('')

  useEffect(() => {
    (async () => {
      setLoading(true)
      try {
        const data = await request.get(`/articles/${id}/detail`)
        setArticle(data.article)
        setComments(data.comments || [])
      } catch { /* interceptor handles */ }
      setLoading(false)
    })()
  }, [id])

  const handleComment = async () => {
    if (!commentText.trim()) return
    if (!token) return alert('请先登录')
    try {
      await request.post('/comments', {
        articleId: Number(id),
        username: user.username,
        content: commentText.trim()
      })
      setCommentText('')
      // 刷新评论
      const res = await request.get('/comments', { params: { articleId: id } })
      setComments(res || [])
    } catch { /* interceptor handles */ }
  }

  const handleDeleteComment = async (commentId) => {
    if (!confirm('确定删除此评论？')) return
    try {
      await request.delete(`/comments/${commentId}`)
      setComments(comments.filter(c => c.id !== commentId))
    } catch { /* interceptor handles */ }
  }

  const fmtDate = (d) => {
    if (!d) return ''
    return new Date(d).toLocaleString('zh-CN')
  }

  if (loading) return <div className="loading">加载中...</div>
  if (!article) return <div className="empty">文章不存在</div>

  return (
    <div className="article-detail">
      <Link to="/" style={{ fontSize: 13, color: '#3d5a7c', marginBottom: 16, display: 'inline-block' }}>
        &larr; 返回首页
      </Link>
      <h1>{article.title}</h1>
      <div className="meta">
        {article.category && <span>📂 {article.category}</span>}
        <span>🕒 {fmtDate(article.createTime)}</span>
        {article.updateTime && article.updateTime !== article.createTime && (
          <span>✏️ 更新于 {fmtDate(article.updateTime)}</span>
        )}
      </div>
      <div className="content" style={{ whiteSpace: 'pre-wrap' }}>{article.content}</div>

      <div className="comment-section">
        <h3>评论 ({comments.length})</h3>

        <div className="comment-form">
          <input
            placeholder={token ? '写下你的评论...' : '请先登录后再评论'}
            value={commentText}
            onChange={(e) => setCommentText(e.target.value)}
            onKeyDown={(e) => e.key === 'Enter' && handleComment()}
            disabled={!token}
          />
          <button className="btn btn-primary btn-sm" onClick={handleComment} disabled={!token}>
            发表
          </button>
        </div>

        {comments.length === 0 ? (
          <div className="empty" style={{ padding: 20 }}>暂无评论</div>
        ) : (
          comments.map(c => (
            <div key={c.id} className="comment-item">
              <div className="comment-header">
                <span className="comment-author">{c.username}</span>
                <span className="comment-time">{fmtDate(c.createTime)}</span>
              </div>
              <div className="comment-content" style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                <span>{c.content}</span>
                {user?.username === c.username && (
                  <button className="btn btn-danger btn-sm" onClick={() => handleDeleteComment(c.id)}>删除</button>
                )}
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  )
}
