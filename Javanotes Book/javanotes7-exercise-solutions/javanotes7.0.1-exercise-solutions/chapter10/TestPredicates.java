import java.util.*;

/**
 * Perform some simple tests on the Predicates class.
 */
public class TestPredicates {

   /**
    * An object of type Even tests an Object of type Integer to see whether the 
    * integer that it represents is an even number. Note that the test() method 
    * should only be applied to non-null values of type Integer.
    */
   static class Even implements Predicate<Integer> {
      public boolean test(Integer i) {
         if ( i % 2 == 0 )
            return true;
         else
            return false;
      }
   } // end class Even

   
   /**
    * An object of type Big tests an Object of type Integer to see whether the 
    * integer that it represents is bigger than 100.  Note that the test() method 
    * should only be applied to non-null values of type Integer.
    */
   static class Big implements Predicate<Integer> {
      public boolean test(Integer i) {
         if ( i > 100 )
            return true;
         else
            return false;
      }
   } // end class Big

   
   /**
    * For convenience make a Collection containing some integers.  The Collection 
    * is actually a TreeSet, but that is not relevant to the main program.
    */
   static Collection<Integer> makeSet() {
      Collection<Integer> set = new TreeSet<Integer>();
      set.add(new Integer(32));
      set.add(new Integer(17));
      set.add(new Integer(142));
      set.add(new Integer(56));
      set.add(new Integer(1899));
      set.add(new Integer(57));
      set.add(new Integer(999));
      set.add(new Integer(86));
      set.add(new Integer(83));
      set.add(new Integer(100));
      return set;
   } // end makeSet()



   /**
    * Main routine tests the Predicates class by making Collections of Integers 
    * and applying the methods from the Predicates class to them.
    */
   public static void main(String[] args) {

      Collection<Integer> coll;    // A collection (from makeSet() method).

      List<Integer> result;        // The result of applying collect() to coll.

      Predicate<Integer> pred = new Even();  // Tests if an Integer is even.

      coll = makeSet();
      System.out.println("Original collection: " + coll);

      Predicates.remove(coll,pred);
      System.out.println("Remove evens: " + coll);

      coll = makeSet();
      Predicates.retain(coll,pred);
      System.out.println("Retain evens: " + coll);

      coll = makeSet();
      result = Predicates.collect(coll,pred);
      System.out.println("Collect evens: " + result);

      ArrayList<Integer> list = new ArrayList<Integer>();
      int i = Predicates.find(list,pred);
      System.out.println("Find first even: at index " + i);


      pred = new Big();        // Tests if an Integer is bigger than 100.

      coll = makeSet();
      System.out.println("Original collection: " + coll);

      Predicates.remove(coll,pred);
      System.out.println("Remove big: " + coll);

      coll = makeSet();
      Predicates.retain(coll,pred);
      System.out.println("Retain big: " + coll);

      coll = makeSet();
      result = Predicates.collect(coll,pred);
      System.out.println("Collect big: " + result);

      list = new ArrayList<Integer>(coll);
      i = Predicates.find(list,pred);
      System.out.println("Find first big: at index " + i);

   } // end main()

} // end class TestPredicates
