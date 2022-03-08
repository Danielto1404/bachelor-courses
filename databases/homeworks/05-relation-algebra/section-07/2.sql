select CourseName, GroupName
from (Marks natural join Courses),
     (Students natural join Groups)
except
select CourseName, GroupName
from (
         select CourseName, StudentId, GroupName
         from (
                  select CourseName
                  from Marks
                           natural join Courses
              ) Name,
              Students
                  natural join Groups
         except
         select CourseName,
                StudentId,
                GroupName
         from Marks
                  natural join Courses
                  natural join Students
                  natural join Groups
     ) DiffTable