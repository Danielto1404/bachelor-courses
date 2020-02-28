package ru.ifmo.rain.korolev.student;

import info.kgeorgiy.java.advanced.student.Group;
import info.kgeorgiy.java.advanced.student.Student;
import info.kgeorgiy.java.advanced.student.StudentGroupQuery;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class StudentDB implements StudentGroupQuery {


    /*
     * Student Queries
     */

    // Class variables

    private static final String EMPTY_STRING = "";


    // Student comparators

    private static final Comparator<Student> STUDENT_BY_NAME_COMPARATOR =
            Comparator.comparing(Student::getLastName)
                    .thenComparing(Student::getFirstName)
                    .thenComparing(Student::getId);

    private static final Comparator<Student> STUDENT_BY_ID_COMPARATOR =
            Comparator.comparingInt(Student::getId);


    // Student auxiliary functions

    private static Function<Student, String> GET_FULL_NAME =
            student -> student.getFirstName() + " " + student.getLastName();

    private static <R> Stream<R> mappingStream(List<Student> students,
                                               Function<Student, ? extends R> mapper) {
        return students
                .stream()
                .map(mapper);
    }


    private static <R> List<R> listMappingQuery(List<Student> students, Function<Student, ? extends R> mapper) {
        return mappingStream(students, mapper)
                .collect(Collectors.toList());
    }

    private static List<Student> listSortingQuery(Collection<Student> students, Comparator<Student> comparator) {
        return students
                .stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private static <R> R findQuery(Collection<Student> students,
                                   Predicate<Student> predicate,
                                   Comparator<Student> comparator,
                                   Collector<Student, ?, R> collector) {
        return students
                .stream()
                .filter(predicate)
                .sorted(comparator)
                .collect(collector);
    }

    private static <R> List<Student> findQueryByKey(Collection<Student> students,
                                                    Function<Student, R> mapper,
                                                    R key) {
        return findQuery(
                students,
                student -> key.equals(mapper.apply(student)),
                STUDENT_BY_NAME_COMPARATOR,
                Collectors.toList());
    }

    // Student methods implementation
    @Override
    public List<String> getFirstNames(List<Student> students) {
        return listMappingQuery(students, Student::getFirstName);
    }

    @Override
    public List<String> getLastNames(List<Student> students) {
        return listMappingQuery(students, Student::getLastName);
    }

    @Override
    public List<String> getGroups(List<Student> students) {
        return listMappingQuery(students, Student::getGroup);
    }

    @Override
    public List<String> getFullNames(List<Student> students) {
        return listMappingQuery(students, GET_FULL_NAME);
    }

    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return mappingStream(students, Student::getFirstName)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public String getMinStudentFirstName(List<Student> students) {
        return students
                .stream()
                .min(STUDENT_BY_ID_COMPARATOR)
                .map(Student::getFirstName)
                .orElse(EMPTY_STRING);
    }

    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return listSortingQuery(students, STUDENT_BY_ID_COMPARATOR);
    }

    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return listSortingQuery(students, STUDENT_BY_NAME_COMPARATOR);
    }

    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return findQueryByKey(students, Student::getFirstName, name);
    }

    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return findQueryByKey(students, Student::getLastName, name);
    }

    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, String group) {
        return findQueryByKey(students, Student::getGroup, group);
    }

    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, String group) {
        return findQuery(
                students,
                student -> group.equals(student.getGroup()),
                Comparator.naturalOrder(),
                Collectors.toMap(
                        Student::getLastName, Student::getFirstName,
                        BinaryOperator.minBy(String::compareTo)));
    }


    /*
     * Group methods implementation
     */

    // Group comparators

    private static final Comparator<Group> GROUP_BY_NAME_COMPARATOR =
            Comparator.comparing(Group::getName);


    // Group auxiliary functions

    private static <K> Stream<Map.Entry<K, List<Student>>> studentEntryStream(Collection<Student> students,
                                                                              Function<Student, K> keyFunction) {
        return students
                .stream()
                .collect(Collectors.groupingBy(keyFunction))
                .entrySet()
                .stream();
    }

    private static Function<Map.Entry<String, List<Student>>, Group> groupConstructor(Comparator<Student> comparator) {
        return (Map.Entry<String, List<Student>> e) ->
                new Group(e.getKey(), listSortingQuery(e.getValue(), comparator));
    }

    private static Stream<Group> sortedGroupStream(Collection<Student> students,
                                                   Comparator<Student> comparator) {

        return studentEntryStream(students, Student::getGroup)
                .map(groupConstructor(comparator))
                .sorted(GROUP_BY_NAME_COMPARATOR);
    }

    private static List<Group> listGroupQuery(Collection<Student> students, Comparator<Student> comparator) {
        return sortedGroupStream(students, comparator)
                .collect(Collectors.toList());
    }

    private static String maxGroupNameQuery(Collection<Student> students, Comparator<Group> comparator) {
        return sortedGroupStream(students, Comparator.naturalOrder())
                .max(comparator)
                .map(Group::getName)
                .orElse(EMPTY_STRING);
    }

    private static Comparator<Group> maxNameGroupPostComparator(Function<Group, Integer> function) {
        return Comparator.comparing(function)
                .thenComparing(Comparator.comparing(Group::getName).reversed());
    }

    // Group methods implementation

    @Override
    public List<Group> getGroupsByName(Collection<Student> students) {
        return listGroupQuery(students, STUDENT_BY_NAME_COMPARATOR);
    }

    @Override
    public List<Group> getGroupsById(Collection<Student> students) {
        return listGroupQuery(students, STUDENT_BY_ID_COMPARATOR);
    }

    @Override
    public String getLargestGroup(Collection<Student> students) {
        return maxGroupNameQuery(
                students,
                maxNameGroupPostComparator(group -> group.getStudents().size()));
    }

    @Override
    public String getLargestGroupFirstName(Collection<Student> students) {
        return maxGroupNameQuery(students,
                maxNameGroupPostComparator(group ->
                        Math.toIntExact(mappingStream(group.getStudents(), Student::getFirstName).distinct().count())));
    }
}