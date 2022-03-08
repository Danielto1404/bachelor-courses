select StudentName,
       CourseName
from (
         select StudentId,
                CourseId
         from Students S,
              Plan P
         where S.GroupId = P.GroupId
         union
         select StudentId,
                CourseId
         from Marks
     ) HaveCourse,
     Students S,
     Courses C
where HaveCourse.StudentId = S.StudentId
  and HaveCourse.CourseId = C.CourseId;