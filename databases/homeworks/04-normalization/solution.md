## 1. Первая нормальная форма

### 1.1. Описание

```
ФЗ:

StudentId -> StudentName, GroupId
GroupId -> GroupName
CourseId -> CourseName
LecturerId -> LecturerName
CourseId, GroupId -> LecturerId
CourseId, StudentId -> Mark
GroupName -> GroupId

Заданное отношение: (StudentId, StudentName, GroupId, GroupName, CourseId, CourseName, LecturerId, LecturerName, Mark)

Для того чтобы отношение было в 1НФ необходимо:
1) В отношении нет повторяющихся групп (так как все атрибуты различны по смыслу)
2) Все атрибуты атомарны (у нас ограничение на то что у студента по одному предмету не может быть множества оценок и т.д)
3) У отношения есть ключ. Ключ у нашего отношения (StudentId, CourseId) - был получен в ДЗ №3

Вывод: наше отношение находится в 1НФ
```

### 1.2. Результат

```
(StudentId, StudentName, GroupId, GroupName, CourseId, CourseName, LecturerId, LecturerName, Mark) => (StudentId, StudentName, GroupId, GroupName, CourseId, CourseName, LecturerId, LecturerName, Mark);
```

## 2. Вторая нормальная форма

### 2.1 Описание

```
Для того чтобы отношение было в 2НФ нужно:
1) Чтобы отношение было в 1НФ - выполнено по предыдущему пункту
2) Неключевые атрибуты функционально зависели от ключа в целом – не выполнено т.к,
ключ имеет вид (StudentId, CourseId) при этом имеются ФЗ, зависящие от части ключа:
StudentId -> StudentName, GroupId
GroupId, CourseId -> LecturerId
CourseId -> CourseName
и т.д 

Исправим второй пункт, чтобы привести отношение к 2НФ:
Сделаем декомпозиции по «мешающим» ФЗ (суффикс _key будет означать что атрибут является частью первичного ключа):


2.1) Устраним мешающую ФЗ: CourseId -> CourseName

Декомпозируем исходное отношение: (CourseId_key, StudentId_key, StudentName, GroupId, GroupName, CourseName, LecturerId, LecturerName, Mark) 

(CourseId_key, CourseName)  
(CourseId_key, StudentId_key, StudentName, GroupId, GroupName, LecturerId, LecturerName, Mark)

Можно заметить, что (CourseId_key, CourseName) находится в НФБК, так как CourseId_key - ключ (то есть и надключ).
Также можно заметить что (CourseId_key, CourseName) находится в 5НФ, так как у этого отношения - простой ключ CourseId_key
(по теореме Дейта-Фейгина 1)

2.2) По транзитивности ФЗ получим: 
CourseId, GroupId -> LecturerId, LecturerName, GroupName

Устраним мешающую ФЗ: CourseId, GroupId -> LecturerId, LecturerName, GroupName:

Декомпозируем (CourseId_key, StudentId_key, StudentName, GroupId, GroupName, LecturerId, LecturerName, Mark)

(CourseId_key, GroupId_key, LecturerId, LecturerName, GroupName)
(CourseId_key, StudentId_key, StudentName, GroupId, Mark)


2.3) Устраним мешающую ФЗ: StudentId -> StudentName, GroupId
Декомпозируем (CourseId_key, StudentId_key, StudentName, GroupId, Mark):

(StudentId_key, StudentName, GroupId) - 5НФ (по аналогии с (CourseId, CourseName))
(StudentId_key, CourseId_key, Mark)   – НФБК (так как StudentId, CourseId – надключ) 

2.4) Устраним мешающую ФЗ: GroupId -> GroupName:

Декомпозируем (CourseId_key, GroupId_key, LecturerId, LecturerName, GroupName)

(GroupId_key, GroupName_key2) - 5НФ (по аналогии с (CourseId, CourseName))
(CourseId_key, GroupId_key, LecturerId, LecturerName) – 2НФ (так как LecturerName транзитивно зависит от ключа: 
CourseId, GroupId -> LecturerId ; LecturerId -> LecturerName)


В итоге получим отношения:
(CourseId_key, CourseName) - 5НФ
(StudentId_key, StudentName, GroupId) - 5НФ
(StudentId_key, CourseId_key, Mark) – НФБК
(GroupId_key, GroupName_key2) - 5НФ
(CourseId_key, GroupId_key, LecturerId, LecturerName) - 2НФ
```

### 2.2 Результат

```
(StudentId, StudentName, GroupId, GroupName, CourseId, CourseName, LecturerId, LecturerName, Mark) => (CourseId, CourseName); (StudentId, StudentName, GroupId); (StudentId, CourseId, Mark); (GroupId, GroupName); (CourseId, GroupId, LecturerId, LecturerName);
```

## 3. Третья нормальная форма

### 3.1. Описание

```
Для того чтобы отношение было в 3НФ нужно:
1) Оно было в 2НФ (выполнено в 2 пункте)
2) Неключевые атрибуты непосредственно зависели бы от ключей

Из пункта 2 у нас осталось одно отношение в 2НФ (CourseId_key, GroupId_key, LecturerId, LecturerName), в котором неключевой 
атрибут LecturerName транзитивно зависит от ключа: CourseId, GroupId -> LecturerId ; LecturerId -> LecturerName
остальные отношения приведены к НФБК или к более сильным НФ.

Декомпозируем (CourseId_key, GroupId_key, LecturerId, LecturerName)

(CourseId_key, GroupId_key, LecturerId) – НФБК (так как CourseId, GroupId - ключ (то есть надключ))
(LecturerId_key, LecturerName)          - 5НФ (по аналогии с (CourseId, CourseName))
```

