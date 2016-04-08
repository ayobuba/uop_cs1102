
    /** 
     * This program will convert Roman numerals to ordinary arabic numerals
     * and vice versa.  The user can enter a numerals of either type.  Arabic
     * numerals must be in the range from 1 to 3999 inclusive.  The user ends
     * the program by entering an empty line.
     */
    public class RomanConverter {
    
       public static void main(String[] args) {
          
          System.out.println("Enter a Roman numeral and I will convert it to an ordinary");
          System.out.println("arabic integer.  Enter an integer in the range 1 to 3999");
          System.out.println("and I will convert it to a Roman numeral.  Press return when");
          System.out.println("you want to quit.");
          
          while (true) {
    
             System.out.println();
             System.out.print("? ");
             
             /* Skip past any blanks at the beginning of the input line.
                Break out of the loop if there is nothing else on the line. */
             
             while (TextIO.peek() == ' ' || TextIO.peek() == '\t')
                TextIO.getAnyChar();
             if ( TextIO.peek() == '\n' )
                break;
                
             /* If the first non-blank character is a digit, read an arabic
                numeral and convert it to a Roman numeral.  Otherwise, read
                a Roman numeral and convert it to an arabic numeral. */
                
             if ( Character.isDigit(TextIO.peek()) ) {
                int arabic = TextIO.getlnInt();
                try {
                    RomanNumeral N = new RomanNumeral(arabic);
                    System.out.println(N.toInt() + " = " + N.toString());
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                    System.out.println(e.getMessage());
                }
             }
             else {
                String roman = TextIO.getln();
                try {
                    RomanNumeral N = new RomanNumeral(roman);
                    System.out.println(N.toString() + " = " + N.toInt());
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid input.");
                    System.out.println(e.getMessage());
                }
             }
    
          }  // end while
          
          System.out.println("OK.  Bye for now.");
    
       }  // end main()
       
    } // end class RomanConverter
