/**  
 * This program simulates rolling a pair of dice until they
 * come up snake eyes.  It reports how many rolls were needed.
 */

public class SnakeEyes {
  
   public static void main(String[] args) {
   
       int die1, die2;   // The values rolled on the two dice.
       
       int countRolls;   // Used to count the number of rolls.
       
       countRolls = 0;
       
       do {
          die1 = (int)(Math.random()*6) + 1;   // roll the dice
          die2 = (int)(Math.random()*6) + 1;
          countRolls++;                        // and count this roll
       } while ( die1 != 1 || die2 != 1 );
       
       System.out.println("It took " + countRolls + " rolls to get snake eyes.");
   
   }  // end main()

}  // end class