// TabooTest.java
// Taboo class tests -- nothing provided.

import java.util.*;

import junit.framework.TestCase;


public class TabooTest extends TestCase {
    public void testTaboo1(){
        List <String> rules = Arrays.asList("a", "c", "a", "b");
        Taboo t = new Taboo(rules);
        assertEquals(new HashSet<String>(Arrays.asList("c", "b")), t.noFollow("a"));
        assertEquals(new HashSet<String>(Arrays.asList("a")), t.noFollow("c"));
        assertEquals(new HashSet<String>(Arrays.asList()), t.noFollow("y"));
        assertEquals(new HashSet<String>(Arrays.asList()), t.noFollow("b"));
    }

    public void testTaboo2(){
        List <String> rules = Arrays.asList("a", null, "b", "c", "y", "a", null, "b", "z");
        rules.remove(1);
        Taboo t = new Taboo(rules);
        assertEquals(Collections.emptySet(), t.noFollow("a"));
        assertEquals(new HashSet <String> (Arrays.asList("c", "z")), t.noFollow("b"));
        assertEquals(Collections.emptySet(), t.noFollow("m"));
        assertEquals(new HashSet<String>(Arrays.asList("a")),t.noFollow("y"));
        List <String> lst = new ArrayList<String>();
        lst.addAll(Arrays.asList("a", "b", "c", "c", "z", "y", "z", "c", "y"));
        List <String> lst2 = new ArrayList<String>();
        lst2.addAll(Arrays.asList("b", "c", "y", "a", "a", "a", "b", "y", "a", "z"));
        t.reduce(lst);
        t.reduce(lst2);
        assertEquals(Arrays.asList("b", "y", "b", "y", "z"), lst2);
    }

    public void testTaboo3(){
        List <String> rules = Arrays.asList("a", null, "b", null, "c");
        Taboo t = new Taboo(rules);
        assertEquals(Collections.emptySet(), t.noFollow("a"));
        assertEquals(Collections.emptySet(), t.noFollow("b"));
        assertEquals(Collections.emptySet(), t.noFollow("c"));
        assertEquals(Collections.emptySet(), t.noFollow("d"));
        List <String> lst1 = new ArrayList<String>();
        lst1.addAll(Arrays.asList("a", "b", "d", "a", "c", "x", "y", "a", "b"));
        List <String> lst2 = new ArrayList<String>();
        lst2.addAll(Arrays.asList("x", "z", "c", "a", "d", "e"));
        t.reduce(lst1);
        t.reduce(lst2);
        assertEquals(Arrays.asList("a", "b", "d", "a", "c", "x", "y", "a", "b"), lst1);
        assertEquals(Arrays.asList("x", "z", "c", "a", "d", "e"), lst2);
    }

    public void testTaboo4(){
        List <Integer> rules = Arrays.asList(1, 3, null, 5, 9, 5, 7, 5, 6, 5);
        Taboo t = new Taboo(rules);
        assertEquals(new HashSet<Integer>(Arrays.asList(9, 7, 6)), t.noFollow(5));
        assertEquals(new HashSet<Integer>(Arrays.asList(5)), t.noFollow(6));
        assertEquals(Collections.emptySet(), t.noFollow(3));
        assertEquals(Collections.emptySet(), t.noFollow(27));
        List <Integer> lst1 = new ArrayList<Integer>();
        lst1.addAll(Arrays.asList(5, 9, 7, 5, 6, 5, 1, 5, 3, 5, 9, 7));
        t.reduce(lst1);
        assertEquals(Arrays.asList(5, 5, 5, 1, 5, 3, 5), lst1);
    }

    public void testTaboo5(){
        List <Integer> rules = Arrays.asList(3, 3, 3, 3, 3);
        Taboo t = new Taboo(rules);
        assertEquals(new HashSet<Integer>(Arrays.asList(3)), t.noFollow(3));
        List <Integer> lst1 = new ArrayList<>();
        lst1.addAll(Arrays.asList(3, 3, 3, 3, 3, 3, 3));
        t.reduce(lst1);
        assertEquals(Arrays.asList(3), lst1);
    }

    public void testTaboo6(){
        List <Integer> rules = Arrays.asList(1, 5, null, 7, 5, 6, null, 8, 1);
        Taboo t = new Taboo(rules);
        assertEquals(new HashSet<Integer>(Arrays.asList(6)), t.noFollow(5));
        assertEquals(new HashSet<Integer>(Arrays.asList()), t.noFollow(6));
        assertEquals(new HashSet<Integer>(Arrays.asList(1)), t.noFollow(8));
        List <Integer> lst1 = new ArrayList<>();
        lst1.addAll(Arrays.asList(5, 6, 1, 4, 8, 1, 5));
        t.reduce(lst1);
        assertEquals(Arrays.asList(5, 1, 4, 8, 5), lst1);
    }

    public void testTaboo7(){
        List <Integer> rules = Arrays.asList(1, 2, 3, 4, 5, 1);
        Taboo t = new Taboo(rules);
        assertEquals(new HashSet<Integer>(Arrays.asList(1)), t.noFollow(5));
        assertEquals(new HashSet<Integer>(Arrays.asList(3)), t.noFollow(2));
        List <Integer> lst1 = new ArrayList<>();
        lst1.addAll(Arrays.asList(1, 1, 5, 1, 2, 3, 4, 2, 3));
        t.reduce(lst1);
        assertEquals(Arrays.asList(1, 1, 5, 2, 4, 2), lst1);
    }

    public void testTaboo8(){
        List <Integer> rules = Arrays.asList();
        Taboo t = new Taboo(rules);
        assertEquals(Collections.emptySet(), t.noFollow(5));
        assertEquals(Collections.emptySet(), t.noFollow(6));
        List <Integer> lst1 = new ArrayList<>();
        lst1.addAll(Arrays.asList(1, 2, 5));
        t.reduce(lst1);
        assertEquals(Arrays.asList(1, 2, 5), lst1);
    }

    public void testTaboo9(){
        List <Integer> rules = Arrays.asList(1, 8, 5, 6);
        Taboo t = new Taboo(rules);
        List <Integer> lst1 = new ArrayList<>();
        lst1.addAll(Arrays.asList());
        t.reduce(lst1);
        assertEquals(Arrays.asList(), lst1);
    }

    public void testTaboo10(){
        List <Integer> rules = Arrays.asList(null, null, null, null, null);
        Taboo t = new Taboo(rules);
        List <Integer> lst1 = new ArrayList<>();
        assertEquals(Collections.emptySet(), t.noFollow(8));
        assertEquals(Collections.emptySet(), t.noFollow(null));
        lst1.addAll(Arrays.asList(1, 5, 8, null, null, 8, 9858, 29, 36, null));
        t.reduce(lst1);
        assertEquals(Arrays.asList(1, 5, 8, null, null, 8, 9858, 29, 36, null), lst1);
    }

    public  void testTaboo11(){
        //nulls between elements
        List <Integer> rules = Arrays.asList(1, null, null, 9, null, 8, null, null);
        Taboo t = new Taboo(rules);
        assertEquals(Collections.emptySet(), t.noFollow(9));
        assertEquals(Collections.emptySet(), t.noFollow(null));
        assertEquals(Collections.emptySet(), t.noFollow(8));
        List <Integer> lst1 = new ArrayList<>();
        lst1.addAll(Arrays.asList(9, 8, null, 8, 89));
        t.reduce(lst1);
        assertEquals(Arrays.asList(9, 8, null, 8, 89), lst1);
    }


}
