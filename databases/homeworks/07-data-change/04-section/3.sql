update
    Marks
set Mark = (
    select 0.5 * (Mark + Marks.Mark + abs(Mark - Marks.Mark))
    from NewMarks
    where Marks.StudentId = NewMarks.StudentId
      and Marks.CourseId = NewMarks.CourseId
)
where exists
          (
              select 1
              from NewMarks
              where Marks.StudentId = NewMarks.StudentId
                and Marks.CourseId = NewMarks.CourseId
          );