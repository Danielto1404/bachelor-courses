select StudentId,
       Total          as Total,
       Passed         as Passed,
       Total - Passed as Failed
from (
         select TotalCourses.StudentId,
                Total,
                coalesce(Passed, 0) as Passed
         from (
                  select StudentId, count(distinct CourseId) as Total
                  from Students
                           left join
                       Plan using (GroupId)
                  group by StudentId
              ) TotalCourses
                  left join
              (
                  select StudentId, count(distinct CourseId) as Passed
                  from Students
                           natural join
                       Marks
                           natural join
                       Plan
                  group by StudentId
              ) PassedCourses using (StudentId)
     ) Statistics
