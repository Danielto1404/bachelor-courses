package main.java.com.github.danielto1404.sd.refactoring.servlet;

import main.java.com.github.danielto1404.sd.refactoring.html.ItemHtmlFormatter;
import main.java.com.github.danielto1404.sd.refactoring.orm.item.ItemOrmManager;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;

/**
 * @author Danielto1404
 */
public class GetItemsServlet extends AbstractServlet {

    public GetItemsServlet(ItemHtmlFormatter httpFormatter, ItemOrmManager ormManager) {
        super(httpFormatter, ormManager);
    }

    @Override
    public void doGet(HttpServletRequest request, PrintWriter writer) {
        writer.println(htmlFormatter.contentPage(htmlFormatter.itemsList(ormManager.retrieveAll())));
    }
}
