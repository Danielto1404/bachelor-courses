-- CompressSeats(FlightId) — оптимизирует занятость мест в самолете.
-- В результате оптимизации, в начале самолета должны быть купленные места, затем — забронированные,
-- а в конце — свободные. Примечание: клиенты, которые уже выкупили билеты также должны быть пересажены.
create or replace procedure CompressSeats(in _FlightId int)
    language plpgsql
as
$$
declare
    seat           varchar(4);
    boughtSeat     varchar(4);
    reservedUserId int;
    reservedTime   timestamp;
    reservedCursor refcursor;
    boughtCursor   refcursor;
    seatsCursor cursor for
        select SeatNo
        from Flights
                 natural join Seats
        where FlightId = _FlightId
        order by SeatNo;
begin
    open seatsCursor;

--  Создаем временную таблицу для актуальных броней
    create table TemporaryReservations
    (
        UserId          int,
        ReservationTime timestamp not null
    );

--  Добавляем в нее данные
    insert into TemporaryReservations
    select UserId, ReservationTime
    from Tickets
    where FlightId = _FlightId
      and SeatStatus = 'reserved';

    --  Удаляем из билетов данные об актуальных бронях,
--  чтобы они не конфликтовали когда мы будем пересаживать купленные места.
    delete
    from Tickets
    where FlightId = _FlightId
      and SeatStatus = 'reserved';

    open boughtCursor for
        select SeatNo
        from BoughtSeats
        where FlightId = _FlightId
        order by SeatNo
            for update;

    loop
        fetch boughtCursor into boughtSeat;
        if not found then
            exit;
        end if;
        fetch seatsCursor into seat;
        update Tickets
        set SeatNo = seat
        where FlightId = _FlightId
          and SeatNo = boughtSeat;
    end loop;

    close boughtCursor;
    -- Open & close cursor automatically
    open reservedCursor for select UserId,
                                   ReservationTime
                            from TemporaryReservations
                            order by ReservationTime desc;
    loop
        fetch reservedCursor into reservedUserId, reservedTime;
        if not found then
            exit;
        end if;
        fetch seatsCursor into seat;
        insert into Tickets (UserId, FlightId, SeatNo, SeatStatus, ReservationTime)
        values (reservedUserId, _FlightId, seat, 'reserved', reservedTime);
    end loop;

    close reservedCursor;
    close seatsCursor;
    drop table TemporaryReservations;
end;
$$;