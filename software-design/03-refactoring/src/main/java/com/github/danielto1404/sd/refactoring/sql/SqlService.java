package main.java.com.github.danielto1404.sd.refactoring.sql;

import java.util.List;
import java.util.Map;

public interface SqlService {
    List<Map<String, Object>> searchQuery(String request);

    void updateQuery(String request);
}
