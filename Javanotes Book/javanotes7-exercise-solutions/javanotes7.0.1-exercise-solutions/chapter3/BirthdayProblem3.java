/**
 * This program simulates selecting 365 people at random and finding
 * how many different birthdays they have among them.
 */

public class BirthdayProblem3 {

   /**
    * Simulate choosing people at random and checking the
    * day of the year they were born on.  The number of
    * different days found among 365 people is counted
    * and output.
    */
   public static void main(String[] args) {
      
      boolean used[];  // used[i] will be true if a person is found
                       // whose birthday is on the i-th day of the year.
      
      used = new boolean[365];  // Initially, all entries are false.
      
      /* Choose 365 days at random, and mark each day by
         setting the corresponding entry in the array, used,
         to true.  (If the value is already true, it doesn't 
         matter.  We are only interested in whether or not
         the birthday occurs, not how many times it occurs.)
      */
      
      for (int i = 0; i < 365; i++) {
         int birthday;  // The selected birthday.
         birthday = (int)(Math.random()*365);
         used[birthday] = true;
      }
      
      /* Now, count how many entries in the used array are true.
         This is how many different birthdays were found.
      */
      
      int count = 0;
      
      for (int day = 0; day < 365; day++) {
             // If this day occurred as a birthday, count it.
         if (used[day] == true)
            count++;
      }

      System.out.println("Among 365 people, there were " + count
                                      + " different birthdays.");

   } 
 
   
} // end class BirthdayProblem3