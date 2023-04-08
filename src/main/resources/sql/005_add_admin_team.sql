INSERT INTO teams (id, name, secret, role) 
SELECT 'aa48d0d1-4db5-4588-abcb-42cd408c0687', 'admin', 's3cr3t', 'ADMIN'
WHERE NOT EXISTS (
	SELECT name FROM teams WHERE name='admin'
);
