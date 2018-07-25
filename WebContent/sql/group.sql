create table Groups_Tab(
id_Group char(100) not null,
name_Group char(100) unique,
createrId char(100) not null,
describe char(100),
primary key (id_Group)
)