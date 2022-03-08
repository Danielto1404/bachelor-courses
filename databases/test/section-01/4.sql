select TeamName
from Teams
         natural join
     (
         select TeamId
         from Teams
         except
         select TeamId
         from Sessions
                  natural join Runs
         where ContestId = :ContestId
     ) PassSolution