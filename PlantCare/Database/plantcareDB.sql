drop database if exists GreenAtScreen;
create database GreenAtScreen;
use GreenAtScreen;

create table station
(
	sta_id varchar(5) primary key not null,
	sta_description varchar(50) not null
)engine=InnoDB;

insert into station (sta_id, sta_description) values
('v1','Vivero 1'),
('v2','Vivero 2');

create table plant (
	pla_id int auto_increment primary key,
	pla_station_id varchar(5) not null,
	pla_temperature double not null,
	pla_moisture double not null,
	pla_date datetime not null
)engine=InnoDB;

alter table plant add constraint fk_plant_station foreign key (pla_station_id) references station (sta_id);


insert into plant(pla_station_id, pla_temperature, pla_moisture, pla_date) 
values ('v1', 22.3, 32.43, "2012-02-02 12:12:12");
insert into plant(pla_station_id, pla_temperature, pla_moisture, pla_date) 
values ('v1', 25.4, 30.12, "2012-02-02 12:12:12");

insert into plant(pla_station_id, pla_temperature, pla_moisture, pla_date) 
values ('v2', 22.3, 32.43, "2012-02-02 12:12:12");
insert into plant(pla_station_id, pla_temperature, pla_moisture, pla_date) 
values ('v2', 54.3, 23.43, "2012-02-02 12:12:12");


select * from plant;