update Students
set Marks = Marks + (
    select count(*)
    from NewMarks
    where NewMarks.StudentId = Students.StudentId
)