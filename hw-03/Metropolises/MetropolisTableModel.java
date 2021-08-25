package Metropolises;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class MetropolisTableModel extends AbstractTableModel {
    private final String [] header = {"Metropolis", "Continent", "Population"};
    private List <Metropolis> currentDisplay;

    public static final int METROPOLIS = 0;
    public static final int CONTINENT = 1;
    public static final int POPULATION = 2;


    /**
     * MetropolisTableModel initializes private variables.
     */
    public MetropolisTableModel(){
        currentDisplay = new ArrayList<>();
    }

    /**
     * displayChanged method takes one parameter, newDisplay than it calls fireTableChanged function to tell event loop that table data has changed and
     * to repaint table construction.
     * @param newDisplay List which contains metropolis objects that represents info which must be displayed now.
     */
    public void displayChanged(List <Metropolis> newDisplay){
        currentDisplay = newDisplay;
        fireTableDataChanged();
    }

    /**
     * return column name of given index.
     * @param column index of table columns.
     * @return String which represents table header for that column.
     */
    @Override
    public String getColumnName(int column){
        return header[column];
    }

    /**
     * Function  asks implementator if point with give rowIndex and columnIndex in table can be edited or not.
     * @param rowIndex index of row in table.
     * @param columnIndex index of column.
     * @return boolean which tells table model that point with rowIndex, columnIndex must be editable or not.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex){
        return false;
    }

    /**
     * returns count of each row in table.
     * @return  int which represents row count.
     */
    @Override
    public int getRowCount() {
        return currentDisplay.size();
    }

    /**
     * returns count of each column in table.
     * @return  int which represents column count.
     */
    @Override
    public int getColumnCount() {
        return header.length;
    }


    /**
     * takes tow arguments rowIndex and columnIndex and returns value which is positioned in table at given indexes.
     * @param rowIndex index of Row
     * @param columnIndex index of Column
     * @return
     */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(rowIndex < 0 || rowIndex >= currentDisplay.size())
            throw new RuntimeException("Bad row index");
        Metropolis currMetropolis = currentDisplay.get(rowIndex);
        switch (columnIndex){
            case METROPOLIS: return currMetropolis.getMetropolis();
            case CONTINENT: return currMetropolis.getContinent();
            case POPULATION: return currMetropolis.getPopulation();
            default: throw new RuntimeException("Bad column index");
        }
    }
}
