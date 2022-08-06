create table customer (
    id Integer not null AUTO_INCREMENT,
    name varchar (225) not null,
    primary key (id)
);

create table purchase (
    id Integer not null AUTO_INCREMENT,
    customer_id Integer not null,
    primary key (id)
);