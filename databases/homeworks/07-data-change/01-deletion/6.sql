delete
from Students
where StudentId in (
    select StudentId
    from Students
             natural join
         Plan
    where Plan.CourseId not in (
        select CourseId
        from Marks
        where Students.StudentId = StudentId
          and Plan.CourseId = CourseId
    )
)