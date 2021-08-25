package Metropolises;

import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class MetropolisDao {
    private BasicDataSource source;
    private Connection conn;


    /**
     * Takes BasicDataSource object and connects to mySql base.
     */
    public MetropolisDao(BasicDataSource source){
        if(source == null)
            throw new NullPointerException("Null is not allowed.");
        this.source = source;
        try{
            conn = source.getConnection();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    /**
        add's new input witch user entered.
     */
    public int addInput(Metropolis metropolis) throws SQLException {

        PreparedStatement st = conn.prepareStatement("INSERT INTO metropolises(metropolis, continent, population)" +
                                                    "VALUES (?, ?, ?);");
        st.setString(1, metropolis.getMetropolis());
        st.setString(2, metropolis.getContinent());
        st.setInt(3, metropolis.getPopulation());

        int numInserted = st.executeUpdate();
        st.close();
        return numInserted;
    }

    /**
     * Searches for valid metroplises in metropolis table, takes one arguments engine an returns list of metroplises
     * @param engine reprsenets rules for search.
     * @return Metroplises which satisfies given restrictions.
     */
   public List<Metropolis> search(SearchEngine engine){
        List <Metropolis> result = new ArrayList<>();
        Iterator <String> expr = engine.configureExpression();
        StringBuilder builder = new StringBuilder("SELECT * FROM metropolises WHERE metropolis ");
        builder.append(expr.next()).append(" ").append(expr.next()).append(" AND ")
                .append("continent").append(" ").append(expr.next()).append(" ").append(expr.next()).append(" AND ")
                .append("population ").append(expr.next()).append(" ").append(expr.next()).append(";");
        try {
            Statement stm = conn.createStatement();
            ResultSet results = stm.executeQuery(builder.toString());
            while(results.next()){
                String metropolises = results.getString(1);
                String continent = results.getString(2);
                int population = results.getInt(3);
                Metropolis searchMetr = new Metropolis();
                searchMetr.setMetropolis(metropolises);
                searchMetr.setContinent(continent);
                searchMetr.setPopulation(population);
                result.add(searchMetr);
            }
            stm.close();
        } catch (SQLException throwables) {
                throwables.printStackTrace();
        }
        return  result;

   }




}
