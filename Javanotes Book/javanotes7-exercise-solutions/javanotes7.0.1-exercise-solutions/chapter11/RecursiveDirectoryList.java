import java.io.File;
import java.util.Scanner;

/**
 * This program lists the files in a directory specified by
 * the user.  If one of those files is itself a directory, the
 * program recursively lists the contents of that subdirectory.
 * The user is asked to type in the name of the directory that is
 * to be listed.  If the name entered by the user is not a directory,
 * a message is printed and the program ends.
 */
public class RecursiveDirectoryList {


   public static void main(String[] args) {

      String directoryName;  // Directory name entered by the user.
      File directory;        // File object referring to the directory.
      Scanner scanner;       // For reading a line of input from the user.

      scanner = new Scanner(System.in);  // scanner reads from standard input.

      System.out.print("Enter a directory name: ");
      directoryName = scanner.nextLine().trim();
      directory = new File(directoryName);

      if (directory.isDirectory() == false) {
         if (directory.exists() == false)
            System.out.println("There is no such directory!");
         else // the file exists but is not a directory
            System.out.println("That file is not a directory.");
      }
      else {
            // List the contents of the directory recursively, with
            // no indentation at the top level.
         listDirectoryContents(directory,"");
      }

   } // end main()


   /**
    * A recursive subroutine that lists the contents of a directory, 
    * including the contents of its subdirectories to any level of nesting.
    * @param dir the directory whose contents are to be listed.  It is assumed
    *   that dir is in fact a directory. 
    * @param indent a string of blanks that is prepended to each item in
    *    the directory listing.  This string grows in length with each increase
    *    in the level of directory nesting.
    */
   private static void listDirectoryContents(File dir, String indent) {
      String[] files;  // List of names of files in the directory.
      System.out.println(indent + "Directory \"" + dir.getName() + "\":");
      indent += "   ";  // Increase the indentation for listing the contents.
      files = dir.list();
      for (int i = 0; i < files.length; i++) {
            // If the file is a  directory, list its contents
            // recursively.  Otherwise, just print its name.
         File f = new File(dir, files[i]);
         if (f.isDirectory())
            listDirectoryContents(f, indent);
         else
            System.out.println(indent + files[i]);
      }
   } // end listContents()


} // end class RecursiveDirectoryList
