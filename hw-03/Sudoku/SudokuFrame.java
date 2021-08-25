package Sudoku;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;


 public class SudokuFrame extends JFrame {
	
	public SudokuFrame() {
		super("Sudoku.Sudoku Solver");

		JPanel mainPanel = new JPanel(new BorderLayout(4, 4));
		JTextArea puzzleArea = new JTextArea(15, 20);
		puzzleArea.setBorder(new TitledBorder("Puzzle"));
		JTextArea solutionArea = new JTextArea(15, 20);
		solutionArea.setBorder(new TitledBorder("Solution"));
		mainPanel.add(puzzleArea, BorderLayout.WEST);
		mainPanel.add(solutionArea, BorderLayout.EAST);
		JPanel controlPanel = new JPanel(new BorderLayout());
		JButton checkButton = new JButton("check");
		checkButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Sudoku currentBoard = new Sudoku(puzzleArea.getText());
					int numSolutions = currentBoard.solve();
					solutionArea.setText(currentBoard.getSolutionText() + "\n" + "solutions: " + numSolutions + "\n" + "elapsed: "+ currentBoard.getElapsed());
				}catch (RuntimeException ex) {
					solutionArea.setText("Parsing Problem");
				}

			}
		});

		JCheckBox checkBox = new JCheckBox("Auto Check");
		checkBox.setSelected(true);

		puzzleArea.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(checkBox.isSelected()) {
					checkButton.doClick();
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(checkBox.isSelected()){
					checkButton.doClick();
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
//
			}
		});



		controlPanel.add(checkButton, BorderLayout.WEST);
		controlPanel.add(checkBox, BorderLayout.CENTER);
		mainPanel.add(controlPanel, BorderLayout.SOUTH);
		this.add(mainPanel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
	
	
	public static void main(String[] args) {
		// GUI Look And Feel
		// Do this incantation at the start of main() to tell Swing
		// to use the GUI LookAndFeel of the native platform. It's ok
		// to ignore the exception.
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ignored) { }
		
		SudokuFrame frame = new SudokuFrame();
	}

}
