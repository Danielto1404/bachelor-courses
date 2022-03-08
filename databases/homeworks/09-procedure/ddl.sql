create extension if not exists pgcrypto;

insert
into Planes (planeid, planename)
values (1, 'Airbus A300'),
       (2, 'SuperJet'),
       (3, 'Boeing 777'),
       (4, 'Boeing 777'),
       (5, 'Bombardier'),
       (6, 'Fokker');

insert into Flights (flightid, flighttime, planeid)
values (1, now() + make_interval(days := 1), 1),
       (2, now() + make_interval(days := 2), 2),
       (3, now() - make_interval(days := 4), 3),
       (4, now() + make_interval(days := 3), 4),
       (5, now() - make_interval(days := 2), 2),
       (6, now() + make_interval(days := 3), 1),
       (7, now() - make_interval(days := 1), 5);

insert into Seats (planeid, seatno)
values (1, 'A1'),
       (1, 'A2'),
       (1, 'A3'),
       (1, 'A4'),
       (2, 'S1'),
       (2, 'S2'),
       (2, 'S3'),
       (3, 'B1'),
       (3, 'B2'),
       (4, 'B1'),
       (4, 'B2'),
       (6, 'A1'),
       (6, 'A2'),
       (6, 'A3'),
       (6, 'A4');

insert into Users (userid, username, passhash, passsalt)
values (1, 'vasya', crypt('0000', '$1$mD3mWL5b'), '$1$mD3mWL5b'),
       (2, 'petya', crypt('1234', '$1$3VaW7Lsp'), '$1$3VaW7Lsp'),
       (3, 'sasha', crypt('1111', '$1$fPOZG/6/'), '$1$fPOZG/6/'),
       (4, 'kostya', crypt('2222', '$1$Tu3JDFz3'), '$1$Tu3JDFz3'),
       (5, 'kostya', crypt('3333', '$1$tgZ5LOj.'), '$1$tgZ5LOj.'),
       (6, 'KOSDICK', crypt('4444', '$1$BjNjj/pP'), '$1$BjNjj/pP');

insert into Tickets (userid, flightid, seatno, SeatStatus, reservationtime)
values (1, 1, 'A1', 'bought', now() - make_interval(days := 5)),
       (1, 6, 'A2', 'bought', now() - make_interval(hours := 3)),
       (2, 1, 'A2', 'reserved', now() - make_interval(days := 1)),
       (3, 2, 'S1', 'reserved', now() - make_interval(days := 4));