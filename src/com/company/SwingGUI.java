package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingGUI extends JFrame implements ActionListener {

    JTextField startNumberTextBox;
    JTextField stopNumberTextBox;
    JTextArea resultBox;
    JButton submitButton;
    JButton cancelButton;

    void startGUI()
    {
        int windowWidth = 400;
        int windowHeight = 300;

        int startX = (Toolkit.getDefaultToolkit().getScreenSize().width/2) - (windowWidth/2);
        int startY = (Toolkit.getDefaultToolkit().getScreenSize().height/2) - (windowHeight/2);

        this.setTitle("Prime Counter");

        JLabel startNumberLabel = new JLabel("Start Number:");
        startNumberLabel.setBounds(100, 25, 150, 25);
        this.add(startNumberLabel);

        startNumberTextBox = new JTextField();
        startNumberTextBox.setBounds(200, 25, 150, 25);
        this.add(startNumberTextBox);

        JLabel stopNumberLabel = new JLabel("Stop Number:");
        stopNumberLabel.setBounds(100, 75, 150, 25);
        this.add(stopNumberLabel);

        stopNumberTextBox = new JTextField();
        stopNumberTextBox.setBounds(200, 75, 150, 25);
        this.add(stopNumberTextBox);

        JLabel resultLabel = new JLabel("Number of primes in range:");
        resultLabel.setBounds(windowWidth/2-75, 125, windowWidth, 25);
        this.add(resultLabel);

        resultBox = new JTextArea();
        resultBox.setBounds(windowWidth/2 - 50, 150, 100, 30);
        this.add(resultBox);

        submitButton = new JButton("Submit");
        submitButton.setBounds(50, 200, 90, 30);
        submitButton.addActionListener(this);
        this.add(submitButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(250, 200, 90, 30);
        cancelButton.addActionListener(this);
        this.add(cancelButton);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(windowWidth, windowHeight);
        this.setLocation(new Point(startX, startY));
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == submitButton)
        {
            submitButtonProcessing();
        }
        else
        {
            cancelButtonProcessing();
        }
    }

    private void submitButtonProcessing()
    {
        System.out.println("Submit");
        resultBox.setText("Submit");
    }

    private void cancelButtonProcessing()
    {
        System.out.println("Cancel");
        resultBox.setText("Cancel");
    }
}
