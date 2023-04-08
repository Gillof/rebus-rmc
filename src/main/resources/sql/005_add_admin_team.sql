INSERT INTO teams (id, name, secret, role) VALUES ('aa48d0d1-4db5-4588-abcb-42cd408c0687', 'admin', 's3cr3t', 'ADMIN') ON CONFLICT (name) DO NOTHING;
