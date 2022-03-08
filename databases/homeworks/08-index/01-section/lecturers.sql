-- ДЗ-5.3.4. Информацию о студентах с :Mark по предмету :LecturerName
-- ДЗ-5.6.1. StudentId имеющих хотя бы одну оценку у :LecturerName
-- ДЗ-5.6.2. Идентификаторы студентов по преподавателю :LecturerName
-- объяснение аналогичное Students hash (StudentId)
create unique index on Lecturers using hash (LecturerId);

-- ДЗ-5.3.4. Информацию о студентах с :Mark по предмету :LecturerName
-- ДЗ-5.6.1. StudentId имеющих хотя бы одну оценку у :LecturerName
-- ДЗ-5.6.2. Идентификаторы студентов по преподавателю :LecturerName
-- объяснение аналогичное Courses btree (CourseName, CourseId)
create index on Lecturers using btree (LecturerName, LecturerId);