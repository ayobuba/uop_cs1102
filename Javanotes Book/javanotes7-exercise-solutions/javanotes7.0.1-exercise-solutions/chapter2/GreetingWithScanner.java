import java.util.Scanner;

public class GreetingWithScanner {
    
    public static void main(String[] args) {
    
        Scanner stdin = new Scanner( System.in );
    
        String usersName;      // The user's name, as entered by the user.
        String upperCaseName;  // The user's name, converted to upper case letters.
        
        System.out.print("Please enter your name: ");
        usersName = stdin.nextLine();
        
        upperCaseName = usersName.toUpperCase();
        
        System.out.println("Hello, " + upperCaseName + ", nice to meet you!");
    
    }  // end main()
 
}  // end class
