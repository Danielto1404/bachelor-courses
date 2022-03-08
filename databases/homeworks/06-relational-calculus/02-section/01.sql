select StudentId,
       StudentName,
       G.GroupName
from Students S,
     Groups G
where S.GroupId = G.GroupId