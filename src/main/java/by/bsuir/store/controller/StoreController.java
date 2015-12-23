package by.bsuir.store.controller;

import by.bsuir.store.resource.ConfigurationManager;
import by.bsuir.store.resource.MessageManager;
import by.bsuir.store.controller.command.ICommand;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig
@SuppressWarnings("serial")
@WebServlet("/controller")
public class StoreController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final static ActionFactory actionFactory = new ActionFactory();
    private static final String INDEX_PAGE = "path.page.index";
    private static final String MESSAGE = "message";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String page = null;
        ICommand command = null;
        command = actionFactory.defineCommand(request);
        page = command.execute(request);

        if (page != null) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
            dispatcher.forward(request, response);
        } else {
            page = ConfigurationManager.getProperty(INDEX_PAGE);
            request.getSession().setAttribute(MESSAGE,
                    MessageManager.NULL_PAGE);
            response.sendRedirect(request.getContextPath() + page);
        }
    }
}
