update
    Runs
set Accepted = 0
where RunId in
      (
          select RunId
          from Runs
                   natural join
               (
                   select SessionId, Letter, min(SubmitTime) as SubmitTime
                   from Runs
                   group by SessionId, Letter
               ) LastFail
      )