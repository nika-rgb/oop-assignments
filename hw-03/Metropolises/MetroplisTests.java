package Metropolises;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import static org.junit.Assert.*;

public class MetroplisTests {
    Metropolis metr;

    @Before
    public void init(){
        metr = new Metropolis();
    }

    @Test
    public void MetropolisTests1(){
        Metropolis metr = new Metropolis();
        assertThrows(RuntimeException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                metr.getMetropolis();
            }
        });
        assertThrows(RuntimeException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                metr.getContinent();
            }
        });
        assertThrows(RuntimeException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                metr.getPopulation();
            }
        });
    }

    @Test
    public void MetropolisTests2(){
        metr.setMetropolis("abc");
        assertEquals("abc", metr.getMetropolis());
        metr.setMetropolis("bgd");
        assertEquals("bgd", metr.getMetropolis());
    }

    @Test
    public void MetropolisTests3(){
        metr.setMetropolis("gte");
        assertEquals("gte", metr.getMetropolis());
        metr.setContinent("abc");
        assertTrue(metr.getMetropolis().equals("gte") && metr.getContinent().equals("abc"));
        metr.setMetropolis("mlo");
        assertEquals("mlo", metr.getMetropolis());
        metr.setContinent("lll");
        assertEquals("lll", metr.getContinent());
    }

    @Test
    public void MetropolisTests4(){
        metr.setContinent("aaaa");
        metr.setMetropolis("bbbb");
        metr.setPopulation(125);
        assertEquals(125, metr.getPopulation());
        assertEquals("bbbb", metr.getMetropolis());
        assertEquals("aaaa", metr.getContinent());
        metr.setPopulation(0);
        assertEquals(0, metr.getPopulation());
    }

    @Test
    public void MetropolisTests5(){
        Assert.assertThrows(IllegalArgumentException.class, new ThrowingRunnable() {
            @Override
            public void run() throws Throwable {
                metr.setPopulation(-10);
            }
        });
    }


}
