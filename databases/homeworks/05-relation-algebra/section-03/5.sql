select StudentId, StudentName, GroupId
from Students
         natural join Marks
         natural join
         (select CourseId from Plan where LecturerId = :LecturerId) Course
where Mark = :Mark;