-- Inserting test data into the UserAccount table
INSERT INTO USER_ACCOUNT (id, username, password) VALUES (1, 'john_doe', 'password123');
INSERT INTO USER_ACCOUNT (id, username, password) VALUES (2, 'jane_smith', 'passw0rd');
INSERT INTO USER_ACCOUNT (id, username, password) VALUES (3, 'alice_jones', 'alicepass');

-- Assuming UserAccount IDs 1, 2, and 3 already exist in the UserAccount table

INSERT INTO WISH_ITEM (NAME, CURRENT_AMOUNT, NEEDED_AMOUNT, USER_ACCOUNT_ID) VALUES ('Vacation Fund', 500.00, 3000.00, 1);
INSERT INTO WISH_ITEM (NAME, CURRENT_AMOUNT, NEEDED_AMOUNT, USER_ACCOUNT_ID) VALUES ('New Car', 1500.00, 15000.00, 1);
INSERT INTO WISH_ITEM (NAME, CURRENT_AMOUNT, NEEDED_AMOUNT, USER_ACCOUNT_ID) VALUES ('Emergency Fund', 2000.00, 10000.00, 1);

--MySavingRule--
INSERT INTO MY_SAVING_RULE (AMOUNT, ID, USER_ID, DESCRIPTION, TITLE) VALUES
(5000, 5, 1, '毎月の貯金', '月次貯金'),
(3000, 2, 1, '旅行用の貯金', '旅行貯金'),
(10000, 3, 1, '新しいガジェット購入のための貯金', 'ガジェット貯金');

-- 月次貯金: 月曜と金曜に適用
INSERT INTO MY_SAVING_RULE_FREQUENCY (MY_SAVING_RULE_ID, DAY_OF_WEEK) VALUES
(5, 'MONDAY'),
(5, 'FRIDAY');

-- 旅行貯金: 水曜と土曜に適用
INSERT INTO MY_SAVING_RULE_FREQUENCY (MY_SAVING_RULE_ID, DAY_OF_WEEK) VALUES
(2, 'WEDNESDAY'),
(2, 'SATURDAY');

-- ガジェット貯金: 火曜と木曜に適用
INSERT INTO MY_SAVING_RULE_FREQUENCY (MY_SAVING_RULE_ID, DAY_OF_WEEK) VALUES
(3, 'TUESDAY'),
(3, 'THURSDAY');
