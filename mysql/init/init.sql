CREATE DATABASE IF NOT EXISTS shipment default charset utf8 COLLATE utf8_general_ci;
use shipment;

DROP TABLE IF EXISTS `t_shipment`;

create table t_shipment
(
    id             int auto_increment
        primary key,
    trade_id       int           null,
    weight         double(10, 2) null,
create_date    datetime      null,
courier_number varchar(16)   null,
constraint t_shipment__index_courier_unique
unique (courier_number)
)
    comment 'send goods spit';

DROP TABLE IF EXISTS `t_trade`;
create table t_trade
(
    id          int auto_increment
        primary key,
    name        varchar(100)  not null,
    weight      double(12, 2) not null,
create_date datetime      null,
remark      varchar(100)  null,
order_id    varchar(32)   null,
constraint t_trade__index_order_id
unique (order_id)
);