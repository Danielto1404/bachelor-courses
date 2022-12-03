package main.java.com.github.danielto1404.sd.refactoring;


import main.java.com.github.danielto1404.sd.refactoring.html.ItemHtmlFormatter;
import main.java.com.github.danielto1404.sd.refactoring.orm.item.ItemMapper;
import main.java.com.github.danielto1404.sd.refactoring.orm.item.ItemOrmManager;
import main.java.com.github.danielto1404.sd.refactoring.orm.item.ItemOrmManagerImpl;
import main.java.com.github.danielto1404.sd.refactoring.servlet.AddItemServlet;
import main.java.com.github.danielto1404.sd.refactoring.servlet.GetItemsServlet;
import main.java.com.github.danielto1404.sd.refactoring.servlet.QueryServlet;
import main.java.com.github.danielto1404.sd.refactoring.sql.SqlService;
import main.java.com.github.danielto1404.sd.refactoring.sql.SqlServiceImpl;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;

/**
 * @author Danielto1404
 */
public class Main {

    private final static String DB_PATH = "jdbc:sqlite:shop.db";
    private final static int PORT = 8081;
    private final static String ADD_ITEM_ENDPOINT = "/add-product";
    private final static String GET_ITEMS_ENDPOINT = "/get-products";
    private final static String QUERY_ENDPOINT = "/query";

    public static void main(String[] args) throws Exception {


        SqlService sqlRequestService = new SqlServiceImpl(DB_PATH);
        ItemMapper productMapper = new ItemMapper();
        ItemHtmlFormatter htmlFormatter = new ItemHtmlFormatter();

        ItemOrmManager ormManager = new ItemOrmManagerImpl(sqlRequestService, productMapper);
        ormManager.createTable();

        Server server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        registerServlet(context, new AddItemServlet(htmlFormatter, ormManager), ADD_ITEM_ENDPOINT);
        registerServlet(context, new GetItemsServlet(htmlFormatter, ormManager), GET_ITEMS_ENDPOINT);
        registerServlet(context, new QueryServlet(htmlFormatter, ormManager), QUERY_ENDPOINT);

        server.start();
        server.join();
    }

    private static void registerServlet(ServletContextHandler contextHandler, HttpServlet servlet, String url) {
        contextHandler.addServlet(new ServletHolder(servlet), url);
    }
}