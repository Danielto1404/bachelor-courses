-- Вывести всех преподавателей для курса :CourseName.
-- Когда хочется слушать курс у другого преподавателя.
select distinct LecturerId, LecturerName
from Lecturers
         natural join Plan
         natural join Courses
where CourseName = :CourseName