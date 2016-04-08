/**
 *  How many random people do you have to select before you find
 *  THREE with the same birthday (that is, three people who were born 
 *  on the same day of the same month, but not necessarily in the 
 *  same year).  This program simulates the process.  (It ignores the
 *  possibility of people born on leap day.)
 */

public class BirthdayProblem2 {

   /**
    * Simulate choosing people at random and checking the
    * day of the year they were born on.  If the person is
    * the third who was born on that day of the year, stop,
    * and output the number of people who were checked.
    */
   public static void main(String[] args) {

      int[] numFound;  // numFound[i] will be the number of people
                       // who have been found who have a birthday
                       // on the i-th day of the year
      
      int count;       // The number of people who have been checked.

      numFound = new int[365];  // Initially, all entries are 0.
      
      count = 0;

      while (true) {
             // Select a birthday at random, from 0 to 364.
             // If the same birthday was already seen twice
             // before, then quit.  Otherwise, add one to
             // the corresponding entry in the numFound array 
             // to record that a person with that birthday
             // has been found.
         int birthday;  // The selected birthday.
         birthday = (int)(Math.random()*365);
         count++;
         if ( numFound[birthday] == 2 )
            break;
         numFound[birthday]++;
      }

      System.out.println("It took " + count + 
               " tries to find three people with the same birthday.");

   }
 
   
} // end class BirthdayProblem2