insert into Groups (groupId, groupName)
values (1, 'M34351'),
       (2, 'M34352'),
       (3, 'M3436'),
       (4, 'M3437'),
       (5, 'M3438'),
       (6, 'M3439');

insert into Courses (courseId, courseName)
values (1, 'Java Advanced'),
       (2, 'Базы данных'),
       (3, 'АиСД'),
       (4, 'АиСД'),
       (5, 'Теория типов'),
       (6, 'Математическая логика'),
       (7, 'Дискретная математика'),
       (8, 'Дискретная математика');

insert into Students (studentId, studentName, groupId)
values (1, 'Королев Даниил', 1),
       (2, 'Ившин Андрей', 2),
       (3, 'Хакимов Мухаммаджон', 3),
       (4, 'Сердюков Александр', 4),
       (5, 'Джиблави Ибрагим', 5),
       (6, 'Гнатюк Дмитрий', 6);

insert into Lecturers (lecturerId, lecturerName)
values (1, 'Георгий Корнеев'),
       (2, 'Андрей Станкевич'),
       (3, 'Дмитрий Штукенберг'),
       (4, 'Павел Маврин'),
       (5, 'Артем Васильев');

insert into StudyingPlan (courseId, lecturerId, groupId)
values (1, 1, 1),
       (2, 1, 2),
       (3, 4, 1),
       (4, 4, 4),
       (5, 3, 3),
       (6, 3, 1),
       (7, 5, 6),
       (8, 2, 2),
       (8, 5, 5);

insert into Marks (courseId, studentId, mark)
values (1, 1, 'A'),
       (1, 2, 'B'),
       (1, 3, 'C'),
       (1, 4, 'D'),
       (1, 5, 'E'),
       (1, 6, 'FX');