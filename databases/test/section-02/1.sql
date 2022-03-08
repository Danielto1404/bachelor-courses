select distinct TeamId
from Runs,
     Sessions
where Runs.SessionId = Sessions.SessionId
  and Accepted = 0
  and Letter = :Letter
  and ContestId = :ContestId