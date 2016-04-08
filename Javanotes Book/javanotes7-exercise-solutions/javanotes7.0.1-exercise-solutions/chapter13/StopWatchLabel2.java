import java.awt.event.*;
import javax.swing.*;

/**
 * A custom component that acts as a simple stop-watch.  When the user clicks
 * on it, this component starts timing.  When the user clicks again,
 * it displays the time between the two clicks.  Clicking a third time
 * starts another timer, etc.  While it is timing, the label displays
 * the integral number of seconds since it was started.
 */
public class StopWatchLabel2 extends JLabel 
                               implements MouseListener, ActionListener {

   private long startTime;   // Start time of timer.
                             //   (Time is measured in milliseconds.)

   private boolean running;  // True when the timer is running.
   
   private Timer timer;      // Generates events that cause the component
                             // to be repainted periodically while the
                             // stop watch is running.

   /**
    * Constructor sets initial text on the label to
    * "Click to start timer." and sets up a mouse listener
    * so the label can respond to clicks.
    */
   public StopWatchLabel2() {
      super("  Click to start timer.  ", JLabel.CENTER);
      addMouseListener(this);
   }
   
   
   /**
    * Tells whether the timer is currently running.
    */
   public boolean isRunning() {
      return running;
   }
   
   
   /**
    * This will be called when an event from the timer is received.  It just 
    * sets the stop watch to show the amount of time that it has been running.
    * Time is rounded down to the nearest second.
    */
   public void actionPerformed(ActionEvent evt) {
      long time = (System.currentTimeMillis() - startTime) / 1000;
      setText("Running:  " + time + " seconds");
    }

   
   /**
    * React when the user presses the mouse by starting
    * or stopping the stop watch and changing the text that
    * is shown on the label.
    */
   public void mousePressed(MouseEvent evt) {
      if (running == false) {
            // Record the time and start the stop watch.
         running = true;
         startTime = evt.getWhen();  // Time when mouse was clicked.
         setText("Running:  0 seconds");
         if (timer == null) {
            timer = new Timer(100,this);
            timer.start();
         }
         else
            timer.restart();
      }
      else {
            // Stop the stop watch.  Compute the elapsed time since the
            // stop watch was started and display it.
         timer.stop();
         running = false;
         long endTime = evt.getWhen();
         double seconds = (endTime - startTime) / 1000.0;
         setText("Time: " + seconds + " sec.");
      }
   }

   public void mouseReleased(MouseEvent evt) { }
   public void mouseClicked(MouseEvent evt) { }
   public void mouseEntered(MouseEvent evt) { }
   public void mouseExited(MouseEvent evt) { }

}
