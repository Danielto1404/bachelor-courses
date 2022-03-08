-- Вывести все курсы по плану,
-- которые читаются у группы :GroupName.
-- Например если хочется перевестись в другую группу,
-- и посмотреть какие там читаются курсы.
select distinct CourseId, CourseName
from Courses
         natural join Plan
         natural join Groups
where GroupName = :GroupName