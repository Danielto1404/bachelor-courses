select GroupName, CourseName, avg(cast(Mark as float)) as AvgMark
from Students
         natural join Marks
         natural join Groups
         natural join Courses
group by Groups.GroupId, Courses.CourseId
having GroupName = :GroupName
   and CourseName = :CourseName