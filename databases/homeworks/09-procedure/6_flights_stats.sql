-- FlightsStatistics(UserId, Pass) — статистика по рейсам: возможность бронирования и покупки,
-- число свободных, забронированных и проданных мест.
create or replace function
    FlightsStatistics(_UserId int, _Pass varchar(30))
    returns table
            (
                FlightId   int,
                CanReserve bool,
                CanBuy     bool,
                Free       int,
                Reserved   int,
                Bought     int
            )
as
$$
begin
    if not CheckAuth(_UserId := _UserId, _Pass := _Pass)
    then
        raise exception 'Authorization error. Try again.';
    end if;
    return query
        select Stats.FlightId,
               Stats.CanReserve,
               Stats.CanBuy,
               Stats.Free::int,
               Stats.Reserved::int,
               Stats.Bought::int
        from FlightsUsersStats Stats
        where Stats.UserId = _UserId;
end;
$$ language plpgsql;