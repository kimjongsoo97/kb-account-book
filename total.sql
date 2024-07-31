drop table account;
CREATE TABLE account (
                         id VARCHAR(255) PRIMARY KEY,
                         title VARCHAR(255) NOT NULL,
                         total INT,
                         income INT,
                         expense INT,
                         category VARCHAR(255),
                         description TEXT,
                         date DATE,
                         userId VARCHAR(255),  -- 컬럼 이름을 수정
                         FOREIGN KEY (userId) REFERENCES Users(id) -- 사용자 ID, 문자열
);
INSERT INTO account (id, title, total, income, expense, category, description, date, userid) VALUES
                                                                                                  ('acc1', 'Salary Payment', 3000, 3000, 0, 'Income', 'Monthly salary for July 2024', '2024-07-31', 'user1'),
                                                                                                  ('acc2', 'Groceries', 150, 0, 150, 'Expense', 'Weekly grocery shopping', '2024-07-20', 'user1'),
                                                                                                  ('acc3', 'Rent', 1200, 0, 1200, 'Expense', 'Monthly rent payment', '2024-07-01', 'user2'),
                                                                                                  ('acc4', 'Project Bonus', 500, 500, 0, 'Income', 'Bonus for project completion', '2024-07-25', 'user2'),
                                                                                                  ('acc5', 'Utility Bill', 100, 0, 100, 'Expense', 'Electricity and water bill', '2024-07-15', 'user3'),
                                                                                                  ('acc6', 'Freelance Work', 800, 800, 0, 'Income', 'Payment for freelance work', '2024-07-22', 'user3');

ALTER TABLE account CHANGE COLUMN user_id userId VARCHAR(255);