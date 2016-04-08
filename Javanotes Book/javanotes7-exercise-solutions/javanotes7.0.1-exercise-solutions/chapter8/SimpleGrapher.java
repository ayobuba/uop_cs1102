import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
   The SimpleGrapher program can draw graphs of functions input by the
   user.  The user enters the definition of the function in a text
   input box.  When the user presses return, the function is graphed.
   (Unless the definition contains an error.  In that case, an error
   message is displayed.)
   
   The graph is drawn on a canvas which represents the region of the
   (x,y)-plane given by  -5 <= x <= 5  and  -5 <= y <= 5.  Any part of
   the graph that lies outside this region is not shown.  The graph
   is drawn by plotting 301 points and joining them with lines.  This
   does not handle discontinuous functions properly.
   
   This program requires the class Expr,
   which is defined in by a separate file, Expr.java.
   That file contains a full description of the syntax
   of legal function definitions.
   
   This class has a main() routine so that it can be run as an application.
 */

public class SimpleGrapher extends JPanel {
   
   //-- Support for running this class as a stand-alone application --
   
   public static void main(String[] args) {
          // Open a window that shows a SimpleGrapher panel.
      JFrame window = new JFrame("SimpleGrapher");
      window.setContentPane( new SimpleGrapher() );
      window.setLocation(50,50);
      window.setSize(500,540);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setVisible(true);
   }
      
   //---------------------------------------------------------------------------------

   private GraphPanel graph;  // The JPanel that will display the graph.
                              // The nested class GraphPanel is defined below.

   private JTextField functionInput;  // A text input box where the user enters
                                      // the definition of the function.

   private JLabel message;  // A label for displaying messages to the user,
                            // including error messages when the function
                            // definition is illegal.

   public SimpleGrapher() {
         // Initialize the panel by creating and laying out the components
         // and setting up an action listener for the text field.

      setBackground(Color.GRAY);
      setLayout(new BorderLayout(3,3));
      setBorder(BorderFactory.createLineBorder(Color.GRAY,3));

      graph = new GraphPanel();
      add(graph, BorderLayout.CENTER);

      message = new JLabel(" Enter a function and press return");
      message.setBackground(Color.WHITE);
      message.setForeground(Color.RED);
      message.setOpaque(true);
      message.setBorder( BorderFactory.createEmptyBorder(5,0,5,0) );
      add(message, BorderLayout.NORTH);

      functionInput = new JTextField();
      JPanel subpanel = new JPanel();
      subpanel.setLayout(new BorderLayout());
      subpanel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
      subpanel.add(new JLabel("f(x) = "), BorderLayout.WEST);
      subpanel.add(functionInput, BorderLayout.CENTER);
      add(subpanel, BorderLayout.SOUTH);
      
      functionInput.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent evt) {
                  // Get the user's function definition from the box and use it
                  // to create a new object of type Expr.  Tell the GraphPanel to 
                  // graph this function.  If the  definition is illegal, an 
                  // IllegalArgumentException is  thrown by the Expr constructor.
                  // If this happens, the graph is cleared and an error message 
                  // is displayed in the message label.
            Expr function;  // The user's function.
            try {
               String def = functionInput.getText();
               function = new Expr(def);
               graph.setFunction(function);
               message.setText(" Enter a function and press return.");
            }
            catch (IllegalArgumentException e) {
               graph.clearFunction();
               message.setText(e.getMessage());
            }
            functionInput.selectAll();
            functionInput.requestFocus();  // Let's user start typing in input box.
         }
      });

   }  // end constructor


    //   -------------------------- Nested class ----------------------------

   private static class GraphPanel extends JPanel {

      // A object of this class can display the graph of a function
      // on the region of the (x,y)-plane given by -5 &lt;= x &lt;= 5 and
      // -5 &lt;= y &lt;= 5.  The graph is drawn very simply, by plotting
      // 301 points and connecting them with line segments.


      Expr func;  // The definition of the function that is to be graphed.
                  // If the value is null, no graph is drawn.


      GraphPanel() {
             // Constructor.
         setBackground(Color.WHITE);
         func = null;
      }


      public void setFunction(Expr exp) {
             // Set the canvas to graph the function whose definition is
             // given by the function exp.
         func = exp;
         repaint();
      }


      public void clearFunction() {
             // Set the canvas to draw no graph at all.
         func = null;
         repaint();
      }


      public void paintComponent(Graphics g) {
             // Draw the graph of the function or, if func is null, 
             // display a message that there is no function to be graphed.
         super.paintComponent(g); // Fill with background color, white.
         if (func == null) {
            g.drawString("No function is available.", 20, 30);
         }
         else {
            g.drawString("y = " + func, 5, 15);
            drawAxes(g);
            drawFunction(g);
         }
      }


      void drawAxes(Graphics g) {
             // Draw horizontal and vertical axes in the middle of the
             // canvas.  A 5-pixel border is left at the ends of the axes.
         int width = getWidth();
         int height = getHeight();
         g.setColor(Color.BLUE);
         g.drawLine(5, height/2, width-5, height/2);
         g.drawLine(width/2, 5, width/2, height-5);
      }


      void drawFunction(Graphics g) {
             // Draw the graph of the function defined by the instance 
             // variable func.  Just plot 301 points with lines 
             // between them. 
         
         double x, y;          // A point on the graph.  y is f(x).
         double prevx, prevy;  // The previous point on the graph.

         double dx;  // Difference between the x-values of consecutive 
                     // points on the graph.

         dx  = 10.0 / 300;

         g.setColor(Color.RED);

         /* Compute the first point. */

         x = -5;
         y = func.value(x);

         /* Compute each of the other 300 points, and draw a line segment
               between each consecutive pair of points.  Note that if the
               function is undefined at one of the points in a pair, then 
               the line segment is not drawn.  */

         for (int i = 1; i <= 300; i++) {

            prevx = x;           // Save the coords of the previous point.
            prevy = y;

            x += dx;            // Get the coords of the next point.

            y = func.value(x);

            if ( (! Double.isNaN(y)) && (! Double.isNaN(prevy)) ) {
                   // Draw a line segment between the two points.
               putLine(g, prevx, prevy, x, y);
            }

         }  // end for

      }  // end drawFunction()


      void putLine(Graphics g, double x1, double y1, 
                                   double x2, double y2) {
                // Draw a line segment from the point (x1,y1) to (x2,y2).
                // These real values must be scaled to get the integer 
                // coordinates of the corresponding pixels.

         int a1, b1;   // Pixel coordinates corresponding to (x1,y1).
         int a2, b2;   // Pixel coordinates corresponding to (x2,y2).

         int width = getWidth();     // Width of the canvas.
         int height = getHeight();   // Height of the canvas. 

         a1 = (int)( (x1 + 5) / 10 * width );
         b1 = (int)( (5 - y1) / 10 * height );
         a2 = (int)( (x2 + 5) / 10 * width );
         b2 = (int)( (5 - y2) / 10 * height );

         if (Math.abs(y1) < 30000 && Math.abs(y2) < 30000) {
                // Only draw lines for reasonable y-values.
                // This should not be necessary, I think,
                // but I got a problem when y was very large.)
            g.drawLine(a1,b1,a2,b2);
         }

      }  // end putLine()

   }  // end nested class GraphPanel


} // end class SimpleGrapher
