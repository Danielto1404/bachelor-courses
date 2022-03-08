select Marks.CourseId, Students.GroupId
from Marks
   , Students
except
select CourseId, GroupId
from (
         select MarkCourses.CourseId, Students.StudentId, Students.GroupId
         from Students,
              (select CourseId from Marks) MarkCourses
         except
         select CourseId, StudentId, GroupId
         from Marks
                  natural join Students
     ) BigDivision;

