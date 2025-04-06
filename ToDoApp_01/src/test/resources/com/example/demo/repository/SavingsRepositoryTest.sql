-- MySavingRule
-- 1件目
INSERT INTO My_Saving_Rule (id, amount, title, user_id) 
VALUES (10, 500.00, '毎週貯金', 101);

-- 2件目
INSERT INTO My_Saving_Rule (id, amount, title, user_id) 
VALUES (11, 1000.00, '月曜日貯金', 102);

-- 3件目
INSERT INTO My_Saving_Rule (id, amount, title, user_id) 
VALUES (12, 2000.00, '毎月1日貯金', 103);

-- MY_SAVING_RULE_FREQUENCY
-- 1件目
INSERT INTO MY_SAVING_RULE_FREQUENCY (MY_SAVING_RULE_ID, DAY_OF_WEEK) 
VALUES (10, 'MONDAY'), (10, 'WEDNESDAY');

-- 2件目
INSERT INTO MY_SAVING_RULE_FREQUENCY (MY_SAVING_RULE_ID, DAY_OF_WEEK) 
VALUES (11, 'MONDAY');

-- 3件目
INSERT INTO MY_SAVING_RULE_FREQUENCY (MY_SAVING_RULE_ID, DAY_OF_WEEK) 
VALUES (12, 'MONDAY'), (12, 'TUESDAY');

-- Savings
-- 1件目
INSERT INTO Savings (amount, MY_SAVING_RULE_ID, user_id, saved_date) 
VALUES (500.00, 10, 101, '2025-04-06');

-- 2件目
INSERT INTO Savings (amount, MY_SAVING_RULE_ID, user_id, saved_date) 
VALUES (1500.00, 11, 101, '2025-04-06');

-- 3件目
INSERT INTO Savings (amount, MY_SAVING_RULE_ID, user_id, saved_date) 
VALUES (2000.00, 12, 103, '2025-04-06');
