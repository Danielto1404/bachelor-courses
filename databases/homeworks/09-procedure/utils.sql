drop trigger if exists CheckSeatExistsInFlight on tickets;

-- Модуль для работы с паролями / хэшами и т.д.
create extension if not exists pgcrypto;

-- Функция проверяющая, что при добавлении места в таблицу с билетами такое место действительно существует
create or replace
    function CheckSeatNoExists() returns trigger
as
$$
begin
    if not exists
        (
            select *
            from Seats
                     natural join Flights
            where SeatNo = new.SeatNo
              and FlightId = new.FlightId
        )
    then
        raise exception 'Could not insert or update booking with seat number that not presented in seats';
    end if;
    return new;
end;
$$ language plpgsql;

-- Тригер для проверки,
-- что место для покупки / брони  существует в данном самолете (список мест можем получить из таблицы Flights)
create trigger CheckSeatExistsInFlight
    before
        insert or
        update of SeatNo
    on Tickets
    for each row
execute function CheckSeatNoExists();

-- Функция проверяющая аутентификацию пользователя
create or replace
    function CheckAuth(_UserId int, _Pass varchar(50)) returns boolean
as
$$
begin
    return exists(
            select 1
            from Users
            where UserId = _UserId
              and PassHash = crypt(_Pass, PassSalt)
        );
end;
$$ language plpgsql;

-- Функция проверяющая, что в будущем рейсе место :SeatNo свободно для покупки / брони,
create or replace
    function IsPlaceFree(_FlightId int, _SeatNo varchar(4)) returns boolean
as
$$
begin
    return exists(
            select *
            from FreeSeatsView
                     natural join FutureFlights
            where FlightId = _FlightId
              and SeatNo = _SeatNo
        );
end;
$$ language plpgsql;