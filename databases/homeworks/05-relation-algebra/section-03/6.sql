select StudentId, StudentName, GroupId
from Students
         natural join Marks
         natural join
     (select CourseId
      from Plan
               natural join Lecturers
      where LecturerName = :LecturerName) Course
where Mark = :Mark;