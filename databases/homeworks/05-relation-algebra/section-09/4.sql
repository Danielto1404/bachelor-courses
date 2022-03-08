select GroupName, AvgAvgMark
from Groups
         left join
     (
         select GroupId, avg(AvgMark) as AvgAvgMark
         from (
                  select GroupId, avg(cast(Mark as float)) as AvgMark
                  from Marks
                           natural join Students
                  group by StudentId, GroupId
              ) StudentMeans
         group by GroupId
     ) GroupMeans using (GroupId)
