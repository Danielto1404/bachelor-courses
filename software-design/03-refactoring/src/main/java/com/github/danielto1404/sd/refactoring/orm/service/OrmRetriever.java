package main.java.com.github.danielto1404.sd.refactoring.orm.service;

import java.util.List;

public interface OrmRetriever<T> {
    List<T> retrieveAll();
}
