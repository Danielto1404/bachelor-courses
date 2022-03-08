-- BuyReserved(UserId, Pass, FlightId, SeatNo) — пытается выкупить забронированное место (пользователи должны совпадать).
-- Возвращает истину, если удалось и ложь — в противном случае.
create or replace function
    BuyReserved(_UserId int, _Pass varchar(30), _FlightId int, _SeatNo varchar(4)) returns boolean
as
$$
begin
    if not CheckAuth(_UserId := _UserId, _Pass := _Pass)
    then
        return false;
    end if;
    --     Проверяем что у нас есть актуальная бронь,
    if exists(
            select *
            from CurrentReservedSeats
            where FlightId = _FlightId
              and SeatNo = _SeatNo
              and UserId = _UserId
        )
    then
        update Tickets
        set ReservationTime = now(),
            SeatStatus      = 'bought'
        where FlightId = _FlightId
          and SeatNo = _SeatNo;
        return true;
    end if;
    return false;
end;
$$ language plpgsql;