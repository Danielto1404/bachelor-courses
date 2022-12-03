package main.java.com.github.danielto1404.sd.refactoring.orm.item;

import main.java.com.github.danielto1404.sd.refactoring.orm.service.OrmManager;

import java.util.Optional;

public interface ItemOrmManager extends OrmManager<Item> {
    int getAmount();

    Optional<Item> getMostExpensive();

    Optional<Item> getLessExpensive();

    int getPriceSum();
}
