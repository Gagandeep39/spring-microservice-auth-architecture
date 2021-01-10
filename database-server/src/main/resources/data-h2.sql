-- Dummy data for User Table
-- Default password: 123456
INSERT INTO user (user_id, password, username, role) VALUES (100001,'$2a$10$B4FvjVc2oXqDTFdGGZjT5urzHS6TSdzrQ7p/vFi1rUuZgjXR4.m5K', 'gagan', 'Admin');
INSERT INTO user (user_id, password, username, role) VALUES (100002,'$2a$10$B4FvjVc2oXqDTFdGGZjT5urzHS6TSdzrQ7p/vFi1rUuZgjXR4.m5K', 'stefani', 'User');
INSERT INTO user (user_id, password, username, role) VALUES (100003,'$2a$10$B4FvjVc2oXqDTFdGGZjT5urzHS6TSdzrQ7p/vFi1rUuZgjXR4.m5K', 'rhoda', 'User');
INSERT INTO user (user_id, password, username, role) VALUES (100004,'$2a$10$B4FvjVc2oXqDTFdGGZjT5urzHS6TSdzrQ7p/vFi1rUuZgjXR4.m5K', 'olive', 'User');
INSERT INTO user (user_id, password, username, role) VALUES (100005,'$2a$10$B4FvjVc2oXqDTFdGGZjT5urzHS6TSdzrQ7p/vFi1rUuZgjXR4.m5K', 'aida', 'User');
INSERT INTO user (user_id, password, username, role) VALUES (100006,'$2a$10$B4FvjVc2oXqDTFdGGZjT5urzHS6TSdzrQ7p/vFi1rUuZgjXR4.m5K', 'john', 'User');
-- MYSQL sequence
-- UPDATE user_id_sequence SET next_val = 100021;
-- H2 sequence
ALTER sequence user_id_sequence restart with 100007;
