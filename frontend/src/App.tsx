import { useState } from 'react'
import Login from './Login'
import Notas from './Notas'

export default function App() {
  const [loggedIn, setLoggedIn] = useState(!!localStorage.getItem('token'))

  if (!loggedIn) {
    return <Login onLogin={() => setLoggedIn(true)} />
  }

  return <Notas onLogout={() => setLoggedIn(false)} />
}
