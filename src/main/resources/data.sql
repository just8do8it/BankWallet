DELETE FROM wallets;
DELETE FROM users;

INSERT INTO users (id, name, email)
VALUES (1, 'Alice Smith',   'alice@example.com');

INSERT INTO users (id, name, email)
VALUES (2, 'Bob Brown',     'bob@example.com');

INSERT INTO users (id, name, email)
VALUES (3, 'Charlie Davis', 'charlie@example.com');


INSERT INTO wallets (id, balance, user_id)
VALUES (1, 1000.00, 1);

INSERT INTO wallets (id, balance, user_id)
VALUES (2, 568.50, 2);

INSERT INTO wallets (id, balance, user_id)
VALUES (3, 5610.23, 3);
