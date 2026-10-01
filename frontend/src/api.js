// The Gateway's published port on the host machine. Overridable at build time
// via VITE_API_BASE_URL if you deploy this somewhere other than localhost.
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';

function getStoredTokens() {
  const raw = localStorage.getItem('auth');
  return raw ? JSON.parse(raw) : null;
}

function storeTokens(auth) {
  localStorage.setItem('auth', JSON.stringify(auth));
}

function clearTokens() {
  localStorage.removeItem('auth');
}

async function request(path, { method = 'GET', body, auth = true, retry = true } = {}) {
  const headers = { 'Content-Type': 'application/json' };
  const tokens = getStoredTokens();

  if (auth && tokens?.accessToken) {
    headers['Authorization'] = `Bearer ${tokens.accessToken}`;
  }

  const response = await fetch(`${API_BASE_URL}${path}`, {
    method,
    headers,
    body: body ? JSON.stringify(body) : undefined,
  });

  // Access token expired mid-session — try one silent refresh, then replay the request.
  if (response.status === 401 && auth && retry && tokens?.refreshToken) {
    const refreshed = await refreshAccessToken(tokens.refreshToken);
    if (refreshed) {
      return request(path, { method, body, auth, retry: false });
    }
    clearTokens();
    window.location.href = '/login';
    return null;
  }

  if (!response.ok) {
    let message = `Request failed (${response.status})`;
    try {
      const errorBody = await response.json();
      if (errorBody?.messages?.length) message = errorBody.messages.join(', ');
    } catch {
      // response had no JSON body — keep the generic message
    }
    throw new Error(message);
  }

  if (response.status === 204) return null;
  return response.json();
}

async function refreshAccessToken(refreshToken) {
  try {
    const res = await fetch(`${API_BASE_URL}/api/users/refresh`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ refreshToken }),
    });
    if (!res.ok) return false;
    const data = await res.json();
    storeTokens({ accessToken: data.accessToken, refreshToken: data.refreshToken, user: data.user });
    return true;
  } catch {
    return false;
  }
}

export const api = {
  register: (payload) => request('/api/users/register', { method: 'POST', body: payload, auth: false }),
  login: (payload) => request('/api/users/login', { method: 'POST', body: payload, auth: false }),
  logout: (refreshToken) => request('/api/users/logout', { method: 'POST', body: { refreshToken }, auth: false }),
  getMyProfile: () => request('/api/users/me'),

  listContacts: (userId) => request(`/api/users/${userId}/contacts`),
  addContact: (userId, payload) => request(`/api/users/${userId}/contacts`, { method: 'POST', body: payload }),
  deleteContact: (contactId) => request(`/api/contacts/${contactId}`, { method: 'DELETE' }),

  triggerEmergency: (payload) => request('/api/emergency/trigger', { method: 'POST', body: payload }),
  getEmergencyHistory: () => request('/api/emergency/my'),
};

export { getStoredTokens, storeTokens, clearTokens };