### 3.2. Результат

```
(CourseId, CourseName) => (CourseId, CourseName);
(StudentId, StudentName, GroupId) => (StudentId, StudentName, GroupId);
(StudentId, CourseId, Mark) => (StudentId, CourseId, Mark); 
(GroupId, GroupName) => (GroupId, GroupName); 
(CourseId, GroupId, LecturerId, LecturerName) => (CourseId, GroupId, LecturerId); (LecturerId, LecturerName);
```

## Б. Нормальная форма Бойса-Кодда

### Б.1. Описание

```Для того чтобы отношение было в НФБК нужно:
1) В каждой нетривиальной функциональной зависимости X -> Y, X должен являться надключом

Все отношения из пункта 3 уже находятся в НФБК или более сильных НФ.
```

### Б.2. Результат

 ```
(CourseId, CourseName) => (CourseId, CourseName);
(LecturerId, LecturerName) => (LecturerId, LecturerName);
(GroupId, GroupName) => (GroupId, GroupName); 
(StudentId, StudentName, GroupId) => (StudentId, StudentName, GroupId);
(StudentId, CourseId, Mark) => (StudentId, CourseId, Mark); 
(CourseId, GroupId, LecturerId) => (CourseId, GroupId, LecturerId);
```

## 4. Четвёртая нормальная форма

### 4.1. Описание

```
Для того чтобы отношение было в 4НФ нужно одно из следующих условий:
1) Для каждой нетривиальной МЗ X ->> Y|Z и атрибута A: X -> A
2) Для каждой нетривиальной МЗ X ->> Y|Z и X – надключ
3) Каждая нетривиальная МЗ является ФЗ и отношение находится в НФБК

В нашем случае только два отношения ( (CourseId, GroupId, LecturerId); (StudentId, CourseId, Mark) ) остались в НФБК:
остальные отношения приведены к 5НФ.

Выпишем нетривиальные МЗ для отношения (StudentId, CourseId, Mark):

StudentId ->> CourseId  | Mark     (у студента оценки по всем курсам одинаковые)
CourseId  ->> StudentId | Mark     (по курсу у всех студентов одинаковая оценка)
Mark      ->> StudentId | CourseId (для заданной оценки множество студентов имеют по всем курсам)

{} ->> StudentId | CourseId Mark      (студенты не зависят от сутдентов)
{} ->> Mark      | CourseId StudentId (оценки не зависят от студентов и курсов)
{} ->> CourseId  | StudentId Mark     (курсы не зависят от оценок и студентов)

Контрпример к StudentId ->> CourseId | Mark 
StudentId CourseId Mark 
1         1        A
1         2        B

Выше приведена валидная табличка значит множественная зависимость: StudentId ->> CourseId | Mark - не выполняется
Аналогично можно привести контрпримеры к остальным нетривиальным МЗ.
Итого наши нетривиальные множественные зависимости для (StudentId, CourseId, Mark) - не выполняются 

По аналогии тоже самое можно доказать для отношения (GroupId, CourseId LecturerId)

Итого у нас нет ни одной нетривиальной зависимости => рассматриваемые отношения находятся в 4НФ.
```

### 4.2. Результат

```
(CourseId, CourseName) => (CourseId, CourseName);
(LecturerId, LecturerName) => (LecturerId, LecturerName);
(GroupId, GroupName) => (GroupId, GroupName); 
(StudentId, StudentName, GroupId) => (StudentId, StudentName, GroupId);
(StudentId, CourseId, Mark) => (StudentId, CourseId, Mark); 
(CourseId, GroupId, LecturerId) => (CourseId, GroupId, LecturerId);
```

## 5. Пятая нормальная форма

### 5.1. Описание

```
Для того чтобы отношение было в 5НФ необходимо:
1) Для каждой нетривиальной ЗС *{X1,X2,...,Xn} каждое X_i – надключ

Не будем рассматривать тривиальные ЗС. 
Так же ЗС размера 4 и больше, так как какой-нибудь из элементов будет надмножеством другого и 
следовательно можно свести это все к рассмотрению ЗС меньшего размера.

Итого нас интересуют ЗС размера 1 и ЗС размера 2:

Рассмотрим ЗС: (CourseId, StudentId, Mark)
Рассмотрим контрпример:
StudentId, CourseId, Mark
1          1         A 
1          2         B

π_CourseId(R) ⋈ π_StudentId(R) ⋈ π_Mark(R) != R => ЗС не выполняется

По аналогии можно рассмотреть этот же контрпример для размера 2: (CourseId StudentId, StudentId Mark, CourseId Mark)
Получаем, что для (CourseId, StudentId, Mark) - нет нетривиальных ЗС

Аналогично доказываем для (CourseId, GroupId, LecturerId)

Итого у нас нет нетривиальных ЗС => все отношения находятся в 5НФ
```

### 5.2. Результат
```
(CourseId, CourseName) => (CourseId, CourseName);
(LecturerId, LecturerName) => (LecturerId, LecturerName);
(GroupId, GroupName) => (GroupId, GroupName); 
(StudentId, StudentName, GroupId) => (StudentId, StudentName, GroupId);
(StudentId, CourseId, Mark) => (StudentId, CourseId, Mark); 
(CourseId, GroupId, LecturerId) => (CourseId, GroupId, LecturerId);
```