select TeamId, count(Letter) as Opened
from (
         select distinct TeamId, Letter, ContestId
         from Sessions
                  natural join Runs
     ) UniqueTeamsTasks
group by TeamId;