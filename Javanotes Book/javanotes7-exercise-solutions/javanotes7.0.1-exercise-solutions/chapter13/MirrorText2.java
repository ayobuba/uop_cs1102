import java.awt.*;
import javax.swing.*;

/**
 * A component for displaying a mirror-reversed line of text.
 * The text will be centered in the available space.  This component
 * is defined as a subclass of JLabel.
 * 
 * This version uses a transform in a Graphics2D graphics context
 * to achieve the mirror-reversal, and is defined as a simple
 * subclass of JLabel.
 */
public class MirrorText2 extends JLabel {
   
   /**
    * Define the constructor so that the default alignment of
    * the text will be centered, instead of left-justified.
    * @param text The string that is to be shown on the label.
    */
   public MirrorText2(String text) {
      super(text,JLabel.CENTER);
   }

   /**
    * The paintComponent method calls super.paintComponent() after
    * applying a left/right mirror transform.
    */
   public void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D)g.create();
      g2.translate(getWidth(),0);
      g2.scale(-1,1);
      super.paintComponent(g2);
      g2.dispose();
   }


}  // end MirrorText2

