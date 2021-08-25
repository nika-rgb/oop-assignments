package WebServlets;

import DBManager.DBManager;
import Product.Product;
import com.sun.scenario.effect.impl.prism.PrDrawable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

@WebServlet("/")
public class HomePageServlet extends HttpServlet {
    @Override
     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBManager manager = (DBManager) request.getServletContext().getAttribute("DbManager");
        List<Product> products = manager.getAllProducts();
        request.setAttribute("prod", products);
        request.getRequestDispatcher("/WEB-INF/HomePage.jsp").forward(request, response);
    }
}
