package webServlets;

import accountManager.accountManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/")
public class HomePageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.getRequestDispatcher("WEB-INF/HomePage.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        accountManager manager = (accountManager) req.getServletContext().getAttribute("accManager");
        String userName = req.getParameter("Login");
        String password = req.getParameter("Password");
        System.out.println(userName);
        System.out.println(password);
        if (manager.containsAccount(userName) && manager.getPassword(userName).equals(password)){
            req.getRequestDispatcher("/WEB-INF/WelcomePage.jsp").forward(req, resp);
        }else{
            req.getRequestDispatcher("/WEB-INF/PleaseTryAgain.jsp").forward(req, resp);
        }
    }

}
