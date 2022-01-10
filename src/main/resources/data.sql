INSERT INTO USERS (EMAIL, NAME, PASSWORD)
VALUES ('user@gmail.com', 'User', '{noop}password'),
       ('admin@joo.md', 'Admin', '{noop}admin'),
       ('name@ya.ru', 'Third', '{noop}third');


INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3);

INSERT INTO RESTAURANTS (NAME, DISHES)
VALUES ('Resta1', 'первое, второе, компот'),
       ('Resta2', 'day_s dish, business, super discount');

INSERT INTO VOTES (VOTE_DATE, RESTAURANT_ID, USER_ID)
VALUES ('2020-01-01 10:30:30', 1, 1),
       ('2020-01-01 00:55:00', 2, 2),
       ('2020-01-01 09:07:00', 2, 3);



