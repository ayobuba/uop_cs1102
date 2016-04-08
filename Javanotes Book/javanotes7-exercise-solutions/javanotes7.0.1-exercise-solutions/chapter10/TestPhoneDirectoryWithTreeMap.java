
public class TestPhoneDirectoryWithTreeMap {

   public static void main(String[] args) {
       PhoneDirectoryWithTreeMap directory = new PhoneDirectoryWithTreeMap();
       System.out.println("This program creates a PhoneDirectoryWithTreeMap and");
       System.out.println("adds several entries.  It then prints the contents of");
       System.out.println("directory and does a few lookups.");
       System.out.println();
       directory.putNumber("Fred","555-1234");
       directory.putNumber("Barney","555-2345");
       directory.putNumber("Wilma","555-3456");
       System.out.println("Contents are:");
       System.out.println();
       directory.print();
       System.out.println();
       System.out.println("Number for Fred is " + directory.getNumber("Fred"));
       System.out.println("Number for Wilma is " + directory.getNumber("Wilma"));
       System.out.println("Number for Tom is " + directory.getNumber("Tom"));
          // The output from the last line should be null.
   }

}
