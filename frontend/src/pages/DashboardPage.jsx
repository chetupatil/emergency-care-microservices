import { useEffect, useState, useCallback } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext.jsx';
import { api } from '../api.js';
import SosButton from '../components/SosButton.jsx';
import { ContactList, ContactForm } from '../components/ContactForm.jsx';

export default function DashboardPage() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const [contacts, setContacts] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  const loadContacts = useCallback(async () => {
    if (!user) return;
    try {
      const data = await api.listContacts(user.id);
      setContacts(data);
    } catch (err) {
      setError(err.message || 'Could not load contacts');
    } finally {
      setLoading(false);
    }
  }, [user]);

  useEffect(() => {
    loadContacts();
  }, [loadContacts]);

  async function handleAddContact(payload) {
    await api.addContact(user.id, payload);
    await loadContacts();
  }

  async function handleDeleteContact(contactId) {
    await api.deleteContact(contactId);
    setContacts((prev) => prev.filter((c) => c.id !== contactId));
  }

  async function handleLogout() {
    await logout();
    navigate('/login');
  }

  return (
    <div>
      <div className="top-bar">
        <div>
          <strong>{user?.fullName}</strong>
        </div>
        <button className="logout" onClick={handleLogout}>Log out</button>
      </div>

      <div className="page">
        <SosButton />

        {error && <div className="error-banner">{error}</div>}

        <div className="section-header">
          <h2>Emergency contacts</h2>
        </div>

        {loading ? <div className="empty-state">Loading…</div> : <ContactList contacts={contacts} onDelete={handleDeleteContact} />}

        <div style={{ marginTop: 20 }}>
          <ContactForm onAdd={handleAddContact} />
        </div>
      </div>
    </div>
  );
}
