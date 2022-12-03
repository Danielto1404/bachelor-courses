package main.java.com.github.danielto1404.sd.refactoring.servlet;

import main.java.com.github.danielto1404.sd.refactoring.html.ItemHtmlFormatter;
import main.java.com.github.danielto1404.sd.refactoring.orm.item.Item;
import main.java.com.github.danielto1404.sd.refactoring.orm.item.ItemOrmManager;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Optional;

/**
 * @author Danielto1404
 */
public class QueryServlet extends AbstractServlet {

    public QueryServlet(ItemHtmlFormatter htmlFormatter, ItemOrmManager ormManager) {
        super(htmlFormatter, ormManager);
    }

    @Override
    public void doGet(HttpServletRequest request, PrintWriter writer) {
        String command = getParameter(request, writer, "command");
        String content = switch (command) {
            case "max" -> productListPage(ormManager.getMostExpensive());
            case "min" -> productListPage(ormManager.getLessExpensive());
            case "sum" -> htmlFormatter.contentPage("Summary price: " + ormManager.getPriceSum());
            case "count" -> htmlFormatter.contentPage("Number of products: " + ormManager.getAmount());
            default -> htmlFormatter.errorPage("Unknown command: " + command);
        };

        writer.println(content);
    }

    private String productListPage(Optional<Item> productSupplier) {
        String content = productSupplier
                .map(product -> htmlFormatter.itemsList(Collections.singletonList(product)))
                .orElse("No products found");

        return htmlFormatter.contentPage(content);
    }
}