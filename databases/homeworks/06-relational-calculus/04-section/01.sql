select StudentName,
       CourseName
from Students S,
     Courses C
where exists(
        select 1
        from Plan
        where Plan.GroupId = S.GroupId
          and Plan.CourseId = C.CourseId
    )
  and not exists(
        select 1
        from Marks
        where Marks.StudentId = S.StudentId
          and Marks.CourseId = C.CourseId
    );