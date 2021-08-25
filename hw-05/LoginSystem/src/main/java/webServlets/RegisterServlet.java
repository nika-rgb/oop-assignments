package webServlets;

import accountManager.accountManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/CreateAccount.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        accountManager accManager = (accountManager) req.getServletContext().getAttribute("accManager");
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        if(!accManager.containsAccount(userName)){
            accManager.addAccount(userName, password);
            req.getRequestDispatcher("WEB-INF/WelcomePage.jsp").forward(req, resp);
        }else{
            req.getRequestDispatcher("WEB-INF/AlreadyInUse.jsp").forward(req, resp);
        }
    }
}
