-- Every plan gets the same baseline emergency dispatch — tiers only affect
-- extras (contact slots, facility search radius), never core SOS response.
INSERT INTO membership_plans (plan_name, max_emergency_contacts, facility_search_radius_km, price_monthly) VALUES
('FREE', 2, 10, 0.00),
('PREMIUM', 5, 25, 9.99),
('FAMILY', 10, 25, 19.99);
