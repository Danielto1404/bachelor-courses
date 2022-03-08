select distinct S.StudentId,
                StudentName,
                GroupId
from Students S,
     Marks M
where Mark = :Mark
  and CourseId = :CourseId
  and S.StudentId = M.StudentId