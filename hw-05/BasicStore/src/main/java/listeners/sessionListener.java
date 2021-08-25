package listeners;

import shoppingCart.cart;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
@WebListener
public class sessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        cart newCart = new cart();
        se.getSession().setAttribute("cart", newCart);
    }
}
