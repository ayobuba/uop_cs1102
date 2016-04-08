
import java.awt.*;
import javax.swing.*;

/**
 * A trivial program that tests the StopWatchLabel2 component.
 * The program just creates a window to show a StopWatchLabel2.
 */

public class TestMirrorText2 {

   public static void main(String[] args) {

      MirrorText2 mtext = new MirrorText2("Let me out of here!");
      mtext.setFont( new Font("SansSerif", Font.BOLD, 48) );
      mtext.setBackground(Color.BLACK);
      mtext.setForeground(Color.RED);
      mtext.setOpaque(true);
      mtext.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
  
      JFrame window = new JFrame("Stop Watch");
      window.setContentPane(mtext);
      window.pack();
      window.setResizable(false);
      window.setLocation(100,80);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setVisible(true);

   }

}
