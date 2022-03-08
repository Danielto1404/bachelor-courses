select GroupName,
       CourseName
from Groups G,
     Courses C
where not exists(
        select 1
        from Students S
        where S.GroupId = G.GroupId
          and not exists(
                select 1
                from Marks M
                where S.StudentId = M.StudentId
                  and C.CourseId = M.CourseId
            )
    );
