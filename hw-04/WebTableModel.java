import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WebTableModel extends AbstractTableModel {
    private String [] header;
    private List <urlStatus> urls;

    public WebTableModel(String [] header){
        this.header = header;
        urls = new ArrayList<>();
    }


    /*
        adds row in the url list and tells that to table model to repaint.
     */
    public void addRow(urlStatus url){
        urls.add(url);
        fireTableRowsInserted(urls.size() - 1, urls.size() - 1);
    }


    /*
        returns row with given index.
     */
    public urlStatus getRow(int index){
        return urls.get(index);
    }


    /*
        returns number of rows.
     */
    public int numberOfRows(){
        return urls.size();
    }

    /*
        function changes row and tells table model that it changed.
     */
    public void changeRow(int index, urlStatus url){
        urls.set(index, url);
        SwingUtilities.invokeLater(() -> {
            fireTableRowsUpdated(index, index);
        });

    }


    @Override
    public String getColumnName(int column){
        return header[column];
    }

    @Override
    public int getRowCount() {
        return urls.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if(columnIndex == 0) return urls.get(rowIndex).getUrl();
        else return urls.get(rowIndex).getStatus();
    }
    @Override
    public boolean isCellEditable(int i, int j){
        return false;
    }
}
