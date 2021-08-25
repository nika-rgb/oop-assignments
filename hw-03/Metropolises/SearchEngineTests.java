package Metropolises;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SearchEngineTests {

    @Test
    public void searchEngineTests1(){
        SearchEngine engine = new SearchEngine("San", "North America", "12358");
        engine.configureSearchRules(SearchEngine.GREATER, SearchEngine.SUBSTRING);
        List <Integer> res = new ArrayList<>();
        res.add(engine.ruleForInt, SearchEngine.GREATER);
        res.add(engine.ruleForString, SearchEngine.SUBSTRING);
        Iterator actualResult = engine.getSearchRules();
        for(int k = 0; k < res.size(); k++){
            Assert.assertEquals(res.get(k), actualResult.next());
        }
    }

    @Test
    public void searchEngineTests2(){
        SearchEngine engine = new SearchEngine("San", "North America", "12358");
        engine.configureSearchRules(SearchEngine.GREATER, SearchEngine.EXACT);
        List <Integer> res = new ArrayList<>();
        res.add(engine.ruleForInt, SearchEngine.GREATER);
        res.add(engine.ruleForString, SearchEngine.EXACT);
        Iterator actualResult = engine.getSearchRules();
        for(int k = 0; k < res.size(); k++){
            Assert.assertEquals(res.get(k), actualResult.next());
        }
    }

    @Test
    public void searchEngineTests3(){
        SearchEngine engine = new SearchEngine("San", "North America", "12358");
        engine.configureSearchRules(SearchEngine.LESS, SearchEngine.SUBSTRING);
        List <Integer> res = new ArrayList<>();
        res.add(engine.ruleForInt, SearchEngine.LESS);
        res.add(engine.ruleForString, SearchEngine.SUBSTRING);
        Iterator actualResult = engine.getSearchRules();
        for(int k = 0; k < res.size(); k++){
            Assert.assertEquals(res.get(k), actualResult.next());
        }
    }

    @Test
    public void seaarchEngineTests4(){
        SearchEngine engine = new SearchEngine("San", "North America", "12358");
        engine.configureSearchRules(SearchEngine.GREATER, SearchEngine.EXACT);
        List <Integer> res = new ArrayList<>();
        res.add(engine.ruleForInt, SearchEngine.GREATER);
        res.add(engine.ruleForString, SearchEngine.EXACT);
        Iterator actualResult = engine.getSearchRules();
        for(int k = 0; k < res.size(); k++){
            Assert.assertEquals(res.get(k), actualResult.next());
        }
    }

    // tests for generating expression

    @Test
    public void searchEngineTests5(){
        SearchEngine engine = new SearchEngine("San", "North America", "12358");
        engine.configureSearchRules(SearchEngine.GREATER, SearchEngine.EXACT);
        List <String> res = new ArrayList<>();
        res.add("LIKE");
        res.add("'San'");
        res.add("LIKE");
        res.add("'North America'");
        res.add(">");
        res.add("12358");
        Iterator actualResult = engine.configureExpression();
        for(int k = 0; k < res.size(); k++){
            Assert.assertEquals(res.get(k), actualResult.next());
        }
    }

    @Test
    public void searchEngineTests6(){
        SearchEngine engine = new SearchEngine("San", "North America", "12358");
        engine.configureSearchRules(SearchEngine.LESS, SearchEngine.SUBSTRING);
        List <String> res = new ArrayList<>();
        res.add("LIKE");
        res.add("'%San%'");
        res.add("LIKE");
        res.add("'%North America%'");
        res.add("<");
        res.add("12358");
        Iterator actualResult = engine.configureExpression();
        for(int k = 0; k < res.size(); k++){
            Assert.assertEquals(res.get(k), actualResult.next());
        }
    }

    @Test
    public void searchEngineTests7(){
        SearchEngine engine = new SearchEngine("London", "Europe", "8580000");
        engine.configureSearchRules(SearchEngine.GREATER, SearchEngine.SUBSTRING);
        List <String> res = new ArrayList<>();
        res.add("LIKE");
        res.add("'%London%'");
        res.add("LIKE");
        res.add("'%Europe%'");
        res.add(">");
        res.add("8580000");
        Iterator actualResult = engine.configureExpression();
        for(int k = 0; k < res.size(); k++){
            Assert.assertEquals(res.get(k), actualResult.next());
        }
    }

}
