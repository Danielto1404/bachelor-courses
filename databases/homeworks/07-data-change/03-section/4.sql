update Students
set Marks = (
    select count(distinct CourseId)
    from Marks
    where Marks.StudentId = Students.StudentId
)