/**
 *  This program evaluates simple expressions such as 2 + 2
 *  and 34.2 * 7.81, consisting of a number, an operator,
 *  and another number.  The operators +, -, *, / are allowed.
 *  The program will read and evaluate expressions until
 *  the user inputs a line that starts with the number 0.
 */

public class SimpleCalculator {

   public static void main(String[] args) {
   
      double firstNum;    // First number in the expression.
      double secondNum;   // Second number in the expression.
      char operator;      // The operator in the expression.
      double value;       // The value of the expression.
      
      System.out.println("Enter expressions such as  2 + 2  or  34.2 * 7.81");
      System.out.println("using any of the operators +, -, *, /.");
      System.out.println("To end, enter a 0.");
      System.out.println();
      
      while (true) {
          
          /* Get user's input, ending program if first number is 0. */
      
          System.out.print("? ");
          firstNum = TextIO.getDouble();
          if (firstNum == 0)
             break;
          operator = TextIO.getChar();
          secondNum = TextIO.getlnDouble();
          
          /* Compute the value of the expression. */
          
          switch (operator) {
              case '+':
                 value = firstNum + secondNum;
                 break;
              case '-':
                 value = firstNum - secondNum;
                 break;
              case '*':
                 value = firstNum * secondNum;
                 break;
              case '/':
                 value = firstNum / secondNum;
                 break;
              default:
                 System.out.println("Unknown operator: " + operator);
                 continue;  // Back to start of loop!
          } // end switch
          
          /* Display the value. */
          
          System.out.println("Value is " + value);
          System.out.println();
                    
      } // end while
      
      System.out.println("Good bye");
   
   }  // end main()

}  // end class SimpleCalculator