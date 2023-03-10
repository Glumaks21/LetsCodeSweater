DELETE
FROM message;

INSERT INTO message(id, text, tag, user_id)
VALUES (1, 'first', 'my-tag', 1);

INSERT INTO message(id, text, tag, user_id)
VALUES (2, 'second', 'more', 1);

INSERT INTO message(id, text, tag, user_id)
VALUES (3, 'third', 'my-tag', 1);

INSERT INTO message(id, text, tag, user_id)
VALUES (4, 'fourth', 'another', 1);

ALTER TABLE message AUTO_INCREMENT = 10;