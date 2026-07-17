import { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import request from '../utils/request'

export default function ArticleFormPage() {
  const { id } = useParams()
  const navigate = useNavigate()
  const isEdit = Boolean(id)

  const [form, setForm] = useState({ title: '', content: '', summary: '', category: '' })
  const [loading, setLoading] = useState(false)

  useEffect(() => {
    if (isEdit) {
      (async () => {
        try {
          const data = await request.get(`/articles/${id}`)
          setForm({ title: data.title, content: data.content, summary: data.summary || '', category: data.category || '' })
        } catch { navigate('/admin/articles') }
      })()
    }
  }, [id])

  const handleSubmit = async (e) => {
    e.preventDefault()
    if (!form.title || !form.content) return alert('标题和内容不能为空')
    setLoading(true)
    try {
      if (isEdit) {
        await request.put(`/articles/${id}`, form)
      } else {
        await request.post('/articles', form)
      }
      navigate('/admin/articles')
    } catch { /* interceptor handles */ }
    setLoading(false)
  }

  const update = (field) => (e) => setForm({ ...form, [field]: e.target.value })

  return (
    <div>
      <div className="page-header">
        <h1>{isEdit ? '编辑文章' : '写文章'}</h1>
        <button className="btn btn-ghost" onClick={() => navigate('/admin/articles')}>返回列表</button>
      </div>

      <div className="card">
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>标题 *</label>
            <input value={form.title} onChange={update('title')} placeholder="文章标题" />
          </div>
          <div className="form-group">
            <label>分类</label>
            <select value={form.category} onChange={update('category')}>
              <option value="">选择分类</option>
              <option value="技术">技术</option>
              <option value="生活">生活</option>
              <option value="随笔">随笔</option>
            </select>
          </div>
          <div className="form-group">
            <label>摘要</label>
            <input value={form.summary} onChange={update('summary')} placeholder="一句话描述文章内容（可选）" />
          </div>
          <div className="form-group">
            <label>内容 *</label>
            <textarea value={form.content} onChange={update('content')} placeholder="文章正文..." />
          </div>
          <button className="btn btn-primary" disabled={loading}>
            {loading ? '保存中...' : (isEdit ? '更新文章' : '发布文章')}
          </button>
        </form>
      </div>
    </div>
  )
}
