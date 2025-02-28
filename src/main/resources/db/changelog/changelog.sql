-- changelog.sql
--liquibase formatted sql

--changeset author:loint description:init
CREATE SCHEMA IF NOT EXISTS "order";

create type Status as enum ('ACTIVE', 'INACTIVE');
create type OrderStatus as enum ('PENDING', 'AWAITING_PAYMENT', 'IN_QUEUE', 'PICKED_UP', 'COMPLETED', 'CANCELLED', 'FAILED', 'REFUNDED');
create type QueueStatus as enum ('PREPARING', 'READY');

create table "order"."order"
(
    id           bigserial
        constraint order_pk
            primary key,
    customer_id  bigint not null,
    total_amount decimal,
    status       OrderStatus,
    pickup_time  timestamp,
    order_date   timestamp,
    created_date timestamp,
    created_by   varchar,
    updated_date timestamp,
    updated_by   varchar
);

create table "order"."order_item"
(
    id                   bigserial
        constraint order_item_pk
            primary key,
    order_id             bigint not null,
    menu_item_id         bigint not null,
    quantity             integer,
    special_instructions varchar,
    created_date         timestamp,
    created_by           varchar,
    updated_date         timestamp,
    updated_by           varchar
);

create table "order"."order_queue"
(
    id                   bigserial
        constraint order_queue_pk
            primary key,
    order_id             bigint not null,
    queue_id             bigint not null,
    status               QueueStatus,
    estimated_ready_time timestamp,
    created_date         timestamp,
    created_by           varchar,
    updated_date         timestamp,
    updated_by           varchar
);

create table "order"."queue"
(
    id           bigserial
        constraint queue_pk
            primary key,
    gate         varchar not null,
    status       Status,
    created_date timestamp,
    created_by   varchar,
    updated_date timestamp,
    updated_by   varchar
);

create table "order"."review"
(
    id           bigserial
        constraint review_pk
            primary key,
    gate         varchar not null,
    status       Status,
    created_date timestamp,
    created_by   varchar,
    updated_date timestamp,
    updated_by   varchar
);

INSERT INTO "order".queue (id, gate, status, created_date, created_by, updated_date, updated_by)
VALUES (1, 'number 1', 'ACTIVE', '2024-11-08 11:18:46.000000', 'system',
        '2024-11-08 11:18:48.000000', 'system');
INSERT INTO "order".queue (id, gate, status, created_date, created_by, updated_date, updated_by)
VALUES (2, 'number 2', 'ACTIVE', '2024-11-08 11:18:46.000000', 'system',
        '2024-11-08 11:18:48.000000', 'system');
INSERT INTO "order".queue (id, gate, status, created_date, created_by, updated_date, updated_by)
VALUES (3, 'number 3', 'ACTIVE', '2024-11-08 11:18:46.000000', 'system',
        '2024-11-08 11:18:48.000000', 'system');
