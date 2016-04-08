import java.awt.*;
import javax.swing.*;

/**
 * A trivial program that tests the StopWatchLabel2 component.
 * The program just creates a window to show a StopWatchLabel2.
 */

public class TestStopWatch2 {

   public static void main(String[] args) {

      StopWatchLabel2 watch = new StopWatchLabel2();
      watch.setFont( new Font("SansSerif", Font.BOLD, 24) );
      watch.setBackground(Color.WHITE);
      watch.setForeground( new Color(180,0,0) );
      watch.setOpaque(true);
  
      JFrame window = new JFrame("Stop Watch");
      window.setContentPane(watch);
      window.pack();
      window.setResizable(false);
      window.setLocation(100,80);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setVisible(true);

   }

}
