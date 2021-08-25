package WebServlets;

import DBManager.DBManager;
import Product.Product;
import shoppingCart.cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/shopping-cart")
public class shoppingCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cart currentCart = (cart) req.getSession().getAttribute("cart");
        HashMap <Product, Integer> alreadyBought = currentCart.getAllProducts();
        req.setAttribute("alreadyBought", alreadyBought);
        req.setAttribute("spentMoney", currentCart.spentMoney());
        req.getRequestDispatcher("WEB-INF/shopping-cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBManager dbManager = (DBManager) req.getServletContext().getAttribute("DbManager");
        Product addedProduct = dbManager.getProduct(req.getParameter("productId"));
        cart currentCart = (cart) req.getSession().getAttribute("cart");
        currentCart.addItem(addedProduct);
        resp.sendRedirect("/shopping-cart");
    }
}
