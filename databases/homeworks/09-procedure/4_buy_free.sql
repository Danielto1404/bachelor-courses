drop function if exists BuyFree;

-- BuyFree(FlightId, SeatNo) — пытается купить свободное место.
-- Возвращает истину, если удалось и ложь — в противном случае.
create or replace function
    BuyFree(_FlightId int, _SeatNo varchar(4)) returns boolean
as
$$
begin
    if IsPlaceFree(_FlightId := _FlightId, _SeatNo := _SeatNo) then
        insert into Tickets (UserId, FlightId, SeatNo, SeatStatus, ReservationTime)
        values (null, _FlightId, _SeatNo, 'bought', now())
        on conflict on constraint TicketsPK do update set SeatStatus      = 'bought',
                                                          ReservationTime = now();
        return true;
    end if;
    return false;
end;
$$ language plpgsql;