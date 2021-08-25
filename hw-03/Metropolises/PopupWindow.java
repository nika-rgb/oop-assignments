package Metropolises;

import javafx.scene.*;
import javafx.scene.layout.BorderPane;

import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.dbcp2.AbandonedTrace;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * If user enters non-valid input this popup window will be visible and tell user that input is not valid.
 */
public class PopupWindow extends JFrame{
    public PopupWindow(String title, String text){
        JButton close = new JButton("Close");
        close.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PopupWindow.super.dispose();
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout(0, 100));

        mainPanel.add(new JLabel(text), BorderLayout.CENTER);
        mainPanel.add(close, BorderLayout.SOUTH);

        this.setTitle(title);
        this.setContentPane(mainPanel);
        this.setSize(200, 200);

    }

    public void setVisible(){
        this.setVisible(true);
    }

}
