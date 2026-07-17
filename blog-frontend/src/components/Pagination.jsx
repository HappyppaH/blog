export default function Pagination({ current, pages, total, onPageChange }) {
  if (pages <= 1) return null

  const items = []
  const maxShow = 5
  let start = Math.max(1, current - 2)
  let end = Math.min(pages, start + maxShow - 1)
  if (end - start < maxShow - 1) start = Math.max(1, end - maxShow + 1)

  for (let i = start; i <= end; i++) {
    items.push(i)
  }

  return (
    <div className="pagination">
      <button disabled={current === 1} onClick={() => onPageChange(1)}>首页</button>
      <button disabled={current === 1} onClick={() => onPageChange(current - 1)}>上一页</button>
      {items.map(i => (
        <button key={i} className={i === current ? 'active' : ''} onClick={() => onPageChange(i)}>
          {i}
        </button>
      ))}
      <button disabled={current === pages} onClick={() => onPageChange(current + 1)}>下一页</button>
      <button disabled={current === pages} onClick={() => onPageChange(pages)}>末页</button>
      <span className="page-info">共 {total} 条</span>
    </div>
  )
}
