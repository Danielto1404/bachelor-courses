create view AllMarks as
select StudentId,
       count(Mark) as Marks
from Students
         left join (select StudentId, Mark
                    from Marks
                    union all
                    select StudentId, Mark
                    from NewMarks) allMarks
                   using (StudentId)
group by StudentId