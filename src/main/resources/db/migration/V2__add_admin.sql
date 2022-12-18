INSERT INTO user_t (email, enabled, password, role, username)
VALUES ('unexcite2@gmail.com', TRUE, '$2a$10$qp5jwIc/.ELds0nCZ2q19ezTkAxcJ76suq0UYH80rKKwOByS9cbpC', 'ADMIN', 'admin'),
       ('unexcite@gmail.com', TRUE, '$2a$08$bSnD.Oy5tzAZMQEitpLyn.L/kg1IGD2Kp848LZOceDW03GsShCf12', 'CUSTOMER', 'user');

INSERT INTO bucket_t (total_items, total_prices, user_id)
VALUES (0, 0, (SELECT id FROM user_t WHERE username='admin')),
       (0, 0, (SELECT id FROM user_t WHERE username='user'));
