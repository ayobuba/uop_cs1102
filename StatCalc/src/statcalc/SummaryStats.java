package statcalc;
      /**
      * Computes and display several statistics for a set of non-zero
      * numbers entered by the user.  (Input ends when user enters 0.)
      * This program uses StatCalc.java.
      */
     
     import java.util.Scanner;

     public class SummaryStats {
   
       public static void main(String[] args) {
   
          Scanner in = new Scanner(System.in);
   
          StatCalc calc; // Computes stats for numbers entered by user.
          calc = new StatCalc();
   
          double item; // One number entered by the user.
   
          System.out.println("Enter your list of numbers, zero to end: ");
   
          do {
             System.out.print(">> ");
             item = in.nextDouble();
             if (item != 0)
                calc.enter(item);
          } while (item != 0);
   
          System.out.println("\nStatistics:");
          System.out.println("Count:              " + calc.getSum());
          System.out.println("Mean:               " + calc.getMean());
          System.out.println("Standard Deviation: " + calc.getStandardDeviation());
   
       } // end main()
   
    } // end SimpleStats