delete
from Students
where StudentId not in (
    select StudentId
    from (
             select distinct StudentId, CourseId
             from Students
                      natural join
                  Plan
             where Plan.CourseId not in (
                 select CourseId
                 from Marks
                 where Students.StudentId = StudentId
                   and Plan.CourseId = CourseId
             )
         ) Debts
    group by StudentId
    having count(*) > 2
)