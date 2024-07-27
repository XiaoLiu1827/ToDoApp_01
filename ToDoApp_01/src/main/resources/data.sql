-- Inserting test data into the UserAccount table
INSERT INTO USER_ACCOUNT (id, username, password) VALUES (1, 'john_doe', 'password123');
INSERT INTO USER_ACCOUNT (id, username, password) VALUES (2, 'jane_smith', 'passw0rd');
INSERT INTO USER_ACCOUNT (id, username, password) VALUES (3, 'alice_jones', 'alicepass');

-- Assuming UserAccount IDs 1, 2, and 3 already exist in the UserAccount table

INSERT INTO SAVING_PURPOSE (NAME, CURRENT_AMOUNT, NEEDED_AMOUNT, USER_ACCOUNT_ID) VALUES ('Vacation Fund', 500.00, 3000.00, 1);
INSERT INTO SAVING_PURPOSE (NAME, CURRENT_AMOUNT, NEEDED_AMOUNT, USER_ACCOUNT_ID) VALUES ('New Car', 1500.00, 15000.00, 2);
INSERT INTO SAVING_PURPOSE (NAME, CURRENT_AMOUNT, NEEDED_AMOUNT, USER_ACCOUNT_ID) VALUES ('Emergency Fund', 2000.00, 10000.00, 3);


