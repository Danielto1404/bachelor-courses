package main.java.com.github.danielto1404.sd.refactoring.orm.service.mapper;

import java.util.Map;

public interface Mapper<T> {
    T map(Map<String, Object> sqlRow);
}

