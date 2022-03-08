merge into Marks
    using NewMarks
    on NewMarks.StudentId = Marks.StudentId and
       NewMarks.CourseId = Marks.CourseId
    when matched then update set Marks.Mark =
            case
                when NewMarks.Mark >= Marks.mark then NewMarks.Mark
                when NewMarks.Mark < Marks.mark then Marks.Mark
                end
    when not matched then insert (StudentId, CourseId, Mark)
        values (NewMarks.StudentId, NewMarks.CourseId, NewMarks.Mark);