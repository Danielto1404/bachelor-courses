select S.StudentId,
       StudentName,
       GroupName
from Students S,
     Groups G
where S.GroupId = G.GroupId
  and StudentId not in (
    select S.StudentId
    from Students S,
         Marks M
    where S.StudentId = M.StudentId
      and M.CourseId = :CourseId
);