create table User_Groups(
id_Group char(100) not null,
id_User char(100) not null,
primary key(id_Group,id_User)
)