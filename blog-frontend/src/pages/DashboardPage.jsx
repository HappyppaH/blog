import { Link } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function DashboardPage() {
  const { isAdmin } = useAuth()

  const cards = [
    { icon: '📝', label: '文章管理', desc: '发布、编辑、删除文章', link: '/admin/articles' },
    { icon: '✍️', label: '写文章', desc: '撰写一篇新文章', link: '/admin/articles/new' },
  ]
  if (isAdmin()) {
    cards.push({ icon: '👥', label: '用户管理', desc: '查看和管理所有用户', link: '/admin/users' })
  }

  return (
    <div>
      <div className="page-header"><h1>管理后台</h1></div>
      <div className="dashboard-grid">
        {cards.map(c => (
          <Link to={c.link} key={c.link} className="dashboard-card">
            <div className="icon">{c.icon}</div>
            <div className="label">{c.label}</div>
            <div className="desc">{c.desc}</div>
          </Link>
        ))}
      </div>
    </div>
  )
}
