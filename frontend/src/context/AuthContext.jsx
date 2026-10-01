import { createContext, useContext, useState, useCallback } from 'react';
import { api, getStoredTokens, storeTokens, clearTokens } from '../api.js';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => getStoredTokens()?.user || null);

  const login = useCallback(async (email, password) => {
    const data = await api.login({ email, password });
    storeTokens({ accessToken: data.accessToken, refreshToken: data.refreshToken, user: data.user });
    setUser(data.user);
    return data.user;
  }, []);

  const register = useCallback(async (fullName, email, phone, password) => {
    const data = await api.register({ fullName, email, phone, password });
    storeTokens({ accessToken: data.accessToken, refreshToken: data.refreshToken, user: data.user });
    setUser(data.user);
    return data.user;
  }, []);

  const logout = useCallback(async () => {
    const tokens = getStoredTokens();
    if (tokens?.refreshToken) {
      try {
        await api.logout(tokens.refreshToken);
      } catch {
        // best-effort — clear local state regardless
      }
    }
    clearTokens();
    setUser(null);
  }, []);

  return (
    <AuthContext.Provider value={{ user, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth must be used within an AuthProvider');
  return ctx;
}
