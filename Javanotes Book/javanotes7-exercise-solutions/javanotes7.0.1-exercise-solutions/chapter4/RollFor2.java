/**
 * This program simulates rolling a pair of dice over and over until the
 * total showing on the two dice is 2.  It reports the number of rolls 
 * it took to get a 2.  (This was written to test the subroutine, rollFor.)
 */
public class RollFor2 {
  
   public static void main(String[] args) {
      int numberOfRolls;  // Number of rolls to get a 2.
      numberOfRolls = rollFor(2);
      System.out.println("It took " + numberOfRolls + " rolls to get snake eyes.");
   }  // end main()
   
   /**
    * Simulates rolling a pair of dice until a given total comes up.
    * Precondition:  The desired total is between 2 and 12, inclusive.
    * @param N the total that we want to get on the dice
    * @return the number of times the dice are rolled before the
    *    desired total occurs
    * @throws IllegalArgumentException if the parameter, N, is not a number
    *    that could possibly come up on a pair of dice
    */
   public static int rollFor( int N ) {
       if ( N < 2 || N > 12 )
          throw new IllegalArgumentException("Impossible total for a pair of dice.");
       int die1, die2;  // Numbers between 1 and 6 representing the dice.
       int roll;        // Total showing on dice.
       int rollCt;      // Number of rolls made.
       rollCt = 0;
       do {
          die1 = (int)(Math.random()*6) + 1;
          die2 = (int)(Math.random()*6) + 1;
          roll = die1 + die2;
          rollCt++;
       } while ( roll != N );
       return rollCt;
   }

}  // end class RollFor2