create
transaction isolation level read committed;
select Reserve(:userId, :userPass, :flightId, :seatNo);
commit;

create
transaction isolation level repeatable read;
select ExtendReservation(:userId, :userPass, :flightId, :seatNo);
commit;

create
transaction isolation level read committed;
select BuyFree(:flightId, :seatNo);
commit;

create
transaction isolation level repeatable read;
select BuyReserved(:userId, :userPass, :flightId, :seatNo);
commit;