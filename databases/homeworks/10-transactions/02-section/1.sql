create
transaction isolation level read committed;
select FreeSeats(:flightId);
commit;