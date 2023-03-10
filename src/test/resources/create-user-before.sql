DELETE
FROM user_role;
DELETE
FROM my_user;

INSERT INTO my_user(id, active, password_hash, username)
VALUES (1, true, "$2a$12$9laWZ79FnacJjedJLzkX/OBf1wNnCr3F8/VkjxNHKrrXJslSO3.tK", "drumba");
INSERT INTO my_user(id, active, password_hash, username)
VALUES (2, true, "$2a$12$9laWZ79FnacJjedJLzkX/OBf1wNnCr3F8/VkjxNHKrrXJslSO3.tK", "maks21");

INSERT INTO user_role(user_id, roles)
VALUES (1, 'USER');
INSERT INTO user_role(user_id, roles)
VALUES (2, 'USER');
INSERT INTO user_role(user_id, roles)
VALUES (2, 'ADMIN');
