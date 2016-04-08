import java.awt.*;
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
 * In this version of the program, the computation thread runs
 * continually from the time the program is started until it
 * ends.  It is run at a reduced priority so that it does not
 * interfere with the GUI thread.
 */
public class EstimatePi_1 extends JPanel {
    
    /**
     * Main program just creates a window to show the panel that
     * contains the JLabels.
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("Estimating Pi Probabilistically");
        EstimatePi_1 content = new EstimatePi_1();
        window.setContentPane(content);
        window.pack(); 
        window.setResizable(false);
        window.setLocation( 300, 200 );
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setVisible(true);
    }
    
    private JLabel piEstimateLabel;  // A label for showing the current estimate of pi.
    private JLabel countLabel;       // A label for showing the number of trials.
    
    private ComputationThread runner;  // The thread that does the computation.
    
    
    /**
     * Constructor creates the three display labels and adds them to this
     * panel.  It also creates and starts the computation thread.
     */
    public EstimatePi_1() {

        setLayout(new GridLayout(0, 1, 2, 2));
        setBackground(Color.BLUE);
        setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
        
        countLabel =      new JLabel(" Number of Trials:   0");
        piEstimateLabel = new JLabel(" Current Estimate:   (none)");
        JLabel piLabel =  new JLabel(" Actual value of pi: " + Math.PI + "  ");
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
        
        runner = new ComputationThread();
        runner.start();
        
    } // end constructor

    
    /**
     *  This class defines the thread that does the computation.
     *  The thread runs in an infinite loop in which it performs
     *  batches of 1000000 trials and then updates the display labels.
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
    
    
} // end class EstimatePi_1
