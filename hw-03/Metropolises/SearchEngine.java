package Metropolises;

import javafx.util.Pair;

import javax.xml.soap.Node;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SearchEngine {
    private List<String> searchParameters;
    public static final int LESS = 0;
    public static final int GREATER = 1;
    public static final int EXACT = 2;
    public static final int SUBSTRING = 4;
    protected int ruleForInt = 0;
    protected int ruleForString = 1;
    private List <Integer> rules;


    /**
     * constructor takes user entered input and saves it locally.
     * @param metropolis this variable represents full name of metropolis.
     * @param continent this variable represents full name of continent where metropolis is located.
     * @param population this variable represents population of goven metropolis.
     */
    public SearchEngine(String metropolis, String continent, String population){
        searchParameters = new ArrayList<>();
        searchParameters.add(metropolis);
        searchParameters.add(continent);
        searchParameters.add(population);
    }

    /**
     * Method takes parametres whic represents search rules(Greater, less, ...).
     * @param integerRule This rule is for integers(population).
     * @param strRule This rule is for Strings(Continent, Metropolis).
     */
    public void configureSearchRules(int integerRule, int strRule){
        rules = new ArrayList<>();
        rules.add(integerRule);
        rules.add(strRule);
    }

    public Iterator<Integer> getSearchRules(){
        return rules.iterator();
    }


    /*
        Method writes data which helps base to write sql query
     */
    private void configureString(List <String> result, int index){
        result.add("LIKE");
        if(searchParameters.get(index).equals("")) result.add("'%'");
        else if(rules.get(ruleForString) == EXACT) result.add("'" + searchParameters.get(index) + "'");
        else result.add("'%" + searchParameters.get(index) + "%'");
    }

    /*
        Method writes data which helps base to write sql query
     */
    private void configureInt(List <String> result, int index){
        if(searchParameters.get(index).equals("")){
            result.add(">");
            result.add("-1");
            return;
        }else if(rules.get(ruleForInt) == LESS){
            result.add("<");
        }else{
            result.add(">");
        }
        result.add(searchParameters.get(index));
    }



    /*
        returns Iterator <String> which contains rules and data which should be written in sql query.
     */
    public Iterator<String> configureExpression(){
        List <String> result = new ArrayList<>();
        configureString(result, 0);
        configureString(result, 1);
        configureInt(result, 2);
        return result.iterator();
    }





}
