package main.java.com.github.danielto1404.sd.refactoring.orm.item;

import main.java.com.github.danielto1404.sd.refactoring.orm.service.mapper.Mapper;
import main.java.com.github.danielto1404.sd.refactoring.sql.SqlService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

public record ItemOrmManagerImpl(SqlService sqlService, Mapper<Item> itemMapper) implements ItemOrmManager {


    @Override
    public String tableName() {
        return "ITEMS";
    }

    @Override
    public void createTable() {
        sqlService.updateQuery(format(
                "CREATE TABLE IF NOT EXISTS %s " +
                        "(" +
                        "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "NAME VARCHAR(255) NOT NULL," +
                        "PRICE INT NOT NULL" +
                        ")", tableName()
        ));
    }

    @Override
    public void insert(Item item) {
        sqlService.updateQuery(
                format("INSERT INTO %s (NAME, PRICE) VALUES (\"%s\", %s)",
                        tableName(),
                        item.name(),
                        item.price())
        );
    }

    @Override
    public List<Item> retrieveAll() {
        return mapItems(format("SELECT * FROM %s", tableName()));
    }

    public List<Item> mapItems(String query) {
        return sqlService.searchQuery(query)
                .stream()
                .map(itemMapper::map)
                .collect(Collectors.toList());
    }


    @Override
    public int getAmount() {
        return retrieveAll().size();
    }

    @Override
    public Optional<Item> getMostExpensive() {
        return mapItems(format("SELECT * FROM %s ORDER BY PRICE DESC LIMIT 1", tableName())).stream().findAny();
    }

    @Override
    public Optional<Item> getLessExpensive() {
        return mapItems(format("SELECT * FROM %s ORDER BY PRICE LIMIT 1", tableName())).stream().findAny();
    }

    @Override
    public int getPriceSum() {
        return getAggregationInt(format("SELECT SUM(PRICE) FROM %s", tableName()));
    }

    private static <R> R getSingleElement(List<R> list) {
        if (list.size() != 1) {
            throw new RuntimeException("Invalid SQL response");
        }
        return list.get(0);
    }


    private int getAggregationInt(String query) {
        List<Map<String, Object>> elements = sqlService.searchQuery(query);
        if (elements.isEmpty()) {
            throw new RuntimeException("Invalid SQL response for given query: " + query);
        }

        Map<String, Object> stringObjectMap = getSingleElement(sqlService.searchQuery(query));
        Object result = stringObjectMap.get(getSingleElement(new ArrayList<>(stringObjectMap.keySet())));

        if (!(result instanceof Integer)) {
            throw new RuntimeException("Invalid database response");
        }
        return (Integer) result;
    }
}
