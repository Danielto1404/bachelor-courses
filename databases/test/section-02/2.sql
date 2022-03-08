select TeamName
from Teams
where TeamId not in
      (
          select TeamId
          from Sessions,
               Runs
          where Sessions.SessionId = Runs.sessionId
            and Sessions.ContestId = :ContestId
            and Runs.Letter = :Letter
            and Runs.Accepted = 1
      )