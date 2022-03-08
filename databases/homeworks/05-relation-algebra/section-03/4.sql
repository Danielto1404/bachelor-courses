select StudentId, StudentName, GroupId
from Students
         natural join Marks
         natural join Plan
         natural join Lecturers
where Mark = :Mark
  and LecturerName = :LecturerName;