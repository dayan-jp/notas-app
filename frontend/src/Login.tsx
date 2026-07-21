import { useState } from 'react'
import { login } from './api'

export default function Login({ onLogin }: { onLogin: () => void }) {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    setError('')
    try {
      const token = await login(username, password)
      localStorage.setItem('token', token)
      onLogin()
    } catch {
      setError('Usuario o contraseña incorrectos')
    }
  }

  return (
    <div style={{ maxWidth: 320, margin: '80px auto', fontFamily: 'sans-serif' }}>
      <h2>Notas Rápidas</h2>
      <form onSubmit={handleSubmit}>
        <input
          placeholder="Usuario"
          value={username}
          onChange={e => setUsername(e.target.value)}
          style={{ display: 'block', width: '100%', marginBottom: 8, padding: 8 }}
        />
        <input
          type="password"
          placeholder="Contraseña"
          value={password}
          onChange={e => setPassword(e.target.value)}
          style={{ display: 'block', width: '100%', marginBottom: 8, padding: 8 }}
        />
        {error && <p style={{ color: 'red' }}>{error}</p>}
        <button type="submit" style={{ width: '100%', padding: 8 }}>Entrar</button>
      </form>
    </div>
  )
}
