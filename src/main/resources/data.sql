INSERT INTO RESTAURANTS (NAME, DISHES)
VALUES ('Resta1', 'первое, второе, компот'),
       ('Resta2', 'day_s dish, business, super discount');

INSERT INTO USERS (EMAIL, NAME, PASSWORD, VOTE_TIME, RESTAURANT_ID)
VALUES ('user@gmail.com', 'User', 'password', '2020-01-01 10:30:30', 2),
       ('admin@joo.md', 'Admin', 'admin', '2020-01-01 00:55:00', 1),
       ('name@ya.ru', 'Third', 'third', '2020-01-01 09:07:00', 1);


INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('ROLE_USER', 1),
       ('ROLE_ADMIN', 2),
       ('ROLE_USER', 2),
       ('ROLE_USER', 3);



