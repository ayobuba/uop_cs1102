package icecream;

/**
 *
 * @author brocd8s
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
      /* Open file for reading; if it can't be opened, end the program */
      try {
         TextIO.readFile("icecream.dat");
      }
      catch (IllegalArgumentException e) {
         System.out.println("Can't open file icecream.dat for reading!");
         System.out.println("Please make sure the file is present before");
         System.out.println("running the program.");
         System.exit(1);  // Terminates the program.
      }
      
        /* Read the file, keeping track of total cones, strawberry and % strawberry */
        String flavor = "";
        int totalCones = 0; // Initialize a variable to count lines (cones)
        int strawCones = 0; // Initialize a variable to count strawberry cones
        double strawPercent = 0.0; // % of cones that are strawberry
        while ( ! TextIO.eof() ) {  // process one line of data.
            flavor = TextIO.getln();  // Reads an entire input line as a String.
            totalCones += 1;
            if (flavor.equals("Strawberry")) 
                 strawCones += 1; // adds each instance of a strawberry flavor 
        strawPercent = ( (( (float)strawCones / totalCones ) * 100) ); // % of cones that are strawberry 
      }
       
       System.out.println("Number of cones: " + totalCones); 
       System.out.println("Number of strawberry cones: " + strawCones + " (" + (Math.round(strawPercent) + " percent)"); 
    }
}
