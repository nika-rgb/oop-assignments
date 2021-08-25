package test;

import Product.Product;
import org.junit.jupiter.api.Test;

import java.awt.color.ProfileDataException;

import static org.junit.jupiter.api.Assertions.*;


public class ProductTest {

    @Test
    public void testProduct1(){
        Product prod = new Product("first", "productName", "img", 8);
        assertEquals("first", prod.getProductId());
        assertEquals( "img", prod.getImageFile());
        assertEquals(8, prod.getPrice());
        assertEquals("productName", prod.getItemName());
    }

    @Test
    public void testProduct2(){
        Product prod = new Product("second", "productName2", "img", 698);
        assertEquals("second", prod.getProductId());
        assertEquals("productName2", prod.getItemName());
        assertEquals("img", prod.getImageFile());
        assertEquals(prod.getPrice(), 698);
    }

    @Test
    public void testProduct3(){
        Product prod = new Product(new Product("third", "productName3", "img2", 52));
        assertEquals("third", prod.getProductId());
        assertEquals("productName3", prod.getItemName());
        assertEquals("img2", prod.getImageFile());
        assertEquals(52, prod.getPrice());
    }

    @Test
    public void testProduct4(){
        Product prod1 = new Product("fourth", "productName4", "img3", 26);
        Product prod2 = new Product("fourth", "productName5", "img4", 36);
        Product prod3 = new Product("fifth", "productName6", "img5", 89);
        assertTrue(prod1.equals(prod2));
        assertFalse(prod3.equals(prod2));
        assertFalse(prod3.equals(prod1));
    }

    @Test
    public void testProduct5(){
        Product prod1 = new Product("sixth", "productName7", "img1", 56);
        Product prod2 = new Product("sixth", "productName7", "img1", 56);
        assertEquals(prod1.hashCode(), prod2.hashCode());
    }

    @Test
    public void testProduct6(){
        Product prod1 = new Product("seven", "productName8", "img2", 899);
        Product prod2 = new Product(prod1);
        assertEquals(prod1.hashCode(), prod2.hashCode());
    }

    @Test
    public void testProduct7(){
        Product prod1 = new Product("eight", "productName9", "img3", 23);
        Product prod2= new Product("nine", "productName9", "img3", 23);
        assertTrue(prod1.compareTo(prod2) > 0);
    }

    @Test
    public void testProduct8(){
        Product prod1 = new Product("nine", "productName10", "img4", 36);
        Product prod2 = new Product("ten", "productName11", "img5", 36);
        assertTrue(prod2.compareTo(prod1) > 0);
    }

    @Test
    public void testProduct9(){
        Product prod1 = new Product("ten", "productName11", "img5", 369);
        Product prod2 = new Product("ten", "productName12", "img6", 256);
        assertTrue(prod1.equals(prod2));
    }

    @Test
    public void testProduct10(){
        Product prod1 = new Product("eleven", "productName12", "img6", 7889);
        Product prod2= new Product("twelve", "productName12", "img7", 7889);
        assertTrue(prod1.hashCode() != prod2.hashCode());
    }

}
