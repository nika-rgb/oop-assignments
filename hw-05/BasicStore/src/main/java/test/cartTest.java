package test;

import Product.Product;
import org.junit.jupiter.api.Test;
import shoppingCart.cart;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class cartTest {

    @Test
    public void testCart1(){
        cart newCart = new cart();
        assertEquals(0, newCart.spentMoney());
        assertEquals(0, newCart.numDistinctItems());
    }

    @Test
    public void testCart2(){
        cart newCart = new cart();
        newCart.addItem(new Product("1", "first", "img1", 45));
        assertEquals(1, newCart.numDistinctItems());
        assertEquals(45, newCart.spentMoney());
    }

    @Test
    public void testCart3(){
        cart newCart = new cart();
        newCart.addItem(new Product("2", "second", "img2", 56));
        newCart.addItem(new Product("3", "third", "img3", 44));
        assertEquals(2, newCart.numDistinctItems());
        assertEquals(100, newCart.spentMoney());
    }

    @Test
    public void testCart4(){
        cart newCart = new cart();
        Product prod1 = new Product("2", "second", "img3", 58);
        Product prod2 = new Product("3", "third", "img2", 786);
        newCart.addItem(prod1);
        newCart.addItem(prod2);
        newCart.addItem(new Product("4", "fourth", "img4", 56));
        assertEquals(58 + 786 + 56, newCart.spentMoney());
        assertEquals(3, newCart.numDistinctItems());
        newCart.addItem(new Product("2", "second", "img3", 58));
        assertEquals(58 * 2 + 786 + 56, newCart.spentMoney());
        assertEquals(3, newCart.numDistinctItems());
        assertEquals(2, newCart.numBought(prod1));
        assertEquals(1, newCart.numBought(prod2));
    }

    @Test
    public void testCart5(){
        cart newCart = new cart();
        Product prod2 = new Product("3", "productId3", "img4", 236);
        Product prod1 = new Product(prod2);
        Product prod3 = new Product("4", "productId4", "img5", 2365);
        newCart.addItem(prod2);
        newCart.addItem(prod2);
        newCart.addItem(prod1);
        newCart.addItem(prod3);
        assertEquals(3, newCart.numBought(prod2));
        assertEquals(3073, newCart.spentMoney());
        assertEquals(2, newCart.numDistinctItems());
        assertEquals(1, newCart.numBought(prod3));
        newCart.setAmount(prod3, 3);
        assertEquals(7803, newCart.spentMoney());
        Product prod4 = new Product("5", "productId5", "img6", 450);
        newCart.setAmount(prod4, 10);
        assertEquals(3, newCart.numDistinctItems());
        assertEquals(10, newCart.numBought(prod4));
        assertEquals(3, newCart.numBought(prod3));
    }

    @Test
    public void testCart6(){
        cart newCart = new cart();
        Product prod2 = new Product("3", "productId3", "img4", 230);
        Product prod1 = new Product("1", "productId1", "img1", 250);
        Product prod3 = new Product("4", "productId4", "img5", 240);
        newCart.setAmount(prod1, 5);
        newCart.setAmount(prod2, 2);
        newCart.setAmount(prod3, 3);
        assertEquals(2430, newCart.spentMoney());
        assertEquals(3, newCart.numDistinctItems());
        newCart.setAmount(prod1, 0);
        assertEquals(1180, newCart.spentMoney());
        newCart.addItem(prod1);
        assertEquals(1430, newCart.spentMoney());
        Product prod4 = new Product("5", "productId5", "img5", 100);
        newCart.setAmount(prod4, 0);
        assertEquals(1430, newCart.spentMoney());
        newCart.setAmount(prod4, 3);
        assertEquals(1730, newCart.spentMoney());
    }

    @Test
    public void testCart7(){
        cart newCart = new cart();
        Product prod1 = new Product("1", "productId1", "img1", 100);
        Product prod2 = new Product("2", "productId2", "img2", 200);
        newCart.addItem(prod1);
        newCart.setAmount(prod2, 3);
        assertEquals(2, newCart.numDistinctItems());
        Set<Product> actual = newCart.getItemSet();
        assertTrue(actual.equals(new HashSet<Product>(Arrays.asList(prod1, prod2))));
        Product prod3 = new Product("3", "productId3", "img3", 300);
        newCart.setAmount(prod3, 3);
        actual = newCart.getItemSet();
        assertEquals(3, newCart.numDistinctItems());
        assertTrue(actual.equals(new HashSet<Product>(Arrays.asList(prod1, prod2, prod3))));
        assertEquals(100 + 600 + 900, newCart.spentMoney());
    }

    @Test
    public void testCart8(){
        cart newCart = new cart();
        Product prod1 = new Product("1", "productId1", "img1", 100);
        Product prod2 = new Product("2", "productId2", "img2", 200);
        Product prod3 = new Product("3", "productId3", "img3", 300);
        Product prod4 = new Product("4", "productId4", "img4", 400);
        newCart.addItem(prod1);
        newCart.setAmount(prod2, 3);
        newCart.setAmount(prod3, 5);
        newCart.setAmount(prod4, 6);
        assertEquals(4, newCart.numDistinctItems());
        assertEquals(6, newCart.numBought(prod4));
        assertEquals(1, newCart.numBought(prod1));
        assertEquals(5, newCart.numBought(prod3));
        assertEquals(4600, newCart.spentMoney());
        HashMap <Product, Integer> allProducts = newCart.getAllProducts();
        assertTrue(allProducts.keySet().equals(newCart.getItemSet()));
        for(Product current : allProducts.keySet()){
            assertTrue(allProducts.get(current) == newCart.numBought(current));
        }
    }

    @Test
    public void testCart9(){
        cart newCart = new cart();
        Product prod1 = new Product("1", "prod1", "img1", 20);
        Product prod2 = new Product("2", "prod2", "img2", 10);
        Product prod3 = new Product("3", "prod3", "img3", 30);
        newCart.setAmount(prod1, 2);
        newCart.setAmount(prod2, 0);
        newCart.setAmount(prod3, 2);
        newCart.setAmount(prod3, 0);
        HashMap <Product, Integer> allProducts = newCart.getAllProducts();
        assertTrue(allProducts.keySet().equals(newCart.getItemSet()));
        for(Product prod : allProducts.keySet()){
            assertTrue(allProducts.get(prod) == newCart.numBought(prod));
        }
    }
}
