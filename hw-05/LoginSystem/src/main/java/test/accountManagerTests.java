package test;

//import junit.framework.TestCase;

import accountManager.accountManager;
import accountManager.UserInsertedException;

import org.junit.Test;
import org.junit.jupiter.api.function.Executable;

import accountManager.UserNotRegistredException;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class accountManagerTests{
    /*
        tested add account and size.
     */
    @Test
    public void testAdd(){
        accountManager manager = new accountManager();
        assertEquals(2, manager.numAccounts());
        manager.addAccount("user", "122");
        assertEquals(3, manager.numAccounts());
        manager.addAccount("user1", "35646");
        assertEquals(4, manager.numAccounts());
    }


    //basic contains
    @Test
    public void testContains(){
        accountManager manager = new accountManager();
        manager.addAccount("user1", "pass");
        manager.addAccount("user2", "pass2");
        manager.addAccount("user3", "pass3");
        assertEquals(5, manager.numAccounts());
        assertTrue(manager.containsAccount("user1"));
        assertTrue(manager.containsAccount("user2"));
        assertFalse(manager.containsAccount("user6"));
    }

    // test add throwing exception

    @Test
    public void testAdd2(){
        final accountManager manager = new accountManager();
        manager.addAccount("user1", "pass1");
        manager.addAccount("user2", "pass2");
        manager.addAccount("user3", "pass3");
        assertThrows(UserInsertedException.class, new Executable() {
            @Override
            public void execute() {
                manager.addAccount("user1", "pass8");
            }
        });
        assertThrows(UserInsertedException.class, new Executable() {
            @Override
            public void execute() {
                manager.addAccount("user2", "asfsdf");
            }
        });
        manager.addAccount("user4", "pass4");
        assertEquals(6, manager.numAccounts());
    }

    @Test
    public void testAdd3(){
        accountManager manager = new accountManager();
        for(int k = 0; k < 10000; k++){
            manager.addAccount("user" + k, "pass" + k);
            assertEquals(2 + k + 1, manager.numAccounts());
        }

        for(int j = 0; j < 10000; j++){
            assertTrue(manager.containsAccount("user" + j));
        }

        assertFalse(manager.containsAccount("asdfg"));
        assertFalse(manager.containsAccount("55safsdf8"));
        manager.addAccount("asdfg", "25899");
        assertTrue(manager.containsAccount("asdfg"));

    }

    @Test
    public void testGetPassword1(){
         accountManager manager = new accountManager();
         manager.addAccount("user1", "pass1");
         manager.addAccount("user2", "pass2");
         manager.addAccount("user4", "pass4");
         manager.addAccount("user3", "pass3");
         manager.addAccount("user5", "pass5");
         for(int k = 0; k < 5; k++) {
             assertEquals("pass" + (k + 1), manager.getPassword("user" + (k + 1)));
         }
         assertEquals(7, manager.numAccounts());
    }

    @Test
    public void testGetPassword2(){
        final accountManager manager = new accountManager();
        manager.addAccount("user1", "pass1");
        manager.addAccount("user2", "pass2");
        assertThrows(UserNotRegistredException.class, new Executable() {
            @Override
            public void execute() {
                manager.getPassword("user3");
            }
        });
    }


    @Test
    public void testGetPassword3(){
        accountManager manager = new accountManager();
        for(int k = 0; k < 10000; k++){
            manager.addAccount("user" + k, "pass" + k);
        }

        for(int k = 0; k < 10000; k++){
            assertEquals("pass" + k, manager.getPassword("user" + k));
        }

    }


}
