import { useEffect, useState } from 'react'
import { Nota, listarNotas, crearNota, actualizarNota, eliminarNota } from './api'

export default function Notas({ onLogout }: { onLogout: () => void }) {
  const [notas, setNotas] = useState<Nota[]>([])
  const [titulo, setTitulo] = useState('')
  const [contenido, setContenido] = useState('')
  const [editId, setEditId] = useState<number | null>(null)

  async function cargar() {
    const data = await listarNotas()
    setNotas(data)
  }

  useEffect(() => { cargar() }, [])

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault()
    if (!titulo.trim()) return

    if (editId) {
      await actualizarNota(editId, titulo, contenido)
    } else {
      await crearNota(titulo, contenido)
    }

    setTitulo('')
    setContenido('')
    setEditId(null)
    cargar()
  }

  function editar(nota: Nota) {
    setEditId(nota.id)
    setTitulo(nota.titulo)
    setContenido(nota.contenido)
  }

  async function eliminar(id: number) {
    await eliminarNota(id)
    cargar()
  }

  function logout() {
    localStorage.removeItem('token')
    onLogout()
  }

  return (
    <div style={{ maxWidth: 480, margin: '40px auto', fontFamily: 'sans-serif' }}>
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <h2>Mis Notas</h2>
        <button onClick={logout}>Salir</button>
      </div>

      <form onSubmit={handleSubmit} style={{ marginBottom: 24 }}>
        <input
          placeholder="Título"
          value={titulo}
          onChange={e => setTitulo(e.target.value)}
          style={{ display: 'block', width: '100%', marginBottom: 8, padding: 8 }}
        />
        <textarea
          placeholder="Contenido"
          value={contenido}
          onChange={e => setContenido(e.target.value)}
          rows={3}
          style={{ display: 'block', width: '100%', marginBottom: 8, padding: 8 }}
        />
        <button type="submit">{editId ? 'Actualizar' : 'Agregar'}</button>
      </form>

      {notas.map(nota => (
        <div key={nota.id} style={{ border: '1px solid #ccc', borderRadius: 6, padding: 12, marginBottom: 8 }}>
          <strong>{nota.titulo}</strong>
          <p style={{ whiteSpace: 'pre-wrap' }}>{nota.contenido}</p>
          <small>{nota.fecha}</small>
          <div>
            <button onClick={() => editar(nota)}>Editar</button>
            <button onClick={() => eliminar(nota.id)}>Eliminar</button>
          </div>
        </div>
      ))}
    </div>
  )
}
