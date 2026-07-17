import { useState, useEffect, useCallback } from 'react'
import { useSearchParams } from 'react-router-dom'
import request from '../utils/request'
import ArticleCard from '../components/ArticleCard'
import Pagination from '../components/Pagination'

export default function HomePage() {
  const [searchParams, setSearchParams] = useSearchParams()
  const [articles, setArticles] = useState([])
  const [pageInfo, setPageInfo] = useState({ current: 1, pages: 1, total: 0 })
  const [loading, setLoading] = useState(true)

  const keyword = searchParams.get('keyword') || ''
  const category = searchParams.get('category') || ''
  const page = parseInt(searchParams.get('page')) || 1

  const fetchArticles = useCallback(async () => {
    setLoading(true)
    try {
      const params = { page, size: 5 }
      if (keyword) params.keyword = keyword
      if (category) params.category = category
      const data = await request.get('/articles', { params })
      setArticles(data.records || [])
      setPageInfo({ current: data.current, pages: data.pages, total: data.total })
    } catch { /* error handled by interceptor */ }
    setLoading(false)
  }, [page, keyword, category])

  useEffect(() => { fetchArticles() }, [fetchArticles])

  const updateParams = (updates) => {
    const next = {}
    for (const [k, v] of searchParams.entries()) next[k] = v
    Object.assign(next, updates)
    // 切换搜索条件时重置页码
    if (updates.keyword !== undefined || updates.category !== undefined) next.page = '1'
    // 清理空值
    if (!next.keyword) delete next.keyword
    if (!next.category) delete next.category
    setSearchParams(next)
  }

  return (
    <div>
      <div className="home-search">
        <input
          placeholder="搜索文章标题或内容..."
          value={keyword}
          onChange={(e) => updateParams({ keyword: e.target.value })}
        />
        <select value={category} onChange={(e) => updateParams({ category: e.target.value })}>
          <option value="">全部分类</option>
          <option value="技术">技术</option>
          <option value="生活">生活</option>
          <option value="随笔">随笔</option>
        </select>
        <button className="btn btn-primary" onClick={() => fetchArticles()}>搜索</button>
      </div>

      {loading ? (
        <div className="loading">加载中...</div>
      ) : articles.length === 0 ? (
        <div className="empty">暂无文章</div>
      ) : (
        articles.map(a => <ArticleCard key={a.id} article={a} />)
      )}

      <Pagination
        current={pageInfo.current}
        pages={pageInfo.pages}
        total={pageInfo.total}
        onPageChange={(p) => updateParams({ page: String(p) })}
      />
    </div>
  )
}
