-- Sample data for local dev/testing — real facility data would come from a proper
-- import/onboarding process, not a migration.
INSERT INTO facilities (name, type, latitude, longitude, phone, is_available) VALUES
('Royal Melbourne Hospital', 'HOSPITAL', -37.7997, 144.9556, '+61393427000', TRUE),
('St Vincent''s Hospital Melbourne', 'HOSPITAL', -37.8079, 144.9736, '+61392312211', TRUE),
('Alfred Hospital', 'HOSPITAL', -37.8442, 144.9821, '+61390762000', TRUE),
('Melbourne Ambulance Station 1', 'AMBULANCE', -37.8136, 144.9631, '+61390840000', TRUE),
('Northern Hospital Epping', 'HOSPITAL', -37.6489, 145.0311, '+61393429000', TRUE),
('Sunshine Hospital', 'HOSPITAL', -37.7833, 144.8333, '+61383453333', FALSE);
