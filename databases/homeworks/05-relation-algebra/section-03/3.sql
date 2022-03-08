select StudentId, StudentName, GroupId
from Students
         natural join Marks
         natural join Plan
where Mark = :Mark
  and LecturerId = :LecturerId;