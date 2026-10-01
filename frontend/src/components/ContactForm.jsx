import { useState } from 'react';

export function ContactList({ contacts, onDelete }) {
  if (!contacts.length) {
    return <div className="empty-state">No emergency contacts yet — add someone below.</div>;
  }

  return (
    <div>
      {contacts.map((c) => (
        <div className="contact-card" key={c.id}>
          <div>
            <div className="name">{c.contactName}</div>
            <div className="meta">{c.relationship || 'Contact'} · {c.phone}</div>
          </div>
          <button onClick={() => onDelete(c.id)}>Remove</button>
        </div>
      ))}
    </div>
  );
}

export function ContactForm({ onAdd }) {
  const [contactName, setContactName] = useState('');
  const [relationship, setRelationship] = useState('');
  const [phone, setPhone] = useState('');
  const [submitting, setSubmitting] = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();
    setSubmitting(true);
    try {
      await onAdd({ contactName, relationship, phone, priorityOrder: 1 });
      setContactName('');
      setRelationship('');
      setPhone('');
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <div className="field">
        <label htmlFor="contactName">Name</label>
        <input id="contactName" value={contactName} onChange={(e) => setContactName(e.target.value)} required />
      </div>
      <div className="field">
        <label htmlFor="relationship">Relationship</label>
        <input id="relationship" value={relationship} onChange={(e) => setRelationship(e.target.value)} placeholder="Sister, friend, ..." />
      </div>
      <div className="field">
        <label htmlFor="contactPhone">Phone</label>
        <input id="contactPhone" type="tel" value={phone} onChange={(e) => setPhone(e.target.value)} placeholder="+61400000000" required />
      </div>
      <button className="btn-secondary" type="submit" disabled={submitting}>
        {submitting ? 'Adding…' : 'Add contact'}
      </button>
    </form>
  );
}
