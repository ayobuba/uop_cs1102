/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package firstsubroutines;

/**
 *
 * @author brocd8s
 */
public class Main () {
        // print the string converted to lower case and stripped of any non-letter characters
        // Print the reverse string and say whether the string is a palindrome
        public static void main(String[] args)
        // prompt the user to enter a string
        String text = ""; //
        String stripped = ""; //
        String reversed = ""; //
        System.out.println("Enter a string to find out if it's a palindrome ");
        text = user_input.next();
        // test the string entered by the user to determine whether it is a palindrome
        if strReverse(text) == text:
            System.out.println("This IS a palindrome.");
        else:
            System.out.println("This is NOT a palindrome.");
        // convert the string to lower case
        // remove any non-letter characters from the string
        // compare the resulting string with the reverse of the same string
        // If strings are equal, then the original string is considered to be a palindrome
    
    System.out.println(stripped);
    // finds the reverse of a string
    static String strReverse (String str) {
 	String reverse;
  	int i;
  	reverse = "";
  	for (i = str.length() - 1; i >= 0; i--) {
   		reverse = reverse + str.charAt(i);      
        return reverse;
  }
    // make a new string that is a copy of the parameter string, omitting all the non-letters
    static String returnString (String str) {
        
        String char = "";
        str = str.toLowerCase();
        if (char >= 'a' && char <= 'z');
        return str
    }
    }
}



    
    stripped = stripped.toLowerCase();
    // print the string converted to lower case and stripped of any non-letter characters
}