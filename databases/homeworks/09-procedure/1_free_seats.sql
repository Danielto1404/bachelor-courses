-- FreeSeats(FlightId) — список мест, доступных для продажи и для бронирования.
create or replace
    function FreeSeats(_FlightId int)
    returns table
            (
                SeatNo varchar(4)
            )
as
$$
begin
    return query
        select FreeSeatsView.SeatNo
        from FreeSeatsView
                 natural join FutureFlights
        where FlightId = _FlightId;
end ;
$$ language plpgsql;