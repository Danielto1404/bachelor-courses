update Students
set Marks = (
    select count(*)
    from Marks
    where Marks.StudentId = :StudentId
)
where StudentId = :StudentId