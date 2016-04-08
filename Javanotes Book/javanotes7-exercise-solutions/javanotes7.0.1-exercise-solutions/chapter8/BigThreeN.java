import java.math.BigInteger;

/** 
 * This program prints out 3N+1 sequences for starting values of N that
 * are entered by the user.  Since integers are represented as objects of
 * type BigInteger, it will work for arbitrarily large integers.  The
 * starting value specified by the user must be greater than zero.  The
 * program continues to read input from the user and print 3N+1 sequences
 * until the user inputs an empty line.  If the user's input is illegal,
 * the program will print an error message and continue.
 */
public class BigThreeN {
 
 
    private static final BigInteger THREE = new BigInteger("3");
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");
    
    
    public static void main(String[] args) {
    
       String line;   // A line of input from the user.
  
       BigInteger N;  // The starting point for the 3N+1 sequence,
                      //   as specified by the user.
       
       System.out.println("This program will print 3N+1 sequences for positive starting values");
       System.out.println("that you enter.  There is no pre-set limit on the number of");
       System.out.println("digits in the numbers that you enter.  The program will end when");
       System.out.println("you enter an empty line.");
       
       while (true) {
          System.out.println();
          System.out.println("Enter the starting value, or press return to end.");
          System.out.print("? ");
          line = TextIO.getln().trim();
          if (line.length() == 0)
              break;
          try {
              N = new BigInteger(line);
              if (N.signum() == 1)
                 printThreeNSequence(N);
              else
                 System.out.println("Error:  The starting value cannot be less than or equal to zero.");
          }
          catch (NumberFormatException e) {
              System.out.println("Error:  \"" + line + "\" is not a legal integer.");
          }
       }
       
       System.out.println();
       System.out.println("OK.  Bye for now!");
    
    }  // end main()
    
    
    /**
     * Print the 3N+1 sequence starting from N, and count the number
     * of terms in the sequence.  It is assumed that N is not null and
     * that it is greater than zero.
     */
    private static void printThreeNSequence(BigInteger N) {
 
       assert N != null && N.signum() == 1 : "Illegal parameter value.";
 
       int count;  // The number of terms in the sequence.
       
       System.out.println();
       System.out.println("The 3N+1 sequence starting with " + N + " is:");
       System.out.println();
       
       System.out.println(N.toString());   // Print N as the first term of the sequence
       count = 1;
       
       while ( ! N.equals(ONE) ){   // Compute and print the next term
          if (N.testBit(0) == false) {
                  // N is even.  Divide N by 2.
              N = N.divide(TWO);
          }
          else {
                  // N is odd.  Multiply N by 3, then add 1.
              N = N.multiply(THREE);
              N = N.add(ONE);
          }
          System.out.println(N.toString());
          count++;
       }
 
       System.out.println();
       System.out.println("There were " + count + " terms in the sequence.");
 
    }  // end printThreeNSequence
    
    
} // end BigThreeN
