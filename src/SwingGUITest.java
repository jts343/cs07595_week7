import com.github.stefanbirkner.systemlambda.SystemLambda;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SwingGUITest
{
    SwingGUI gui;

    @org.junit.jupiter.api.BeforeEach
    void setUp()
    {
        gui = new SwingGUI();
        gui.startGUI();

        // Default to stop=0, start=1000
        gui.startNumberTextBox.setText("0");
        gui.stopNumberTextBox.setText("10000");
    }

    @org.junit.jupiter.api.Test
    void ExitActionPerformed() throws Exception
    {
        // Catch the exit status and make sure was exit(0)
        int exitStatus = 5;
        exitStatus = SystemLambda.catchSystemExit(() -> {
            gui.exitButton.doClick();
        });

        assertEquals(0, exitStatus);
    }

    @org.junit.jupiter.api.Test
    void ExitActionPerformed_MidProcess() throws Exception
    {
        gui.submitButton.doClick();

        // Catch the exit status and make sure was exit(0)
        int exitStatus = 5;
        exitStatus = SystemLambda.catchSystemExit(() -> {
            gui.exitButton.doClick();
        });

        TimeUnit.SECONDS.sleep(1);

        // Tried to exit, if ==0
        assertEquals(0, exitStatus);

        // Exit call would interrupt as with Cancel
        assertTrue(gui.primeCounter.isAvailable());
    }

    @org.junit.jupiter.api.Test
    void CancelActionPerformed_MidProcess() throws InterruptedException
    {
        gui.submitButton.doClick();
        TimeUnit.MILLISECONDS.sleep(100);

        gui.cancelButton.doClick();
        assertTrue((Integer.parseInt(gui.resultBox.getText()) < 1229));

        TimeUnit.SECONDS.sleep(1);
        assertTrue(gui.primeCounter.isAvailable());
    }

    @org.junit.jupiter.api.Test
    void CancelActionPerformed_NoRunningThreads() throws InterruptedException
    {
        assertTrue(gui.primeCounter.isAvailable());

        String test1 = gui.resultBox.getText();
        gui.cancelButton.doClick();
        String test2 = gui.resultBox.getText();

        assertEquals(test1, test2);
        assertTrue(gui.primeCounter.isAvailable());
    }

    @org.junit.jupiter.api.Test
    void SubmitActionPerformed_ExpectedCase() throws InterruptedException
    {
        gui.submitButton.doClick();

        TimeUnit.SECONDS.sleep(2);
        assertEquals("1229", gui.resultBox.getText());
    }

    @org.junit.jupiter.api.Test
    void SubmitActionPerformed_CheckForDisable() throws InterruptedException
    {
        gui.submitButton.doClick();

        assertTrue(gui.primeCounter.isAvailable() == false);
    }

    @org.junit.jupiter.api.Test
    void SubmitActionPerformed_DoesResultBoxContinuouslyUpdate() throws InterruptedException
    {
        gui.submitButton.doClick();
        int test1 = Integer.parseInt(gui.resultBox.getText());
        TimeUnit.SECONDS.sleep(1);
        int test2 = Integer.parseInt(gui.resultBox.getText());

        assertTrue((test1 != test2));
        assertTrue((test1 != 0));
        assertTrue((test2 != 0));
    }

    @org.junit.jupiter.api.Test
    void SubmitActionPerformed_StopLTStart() throws InterruptedException
    {
        gui.startNumberTextBox.setText("1000");
        gui.stopNumberTextBox.setText("100");
        gui.submitButton.doClick();

        TimeUnit.MILLISECONDS.sleep(2);
        assertEquals("BAD PARAMS", gui.resultBox.getText());
    }

    @org.junit.jupiter.api.Test
    void SubmitActionPerformed_StartLTZero() throws InterruptedException
    {
        gui.startNumberTextBox.setText("-5");
        gui.stopNumberTextBox.setText("100");
        gui.submitButton.doClick();

        TimeUnit.MILLISECONDS.sleep(2);
        assertEquals("BAD PARAMS", gui.resultBox.getText());
    }

    @org.junit.jupiter.api.Test
    void SubmitActionPerformed_StopLTEZero() throws InterruptedException
    {
        gui.stopNumberTextBox.setText("0");
        gui.submitButton.doClick();

        TimeUnit.MILLISECONDS.sleep(2);
        assertEquals("BAD PARAMS", gui.resultBox.getText());
    }

    @org.junit.jupiter.api.Test
    void SubmitActionPerformed_NumberFormatException() throws InterruptedException
    {
        gui.stopNumberTextBox.setText("Jeff");
        gui.submitButton.doClick();

        TimeUnit.MILLISECONDS.sleep(2);
        assertEquals("BAD PARAMS", gui.resultBox.getText());
    }
}