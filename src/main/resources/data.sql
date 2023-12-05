INSERT INTO users (username, password)
VALUES ('user', '$2y$10$X3hj3qZ1nb3ok6.CBK1Tu.J0UShodeFkC7S4gKEAd7ZfipFVjJq.q'),
       ('admin', '$2y$10$nR2NSC8mf1XQyoNAskL85OfNIxATrBVgwjEbbOJ1O1Fte0xrzNkLK');

INSERT INTO roles (name)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id)
VALUES ('1', '1'),
       ('2', '2');