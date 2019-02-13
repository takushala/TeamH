INSERT INTO item (item_name, item_category, price, barcode) 
VALUES ('piano', 'instruments', 100000000, '123456789'),
('Test1', 'test', 10000, 'KK50LF'),
('test2', 'test', 10000, 'K-7018'),
('test3', 'test', 10000, 'KST-308'),
('test4', 'test', 10000, 'K-71T'),
('test5', 'test1', 20000, 'T308'),
('test6', 'test2', 50000, 'M308')
;

/* data for table emp */
INSERT INTO emp (emp_username,emp_name, emp_password,active, role)
VALUES 
('admin','Vinh', '{bcrypt}$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',true, 'ROLE_MANAGER'),
('user1','Vinh', '{bcrypt}$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',true, 'ROLE_EMPLOYEE'),
('user2','Vinh', '{bcrypt}$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',true, 'ROLE_EMPLOYEE'),
('user3','Vinh', '{bcrypt}$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',true, 'ROLE_EMPLOYEE'),
('user4','Vinh', '{bcrypt}$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',true, 'ROLE_EMPLOYEE'),
('user5','Vinh', '{bcrypt}$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',true, 'ROLE_EMPLOYEE');


/* data for table shop */
INSERT INTO shop (shop_id, shop_name)
VALUES (1, 'Shop1'), (2, 'Shop2');

/*data for table emp_shop */
insert into emp_shop(emp_username,shop_id)
values  ('user1',1),('user2',1),('user3',2),('user4',2);
/*data for the import_history */
insert into import_history (id,shop_id,emp_username,imp_date,barcode,quantity)
values (1,1,'admin','2018-04-30','123456789',1000),
(2,1,'admin','2018-04-30','KK50LF',1000),
(3,1,'admin','2018-04-30','K-7018',1000),
(4,1,'admin','2018-04-30','KST-308',1000),
(5,2,'admin','2018-04-30','K-71T',1000),
(6,2,'admin','2018-04-30','T308',1000),
(7,2,'admin','2018-04-30','M308',1000);

/*data for table sale */
INSERT into sale (id,barcode,quantity,ex_date,shop_id,emp_username)
values (1,'123456789',100,'2018-05-07',1,'user1'),
(2,'KK50LF',100,'2018-05-07',1,'user1'),
(3,'KK50LF',100,'2018-05-07',1,'user1'),
(4,'K-7018',100,'2018-05-07',1,'user2'),
(5,'K-71T',100,'2018-05-07',2,'user3'),
(6,'M308',100,'2018-05-07',2,'user3'),
(7,'M308',100,'2018-05-07',2,'user4');

