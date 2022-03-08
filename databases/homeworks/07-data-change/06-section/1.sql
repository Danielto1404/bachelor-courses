-- psql (PostgreSQL) 14.0

create or replace function check_course_in_plan() returns trigger
    language plpgsql as
$$
begin
    if new.CourseId not in
       (
           select CourseId
           from Students
                    natural join Plan
           where StudentId = new.StudentId
       ) then
        raise exception 'Could not insert or update mark with course that not presented in plan';
    end if;
    return new;
end ;
$$;


create trigger NoExtraMarks
    before update or insert
    on Marks
    for each row
    execute function check_course_in_plan();