import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingGUI extends JFrame implements ActionListener
{
    JTextField startNumberTextBox;
    JTextField stopNumberTextBox;
    JTextArea resultBox;
    JButton submitButton;
    JButton cancelButton;
    protected final PrimeCounter primeCounter;
    JButton exitButton;

    SwingGUI()
    {
        primeCounter = new PrimeCounter();
    }

    void startGUI()
    {
        int windowWidth = 400;
        int windowHeight = 300;

        int startX = (Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (windowWidth / 2);
        int startY = (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - (windowHeight / 2);

        this.setTitle("Prime Counter");

        JLabel startNumberLabel = new JLabel("Start Number:");
        startNumberLabel.setBounds(100, 25, 150, 25);
        this.add(startNumberLabel);

        startNumberTextBox = new JTextField();
        startNumberTextBox.setBounds(200, 25, 150, 25);
        startNumberTextBox.setText("0");
        this.add(startNumberTextBox);

        JLabel stopNumberLabel = new JLabel("Stop Number:");
        stopNumberLabel.setBounds(100, 75, 150, 25);
        this.add(stopNumberLabel);

        stopNumberTextBox = new JTextField();
        stopNumberTextBox.setBounds(200, 75, 150, 25);
        stopNumberTextBox.setText("1000000");
        this.add(stopNumberTextBox);

        JLabel resultLabel = new JLabel("Number of primes in range:");
        resultLabel.setBounds(windowWidth / 2 - 75, 125, windowWidth, 25);
        this.add(resultLabel);

        resultBox = new JTextArea();
        resultBox.setBounds(windowWidth / 2 - 50, 150, 100, 30);
        this.add(resultBox);

        submitButton = new JButton("Submit");
        submitButton.setBounds(35, 200, 90, 30);
        submitButton.addActionListener(this);
        this.add(submitButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(150, 200, 90, 30);
        cancelButton.addActionListener(this);
        this.add(cancelButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(265, 200, 90, 30);
        exitButton.addActionListener(this);
        this.add(exitButton);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(windowWidth, windowHeight);
        this.setLocation(new Point(startX, startY));
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent)
    {
        if(actionEvent.getSource() == submitButton)
        {
            if(primeCounter.isAvailable())
            {
                submitButtonProcessing();
            }
        }
        else if(actionEvent.getSource() == cancelButton)
        {
            cancelButtonProcessing();
        }
        else if(actionEvent.getSource() == exitButton)
        {
            exitButtonProcessing();
        }
    }

    private void exitButtonProcessing()
    {
        primeCounter.interrupt();
        System.exit(0);
    }

    private void submitButtonProcessing()
    {
        System.out.println("Submit clicked");

        if(submitButton.isEnabled())
        {
            if(isSubmitClickValid())
            {
                int startN = Integer.parseInt(startNumberTextBox.getText());
                int stopN = Integer.parseInt(stopNumberTextBox.getText());

                System.out.println("starting prime counter");
                primeCounter.start2(startN, stopN, resultBox);

                resultBox.setText(String.valueOf(primeCounter.getCount()));
            }
            else
            {
                resultBox.setText("BAD PARAMS");
            }
        }

        System.out.println("End Submit Button");
    }

    private void disableSubmitButton()
    {
        Runnable disableButtonTask = new Runnable()
        {
            @Override
            public void run()
            {
                while(!primeCounter.isAvailable())
                {
                }
                System.out.println("Submit button Reenabled");
                submitButton.setEnabled(true);
            }
        };
        disableButtonTask.run();
    }

    private boolean isSubmitClickValid()
    {

        boolean isValid = true;

        try
        {
            int start_n = Integer.parseInt(startNumberTextBox.getText());
            int stop_n = Integer.parseInt(stopNumberTextBox.getText());

            if((start_n < 0) || (stop_n <= 0) || (start_n >= stop_n))
            {
                isValid = false;
            }
        }
        catch(NumberFormatException nfe)
        {
            isValid = false;
        }

        return isValid;
    }

    private void cancelButtonProcessing()
    {
        System.out.println("Cancel clicked");
        primeCounter.interrupt();
    }
}
