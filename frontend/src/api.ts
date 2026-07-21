const API_URL = import.meta.env.VITE_API_URL || '/api'

export interface Nota {
  id: number
  titulo: string
  contenido: string
  fecha: string
}

function getToken(): string | null {
  return localStorage.getItem('token')
}

async function request(path: string, options: RequestInit = {}) {
  const token = getToken()
  const headers: Record<string, string> = {
    'Content-Type': 'application/json',
    ...(options.headers as Record<string, string>)
  }
  if (token) headers['Authorization'] = `Bearer ${token}`

  const res = await fetch(`${API_URL}${path}`, { ...options, headers })

  if (res.status === 401) {
    localStorage.removeItem('token')
    window.location.reload()
    throw new Error('Sesión expirada')
  }

  if (!res.ok) {
    const text = await res.text()
    throw new Error(text || 'Error en la petición')
  }

  if (res.status === 204) return null
  return res.json()
}

export async function login(username: string, password: string): Promise<string> {
  const data = await request('/auth/login', {
    method: 'POST',
    body: JSON.stringify({ username, password })
  })
  return data.token
}

export function listarNotas(): Promise<Nota[]> {
  return request('/notas')
}

export function crearNota(titulo: string, contenido: string): Promise<Nota> {
  return request('/notas', {
    method: 'POST',
    body: JSON.stringify({ titulo, contenido })
  })
}

export function actualizarNota(id: number, titulo: string, contenido: string): Promise<Nota> {
  return request(`/notas/${id}`, {
    method: 'PUT',
    body: JSON.stringify({ titulo, contenido })
  })
}

export function eliminarNota(id: number): Promise<null> {
  return request(`/notas/${id}`, { method: 'DELETE' })
}
