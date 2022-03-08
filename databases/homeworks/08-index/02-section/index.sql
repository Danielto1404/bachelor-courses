-- для having и получения id
create index on Groups using btree (GroupName, GroupId);
create index on Courses using btree (CourseName, CourseId);

-- для natural join, ниже приведены индексы для
-- primary и foreign key разных таблиц
create unique index on Groups using hash (GroupId);
create unique index on Courses using hash (CourseId);
create unique index on Students using hash (StudentId);
create index on Students using hash (GroupId);
create index on Marks using hash (CourseId);
create index on Marks using hash (StudentId);