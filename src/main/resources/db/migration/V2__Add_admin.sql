INSERT INTO my_user(id, username, password_hash)
VALUES (1, 'maks21', '$2a$10$FgeNXZEv6yzUoN4jITJNgeRh8s8P6Arh5eMaG0oiSelYJtiU4AX3G');
INSERT INTO user_role(user_id, roles)
VALUES (1, 'ADMIN');
INSERT INTO user_role(user_id, roles)
VALUES (1, 'USER');

INSERT INTO my_user(id, username, password_hash)
VALUES (2, 'habuma', '$2a$10$FgeNXZEv6yzUoN4jITJNgeRh8s8P6Arh5eMaG0oiSelYJtiU4AX3G');
INSERT INTO user_role(user_id, roles)
VALUES (2, 'USER');