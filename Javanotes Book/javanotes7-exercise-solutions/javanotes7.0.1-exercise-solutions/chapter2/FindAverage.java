
public class FindAverage {

   public static void main(String[] args) {

       String name;     // The student's name, from the first line of the file.
       int exam1, exam2, exam3;     // The student's grades on the three exams.
       double average;  // The average of the three exam grades.

       TextIO.readFile("testdata.txt");  // Read from the file.

       name = TextIO.getln();  // Reads the entire first line of the file.
       exam1 = TextIO.getlnInt();
       exam2 = TextIO.getlnInt();
       exam3 = TextIO.getlnInt();

       average = ( exam1 + exam2 + exam3 ) / 3.0;

       System.out.printf("The average grade for %s was %1.1f", name, average);
       System.out.println();

   }

}
