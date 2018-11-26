drop table Messages cascade;
drop table Persons cascade;
drop table ClassRooms cascade;
drop table subscribes cascade;

create table Persons (
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
  MessageId serial,
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

alter table subscribes
  add constraint unique_subscribes unique(Subscriber, ClassRoomName);

insert into Persons values('student@haw-landshut.de', 'password', 'student');
insert into Persons values('lecturer@haw-landshut.de', 'password', 'lecturer');
insert into Persons values('gernericLecturer@haw-landshut.de', 'password', 'lecturer');

insert into ClassRooms values('Mobile Computing', 'lecturer@haw-landshut.de');
insert into ClassRooms values('Bildverstehen', 'lecturer@haw-landshut.de');
insert into ClassRooms values('Human Computer Interface', 'lecturer@haw-landshut.de');
insert into ClassRooms values('Generic Empty Classroom', 'lecturer@haw-landshut.de');
insert into ClassRooms values('Generic Not Belonging Classroom', 'gernericLecturer@haw-landshut.de');

insert into subscribes values('student@haw-landshut.de', 'Mobile Computing');
insert into subscribes values('student@haw-landshut.de', 'Bildverstehen');
insert into subscribes values('student@haw-landshut.de', 'Human Computer Interface');

insert into Messages(ClassRoomName, Payload) values('Mobile Computing', 'Die Vorlesung fällt diese Woche aus');
insert into Messages(ClassRoomName, Payload) values('Mobile Computing', 'In den Fluss');
insert into Messages(ClassRoomName, Payload) values('Mobile Computing', 'Android ist super');
insert into Messages(ClassRoomName, Payload) values('Bildverstehen', 'Vorzeichen sind glückssache');
insert into Messages(ClassRoomName, Payload) values('Generic Not Belonging Classroom', 'Generic Message');
insert into Messages(ClassRoomName, Payload) values('Generic Empty Classroom', 'Generic Message');