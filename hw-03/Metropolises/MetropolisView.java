package Metropolises;

import javafx.scene.layout.HBox;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;


public class MetropolisView implements BasicView{

    private inputAddedListener inpListener;
    private JTable table;
    private JTextField metropolisF;
    private JTextField continentF;
    private JTextField populationF;
    private JComboBox <String> populationPullDown;
    private JComboBox <String> matchPullDown;
    private MetropolisTableModel tableModel;
    private requestBase request;
    private JFrame mainFrame;

    private JPanel createInputPanel(){
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        JPanel metrPanel = new JPanel(new BorderLayout());
        metropolisF = new JTextField(10);
        continentF = new JTextField(10);
        populationF = new JTextField(10);
        metrPanel.add(new JLabel("Metropolis: "), BorderLayout.WEST);
        metrPanel.add(metropolisF, BorderLayout.CENTER);
        JPanel continentPanel = new JPanel(new BorderLayout());
        continentPanel.add(new JLabel("Continent: "), BorderLayout.WEST);
        continentPanel.add(continentF, BorderLayout.CENTER);
        JPanel populationPanel = new JPanel(new BorderLayout());
        populationPanel.add(new JLabel("Population: "), BorderLayout.WEST);
        populationPanel.add(populationF, BorderLayout.CENTER);

        inputPanel.add(metrPanel, BorderLayout.WEST);
        inputPanel.add(continentPanel, BorderLayout.CENTER);
        inputPanel.add(populationPanel, BorderLayout.EAST);
        return inputPanel;
    }

    private JButton createNewButton(String buttonName){
        JButton button = new JButton(buttonName);
        return button;
    }


    private JPanel createSearchPanel(){
        JPanel searchPanel = new JPanel(new BorderLayout(20, 0));
        searchPanel.setBorder(new TitledBorder("Search Options"));
        populationPullDown = new JComboBox<>();
        matchPullDown = new JComboBox<>();
        JPanel comboBoxPanel = new JPanel(new BorderLayout(0, 10));
        populationPullDown.addItem("Population Greater Than.");
        populationPullDown.addItem("Population Less Than.");
        matchPullDown.addItem("Exact Match");
        matchPullDown.addItem("Partial Match");
        comboBoxPanel.add(populationPullDown, BorderLayout.NORTH);
        comboBoxPanel.add(matchPullDown, BorderLayout.CENTER);
        searchPanel.add(comboBoxPanel, BorderLayout.NORTH);
        return searchPanel;
    }


    private JPanel createControlPanel(){
        JPanel controlPanel = new JPanel(new BorderLayout(0, 30));
        JPanel buttonPanel = new JPanel(new BorderLayout(0, 10));
        JButton addButton = createNewButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Metropolis added = new Metropolis();
                    added.setMetropolis(metropolisF.getText());
                    added.setContinent(continentF.getText());
                    added.setPopulation(Integer.valueOf(populationF.getText()));
                    inpListener.fireInputAdded(added);
                    ArrayList<Metropolis> newInput = new ArrayList();
                    newInput.add(added);
                    tableModel.displayChanged(newInput);
                }catch (NumberFormatException ex){
                    PopupWindow p = new PopupWindow("Popup Window", "Population must be Integer.");
                    p.setVisible();
                }catch(SQLException exception){
                    PopupWindow p = new PopupWindow("Insert Problems", "Can't add metropolis, please try again.");
                    p.setVisible();
                }
            }
        });

        JButton searchButton = createNewButton("Search");

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SearchEngine newEngine = new SearchEngine(metropolisF.getText(), continentF.getText(), populationF.getText());
                    int firstRule = -1, secondRule = -1;
                    int ruleOne = populationPullDown.getSelectedIndex();
                    int ruleTwo = matchPullDown.getSelectedIndex();
                    if (ruleOne == 0) firstRule = SearchEngine.GREATER;
                    else firstRule = SearchEngine.LESS;
                    if (ruleTwo == 0) secondRule = SearchEngine.EXACT;
                    else secondRule = SearchEngine.SUBSTRING;
                    newEngine.configureSearchRules(firstRule, secondRule);
                    tableModel.displayChanged(request.getNewDisplay(newEngine));
                }catch (NumberFormatException ex){
                    PopupWindow p = new PopupWindow("Popup Window", "Population must be Integer, or empty string.");
                    p.setVisible();
                }
            }
        });



        buttonPanel.add(addButton, BorderLayout.NORTH);
        buttonPanel.add(searchButton, BorderLayout.CENTER);
        JPanel searchOptions = createSearchPanel();
        controlPanel.add(buttonPanel, BorderLayout.NORTH);
        controlPanel.add(searchOptions, BorderLayout.CENTER);
        return controlPanel;
    }


    private JPanel createTablePanel(){
        JPanel tablePanel = new JPanel(new BorderLayout());
        tableModel = new MetropolisTableModel();
        table = new JTable(tableModel);
        tablePanel.add(table.getTableHeader(), BorderLayout.NORTH);
        tablePanel.add(table, BorderLayout.CENTER);
        return tablePanel;
    }


    public MetropolisView(){
        mainFrame = new JFrame();
        mainFrame.setSize(600, 600);
        JPanel inputPanel = createInputPanel();
        JPanel controlPanel = createControlPanel();
        JPanel tablePanel = createTablePanel();
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.EAST);
        mainFrame.setContentPane(mainPanel);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void show() {
        mainFrame.setVisible(true);
    }

    @Override
    public void registerInputAddedListener(inputAddedListener listener) {
        if(listener == null) throw new IllegalArgumentException("listener can't be null");
        inpListener = listener;
    }

    @Override
    public void registerRequestBaseListener(requestBase listener) {
        if(listener == null) throw new IllegalArgumentException("listener can't be null");
        request = listener;
    }
}
