package main.java.com.github.danielto1404.sd.refactoring.orm.item;

import main.java.com.github.danielto1404.sd.refactoring.orm.service.mapper.Mapper;
import main.java.com.github.danielto1404.sd.refactoring.orm.service.mapper.MapperException;

import java.util.Map;

import static java.lang.String.format;

public class ItemMapper implements Mapper<Item> {
    @Override
    public Item map(Map<String, Object> sqlRow) {
        int price = extractField(sqlRow, "price", Integer.class);
        String name = extractField(sqlRow, "name", String.class);
        return new Item(price, name);
    }

    public <R> R extractField(Map<String, Object> sqlRow, String fieldName, Class<R> expectedClass) {
        Object fieldValue = sqlRow.get(fieldName);
        if (fieldValue == null) {
            throw new MapperException(format("Field %s does not exists", fieldName));
        } else if (!fieldValue.getClass().equals(expectedClass)) {
            throw new MapperException(
                    format("Can't retrieve field for name: %s | Expected class %s, Actual class: %s",
                            fieldName,
                            expectedClass,
                            fieldValue.getClass())
            );
        }
        return expectedClass.cast(fieldValue);
    }
}
