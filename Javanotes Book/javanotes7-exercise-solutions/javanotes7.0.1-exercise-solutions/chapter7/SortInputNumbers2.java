/**
 * This program reads up to 100 positive integers from the user and 
 * prints them in sorted order.  Input ends when the user enters a
 * non-positive integer.  The numbers are read and inserted into
 * an array.  The array is maintained at all times in sorted order.
 */

public class SortInputNumbers2 {

   public static void main(String[] args) {
  
      double[] numbers;  // An array for storing the input values.
      int numCt;         // The number of numbers saved in the array.
      double num;        // One of the numbers input by the user.
    
      numbers = new double[100];   // Space for 100 numbers.
      numCt = 0;                   // No numbers have been saved yet.
    
      System.out.println("Enter up to 100 positive numbers; Enter 0 to end");
    
      while (true) {   // Get the numbers and insert them into the array.
         System.out.print("? ");
         num = TextIO.getlnDouble();
         if (num <= 0)
            break;
         insert(numbers, numCt, num);
         numCt++;
      }
      
      System.out.println("\nYour numbers in sorted order are:\n");
    
      for (int i = 0; i < numCt; i++) {
          System.out.println( numbers[i] );
      }
    
   } // end main();
   
   /**
    * Assume that A contains itemsInArray in increasing order.
    * Insert newItem into its correct position in the sorted array.
    */
   static void insert(double[] A, int itemsInArray, double newItem) {
      int loc = itemsInArray - 1;
      while (loc >= 0 && A[loc] > newItem) {
             // Move the item from A[loc] up one space.
         A[loc + 1] = A[loc];
         loc = loc - 1;
      }
      A[loc + 1] = newItem;  // Put newItem in the last vacated space.
   } // end insert
  
}  // end class SortInputNumbers2
