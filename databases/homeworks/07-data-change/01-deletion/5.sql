delete
from Students
where StudentId not in (
    select StudentId
    from Marks
    group by StudentId
    having count(*) > 3
)