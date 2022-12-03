package main.java.com.github.danielto1404.sd.refactoring.html;

import java.util.List;

public interface HtmlFormatter<T> {
    String contentPage(String content);

    String itemsList(List<T> items);

    default String errorPage(String message) {
        return "Error occurred: " + message;
    }
}
