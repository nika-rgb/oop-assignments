package Metropolises;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DaoTests {
    private Metropolis metr;
    private static BasicDataSource source;
    private static   MetropolisDao dao;
    private static Connection conn;
    @BeforeClass
    public static void setUp() throws SQLException {
        source = new BasicDataSource();
        source.setUsername("root");
        source.setPassword("Arqimede123@");
        source.setUrl("jdbc:mysql://localhost:3306/metropolis");
        conn = source.getConnection();
        dao = new MetropolisDao(source);
    }


    @Before
    public void init(){
        metr = new Metropolis();

    }


    //insert new metropolises
    @Test
    public void DaoTests1(){
        metr = new Metropolis();
        metr.setMetropolis("kkkkkk");
        metr.setContinent("dddddd");
        metr.setPopulation(1265);
        try {
            Assert.assertEquals(1, dao.addInput(metr));
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void DaoTests2(){
        metr = new Metropolis();
        metr.setMetropolis("BGGGG");
        metr.setContinent("ssdfsdfdfg");
        metr.setPopulation(4656);
        try{
            Assert.assertEquals(1, dao.addInput(metr));
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Test
    public void DaoTests3(){
        SearchEngine engine = new SearchEngine("BG", "ssd", "4520");
        engine.configureSearchRules(SearchEngine.GREATER, SearchEngine.SUBSTRING);
        List<Metropolis> resultList = dao.search(engine);
        try {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM metropolises WHERE metropolis like ?" +
                    " and continent like ? and population > ?;");
            stm.setString(1, "BG%");
            stm.setString(2, "ssd%");
            stm.setInt(3, 4520);
            ResultSet res = stm.executeQuery();
            int k = 0;
            while(res.next()){
                Metropolis metropolis = new Metropolis();
                metropolis.setMetropolis(res.getString(1));
                metropolis.setContinent(res.getString(2));
                metropolis.setPopulation(res.getInt(3));
                Assert.assertTrue(metropolis.equals(resultList.get(k)));
                k++;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void DaoTests4(){
        SearchEngine engine = new SearchEngine("", "", "");
        engine.configureSearchRules(SearchEngine.GREATER, SearchEngine.SUBSTRING);
        List <Metropolis> actualResult = dao.search(engine);
        try {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM metropolises WHERE metropolis like ?" +
                    " and continent like ? and population > ?;");
            stm.setString(1, "%");
            stm.setString(2, "%");
            stm.setInt(3, -1);
            ResultSet res = stm.executeQuery();
            int k = 0;
            while(res.next()){
                Metropolis metropolis = new Metropolis();
                metropolis.setMetropolis(res.getString(1));
                metropolis.setContinent(res.getString(2));
                metropolis.setPopulation(res.getInt(3));
                Assert.assertTrue(metropolis.equals(actualResult.get(k)));
                k++;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void DaoTests5(){
        SearchEngine engine = new SearchEngine("", "san", "100");
        engine.configureSearchRules(SearchEngine.GREATER, SearchEngine.SUBSTRING);
        List <Metropolis> actualResult = dao.search(engine);
        try {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM metropolises WHERE metropolis like ?" +
                    " and continent like ? and population > ?;");
            stm.setString(1, "%");
            stm.setString(2, "san%");
            stm.setInt(3, -1);
            ResultSet res = stm.executeQuery();
            int k = 0;
            while(res.next()){
                Metropolis metropolis = new Metropolis();
                metropolis.setMetropolis(res.getString(1));
                metropolis.setContinent(res.getString(2));
                metropolis.setPopulation(res.getInt(3));
                Assert.assertTrue(metropolis.equals(actualResult.get(k)));
                k++;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    @Test
    public void DaoTests6(){
        SearchEngine engine = new SearchEngine("san", "North America", "50000");
        engine.configureSearchRules(SearchEngine.LESS, SearchEngine.EXACT);
        List <Metropolis> actualResult = dao.search(engine);
        try {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM metropolises WHERE metropolis like ?" +
                    " and continent like ? and population < ?;");
            stm.setString(1, "san");
            stm.setString(2, "North America");
            stm.setInt(3, 50000);
            ResultSet res = stm.executeQuery();
            int k = 0;
            while(res.next()){
                Metropolis metropolis = new Metropolis();
                metropolis.setMetropolis(res.getString(1));
                metropolis.setContinent(res.getString(2));
                metropolis.setPopulation(res.getInt(3));
                Assert.assertTrue(metropolis.equals(actualResult.get(k)));
                k++;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void DaoTests7(){
        SearchEngine engine = new SearchEngine("Mumbai", "Asia", "20000000");
        engine.configureSearchRules(SearchEngine.EXACT, SearchEngine.GREATER);
        List <Metropolis> actualResult = dao.search(engine);
        try {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM metropolises WHERE metropolis like ?" +
                    " and continent like ? and population < ?;");
            stm.setString(1, "Mumbai");
            stm.setString(2, "Asia");
            stm.setInt(3, 20000000);
            ResultSet res = stm.executeQuery();
            int k = 0;
            while(res.next()){
                Metropolis metropolis = new Metropolis();
                metropolis.setMetropolis(res.getString(1));
                metropolis.setContinent(res.getString(2));
                metropolis.setPopulation(res.getInt(3));
                Assert.assertTrue(metropolis.equals(actualResult.get(k)));
                k++;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void DaoTests8(){
        SearchEngine engine = new SearchEngine("", "Austr", "");
        engine.configureSearchRules(SearchEngine.LESS, SearchEngine.SUBSTRING);
        List <Metropolis> actualResult = dao.search(engine);

        try {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM metropolises WHERE metropolis like ?" +
                    " and continent like ? and population > ?;");
            stm.setString(1, "%");
            stm.setString(2, "%Austr%");
            stm.setInt(3, -1);
            ResultSet res = stm.executeQuery();
            int k = 0;
            while(res.next()){
                Metropolis metropolis = new Metropolis();
                metropolis.setMetropolis(res.getString(1));
                metropolis.setContinent(res.getString(2));
                metropolis.setPopulation(res.getInt(3));
                Assert.assertTrue(metropolis.equals(actualResult.get(k)));
                k++;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void DaoTest9(){

        SearchEngine engine = new SearchEngine("Rostov-on-Don", "Euro", "1053000");
        engine.configureSearchRules(SearchEngine.LESS, SearchEngine.SUBSTRING);
        List <Metropolis> actualResult = dao.search(engine);
        try {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM metropolises WHERE metropolis like ?" +
                    " and continent like ? and population < ?;");
            stm.setString(1, "%Rostov-on-Don%");
            stm.setString(2, "%Euro%");
            stm.setInt(3, 1053000);
            ResultSet res = stm.executeQuery();
            int k = 0;
            while(res.next()){
                Metropolis metropolis = new Metropolis();
                metropolis.setMetropolis(res.getString(1));
                metropolis.setContinent(res.getString(2));
                metropolis.setPopulation(res.getInt(3));
                Assert.assertTrue(metropolis.equals(actualResult.get(k)));
                k++;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void DaoTest10(){
        SearchEngine engine = new SearchEngine("London", "Europe", "");
        engine.configureSearchRules(SearchEngine.LESS, SearchEngine.EXACT);
        List <Metropolis> actualResult = dao.search(engine);
        try {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM metropolises WHERE metropolis like ?" +
                    " and continent like ? and population > ?;");
            stm.setString(1, "London");
            stm.setString(2, "Europe");
            stm.setInt(3, -1);
            ResultSet res = stm.executeQuery();
            int k = 0;
            while(res.next()){
                Metropolis metropolis = new Metropolis();
                metropolis.setMetropolis(res.getString(1));
                metropolis.setContinent(res.getString(2));
                metropolis.setPopulation(res.getInt(3));
                Assert.assertTrue(metropolis.equals(actualResult.get(k)));
                k++;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }





}
