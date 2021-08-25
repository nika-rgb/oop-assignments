package WebServlets;

import Product.Product;
import shoppingCart.cart;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@WebServlet("/update")
public class updateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        cart currentCart = (cart) req.getSession().getAttribute("cart");
        Set <Product> cartSet = currentCart.getItemSet();
        for(Product product : cartSet){
            String amount = req.getParameter(product.getProductId());
            int newAmount = Integer.parseInt(amount);
            currentCart.setAmount(product, newAmount);
        }
        resp.sendRedirect("/shopping-cart");
    }
}
