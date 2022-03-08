create view Debts as
select StudentId, count(CourseId) as Debts
from (
         select distinct StudentId, CourseId
         from Students
                  natural join Plan
         where Plan.CourseId not in (
             select CourseId
             from Marks
             where Students.StudentId = StudentId
               and Plan.CourseId = CourseId
         )
     ) DebtsStudentsCourses
group by StudentId