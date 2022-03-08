select TeamName
from (
         select distinct TeamId, TeamName
         from (
                  select TeamId, TeamName, ContestId
                  from Teams
                           natural join Contests
                  except
                  select TeamId, TeamName, ContestId
                  from Teams
                           natural join Sessions
                           natural join Runs
              ) HaveSubmitted
     ) SubmittedTeams;