-- Reserve(UserId, Pass, FlightId, SeatNo) — пытается забронировать место на трое суток начиная с момента бронирования.
-- Возвращает истину, если удалось и ложь — в противном случае.
create or replace
    function Reserve(_UserId int, _Pass varchar(30), _FlightId int, _SeatNo varchar(4)) returns bool
as
$$
begin
    if not CheckAuth(_UserId := _UserId, _Pass := _Pass)
    then
        return false;
    end if;

--  Проверяем, что место не забронированное и не выкупленное и при этом рейс еще не случился.
    if IsPlaceFree(_FlightId := _FlightId, _SeatNo := _SeatNo) then
--      Если есть старая бронь, то мы хотим ее обновить для нового пользователем, иначе добавляем новую.

--      NOTES:
--      Merge отсутствует в PostgreSQL 14.0, по-этому выбран такой метод, что не писать лишний if
--      Знаю, что это плохо переносимо, но merge отсутствует, в свою очередь
--      в документации написано следующее: https://postgrespro.ru/docs/postgresql/14/sql-insert#SQL-ON-CONFLICT
--      Также можно было удалять запись с _FlightId, _SeatNo и потом не разрешать конфликт,
--      а просто вставлять новую запись.
        insert into Tickets(UserId, FlightId, SeatNo, SeatStatus, ReservationTime)
        values (_UserId, _FlightId, _SeatNo, 'reserved', now())
        on conflict on constraint TicketsPK do update set UserId          = _UserId,
                                                          ReservationTime = now();
        return true;
    end if;
    return false;
end;
$$ language plpgsql;