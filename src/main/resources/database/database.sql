CREATE TABLE Persons (
  Email varchar(255),
  PWord varchar(255),
  Role varchar(255),
  Primary Key (Email)
);

create table ClassRooms (
  ClassRoomName varchar (255),
  LecturerMail varchar (255),
  primary key (ClassRoomName),
  foreign key (LecturerMail) references Persons(Email)
);

create table Messages (
  MessageId int,
  ClassRoomName varchar (255),
  Payload varchar (255),
  primary key (MessageId),
  foreign key (ClassRoomName) references ClassRooms(ClassRoomName)
);

create table subscribes (
  Subscriber varchar(255),
  ClassRoomName varchar(255),
  foreign key (Subscriber) references Persons(Email),
  foreign key (ClassRoomName) references ClassRooms(ClassRoomName)
);

insert into Persons values('student@haw-landshut.de', 'password', 'student');
insert into Persons values('lecturer@haw-landshut.de', 'password', 'lecturer');

insert into ClassRooms values('Mobile Computing', 'lecturer@haw-landshut.de');
insert into ClassRooms values('Bildverstehen', 'lecturer@haw-landshut.de');
insert into ClassRooms values('Human Computer Interface', 'lecturer@haw-landshut.de');

insert into subscribes values('student@haw-landshut.de', 'Mobile Computing');
insert into subscribes values('student@haw-landshut.de', 'Bildverstehen');
insert into subscribes values('student@haw-landshut.de', 'Human Computer Interface');