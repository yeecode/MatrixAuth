create schema if not exists matrixauth collate utf8mb4_0900_ai_ci;

create table if not exists application
(
    name varchar(255) not null
        primary key,
    token varchar(255) null,
    dataSourceName varchar(255) null,
    cacheName varchar(255) null
);

create table if not exists cache
(
    name varchar(255) not null
        primary key,
    url varchar(255) not null,
    password varchar(255) null
);

create table if not exists datasource
(
    name varchar(255) not null
        primary key,
    url text not null,
    driver varchar(255) null,
    userName varchar(255) null,
    password varchar(255) null
);

create schema ds01 collate utf8mb4_0900_ai_ci;

create table permission
(
    appName varchar(255) not null,
    `key` varchar(255) not null,
    name varchar(255) null,
    description text null,
    primary key (appName, `key`)
);

create table role
(
    appName varchar(255) not null,
    name varchar(255) not null,
    type varchar(255) not null,
    description text null,
    primary key (appName, name)
);

create table role_x_permission
(
    appName varchar(255) not null,
    roleName varchar(255) not null,
    permKey varchar(255) not null,
    primary key (appName, roleName, permKey)
);

create table user
(
    appName varchar(255) not null,
    `key` varchar(255) not null,
    name varchar(255) null,
    primary key (appName, `key`)
);

create table user_x_permission
(
    fullUserKey varchar(255) not null
        primary key,
    permissionKeys text null
);

create table user_x_role
(
    appName varchar(255) not null,
    userKey varchar(255) not null,
    roleName varchar(255) not null,
    primary key (appName, userKey, roleName)
);



