### 1. Функциональные зависимости
```
StudentId -> StudentName, GroupId
GroupId -> GroupName
CourseId -> CourseName
LecturerId -> LecturerName
CourseId, GroupId -> LecturerId
CourseId, StudentId -> Mark
GroupName -> GroupId
```

## 2. Ключи

### 2.1. Процесс определения ключей
1. Определим тривиальный надключ `(StudentId, StudentName, GroupId, GroupName, CourseId, CourseName, LecturerId, LecturerName, Mark)`.
2. Зная, что `{CourseId, StudentId -> Mark}`, убираем `Mark`. Надключ принимает вид `(StudentId, StudentName, GroupId, GroupName, CourseId, CourseName, LecturerId, LecturerName)`.
3. Зная, что `{LecturerId -> LecturerName}`, убираем `LecturerName`. Надключ принимает вид `(StudentId, StudentName, GroupId, GroupName, CourseId, CourseName, LecturerId)`.
4. Зная, что `{CourseId -> CourseName}`, убираем `CourseName`. Надключ принимает вид `(StudentId, StudentName, GroupId, GroupName, CourseId, LecturerId)`.
5. Зная, что `{GroupId -> GroupName}`, убираем `GroupName`. Надключ принимает вид `(StudentId, StudentName, GroupId, CourseId, LecturerId)`.
6. Зная, что `{CourseId, GroupId -> LecturerId}`, убираем `LecturerId`. Надключ принимает вид `(StudentId, StudentName, GroupId, CourseId)`.
7. Зная, что `{StudentId -> StudentName, GroupId}`, убираем `StudentName` и `GroupId`. Надключ принимает вид `(StudentId, CourseId)`.
8. Оставшиеся атрибуты `StudentId` и `CourseId` не содержатся в правой части ни одной функциональной зависимости. Отсюда можно сделать вывод о том, что данные атрибуты независимы друг от друга, а следовательно наш надключ `(StudentId, CourseId)` является ключом.

### 2.2. Полученные ключи
```
{ StudentId, CourseId }
```

## 3. Замыкания множества атрибутов

### 3.1. GroupId, CourseId
```
{GroupId, CourseId}
{GroupId, CourseId, GroupName}
{GroupId, CourseId, GroupName, CourseName}
{GroupId, CourseId, GroupName, CourseName, LecturerId}
{GroupId, CourseId, GroupName, CourseName, LecturerId, LecturerName}
```

### 3.2. StudentId, CourseId
```
{StudentId, CourseId}
{StudentId, CourseId, StudentName}
{StudentId, CourseId, StudentName, GroupId}
{StudentId, CourseId, StudentName, GroupId, GroupName}
{StudentId, CourseId, StudentName, GroupId, GroupName, CourseName}
{StudentId, CourseId, StudentName, GroupId, GroupName, CourseName, LecturerId}
{StudentId, CourseId, StudentName, GroupId, GroupName, CourseName, LecturerId, LecturerName}
{StudentId, CourseId, StudentName, GroupId, GroupName, CourseName, LecturerId, LecturerName, Mark}
```

### 3.3. StudentId, LecturerId
```
{StudentId, LecturerId}
{StudentId, LecturerId, StudentName}
{StudentId, LecturerId, StudentName, GroupId}
{StudentId, LecturerId, StudentName, GroupId, GroupName}
{StudentId, LecturerId, StudentName, GroupId, GroupName, LecturerName}
```

## 4. Неприводимое множество функциональных зависимостей

### 4.1d. Первый этап

Расщепление правых частей. Делаем все правые части единичными
(в правой части содержится ровно один атрибут).

Пример: `{ X -> Y, Z } ===> { X -> Y,  X -> Z }`

У нас есть одна зависимость с не единичной правой частью:
`{ StudentId -> StudentName, GroupId }` из нее мы получаем две новых зависимости по правилу расщепления:
`{ StudentId -> StudentName, StudentId -> GroupId }`


### 4.1r. Результаты первого этапа
```
StudentId -> StudentName
StudentId -> GroupId
GroupId -> GroupName
CourseId -> CourseName
LecturerId -> LecturerName
CourseId, GroupId -> LecturerId
CourseId, StudentId -> Mark
GroupName -> GroupId
```

### 4.2d. Второй этап
Удаление атрибута X U {Z} -> Y.
Если Y ⊂ X+ над множеством ФЗ, то удаляем правило Z.

У нас есть 2 функциональные зависимости с не единичными левыми частями:

1. CourseId, GroupId -> LecturerId
	+ `{ CourseId }+ = { CourseId, CourseName }`
	+ `{ GroupId }+  = { GroupId, GroupName }`

2. CourseId, StudentId -> Mark
	+ `{ CourseId }+  = { CourseId, CourseName }`
	+ `{ StudentId }+ = { StudentId, StudentName, GroupId, GroupName }`

***Вывод: оставляем все функциональные зависимости.***

### 4.2r. Результаты второго этапа
```
StudentId -> StudentName
StudentId -> GroupId
GroupId -> GroupName
CourseId -> CourseName
LecturerId -> LecturerName
CourseId, GroupId -> LecturerId
CourseId, StudentId -> Mark
GroupName -> GroupId
```

### 4.3d. Третий этап
Удаление правила X -> Y. Пытаемся удалить по одной функциональной зависимости.
Если Y ⊂ X+ над множеством данных S без { X -> Y },
то удаляем ФЗ (S - множество функциональных зависимостей).

Пытаемся удалить правила: (предварительно убрав рассматриваемые правила из S):
1. StudentId -> StudentName
	+ `{ StudentId }+ = { StudentId, GroupId, GroupName }`
2. StudentId -> GroupId
	+ `{ StudentId }+ = { StudentId, StudentName }`
3. GroupId -> GroupName
	+ `{ GroupId }+ = { GroupId }`
4. CourseId -> CourseName
	+ `{CourseId}+ = {CourseId}`
5. LecturerId -> LecturerName
	+ `{ LecturerId }+ = { LecturerId }`
6. CourseId, GroupId -> LecturerId
	+ `{ CourseId, GroupId }+ = { GroupId, GroupName, CourseId, CourseName }`
7. CourseId, StudentId -> Mark
	+ `{ CourseId, StudentId }+ = { StudentId, StudentName, GroupId, GroupName, CourseId, CourseName, LecturerId, LecturerName }`
8. GroupName -> GroupId
	+ `{GroupName}+ = {GroupName}`

***Вывод: Оставляем все функциональные зависимости.***

### 4.3r. Результаты третьего этапа
```
StudentId -> StudentName
StudentId -> GroupId
GroupId -> GroupName
CourseId -> CourseName
LecturerId -> LecturerName
CourseId, GroupId -> LecturerId
CourseId, StudentId -> Mark
GroupName -> GroupId
```
