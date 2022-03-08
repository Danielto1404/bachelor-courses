select StudentId
from Students
where not exists(
        select 1
        from Plan,
             Lecturers
        where Plan.LecturerId = Lecturers.LecturerId
          and LecturerName = :LecturerName
          and Plan.GroupId = Students.GroupId
          and not exists(
                select 1
                from Marks
                where Students.StudentId = Marks.StudentId
                  and Plan.CourseId = Marks.CourseId
            )
    );