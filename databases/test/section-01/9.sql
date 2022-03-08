select distinct TeamName
from Teams
         natural join
     (
         select TeamId, ContestId
         from Sessions
         except
         select TeamId, ContestId
         from (
                  select TeamId, ContestId, Letter
                  from Sessions
                           natural join Contests
                           natural join Problems
                  except
                  select TeamId, ContestId, Letter
                  from (
                           select TeamId, ContestId, Letter
                           from Sessions
                                    natural join Contests
                                    natural join Problems
                           except
                           select TeamId, ContestId, Letter
                           from Sessions
                                    natural join Runs
                           where Accepted = 0
                       ) Failed
              ) NotFailed
     )