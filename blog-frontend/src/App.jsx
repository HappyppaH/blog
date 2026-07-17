import { BrowserRouter, Routes, Route } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext'
import Navbar from './components/Navbar'
import Footer from './components/Footer'
import ProtectedRoute from './components/ProtectedRoute'
import HomePage from './pages/HomePage'
import ArticleDetailPage from './pages/ArticleDetailPage'
import LoginPage from './pages/LoginPage'
import RegisterPage from './pages/RegisterPage'
import DashboardPage from './pages/DashboardPage'
import ArticleManagePage from './pages/ArticleManagePage'
import ArticleFormPage from './pages/ArticleFormPage'
import UserManagePage from './pages/UserManagePage'
import './App.css'

export default function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Navbar />
        <main className="container" style={{ minHeight: 'calc(100vh - 120px)', paddingTop: 24 }}>
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/article/:id" element={<ArticleDetailPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route path="/register" element={<RegisterPage />} />
            <Route path="/admin" element={<ProtectedRoute><DashboardPage /></ProtectedRoute>} />
            <Route path="/admin/articles" element={<ProtectedRoute><ArticleManagePage /></ProtectedRoute>} />
            <Route path="/admin/articles/new" element={<ProtectedRoute><ArticleFormPage /></ProtectedRoute>} />
            <Route path="/admin/articles/:id/edit" element={<ProtectedRoute><ArticleFormPage /></ProtectedRoute>} />
            <Route path="/admin/users" element={<ProtectedRoute adminOnly><UserManagePage /></ProtectedRoute>} />
          </Routes>
        </main>
        <Footer />
      </BrowserRouter>
    </AuthProvider>
  )
}
