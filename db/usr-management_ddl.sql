-- drop table sql5474511.user;
-- drop table sql5474511.user_credential;
-- drop table sql5474511.permission;
-- drop table sql5474511.role;
-- drop table sql5474511.role_permission;
-- drop table sql5474511.user_role;

create table user_management.user
(
    id         int AUTO_INCREMENT,
    full_name  varchar(64) null,
    email      varchar(128) null,
    gender     varchar(16) null,
    birthday   bigint(16) null,
    created_at bigint(16) not null,
    created_by int null,
    updated_at bigint(16) not null,
    constraint user_pk
        primary key (id)
);

create table user_credential
(
    id              int AUTO_INCREMENT,
    user_id         int          not null,
    credential_type varchar(16)  not null,
    credential_id   varchar(64)  not null,
    password        varchar(256) not null,
    salt            varchar(128) not null,
    status          varchar(16)  not null,
    created_at      bigint(16) not null,
    updated_at      bigint(16) not null,
    constraint user_credential_pk
        primary key (id)
);

create table permission
(
    id          int auto_increment,
    name        varchar(64) not null,
    description varchar(512) null,
    constraint permission_pk
        primary key (id)
);

create table role
(
    id          int auto_increment,
    role_name   varchar(128) not null,
    description varchar(512) null,
    status      varchar(16)  not null,
    created_at  bigint(16) not null,
    updated_at  bigint(16) not null,
    created_by  int null,
    constraint role_pk
        primary key (id)
);

create table role_permission
(
    id            int auto_increment,
    role_id       int not null,
    permission_id int not null,
    constraint role_permission_pk
        primary key (id)
);

create table user_role
(
    id      int auto_increment,
    user_id int not null,
    role_id int not null,
    constraint user_role_pk
        primary key (id)
);
