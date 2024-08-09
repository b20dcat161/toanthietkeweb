-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS toanthietkeweb;
USE toanthietkeweb;

-- Tạo bảng users
CREATE TABLE `users` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `fullname` VARCHAR(255) NOT NULL,
  `role` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255),
  `phone` VARCHAR(255)
);

-- Tạo bảng messages
CREATE TABLE `messages` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `senderId` INT NOT NULL,
  `receiverId` INT NOT NULL,
  `content` VARCHAR(255) NOT NULL,
  `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tạo bảng assignments
CREATE TABLE `assignments` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(255) NOT NULL,
  `filePath` VARCHAR(255) NOT NULL,
  `teacherId` INT NOT NULL
);

-- Tạo bảng submissions
CREATE TABLE `submissions` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `assId` INT NOT NULL,
  `studentId` INT NOT NULL,
  `filePath` VARCHAR(255) NOT NULL
);

CREATE TABLE `challenges` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `filename` VARCHAR(255) NOT NULL,
  `hint` VARCHAR(255) NOT NULL
);

-- Thêm khóa ngoại cho bảng messages
ALTER TABLE `messages` ADD FOREIGN KEY (`senderId`) REFERENCES `users`(`id`);
ALTER TABLE `messages` ADD FOREIGN KEY (`receiverId`) REFERENCES `users`(`id`);

-- Thêm khóa ngoại cho bảng assignments
ALTER TABLE `assignments` ADD FOREIGN KEY (`teacherId`) REFERENCES `users`(`id`);

-- Thêm khóa ngoại cho bảng submissions
ALTER TABLE `submissions` ADD FOREIGN KEY (`assId`) REFERENCES `assignments`(`id`);
ALTER TABLE `submissions` ADD FOREIGN KEY (`studentId`) REFERENCES `users`(`id`);


-- Chèn thêm dữ liệu mẫu vào bảng users
	INSERT INTO users (username, password, fullname, role, email, phone)
	VALUES ('student1','0IESlFbehwhP7JoZF9VDlIhVLZg1H0Grt0Pzct/JUHU=','student','student','student1@example.com','0123456'),
('student2','Ci8UpLmzB8Ehl3h7Rz7pnYTd1+jfPrELvofTEo1gq5k=','Sinh Viên 2','student','student2@example.com','0987654322'),
('teacher1','Q4f/zFA/VVu781gLBBu9BMAbwC4RlqzQ9fpmEsPs82k=','teacher','teacher','teacher1@example.com','0987654323'),
('teacher2','YyJdonvkf9mpF/ZAhi5zLayjonhzn+P622Of3Vwqt8w=','Giảng viên 2','teacher','teacher2@example.com','1234567890');

-- Chèn dữ liệu mẫu vào bảng messages
INSERT INTO messages (senderId, receiverId, content)
VALUES (1, 2, 'Hello, student1!'),
       (2, 1, 'Hello, admin! How can I help you?'),
       (1, 3, 'Hello, student2!'),
       (3, 1, 'Hi, admin! I have a question.');

-- Chèn dữ liệu mẫu vào bảng assignments
INSERT INTO assignments (title, filePath, teacherId)
VALUES ('Assignment 1', 'assignment1.pdf', 1),
       ('Assignment 2', 'assignment2.pdf', 1);

-- Chèn dữ liệu mẫu vào bảng submissions
INSERT INTO submissions (assId, studentId, filePath)
VALUES (1, 2, '/submission1_student1.pdf'),
       (2, 3, '/submission2_student2.pdf');
       
       
INSERT INTO challenges (filename, hint)
VALUES ('doan thuyen danh ca.txt','Ngữ văn 9, Huy cận, Thơ, đánh cá, thuyền'),
       ( 'song.txt', 'Xuân quỳnh, gió, biển');

