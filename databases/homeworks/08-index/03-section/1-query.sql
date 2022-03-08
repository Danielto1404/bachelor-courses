-- Вывести все курсы по плану,
-- которые ведет преподаватель :LecturerName
select distinct CourseId, CourseName
from Courses
         natural join Lecturers
         natural join Plan
where LecturerName = :LecturerName