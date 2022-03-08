select distinct S.StudentId
from Students S,
     Marks M,
     Lecturers L,
     Plan P
where S.StudentId = M.StudentId
  and S.GroupId = P.GroupId
  and P.LecturerId = L.LecturerId
  and M.CourseId = P.CourseId
  and LecturerName = :LecturerName;