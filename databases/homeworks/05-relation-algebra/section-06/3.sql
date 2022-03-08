select StudentId
from (
         select StudentId
         from Marks
     ) StudentMarksLeft
except
select StudentId
from (
         select StudentId, CourseId
         from (
                  select StudentId
                  from Marks
              ) StudentsProjection
                  cross join
              (
                  select CourseId
                  from Plan
                           natural join Lecturers
                  where LecturerName = :LecturerName
              ) CoursesProjection
         except
         select StudentId, CourseId
         from Marks
     ) DiffProjection;
