delete
from Runs
where Runs.SessionId in (
    select SessionId
    from Sessions
             natural join Contests
    where ContestName = :ContestName
);