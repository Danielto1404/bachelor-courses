select SessionId, count(SessionId) as Opened
from (
         select distinct SessionId, Letter
         from Runs
     ) UniqueTasks
group by SessionId;