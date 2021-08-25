import javax.swing.*;
import javax.swing.text.BoxView;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class webFrame {
    private WebTableModel model;
    private JButton single;
    private JButton concurrent;
    private JButton stopButton;
    private JProgressBar progressBar;
    private int numURls;
    private JLabel running;
    private JLabel completed;
    private JLabel elapsed;
    private JTextField field;
    private Semaphore limitThreads;
    private Lock interruptLock;

    private void setToRunningState(){
        single.setEnabled(false);
        concurrent.setEnabled(false);
        stopButton.setEnabled(true);
        progressBar.setValue(0);
        progressBar.setMaximum(numURls);
        running.setText("Running : 0");
        completed.setText("Completed : 0");
        elapsed.setText("Elapsed : ");
        for(int k = 0; k < model.numberOfRows(); k++) {
            urlStatus current = model.getRow(k);
            current.setStatus("");
            model.changeRow(k, current);
        }
    }

    private void interruptWorkers(WebWorker[] workers, int numStarted){
        for(int j = 0; j < numStarted; j++){
            workers[j].interrupt();
        }
    }

    private void launchThreads(int numThreads) {
        int numStarted = 0;
        WebWorker [] workers = new WebWorker[model.numberOfRows()];
        long startTime = System.nanoTime();
        try {

            for(int k = 0; k < model.numberOfRows(); k++){
                if(k % 2 == 0 && k != 0){
                    Thread.sleep(100);
                }
                interruptLock.lock();
                if(Thread.currentThread().isInterrupted()){
                    interruptWorkers(workers, numStarted);
                    interruptLock.unlock();
                    break;
                }
                limitThreads.acquire();
                urlStatus currentUrl = model.getRow(k);
                WebWorker worker = new WebWorker(currentUrl.getUrl(), k, this);
                workers[k] = worker;
                worker.start();
                numStarted++;
                interruptLock.unlock();
            }

        } catch (InterruptedException e) {
            interruptWorkers(workers, numStarted);
        }finally {
            for(int k = 0; k < numStarted; k++) {
                try {
                    workers[k].join();
                } catch (InterruptedException e) {
                }
            }
            long endTime = System.nanoTime();
            SwingUtilities.invokeLater(() -> {
                endProcess(startTime, endTime);
            });
        }
    }

    private void endProcess(long startTime, long endTime) {
        running.setText("Running : 0");
        elapsed.setText("Elapsed : " + ((endTime - startTime) / 1000000) / 1000.0);
        concurrent.setEnabled(true);
        single.setEnabled(true);
        stopButton.setEnabled(false);
        progressBar.setValue(0);
    }


    public synchronized void increaseRunningThreads (int rowIndex) throws InterruptedException {
        Thread.sleep(100);
        String runningLabel = running.getText();
        int numRunning = Integer.parseInt(runningLabel.substring(runningLabel.indexOf(":") + 2));
        int increased = numRunning + 1;
        if(Thread.currentThread().isInterrupted())
            changeTable(rowIndex, "interrupted");
        running.setText("Running : " + increased);
    }


    private void joinStopButton(Thread launcher){
        stopButton.addActionListener(listener -> {
            Thread stopThread = new Thread(() -> {
                interruptLock.lock();
                launcher.interrupt();
                interruptLock.unlock();
            });
            stopThread.start();
        });
    }

    private void activateLauncher(int numThreads) {
        Thread launcher = new Thread(() -> {
            launchThreads(numThreads);
        });
        running.setText("Running : 1");
        launcher.start();
        joinStopButton(launcher);
    }


    public synchronized void releaseThread(int rowIndex, boolean increased){
        String runningLabel = running.getText();
        String completedLabel = completed.getText();
        int numRunning = Integer.parseInt(runningLabel.substring(runningLabel.indexOf(":") + 2));
        int numCompleted = Integer.parseInt(completedLabel.substring(completedLabel.indexOf(":") + 2));
        int decreased = numRunning - 1;
        int increaseCompleted = numCompleted + 1;
        if(increased) running.setText("Running : " + decreased);
        progressBar.setValue(progressBar.getValue() + 1);
        completed.setText("Completed : " + increaseCompleted);
        limitThreads.release();
    }

    public synchronized void changeTable(int index, String status){
        urlStatus url = model.getRow(index);
        url.setStatus(status);
        model.changeRow(index, url);
    }



    private JTable createTable(){
        model = new WebTableModel(new String [] {"url", "status"});
        JTable table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        return table;
    }

    

    private JPanel createSouthPanel(){
        JPanel southPanel = new JPanel();
        BoxLayout box = new BoxLayout(southPanel, BoxLayout.Y_AXIS);
        southPanel.setLayout(box);
        single = new JButton("Single Thread Fetch");
        concurrent = new JButton("Concurrent Fetch");
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        single.addActionListener(listener -> {
            setToRunningState();
            limitThreads = new Semaphore(1);
            activateLauncher(1);
        });

        concurrent.addActionListener(listener -> {
            int numThreads = Integer.parseInt(field.getText());
            setToRunningState();
            limitThreads = new Semaphore(numThreads);
            activateLauncher(numThreads);
        });



        running = new JLabel("Running : 0");
        completed = new JLabel("Completed : 0");
        elapsed = new JLabel("Elapsed : 0");

        field = new JTextField(40);
        field.setMaximumSize(new Dimension(40, 20));

        southPanel.add(single);
        southPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        southPanel.add(concurrent);
        southPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        southPanel.add(running);
        southPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        southPanel.add(completed);
        southPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        southPanel.add(elapsed);
        southPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        southPanel.add(field);
        southPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        progressBar = new JProgressBar();
        southPanel.add(progressBar);
        southPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        southPanel.add(stopButton);
        return southPanel;
    }



    private void readFile(){
        try{
            BufferedReader reader = new BufferedReader(new FileReader("links.txt"));
            int numLines = 0;
            while(true){
                String line = reader.readLine();
                if(line == null) break;
                model.addRow(new urlStatus(line, ""));
                numLines++;
            }
            numURls = numLines;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFrame(){
        JFrame frame = new JFrame();
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel southPanel = createSouthPanel();
        JTable table = createTable();
        mainPanel.add(table.getTableHeader(), BorderLayout.NORTH);
        mainPanel.add(table, BorderLayout.CENTER);
        mainPanel.add(southPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setVisible(true);
    }


    public webFrame(){
        interruptLock = new ReentrantLock();
        createFrame();
        readFile();
    }


    public static void main(String[] args) {
        new webFrame();
    }

}
