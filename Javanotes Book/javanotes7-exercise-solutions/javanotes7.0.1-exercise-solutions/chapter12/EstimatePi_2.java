import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This program uses a probabilistic technique to estimate the
 * value of the mathematical constant pi.  The technique is to
 * choose random numbers x and y in the range 0 to 1, and to
 * compute x*x + y*y.  The probability that x*x + y*y is less than
 * 1 is pi/4.  If many trials are performed, and the number of
 * trials in which x*x+y*y is less than 1 is divided by the total
 * number of trials, the result is an approximation for pi/4.
 * Multiplying this by 4 gives an approximation for pi.
 * 
 * The program shows the estimate produced by this procedure, along
 * with the number of trials that have been done and, for comparison,
 * the actual value of pi.  These values are shown in three JLabels.
 * The computation is done by a separate thread that updates the
 * contents of the labels after every millionth trial.
 * 
 * In this version of the program, there is a "Run"/"Pause" button
 * that controls the computation thread.  Clicking the button once
 * starts the thread; clicking it again pauses it.  Initially, the
 * thread is paused.
 */
public class EstimatePi_2 extends JPanel implements ActionListener {
    
    /**
     * Main program just creates a window to show the panel that
     * contains the JLabels and the Run/Pause button.
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("Estimating Pi Probabilistically");
        EstimatePi_2 content = new EstimatePi_2();
        window.setContentPane(content);
        window.pack(); 
        window.setResizable(false);
        window.setLocation( 300, 200 );
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setVisible(true);
    }
    
    private JLabel piEstimateLabel;  // A label for showing the current estimate of pi.
    private JLabel countLabel;       // A label for showing the number of trials.
    
    private JButton runPauseButton;  // Button to control the thread.  Clicking this
                                     // button will pause the thread if it is running
                                     // and will restart it if it is paused.
    
    private ComputationThread runner;  // The thread that does the computation.
    
    private volatile boolean running;  // Control variable for signaling the thread to
                                       // run or pause.  Initially, this is false, so
                                       // the thread pauses as soon as it is created,
                                       // until the user clicks the "Run" button.
    
    
    /**
     * Constructor creates the three display labels and adds them to this
     * panel.  It adds a "Run"/"Pause" button below the labels.  
     * It also creates and starts the computation thread.
     */
    public EstimatePi_2() {

        setLayout(new GridLayout(4, 1, 2, 2));
        setBackground(Color.BLUE);
        setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
        
        countLabel =      new JLabel(" Number of Trials:   0");
        piEstimateLabel = new JLabel(" Current Estimate:   (none)");
        JLabel piLabel =  new JLabel(" Actual value of pi: " + Math.PI + " ");
        Font bigMonospace = new Font("Monospaced", Font.PLAIN, 20);
        countLabel.setFont(bigMonospace);
        piEstimateLabel.setFont(bigMonospace);
        piLabel.setFont(bigMonospace);
        countLabel.setOpaque(true);
        piEstimateLabel.setOpaque(true);
        piLabel.setOpaque(true);
        add(piLabel);
        add(piEstimateLabel);
        add(countLabel);
        
        JPanel bottom = new JPanel();
        add(bottom);
        runPauseButton = new JButton("Run");
        bottom.add(runPauseButton);
        runPauseButton.addActionListener(this);
        
        runner = new ComputationThread();
        runner.start();
        
    } // end constructor


    /**
     * This method responds to clicks on the button, by
     * toggling the value of the signal variable from true
     * to false or from false to true.  The text on the 
     * button is changed to match the state.  When
     * running is set to true, notify() is called to wake
     * up the thread.
     */
    public void actionPerformed(ActionEvent evt) {
        if (running) {
            runPauseButton.setText("Run");
            running = false;
        }
        else {
            runPauseButton.setText("Pause");
            synchronized(runner) {
                running = true;
                runner.notify(); 
            }
        }
    }
    
    
    /**
     *  This class defines the thread that does the computation.
     *  The thread runs in an infinite loop in which it performs
     *  batches of 1000000 trials and then updates the display labels.
     *  Just after it starts and between batches, the thread tests
     *  the value of the signal variable, running.  If this variable
     *  is false, then the thread sleeps until the value of running
     *  is set to true.
     */
    private class ComputationThread extends Thread {
        final int BATCH_SIZE = 1000000;  // Number of trials between updates of the display.
        long trialCount;     // Total number of trials that have been performed.
        long inCircleCount;  // Number of trials in which x*x+y*y is less than 1.
        public ComputationThread() {
            setDaemon(true);
            setPriority(Thread.currentThread().getPriority() - 1);
        }
        public void run() {
            while (true) {
                synchronized(this) {
                    while ( ! running ) { // wait for running to be true
                        try {
                            wait();
                        }
                        catch (InterruptedException e) {
                        }
                    }
                }
                for (int i = 0; i < BATCH_SIZE; i++) {
                    double x = Math.random();
                    double y = Math.random();
                    trialCount++;
                    if (x*x + y*y < 1)
                        inCircleCount++;                        
                }
                double estimateForPi = 4 * ((double)inCircleCount / trialCount);
                countLabel.setText(      " Number of Trials:   " + trialCount);
                piEstimateLabel.setText( " Current Estimate:   " + estimateForPi);
            }
        }
    }
    
    
}  // end class EstimatePi_2
