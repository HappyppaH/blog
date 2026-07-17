import { createContext, useContext, useState, useEffect } from 'react'
import request from '../utils/request'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [token, setToken] = useState(localStorage.getItem('token') || null)

  useEffect(() => {
    if (token) {
      const saved = localStorage.getItem('user')
      if (saved) setUser(JSON.parse(saved))
    }
  }, [])

  const login = async (username, password) => {
    const data = await request.post('/login', { username, password })
    localStorage.setItem('token', data.token)
    const userData = { username: data.username, role: data.role }
    localStorage.setItem('user', JSON.stringify(userData))
    setToken(data.token)
    setUser(userData)
  }

  const register = async (username, password, email) => {
    await request.post('/users', { username, password, email, role: 'user' })
  }

  const logout = () => {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
    setToken(null)
    setUser(null)
  }

  const isAdmin = () => user?.role?.toUpperCase() === 'ADMIN'

  return (
    <AuthContext.Provider value={{ user, token, login, register, logout, isAdmin }}>
      {children}
    </AuthContext.Provider>
  )
}

export function useAuth() {
  const ctx = useContext(AuthContext)
  if (!ctx) throw new Error('useAuth must be inside AuthProvider')
  return ctx
}
