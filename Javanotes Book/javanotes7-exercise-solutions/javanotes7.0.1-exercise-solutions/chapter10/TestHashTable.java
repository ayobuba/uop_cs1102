/**
 * A little program to test the HashTable class.  Note that I
 * start with a really small table so that I can easily test
 * the resize() method.
 */

public class TestHashTable {

   public static void main(String[] args){
      HashTable table = new HashTable(2);  // Initial size of table is 2.
      String key,value;
      while (true) {
         System.out.println("\nMenu:");
         System.out.println("   1. test put(key,value)");
         System.out.println("   2. test get(key)");
         System.out.println("   3. test containsKey(key)");
         System.out.println("   4. test remove(key)");
         System.out.println("   5. show complete contents of hash table.");
         System.out.println("   6. EXIT");
         System.out.print("Enter your command:  ");
         switch ( TextIO.getlnInt()) {
         case 1:
            System.out.print("\n   Key = ");
            key = TextIO.getln();
            System.out.print("   Value = ");
            value = TextIO.getln();
            table.put(key,value);
            break;         
         case 2:
            System.out.print("\n   Key = ");
            key = TextIO.getln();
            System.out.println("   Value is " + table.get(key));
            break;         
         case 3:
            System.out.print("\n   Key = ");
            key = TextIO.getln();
            System.out.println("   containsKey(" + key + ") is " 
                  + table.containsKey(key));
            break;         
         case 4:
            System.out.print("\n   Key = ");
            key = TextIO.getln();
            table.remove(key);
            break;         
         case 5:
            table.dump();
            break;
         case 6:
            return;  // End program by returning from main()         
         default:
            System.out.println("   Illegal command.");
         break;
         }
         System.out.println("\nHash table size is " + table.size());
      }
   }

} // end class TestHashTable
