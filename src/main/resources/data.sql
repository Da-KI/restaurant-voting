INSERT INTO USERS (NAME, EMAIL, PASSWORD)
VALUES ('User', 'user@gmail.com', '{noop}password'),
       ('Admin', 'admin@joo.md', '{noop}admin'),
       ('Third', 'name@ya.ru', '{noop}third');


INSERT INTO USER_ROLE (ROLE, USER_ID)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3);

INSERT INTO RESTAURANT (NAME)
VALUES ('Resta1'),
       ('Resta2');

INSERT INTO MENU_ITEM (RESTAURANT_ID, OFFER_DATE, NAME, PRICE)
VALUES (1, '2020-01-01', 'первое', 50000),
       (1, '2020-01-01', 'второе', 100000),
       (1, '2020-01-01', 'компот', 10000),
       (2, '2020-01-01', 'day_s dish', 80000),
       (2, '2020-01-01', 'business', 120000),
       (2, '2020-01-01', 'super discount', 60000),
       (1, now(), 'today 1', 70000),
       (2, now(), 'today 2', 90000),
       (2, now(), 'today 3', 100000);

INSERT INTO VOTE (DATE, TIME, RESTAURANT_ID, USER_ID)
VALUES ('2020-01-01', '10:30:30', 1, 1),
       ('2020-01-01', '00:55:00', 2, 2),
       ('2020-01-01', '09:07:00', 2, 3),
       ('2022-01-02', '11:43:00', 2, 1),
       ('2022-01-02', '02:55:00', 1, 2),
       ('2022-01-02', '06:08:00', 2, 3),
       (now(), '10:30:30', 2, 1),
       (now(), '00:55:00', 1, 2),
       (now(), '09:07:00', 1, 3);



