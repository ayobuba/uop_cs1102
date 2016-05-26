import java.util.Arrays;

package arraypractice;

public class ArrayPractice {

    public static void printArray(int[] array) {
        System.out.print("[");
        for (int i=0; i < array.length; i++) {
            int item = array[i];
            System.out.print(item);
            if (i < array.length -1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    } 
    
    public static void main(String[] args) {
        // index:  0  1  2  3  4
        // array: [10, 5, 3, 2, 1] --> length 5
        
        // Random objects from array
        // Math.abs(rand.nextInt) % Array.length; -- > use modulo 
        
       
        // declaring, allocating, initializing
        int[] intArray1;
        int[] intArray2 = new int[4];
        int[] intArray3 = {5, 2, 9, 1, 3};
        
        String[] shoppingList = {"bananas", "apples", "pears"};
        
        intArray1[0] = 10;
        intArray1[2] = 3;
        intArray1[3] = 2;
        intArray1[4] = 1;
        
        
        // print out arrays
        System.out.println(Arrays.toString(intArray2));
        System.out.println(Arrays.toString(intArray3));
        System.out.println(Arrays.toString(intArray1));
        System.out.println();
        printArray(intArray2);
        printArray(intArray3);
        
        Arrays.sort(intArray3);
        printArray(intArray3);
  
        printArray(shoppingList);
        
        System.out.println("Special for loop: ");
        // for each loop
        for (String s : shoppingList) {
            System.out.println(s);
        }
    }
}
