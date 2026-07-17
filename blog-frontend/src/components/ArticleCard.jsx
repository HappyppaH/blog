import { Link } from 'react-router-dom'

export default function ArticleCard({ article }) {
  const fmtDate = (d) => {
    if (!d) return ''
    return new Date(d).toLocaleDateString('zh-CN', { year: 'numeric', month: '2-digit', day: '2-digit' })
  }

  return (
    <div className="card" style={{ padding: '20px 24px' }}>
      <Link to={`/article/${article.id}`}>
        <h3 style={{ fontSize: 18, color: '#2b3a4f', marginBottom: 8 }}>{article.title}</h3>
      </Link>
      {article.summary && (
        <p style={{ fontSize: 13, color: '#777', marginBottom: 10 }}>{article.summary}</p>
      )}
      <div style={{ display: 'flex', gap: 16, fontSize: 12, color: '#aaa' }}>
        {article.category && <span>📂 {article.category}</span>}
        <span>🕒 {fmtDate(article.createTime)}</span>
      </div>
    </div>
  )
}
