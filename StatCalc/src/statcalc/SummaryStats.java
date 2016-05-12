package statcalc;
// This program uses StatCalc.java.
import java.util.Scanner;   

public class SummaryStats {
   
   public static void main(String[] args) {
   
          Scanner in = new Scanner(System.in);
   
          StatCalc myStatCalc; // Computes stats for numbers entered by user
          myStatCalc = new StatCalc();
   
          double item; // Number entered by user
          
          // Prompt user for list of numbers
          System.out.println("Enter your list of numbers, zero to end: ");
          
          // for each number, add to myStatCalc
          do {
             System.out.print(">> ");
             item = in.nextDouble();
             if (item != 0)
                myStatCalc.enter(item);
          } while (item != 0);
   
          // Print off summary stats for user
          System.out.println("\n     Summary Statistics");
          System.out.println("-----------------------------");
          System.out.println("Count:                " + myStatCalc.getSum());
          System.out.println("Mean:                 " + myStatCalc.getMean());
          System.out.println("Standard Deviation:   " + myStatCalc.getStandardDeviation());
   
   } // end main()
   
} // end SummaryStats