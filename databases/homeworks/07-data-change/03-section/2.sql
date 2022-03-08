update Students
set Marks = (
    select count(*)
    from Marks
    where Marks.StudentId = Students.StudentId
)