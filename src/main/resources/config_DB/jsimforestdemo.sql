# JSIM FOREST MPD
# 4 tables : configurations, cells, grid, simulation

CREATE DATABASE IF NOT EXISTS jsimforestdemo;
USE jsimforestdemo;

create table if not exists configuration
(
    ID             int auto_increment
        primary key,
    stepsPerSecond int not null,
    stepNumber     int not null,
    rowNumber      int not null,
    columnNumber   int not null
);

create table if not exists grid
(
    ID     int auto_increment
        primary key,
    height int not null,
    width  int not null
);

create table if not exists cells
(
    ID      int auto_increment
        primary key,
    coordX  int         not null,
    coordY  int         not null,
    state   varchar(50) not null,
    age     int         not null,
    ID_Grid int         not null,
    constraint Cells_Grid_FK
        foreign key (ID_Grid) references grid (ID)
);

create table if not exists simulation
(
    ID               int auto_increment
        primary key,
    steps            int         not null,
    name             varchar(50) not null,
    insert_time      datetime    null,
    ID_Configuration int         null,
    ID_Grid          int         null,
    constraint Simulation_Configuration_FK
        foreign key (ID_Configuration) references configuration (ID),
    constraint Simulation_Grid0_FK
        foreign key (ID_Grid) references grid (ID)
);