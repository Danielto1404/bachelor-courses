### Домашнее задание 7. Изменение данных

1. Напишите запросы, удаляющие студентов:
    1. Учащихся в группе :GroupId;
    2. Учащихся в группе :GroupName;
    3. Без оценок;
    4. Имеющих 3 и более оценки;
    5. Имеющих 3 и менее оценки;
    6. Студентов, c долгами (здесь и далее — по отсутствию оценки);
    7. Студентов, имеющих 2 и более долга;
    8. Студентов, имеющих не более 2 долгов.
2. Напишите запросы, обновляющие данные студентов:
    1. Изменение имени студента :StudentId на :StudentName;
    2. Перевод студента :StudentId из группы :FromGroupId в группу :GroupId;
    3. Перевод всех студентов из группы :FromGroupId в группу :GroupId;
    4. Перевод всех студентов из группы :FromGroupName в группу :GroupName;
    5. Перевод всех студентов из группы :FromGroupName в группу :GroupName только если целевая группа существует;
3. Напишите запросы, подсчитывающие статистику по студентам:
    1. Число оценок студента :StudentId (столбец Marks);
    2. Число оценок каждого студента (столбец Marks);
    3. Пересчет числа оценок каждого студента по данным из таблицы NewMarks (столбец Marks);
    4. Число сданных дисциплин каждого студента (столбец Marks);
    5. Число долгов студента :StudentId (столбец Debts);
    6. Число долгов каждого студента (столбец Debts);
    7. Число долгов каждого студента группы :GroupName (столбец Debts);
    8. Число оценок и долгов каждого студента (столбцы Marks, Debts);
4. Напишите запросы, обновляющие оценки, с учетом данных из таблицы NewMarks.
    1. Проставляющий новую оценку только если ранее оценки не было.
    2. Проставляющий новую оценку только если ранее оценка была.
    3. Проставляющий максимум из старой и новой оценки только если ранее оценка была.
    4. Проставляющий максимум из старой и новой оценки (если ранее оценки не было, то новую оценку).
5. Работа с представлениями
    1. Создайте представление StudentMarks в котором для каждого студента указано число оценок (
       столбцы StudentId, Marks);
    2. Создайте представление AllMarks в котором для каждого студента указано число оценок, включая оценки из таблицы
       NewMarks (столбцы StudentId, Marks);
    3. Создайте представление Debts в котором для каждого студента, имеющего долги указано их число (столбцы StudentId,
       Debts);
    4. Создайте представление StudentDebts в котором для каждого студента указано число долгов (столбцы StudentId,
       Debts);
6. Целостность данных.

Обратите внимание, что задания из этого раздела надо посылать в PCMS, но они будут проверяться только вручную после
окончания сдачи. То есть в PCMS вы получите + за любое решение.

В комментарии перед запросом укажите версию использованной СУБД.

1. Добавьте проверку того, что у студентов есть оценки только по предметам из их плана (NoExtraMarks).
2. Добавьте проверку того, что все студенты каждой группы имею оценку по одному и тому же набору курсов (SameMarks).
3. Создайте триггер PreserveMarks, не позволяющий уменьшить оценку студента по предмету. При попытке такого изменения
   оценка изменяться не должна.