-- psql (PostgreSQL) 14.0

create or replace function preserve_mark() returns trigger
    language plpgsql as
$$
begin
    new.Mark := old.Mark;
    return new;
end;
$$;


create trigger PreserveMarks
    before update
    on Marks
    for each row
    when (old.Mark > new.Mark)
    execute function preserve_mark();