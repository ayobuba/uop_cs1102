package squarematrix;

public class SquareMatrix {
    public static void arrayTest( int[][] array ) {
        int[][] m; // initializing a square matrix "m"
        int R = array.length; // number of rows
        int C = array[0].length; // number of columns
        int size = array.length - 1;
        if (R != C) { // check for square
            System.out.print("This is not a square matrix."); 
    }
} // end of array test
    
  public static void secondaryDiagonal( int[][] array ) {
      // adds secondary diagonal
       sum = 0;
       for(int i = 0; i<squarematrix.length; i++){
                sum += squarematrix[i][size-i];
        }

        System.out.println("Secondary diagonal sum: " + sum);
        }

      
  }
    public static void main(String[] args) {
        arrayTest({[2],[3]})
       
        }


 
