insert into Groups (group_id, group_name)
values (1, 'M3435'),
       (2, 'M3436'),
       (3, 'M3437'),
       (4, 'M3438'),
       (5, 'M3439');

insert into Students (student_id, student_name, student_surname, group_id)
values (1, 'Даниил', 'Королев', 1),
       (2, 'Мухаммаджон', 'Хакимов', 2),
       (3, 'Александр', 'Сердюков', 3),
       (4, 'Ибрагим', 'Джиблави', 4),
       (5, 'Дмитрий', 'Гнатюк', 5);

insert into Courses (course_id, course_name, semester)
values (1, 'Java Advanced', 4),
       (2, 'Базы данных', 7),
       (3, 'АиСД', NULL),
       (4, 'АиСД', 2),
       (5, 'Теория типов', 5),
       (6, 'Математическая логика', 4),
       (7, 'Дискретная математика', 2),
       (8, 'Дискретная математика', NULL);

insert into Teachers (teacher_id, teacher_name, teacher_surname)
values (1, 'Георгий', 'Корнеев'),
       (2, 'Григорий', 'Шовкопляс'),
       (3, 'Дмитрий', 'Штукенберг'),
       (4, 'Андрей', 'Станкевич'),
       (5, 'Артем', 'Васильев');

insert into Marks (course_id, student_id, mark)
values (1, 1, 'A'),
       (1, 2, 'B'),
       (1, 3, 'C'),
       (1, 4, 'D'),
       (1, 5, 'E');

insert into GroupsCourses (course_id, group_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5);

insert into TeachersCourses (course_id, teacher_id)
values (1, 1),
       (2, 1),
       (3, 2),
       (4, 2),
       (5, 1),
       (6, 3),
       (7, 4),
       (8, 4),
       (8, 5);
