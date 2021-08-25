// JCount.java

/*
 Basic GUI/Threading exercise.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayDeque;
import java.util.Queue;

public class JCount extends JPanel {
	private static final int SIZE = 550;
	private TextField count;


	public JCount() {
		// Set the JCount to use Box layout
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		count = new TextField("0");
		JLabel label = new JLabel(count.getText());
		JButton startButton = new JButton("Start");
		JButton endButton = new JButton("Stop");


		Queue <Thread> queue = new ArrayDeque<>();

		startButton.addActionListener(listener -> {
			if(!queue.isEmpty()){
				Thread last = queue.poll();
				last.interrupt();
			}

			Thread curr = new Thread(() -> {
				int maxIterations = Integer.parseInt(count.getText());
				boolean allIterationsPassed = false;
				for(int k = 0; k < maxIterations; k++){
					try {
						if ((k + 1) % 10000 == 0) {
							int current = k;
							Thread.sleep(100);
							SwingUtilities.invokeLater(() -> label.setText(String.valueOf(current + 1)));
						}
					}catch (InterruptedException e) {
						break;
					}
					if(Thread.currentThread().isInterrupted()){
						break;
					}
					else if(k == maxIterations - 1)
						allIterationsPassed = true;
				}
				if(allIterationsPassed) SwingUtilities.invokeLater(() -> label.setText(String.valueOf(maxIterations)));
			});
			curr.start();
			queue.add(curr);
		});


		endButton.addActionListener(e -> {
			if(!queue.isEmpty()){
				Thread last = queue.poll();
				last.interrupt();
				SwingUtilities.invokeLater(() -> label.setText("0"));
			}
		});



		add(count);
		add(label);
		add(startButton);
		add(endButton);
		add(Box.createRigidArea(new Dimension(0, 40)));
		// YOUR CODE HERE
	}
	
	static public void main(String[] args)  {
		// Creates a frame with 4 JCounts in it.
		// (provided)
		JFrame frame = new JFrame("The Count");
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());
		frame.add(new JCount());

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(SIZE / 2, SIZE);
	}
}

