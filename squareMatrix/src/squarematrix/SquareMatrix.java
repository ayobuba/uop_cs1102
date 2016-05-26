package squarematrix;

public class SquareMatrix {

    public static void main(String[] args) {
        int[][] m = {   
                       {10, 12, 11}, //   {1, 2, 3},
                       { 9,  8, 31}, //   {4, 5, 6},
                       { 2, 16, 24}  //   {7, 8, 9},
                    };
        
        int sum = 0;
        int R = m.length; // length of rows
        int C = m[0].length; // length of columns
        
        if (R == C) { // checks that matrix is square
        // for m the secondary diagonal is 2, 8, 11 --> sum = 21
            for (int i = 0; i < m.length; i++) { // iterates through secondsary diagonal
                sum += m[i][m.length - i - 1]; // adds each diagonal element to sum
        } } else {
                System.out.println("This is not a square matrix.");
        }
        
        System.out.println("Diagonal sum: " + sum);
        }
        
        
    
    }

