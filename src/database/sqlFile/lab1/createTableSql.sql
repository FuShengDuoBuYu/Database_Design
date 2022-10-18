create table if not exists room (kdno int not null,kcno int not null,ccno int not null,kdname varchar(255),exptime varchar(255),papername varchar(255),primary key(kdno,kcno,ccno));
create table if not exists student (registno varchar(255) not null,name varchar(255) not null,kdno int not null,kcno varchar(255) not null,ccno varchar(255) not null,seat varchar(255) not null,primary key(registno));
