drop view if exists CurrentReservedSeats cascade;
drop view if exists BoughtSeats cascade;
drop view if exists ReservedOrBoughtSeats cascade;
drop view if exists FutureFlights cascade;
drop table if exists Tickets, Users, Flights, Planes, Seats cascade;

drop type if exists SeatStatusType;


-- Перечисление для статуса места (зарезервировано / выкуплено)
create type SeatStatusType as enum ('reserved', 'bought');

-- Таблица для хранения самолетов
create table Planes
(
    PlaneId   int primary key,
    PlaneName varchar(100)
);

-- Таблица для хранения вылетов (id, самолет, время отправления)
create table Flights
(
    FlightId   int primary key,
    PlaneId    int references Planes (PlaneId),
    FlightTime timestamp not null
);

-- Таблица со всеми местами для определенного самолета
create table Seats
(
    PlaneId int references Planes (PlaneId),
    SeatNo  varchar(4),
    primary key (PlaneId, SeatNo)
);

-- Таблица с пользователями (пароль хранить явно не будем, вместо этого храним hash, salt)
create table Users
(
    UserId   int primary key,
    UserName varchar(100) not null,
    PassHash varchar(50)  not null,
    PassSalt varchar(50)  not null
);

-- Таблица с зарезервированными и выкупленными билетами,
-- храним пользователя (может быть null), перелет, место в самолете, статус билета, время резервации / покупки
-- P.S статус может быть только в двух состояниях,
-- потому что мы можем получить свободные билеты из таблицы Seats однозначно.
create table Tickets
(
    --  Может быть null в случае анонимной покупки.
    UserId          int,
    FlightId        int references Flights (FlightId),
    SeatNo          varchar(4)     not null,
    SeatStatus      SeatStatusType not null,

--  В случае резервации выступает как время брони, в случае покупки билета, время покупки.
    ReservationTime timestamp      not null,

    constraint ReservationTimeLessThanNow check ( ReservationTime <= now() ),
    constraint TicketsPK unique (FlightId, SeatNo)
);

-- View для получение рейсов, которые еще не состоялись.
create or replace view FutureFlights as
select FlightId, PlaneId
from Flights
where FlightTime >= now();

-- Вью для получения актуальных броней
create or replace view CurrentReservedSeats
as
select UserId, FlightId, SeatNo
from Tickets
         natural join FutureFlights
where SeatStatus = 'reserved'
  and Tickets.ReservationTime + make_interval(days := 3) >= now();

-- Вью для получения всех когда-либо купленных мест
create or replace view BoughtSeats
as
select FlightId, SeatNo
from Tickets
where SeatStatus = 'bought';

-- Вью для получения свободных мест,
-- в случае если рейс уже состоялся возвращаются все не выкупленные места,
-- в случае если рейс не состоялся возвращаются не забронированные и не выкупленные места
create or replace view FreeSeatsView as
select FlightId, SeatNo
from Seats
         natural join Flights
except
select FlightId, SeatNo
from BoughtSeats
except
select FlightId, SeatNo
from CurrentReservedSeats;

-- Вью считающая основные статистики для заданий 6-7
create or replace view FlightsUsersStats as
select FlightId,
       UserId,
       Flights.flighttime >= now()
           and count(FreeSeats.SeatNo) + count(ReservedSeats.UserId = Users.UserId) > 0 as CanBuy,
       Flights.FlightTime >= now() and count(FreeSeats.SeatNo) > 0                      as CanReserve,
       count(FreeSeats.SeatNo)                                                          as Free,
       count(Seats.SeatNo) - count(BoughtSeats.seatno) - count(FreeSeats.seatno)        as Reserved,
       count(BoughtSeats.SeatNo)                                                        as Bought
from Flights
         cross join Users
         natural left join Seats
         natural left join FreeSeatsView FreeSeats
         natural left join BoughtSeats
         natural left join CurrentReservedSeats ReservedSeats
group by FlightId, UserId;