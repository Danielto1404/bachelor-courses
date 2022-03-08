select distinct S.StudentId,
                StudentName,
                GroupId
from Students S,
     Marks M,
     Courses C
where CourseName = :CourseName
  and Mark = :Mark
  and S.StudentId = M.StudentId
  and M.CourseId = C.CourseId;