CREATE TABLE Account (
                         id INT AUTO_INCREMENT PRIMARY KEY, -- 고유 식별자로 자동 증가
                         title VARCHAR(255) NOT NULL, -- 제목, 비워두면 안 되는 문자열
                         total DECIMAL(15, 2), -- 총합, 소수점 두 자리까지 허용
                         income DECIMAL(15, 2), -- 수입, 소수점 두 자리까지 허용
                         expense DECIMAL(15, 2), -- 지출, 소수점 두 자리까지 허용
                         category VARCHAR(50), -- 카테고리, 문자열
                         description TEXT, -- 설명, 긴 문자열을 저장
                         date DATE, -- 날짜, yyyy-mm-dd 형식
                         userId VARCHAR(50) -- 사용자 ID, 문자열
);