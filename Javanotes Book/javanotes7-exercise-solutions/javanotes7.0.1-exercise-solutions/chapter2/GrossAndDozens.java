
public class GrossAndDozens {
  
   /*  This program will convert a given number of eggs into
       the number of gross plus the number of dozens plus the
       number of left over eggs.
          For example, 1342 eggs is 9 gross plus 3 dozen plus 10.
       The number of eggs is input by the user, and the computed
       results are displayed. 
   */

   public static void main(String[] args) {
   
      int eggs;         // Number of eggs, input by user.
      int gross;        // How many gross in that number of eggs?
      int aboveGross;   // How many eggs are left over, above an
                        //    integral number of gross?  This number
                        //    can be computed as eggs % 144, and is
                        //    in the range 0 to 143.  This number will
                        //    be divided into dozens and extras.
      int dozens;       // How many dozens in aboveGross?
      int extras;       // How many eggs are left over, above integral
                        //    numbers of gross and dozens? 
   
      System.out.print("How many eggs do you have?  ");
      eggs = TextIO.getlnInt();
      
      gross = eggs / 144;
      aboveGross = eggs % 144;
      
      dozens = aboveGross / 12;
      extras = aboveGross % 12;          
      
      System.out.print("Your number of eggs is ");
      System.out.print(gross);
      System.out.print(" gross, ");
      System.out.print(dozens);
      System.out.print(" dozen, and ");
      System.out.print(extras);
      System.out.println();
   
   }  // end main()

}  // end class
