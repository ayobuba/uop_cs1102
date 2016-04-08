public class Dozens {
  
   /*  This program will convert a given number of eggs into
       the number of dozens plus the number of left over eggs.
       For example, 57 eggs is 4 dozen eggs plus 9 eggs left over.
       The number of eggs is input by the user, and the computed
       results are displayed. 
   */

   public static void main(String[] args) {
   
      int eggs;    // Number of eggs, input by user.
      int dozens;  // How many dozens in that number of eggs?
      int extras;  // How many eggs are left over, above an integral
                   //    number of dozens?  This value is in the
                   //    range 0 to 11, and it is computed as
                   //    the remainder when eggs is divided by 12.
   
      System.out.print("How many eggs do you have?  ");
      eggs = TextIO.getlnInt();
      
      dozens = eggs / 12;
      extras = eggs % 12;
      
      System.out.print("Your number of eggs is ");
      System.out.print(dozens);
      System.out.print(" dozen and ");
      System.out.print(extras);
      System.out.println();
   
   }  // end main()

}  // end class
