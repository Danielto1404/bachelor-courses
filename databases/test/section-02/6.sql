select ProblemName
from Problems,
     (
         select ContestId, Letter
         from Problems
         except
         select ContestId, Letter
         from Runs,
              Sessions
         where Sessions.SessionId = Runs.SessionId
           and Runs.Accepted = 1
     ) as Failed
where Failed.ContestId = Problems.ContestId
  and Failed.Letter = Problems.Letter