/**
 * This program performs the following type of experiment:
 * Given a desired total roll, such as 7, roll a pair of
 * dice until the given total comes up, and count how many
 * rolls are necessary.  Now do the experiment over and over,
 * and find the average number of rolls.  The number of times
 * the experiment is repeated is given by the constant,
 * NUMBER_OF_EXPERIMENTS.  Several statistics are computed and
 * printed out for each possible roll = 2, 3, ..., 12:
 * the average number of rolls, the standard deviation,
 * and the maximum number of rolls.
 */

public class DiceRollStats2 {

   static final int NUMBER_OF_EXPERIMENTS = 10000;
   
   private static PairOfDice dice = new PairOfDice();
            // A single pair of dice, which will be used for all
            // the experiments.

   
   public static void main(String[] args) {
   
       System.out.println("Dice Total   Avg # of Rolls   Stand. Deviation   Max # of Rolls");
       System.out.println("----------   --------------   ----------------   --------------");

       for ( int total = 2;  total <= 12;  total++ ) {
          StatCalc stats;  // An object that will compute the statistics.
          stats = new StatCalc();
          for ( int i = 0; i < NUMBER_OF_EXPERIMENTS; i++ ) {
                // Do the experiment of counting the number of rolls
                // required to roll the desired total, and enter the
                // number of rolls into stats' dataset.
             stats.enter( rollFor(total) );
          }
          System.out.printf("%6d", total);
          System.out.printf("%18.3f", stats.getMean());
          System.out.printf("%19.3f", stats.getStandardDeviation());
          System.out.printf("%14.3f", stats.getMax());
          System.out.println();
       }
       
   } // end main
   
   /**
    * Roll the dice repeatedly until the total on the
    * two dice comes up to be N.  N MUST be one of the numbers
    * 2, 3, ..., 12.  (If not, this routine will go into an
    * infinite loop!).  The number of rolls is returned.
    */
   static int rollFor( int N ) {
       int rollCt = 0;  // Number of rolls made.
       do {
          dice.roll();
          rollCt++;
       } while ( dice.getTotal() != N );
       return rollCt;
   }

   
}  // end class DiceRollStats2