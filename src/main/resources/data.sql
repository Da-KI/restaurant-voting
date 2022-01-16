INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@gmail.com', '{noop}password'),
       ('Admin', 'admin@joo.md', '{noop}admin'),
       ('Third', 'name@ya.ru', '{noop}third');


INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3);

INSERT INTO RESTAURANTS (NAME)
VALUES ('Resta1'),
       ('Resta2');

INSERT INTO DISHES (RESTAURANT_ID, CREATE_DATE, NAME, PRICE)
VALUES (1,  '2020-01-01 10:30:31', 'первое', 5),
       (1,  '2020-01-01 10:30:32', 'второе', 10),
       (1,  '2020-01-01 10:30:33', 'компот', 1),
       (2,  '2020-01-01 10:30:34', 'day_s dish', 8),
       (2,  '2020-01-01 10:30:35', 'business', 12),
       (2,  '2020-01-01 10:30:36', 'super discount', 6),
       (2,  '2022-01-13 10:30:36', 'today', 6);

INSERT INTO VOTES (DATE, TIME, RESTAURANT_ID, USER_ID)
VALUES ('2020-01-01', '10:30:30', 1, 1),
       ('2020-01-01', '00:55:00', 2, 2),
       ('2020-01-01', '09:07:00', 2, 3);



