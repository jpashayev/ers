-- drop tables

drop table if exists users;
drop table if exists reimbursement;
drop table if exists user_role;
drop table if exists reimbursement_type;
drop table if exists reimbursement_status;

--------------------------------------
-- USER ROLE TABLE
create table user_role (
	id SERIAL primary key,
	user_role VARCHAR(10) not null
);

insert into user_role (user_role) 
values 
('Employee'),
('Manager');


--------------------------------------
-- REIMBURSEMENT TYPE TABLE
create table reimbursement_type(
	id SERIAL primary key,
	reimb_type VARCHAR(10) not null
);

insert into reimbursement_type (reimb_type) 
values 
('LODGING'),
('TRAVEL'),
('FOOD'),
('OTHER');

--------------------------------------
-- REIMBURSEMENT STATUS TABLE
create table reimbursement_status(
	id SERIAL primary key,
	reimb_status VARCHAR(10) not null
);

insert into reimbursement_status (reimb_status) 
values 
('PENDING'),
('APPROVED'),
('DENIED');

--------------------------------------
-- USERS TABLE
create table users(
	id SERIAL primary key,
	username VARCHAR(50) not null unique,
	password VARCHAR(50) not null,
	user_first_name VARCHAR(100) not null,
	user_last_name VARCHAR(100) not null,
	user_email VARCHAR(150) not null unique,
	user_role_id INTEGER not null,
	
	constraint fk_user_role_id foreign key (user_role_id) references user_role(id) on delete cascade
);

insert into users(username, password, user_first_name, user_last_name, user_email, user_role_id)
values 
('userManager', 'password', 'Bach', 'Tran', 'BT@BT.bt', 2),
('usersam', 'password', 'sam', 's', 'ss@ss.ss', 1),
('usertom', 'password', 'tom', 'b', 'tb@tb.tb', 1);

--------------------------------------
-- REIMBURSEMENT TABLE
create table reimbursement(
	id SERIAL primary key,
	reimb_amount INTEGER not null,
	reimb_submitted TIMESTAMP not null,
	reimb_resolved TIMESTAMP not null,
	reimb_description VARCHAR(250) not null,
	reimb_receipt bytea not null,
	
	reimb_author INTEGER not null,
	reimb_resolver INTEGER not null,
	reimb_status INTEGER not null,
	reimb_type INTEGER not null,
	
	constraint fk_reimb_author foreign key (reimb_author) references users(id) on delete cascade,
	constraint fk_reimb_resolver foreign key (reimb_resolver) references users(id) on delete cascade,
	constraint fk_reimb_status foreign key (reimb_status) references reimbursement_status(id) on delete cascade,
	constraint fk_reimb_type foreign key (reimb_type) references reimbursement_type(id) on delete cascade
);

--------------------------------------
-- select queries

select * 
from user_role;

select * 
from reimbursement_type;

select * 
from reimbursement_status;

select * 
from users;

select * 
from reimbursement

--------------------------------------
-- Complex Queries

-- get user by user & pass
select users.id, users.username, users.password, user_role.user_role  
from users
inner join user_role
on users.user_role_id = user_role.id
where users.username = 'usertom' and users.password = 'password';


SELECT u.id, u.username, u.password, ur.user_role
FROM users u
INNER JOIN user_role ur
ON u.user_role_id = ur.id
WHERE u.username = 'usersam' AND u.password = 'password';
