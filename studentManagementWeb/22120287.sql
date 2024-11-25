USE master;
GO
alter database [QLHOCSINH] set single_user with rollback immediate
DROP DATABASE QLHOCSINH;
GO
CREATE DATABASE QLHOCSINH;
GO
USE QLHOCSINH;
GO

CREATE TABLE HOCSINH(
	ID			CHAR(10) PRIMARY KEY,
	NAME		NVARCHAR(50) NOT NULL,
	BIRTHDAY	DATE,
	ADDRESS		NVARCHAR(100) NOT NULL,
	NOTES		NVARCHAR(100),
)

CREATE TABLE COURSE(
	ID			CHAR(10) PRIMARY KEY,
	NAME		NVARCHAR(50),
	LECTURE		NVARCHAR(50),
	YEAR		INT,
	NOTES		NVARCHAR(100),
)

CREATE TABLE COURSE_STUDENT(
	ID			CHAR(10),
	STUDENTID	CHAR(10),
	COURSEID	CHAR(10),
	GRADE		INT NOT NULL 
					CHECK (GRADE >= 0 AND GRADE <= 10)
	FOREIGN KEY (STUDENTID) REFERENCES HOCSINH(ID),
	FOREIGN KEY (COURSEID) REFERENCES COURSE(ID),
)

INSERT INTO HOCSINH
VALUES
('001', N'A','02/15/2005', N'25/3 Lạc Long Quân, Q.10, TP HCM', N''),
('002', N'Trần Văn Ân','06/20/2005', N'125 Trần Hưng Đạo, Q.1,TP HCM', N''),
('003', N'Hồng Ngọc Ánh', '05/11/2005', N'12/21 Võ Văn Ngân Thủ Đức, TP HCM', N''),
('004', N'Trương Nam Sơn', '06/20/2004', N'215 Lý Thường Kiệt,TP Biên Hòa', N''),
('005', N'Lý Hoàng Hà', '10/23/2004', N'25/5 Nguyễn Xí, Q.Bình Thạnh, TP HCM', N''),
('006', N'Trần Bạch Tuyết', '05/20/2004', N'127 Hùng Vương, TP Mỹ Tho', N''),
('007', N'Nguyễn An Trung', '06/05/2004', N'234 3/2, TP Biên Hòa', N''),
('008', N'Trần Trung Hiếu', '08/06/2003', N'22/11 Lý Thường Kiệt, TP Mỹ Tho', N''),
('009', N'Trần Hoàng Nam', '11/22/2003', N'234 Trần Não, An Phú,TP HCM', N''),
('010', N'Phạm Nam Thanh', '12/12/2002', N'221 Hùng Vương, Q.5, TP HCM', N'')

INSERT INTO COURSE
VALUES
('a001', N'abc1', N'xyz', 2024, Null),
('a002', N'abc2', N'xyz', 2024, Null),
('b001', N'abc3', N'xyz', 2024, Null),
('b002', N'abc4', N'xyz', 2024, Null),
('c001', N'abc5', N'xyz', 2024, Null),
('c002', N'abc6', N'xyz', 2024, Null),
('c003', N'abc7', N'xyz', 2024, Null)

