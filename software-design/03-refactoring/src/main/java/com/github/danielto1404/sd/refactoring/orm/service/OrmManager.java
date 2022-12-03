package main.java.com.github.danielto1404.sd.refactoring.orm.service;

public interface OrmManager<T> extends OrmRetriever<T>, OrmInserter<T> {

    String tableName();

    void createTable();

}