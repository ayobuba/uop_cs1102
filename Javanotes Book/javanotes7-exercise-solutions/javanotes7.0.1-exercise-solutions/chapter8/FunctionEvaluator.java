public class FunctionEvaluator {

   public static void main(String[] args) {
   
      String line;      // A line of input read from the user.
      Expr expression;  // The definition of the function f(x).
      double x;         // A value of x for which f(x) is to be calculated.
      double val;       // The value of f(x) for the specified value of x.
      
      System.out.println("This program will evaluate a specified function, f(x), at");
      System.out.println("specified values of the variable x.  The definition of f(x)");
      System.out.println("can use the operators +, -, *, /, and ^ as well as mathematical");
      System.out.println("functions such as sin, abs, and ln.");
      
      while (true) {
      
         /* Get the function from the user.  A line of input is read and
            used to construct an object of type Expr.  If the input line is
            empty, then the loop will end, and the program will terminate. */
 
         System.out.println("\n\n\nEnter definition of f(x), or press return to quit.");
         System.out.print("\nf(x) = ");
         line = TextIO.getln().trim();
         if (line.length() == 0)
            break;
            
         try {
            expression = new Expr(line);
         }
         catch (IllegalArgumentException e) {
                // An error was found in the input.  Print an error
                //    message and go back to the beginning of the loop.
            System.out.println("Error!  The definition of f(x) is not valid.");
            System.out.println(e.getMessage());
            continue;
         }
         
         /* Read values of x from the user, until the user presses return.
            If the user's input is not a legal number, print an error message.
            Otherwise, compute f(x) and print the result. */
         
         System.out.println("\nEnter values of x where f(x) is to be evaluated.");
         System.out.println("Press return to end.");
         
         while (true) {
            System.out.print("\nx = ");
            line = TextIO.getln().trim();
            if (line.length() == 0)
               break;
            try {
               x = Double.parseDouble(line);
            }
            catch (NumberFormatException e) {
               System.out.println("\"" + line + "\" is not a legal number.");
               continue;
            }
            val = expression.value(x);
            if (Double.isNaN(val))
               System.out.println("f(" + x + ") is undefined.");
            else
               System.out.println("f(" + x + ") = " + val);
         }  // end while
         
      } // end while
      
      System.out.println("\n\n\nOK.  Bye for now.");
   
   }  // end main();
   
} // end class FunctionEvaluator
