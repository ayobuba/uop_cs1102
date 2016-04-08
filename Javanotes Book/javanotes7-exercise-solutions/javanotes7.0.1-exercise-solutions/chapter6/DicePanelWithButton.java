import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Shows a pair of dice that are rolled when the user clicks a button
 * that appears below the dice.
 */
public class DicePanelWithButton extends JPanel {
   
   private int die1 = 4;  // The values shown on the dice.
   private int die2 = 3;
   
   private Timer timer;   // Used to animate rolling of the dice.
   
   /**
    *  The constructor sets up the panel.  It creates the button and
    *  the drawing surface panel on which the dice are drawn and puts
    *  them into a BorderLayout.  It adds an ActionListener to the button
    *  that rolls the dice when the user clicks the button.
    */
   public DicePanelWithButton() {
      
      setLayout(new BorderLayout(2,2));
      setBackground(Color.BLUE);  // Will show through the gap in the BorderLayout.
      setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
      
      JPanel dicePanel = new JPanel() {
         public void paintComponent(Graphics g) { 
            super.paintComponent(g);  // fill with background color.
            drawDie(g, die1, 10, 10); // Just draw the dice.
            drawDie(g, die2, 55, 55);
         }
      };
      dicePanel.setPreferredSize( new Dimension(100,100) );
      dicePanel.setBackground( new Color(200,200,255) );  // light blue
      add(dicePanel, BorderLayout.CENTER);
      
      JButton rollButton = new JButton("Roll!");
      rollButton.addActionListener( new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
            roll();
         }
      });
      add(rollButton, BorderLayout.SOUTH);
      
   } // end constructor
   
   /**
    * Draw a die with upper left corner at (x,y).  The die is
    * 35 by 35 pixels in size.  The val parameter gives the
    * value showing on the die (that is, the number of dots).
    */
   void drawDie(Graphics g, int val, int x, int y) {
      g.setColor(Color.white);
      g.fillRect(x, y, 35, 35);
      g.setColor(Color.black);
      g.drawRect(x, y, 34, 34);
      if (val > 1)  // upper left dot
         g.fillOval(x+3, y+3, 9, 9);
      if (val > 3)  // upper right dot
         g.fillOval(x+23, y+3, 9, 9);
      if (val == 6) // middle left dot
         g.fillOval(x+3, y+13, 9, 9);
      if (val % 2 == 1) // middle dot (for odd-numbered val's)
         g.fillOval(x+13, y+13, 9, 9);
      if (val == 6) // middle right dot
         g.fillOval(x+23, y+13, 9, 9);
      if (val > 3)  // bottom left dot
         g.fillOval(x+3, y+23, 9, 9);
      if (val > 1)  // bottom right dot
         g.fillOval(x+23, y+23, 9,9);
   }
   
   
   /**
    * Run an animation that randomly changes the values shown on
    * the dice 10 times, every 100 milliseconds.
    */
   private void roll() {
      if (timer != null)
         return;
      timer = new Timer(100, new ActionListener() {
         int frames = 1;
         public void actionPerformed(ActionEvent evt) {
            die1 = (int)(Math.random()*6) + 1;
            die2 = (int)(Math.random()*6) + 1;
            repaint();
            frames++;
            if (frames == 10) {
               timer.stop();
               timer = null;
            }
         }
      });
      timer.start();
   }
   
   
} // end class DicePanelWithButton
