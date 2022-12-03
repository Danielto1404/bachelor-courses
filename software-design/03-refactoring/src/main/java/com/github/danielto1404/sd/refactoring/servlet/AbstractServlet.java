package main.java.com.github.danielto1404.sd.refactoring.servlet;

import main.java.com.github.danielto1404.sd.refactoring.html.ItemHtmlFormatter;
import main.java.com.github.danielto1404.sd.refactoring.orm.item.ItemOrmManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class AbstractServlet extends HttpServlet {

    protected final ItemHtmlFormatter htmlFormatter;

    protected final ItemOrmManager ormManager;

    public AbstractServlet(ItemHtmlFormatter htmlFormatter, ItemOrmManager ormManager) {
        this.htmlFormatter = htmlFormatter;
        this.ormManager = ormManager;
    }

    public abstract void doGet(HttpServletRequest request, PrintWriter writer);

    @Override
    protected final void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doGet(request, response.getWriter());
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected String getParameter(HttpServletRequest request, PrintWriter writer, String parameterName) {
        String parameter = request.getParameter(parameterName);
        if (parameter == null) {
            writer.println(htmlFormatter.errorPage(String.format("No valid '%s' parameter given", parameterName)));
        }
        return parameter;
    }
}