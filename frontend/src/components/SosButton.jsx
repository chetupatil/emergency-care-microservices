import { useState } from 'react';
import { api } from '../api.js';

export default function SosButton() {
  const [status, setStatus] = useState('idle'); // idle | locating | sending | sent | error
  const [message, setMessage] = useState('');

  function handlePress() {
    if (status === 'locating' || status === 'sending') return;

    if (!navigator.geolocation) {
      setStatus('error');
      setMessage('Location services are not available on this device/browser.');
      return;
    }

    setStatus('locating');
    setMessage('');

    navigator.geolocation.getCurrentPosition(
      async (position) => {
        setStatus('sending');
        try {
          await api.triggerEmergency({
            latitude: position.coords.latitude,
            longitude: position.coords.longitude,
          });
          setStatus('sent');
          setMessage('Emergency alert sent. Help is on the way and your contacts are being notified.');
        } catch (err) {
          setStatus('error');
          setMessage(err.message || 'Could not send the alert. Please try again or call emergency services directly.');
        }
      },
      () => {
        setStatus('error');
        setMessage('Could not get your location. Please enable location permissions and try again.');
      },
      { enableHighAccuracy: true, timeout: 10000 }
    );
  }

  const isBusy = status === 'locating' || status === 'sending';

  return (
    <div className="sos-wrapper">
      <button className="sos-button" onClick={handlePress} disabled={isBusy}>
        {status === 'locating' && 'LOCATING…'}
        {status === 'sending' && 'SENDING…'}
        {(status === 'idle' || status === 'sent' || status === 'error') && 'SOS'}
      </button>
      <p className="sos-hint">
        {status === 'idle' && 'Press and hold in a real emergency. This alerts your emergency contacts and the nearest facility.'}
        {status === 'sent' && message}
        {status === 'error' && message}
      </p>
    </div>
  );
}
