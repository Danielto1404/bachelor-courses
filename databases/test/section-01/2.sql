select TeamName
from (select distinct TeamId, TeamName
      from Sessions
               natural join Teams
      where ContestId = :ContestId
     ) UniqueTeams