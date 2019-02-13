/* drop all tables if exists */
SET FOREIGN_KEY_CHECKS=0; 
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS emp;
DROP TABLE IF EXISTS shop;
DROP TABLE IF EXISTS emp_shop;
DROP TABLE IF EXISTS sale;
DROP TABLE IF EXISTS import_history;
SET FOREIGN_KEY_CHECKS=1;
/* create the database schema */

CREATE TABLE item(
    item_name VARCHAR(30) NOT NULL ,
    item_category VARCHAR(30) NOT NULL,
    price INTEGER NOT NULL,
    barcode VARCHAR(20) PRIMARY KEY NOT NULL
);

CREATE TABLE emp(
    emp_username VARCHAR(20) PRIMARY KEY NOT NULL,
    emp_name VARCHAR(20) not null,
    emp_password VARCHAR(70) NOT NULL,
    active boolean not null,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE shop(
    shop_id INTEGER PRIMARY KEY NOT NULL auto_increment,
    shop_name VARCHAR(30) NOT NULL
);

create table emp_shop (
	emp_username varchar(20) primary key not null,
    shop_id integer not null
);

alter table emp_shop
  add constraint EMP_SHOP_FK foreign key (emp_username)
  references emp (emp_username) ;
alter table emp_shop
  add constraint SHOP_EMP_FK foreign key (shop_id)
  references shop(shop_id) on delete cascade;

create table import_history(
	id integer primary key not null auto_increment,
    shop_id integer not null,
    emp_username varchar(20) not null,
    imp_date date not null,
    barcode varchar(20) not null,
    quantity int not null
);
alter table import_history
	add constraint IMP_SHOP_FK foreign key (shop_id)
    references shop(shop_id) on delete cascade;
alter table import_history
	add constraint IMP_EMP_FK foreign key (emp_username)
    references emp(emp_username);
alter table import_history
	add constraint IMP_ITEM_FK foreign key (barcode)
    references item(barcode);

CREATE TABLE sale(
	id INTEGER PRIMARY KEY NOT NULL auto_increment,
    barcode VARCHAR(20) NOT NULL,
    quantity integer NOT NULL,
    ex_date DATE NOT NULL,
    shop_id INTEGER NOT NULL,
    emp_username varchar(20) not null
);
alter table sale 
	add constraint SALE_EMP_FK foreign key (emp_username)
    references emp(emp_username);
alter table sale 
	add constraint SALE_SHOP_FK foreign key (shop_id)
    references shop(shop_id) on delete cascade;
alter table sale    
    add constraint SALE_ITEM_FK foreign key (barcode)
    references item(barcode);

