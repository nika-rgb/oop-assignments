import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class crackerTests {

    private final PrintStream stream = System.out;
    private ByteArrayOutputStream out;

    @Before
    public void init(){
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
    }

    @After
    public void after(){
        System.setOut(stream);
    }

    @Test
    public void crackerTests1(){
        //System.setOut(stream);
        Cracker.main(new String [] {"molly"});
        String result = out.toString();
        assertEquals("4181eecbd7a755d19fdf73887c54837cbecf63fd\r\n", result);
    }


    @Test
    public void crackerTests2(){
        Cracker.main(new String[] {"flomo"});
        String result = out.toString();
        assertEquals("886ffd41c568469795a19f52486bdde64f5f5bcc\r\n", result);
    }

    @Test
    public void crackerTests3(){
        Cracker.main(new String[] {"a"});
        String result = out.toString();
        assertEquals("86f7e437faa5a7fce15d1ddcb9eaeaea377667b8\r\n", result);
        init();
        Cracker.main(new String[] {"86f7e437faa5a7fce15d1ddcb9eaeaea377667b8", "3", "1"});
        assertEquals("a\r\nall done\r\n", out.toString());
    }

    @Test
    public void crackerTests4(){
        Cracker.main(new String[]{"fm"});
        String result = out.toString();
        assertEquals("adeb6f2a18fe33af368d91b09587b68e3abcb9a7\r\n", result);
        init();
        Cracker.main(new String[]{"adeb6f2a18fe33af368d91b09587b68e3abcb9a7", "4", "20"});
        assertEquals("fm\r\nall done\r\n", out.toString());
    }

    @Test
    public void crackerTests5(){
        Cracker.main(new String[] {"886ffd41c568469795a19f52486bdde64f5f5bcc", "5", "10"});
        String result = out.toString();
        assertEquals("flomo\r\nall done\r\n", result);
    }

}
