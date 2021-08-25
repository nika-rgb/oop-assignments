package WebServlets;

import DBManager.DBManager;
import Product.Product;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/show-product")
public class visualiseProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemId = request.getParameter("id");
        DBManager manager = (DBManager) request.getServletContext().getAttribute("DbManager");
        Product currentProduct = manager.getProduct(itemId);
        request.setAttribute("currentProduct", currentProduct);
        request.getRequestDispatcher("WEB-INF/show-product.jsp").forward(request, response);
    }

}
