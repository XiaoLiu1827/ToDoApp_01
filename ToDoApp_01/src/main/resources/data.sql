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
(5000, 1, 1, '毎月の貯金', '月次貯金'),
(3000, 2, 1, '旅行用の貯金', '旅行貯金'),
(10000, 3, 1, '新しいガジェット購入のための貯金', 'ガジェット貯金');