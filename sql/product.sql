create table product(
    pid NUMBER(10,0) primary key not null,
    pname VARCHAR2(30 BYTE),
    quantity NUMBER(10,0),
    price NUMBER(10,0)
);

create sequence c##servertest1.pid_seq minvalue 1 maxvalue 9999;

insert into product(pid,pname,quantity,price) values (1,'하이',1,1);
select * from product;

update product set pname = 1, quantity = 2, price = 3 where pid = 4;