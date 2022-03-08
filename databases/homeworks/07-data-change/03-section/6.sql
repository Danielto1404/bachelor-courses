update Students
set Debts = (
    select coalesce(count(distinct P.CourseId), 0)
    from Students S
             natural join Plan P
    where S.StudentId = Students.StudentId
      and not exists(
            select 1
            from Marks M
            where M.StudentId = S.StudentId
              and M.CourseId = P.CourseId
        )
)