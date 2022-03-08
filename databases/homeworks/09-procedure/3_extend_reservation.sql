-- ExtendReservation(UserId, Pass, FlightId, SeatNo) — пытается продлить бронь места на трое суток начиная с момента продления.
-- Возвращает истину, если удалось и ложь — в противном случае.
create or replace
    function ExtendReservation(_UserId int, _Pass varchar(30), _Flight int, _SeatNo varchar(4)) returns bool
as
$$
begin
    if not CheckAuth(_UserId := _UserId, _Pass := _Pass)
    then
        return false;
    end if;
--  Проверяем, что именно мы бронировали место
    if exists(
            select *
            from CurrentReservedSeats
            where FlightId = _Flight
              and SeatNo = _SeatNo
              and UserId = _UserId
        )
    then
        update Tickets
        set ReservationTime = now()
        where FlightId = _Flight
          and SeatNo = _SeatNo;
        return true;
    end if;
    return false;
end;
$$ language plpgsql;