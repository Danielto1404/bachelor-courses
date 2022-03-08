select distinct S.StudentId,
                StudentName,
                GroupName
from Students S,
     Groups G,
     Plan P,
     Courses C
where S.GroupId = G.GroupId
  and P.CourseId = C.CourseId
  and P.GroupId = G.GroupId
  and C.CourseName = :CourseName
  and StudentId not in (
    select S.StudentId
    from Students S,
         Marks M,
         Courses C
    where S.StudentId = M.StudentId
      and M.CourseId = C.CourseId
      and C.CourseName = :CourseName
);