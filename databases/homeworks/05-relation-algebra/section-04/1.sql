select StudentId, StudentName, GroupId
from Students
except
select StudentId, StudentName, GroupId
from Students
         natural join Courses
         natural join Marks
where CourseName = :CourseName;