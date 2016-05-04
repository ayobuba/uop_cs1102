package firstsubroutines;

public class Firstsubroutines {
   // Subroutines stripString and revString
    
   // static subroutine that finds the reverse of a string 
   public static String revString( String str ) {
        // declare and initalize empty string variable
        String reverse = "";
        // declare decrement variable 
        int i;
        // loop through user input string
        for (i = str.length() - 1; i >= 0; i--) {
        // add letters to variable in reverse order
        reverse = reverse + str.charAt(i);
        // return reversed string  }
        
      }
       return reverse;
   } // end of revString
   
   /** 
   * static subroutine that makes a new string
   * (copy of the parameter string with non-letters removed)
   */ 
   public static String stripString( String str ) {
       // go through each character and check if it's a lower case letter
         String stripped; // initialize variable
         stripped = ""; // set variable equal to empty string
         stripped = str.replaceAll("[^a-zA-Z]", "");          
         return stripped; // return new string
 } // end of stripString
   
  // reads in a string from the user and alerts user whether or not it is a palindrome 
  public static void main(String[] args) {
        //initalize and declare variables
        String input_text;
        String lower_string;
        String stripped = "";
        String reversed = "";
        //prompt user for string input
        TextIO.put("enter a string to see if it's a palindrome: ");
        //store user input in a variable
        input_text = TextIO.getln();
        //System.out.println(input_text);
        lower_string = input_text.toLowerCase();
        //System.out.println(lower_string);
        
        // call strip subroutine, assigning value to "stripped"
        stripped = stripString(lower_string);
        System.out.println("stripped: " + stripped);
        
        // call revString using stripped as parameter
        // assign value to "reversed"
        reversed = revString(stripped);
        System.out.println("reversed: " + reversed);
        
        // if block to provide feedback to user
        if (!stripped.equals(reversed)){
            // if reversed is not equal to stripped it is NOT a palindrome
           System.out.println("This is NOT a palindrome.");
       }else{
           // otherwise, it is a palindrome
           System.out.println("This IS a palindrome.");   
       }
   }     
   } // end of firstsubroutines