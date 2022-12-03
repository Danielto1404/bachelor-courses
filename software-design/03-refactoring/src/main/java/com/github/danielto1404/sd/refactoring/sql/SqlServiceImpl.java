package main.java.com.github.danielto1404.sd.refactoring.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

public class SqlServiceImpl implements SqlService {

    private final String databaseURL;
    private Connection connection = null;


    public SqlServiceImpl(String databaseURL) {
        this.databaseURL = databaseURL;
    }

    @Override
    public List<Map<String, Object>> searchQuery(String request) {
        connect();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(request)) {
                List<Map<String, Object>> rows = new ArrayList<>();

                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();

                while (resultSet.next()) {
                    Map<String, Object> row = new HashMap<>();
                    for (int i = 1; i <= columnCount; i++) {
                        row.put(resultSetMetaData.getColumnName(i).toLowerCase(), resultSet.getObject(i));
                    }
                    rows.add(row);
                }

                return rows;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    format("Failed to execute search query: %s, reason: %s", request, e.getMessage()));
        }
    }

    @Override
    public void updateQuery(String request) {
        connect();
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(request);
        } catch (SQLException e) {
            throw new RuntimeException(
                    format("Failed to execute update query: %s, reason: %s", request, e.getMessage()));
        }
    }

    private void connect() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(databaseURL);
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    format("Failed to connect to a database \"%s\": %s", databaseURL, e.getMessage()));
        }
    }
}

