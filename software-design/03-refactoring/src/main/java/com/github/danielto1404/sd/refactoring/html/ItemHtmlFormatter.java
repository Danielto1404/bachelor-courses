package main.java.com.github.danielto1404.sd.refactoring.html;

import main.java.com.github.danielto1404.sd.refactoring.orm.item.Item;

import java.util.List;
import java.util.stream.Collectors;

public class ItemHtmlFormatter implements HtmlFormatter<Item> {
    @Override
    public String contentPage(String content) {
        return String.format("<html><body>%s</body></html>", content);
    }

    @Override
    public String itemsList(List<Item> items) {
        return items.stream()
                .map(product -> String.format("%s\t%s</br>", product.name(), product.price()))
                .collect(Collectors.joining());
    }
}
