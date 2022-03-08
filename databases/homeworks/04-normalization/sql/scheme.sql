create table Groups
(
    groupId   int primary key,
    groupName varchar(10)
);

create table Courses
(
    courseId   int primary key,
    courseName varchar(100) not null
);

create table Students
(
    studentId   int primary key,
    studentName varchar(50) not null,
    groupId     int references Groups (groupId)
);


create table Lecturers
(
    lecturerId   int primary key,
    lecturerName varchar(50) not null
);

create table StudyingPlan
(
    groupId    int references Groups (groupId),
    courseId   int references Courses (courseId),
    lecturerId int references Lecturers (lecturerId),
    primary key (groupId, courseId)
);

create table Marks
(
    studentId int references Students (studentId),
    courseId  int references Courses (courseId),
    mark      varchar(2) not null,
    primary key (studentId, courseId)
);