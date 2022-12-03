package main.java.com.github.danielto1404.sd.refactoring.servlet;

import main.java.com.github.danielto1404.sd.refactoring.html.ItemHtmlFormatter;
import main.java.com.github.danielto1404.sd.refactoring.orm.item.Item;
import main.java.com.github.danielto1404.sd.refactoring.orm.item.ItemOrmManager;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * @author Danielto1404
 */
public class AddItemServlet extends AbstractServlet {

    public AddItemServlet(ItemHtmlFormatter httpFormatter, ItemOrmManager ormManager) {
        super(httpFormatter, ormManager);
    }

    @Override
    public void doGet(HttpServletRequest request, PrintWriter writer) {
        String name = getParameter(request, writer, "name");
        String priceString = getParameter(request, writer, "price");

        int price;
        try {
            price = Integer.parseInt(priceString);
        } catch (NumberFormatException e) {
            writer.println("Failed due to \"price\" parameter invalidity");
            return;
        }

        ormManager.insert(new Item(price, name));
        writer.println("OK");
    }
}
