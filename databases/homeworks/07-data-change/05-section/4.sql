create view StudentDebts as
select Students.StudentId, coalesce(Deb, 0) as Debts
from Students
         left join
     (
         select StudentId, count(distinct P.CourseId) as Deb
         from Students S
                  natural join Plan P
         where not exists(
                 select 1
                 from Marks M
                 where M.StudentId = S.StudentId
                   and M.CourseId = P.CourseId
             )
         group by StudentId
     ) DebtsTable
     using (StudentId)
