package Metropolises;


import org.apache.commons.dbcp2.BasicDataSource;

import javax.swing.*;


public class main {
    public static void main(String [] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) { }
        BasicDataSource source = new BasicDataSource();
        source.setUsername("root");
        source.setPassword("Arqimede123@");
        source.setUrl("jdbc:mysql://localhost:3306/metropolis");
        BasicView board = new MetropolisView();
        MetropolisDao dao = new MetropolisDao(source);
        MetropolisController controller = new MetropolisController(board, dao);

        board.show();
    }
}
