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
VALUES (1, '2020-01-01', 'первое', 5),
       (1, '2020-01-01', 'второе', 10),
       (1, '2020-01-01', 'компот', 1),
       (2, '2020-01-01', 'day_s dish', 8),
       (2, '2020-01-01', 'business', 12),
       (2, '2020-01-01', 'super discount', 6),
       (1, now(), 'today 1', 6),
       (2, now(), 'today 2', 12);

INSERT INTO VOTE (DATE, TIME, RESTAURANT_ID, USER_ID)
VALUES ('2020-01-01', '10:30:30', 1, 1),
       ('2020-01-01', '00:55:00', 2, 2),
       ('2020-01-01', '09:07:00', 2, 3),
       (now(), '10:30:30', 1, 1),
       (now(), '00:55:00', 2, 2),
       (now(), '09:07:00', 2, 3);



