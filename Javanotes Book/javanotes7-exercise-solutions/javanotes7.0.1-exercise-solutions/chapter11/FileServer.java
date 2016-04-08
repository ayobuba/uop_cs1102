import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * This program is a very simple network file server.  The 
 * server has a list of available text files that can be
 * downloaded by the client.  The client can also download
 * the list of files.  When the connection is opened, the
 * client sends one of two possible commands to the server:
 * "INDEX" or "GET <file-name>".  The server replies to
 * the first command by sending the list of available files.
 * It responds to the second with a one-line message,
 * either "OK" or "ERROR".  If the message is "OK", it is
 * followed by the contents of the file with the specified
 * name.  The "ERROR" message indicates that the specified
 * file does not exist on the server. (The server can also
 * respond with the message "unknown command" if the command
 * it reads is not one of the two possible legal commands.)
 * (The commands INDEX and GET are not case-sensitive.)
 * 
 * The server program requires a command-line parameter
 * that specifies the directory that contains the files
 * that the server can serve.  The files should all be
 * text files, but this is not checked.  Also, the server
 * must have permission to read all the files.
 */
public class FileServer {

   static final int LISTENING_PORT = 3210;


   public static void main(String[] args) {

      File directory;        // The directory from which the server
                             //    gets the files that it serves.

      ServerSocket listener; // Listens for connection requests.

      Socket connection;     // A socket for communicating with a client.


      /* Check that there is a command-line argument.
         If not, print a usage message and end. */

      if (args.length == 0) {
         System.out.println("Usage:  java FileServer <directory>");
         return;
      }

      /* Get the directory name from the command line, and make
         it into a file object.  Check that the file exists and
         is in fact a directory. */

      directory = new File(args[0]);
      if ( ! directory.exists() ) {
         System.out.println("Specified directory does not exist.");
         return;
      }
      if (! directory.isDirectory() ) {
         System.out.println("The specified file is not a directory.");
         return;
      }

      /* Listen for connection requests from clients.  For
         each connection, call the handleConnection() method
         to process it.  The server runs until the program
         is terminated, for example by a CONTROL-C. */

      try {
         listener = new ServerSocket(LISTENING_PORT);
         System.out.println("Listening on port " + LISTENING_PORT);
         while (true) {
            connection = listener.accept();
            handleConnection(directory,connection);
         }
      }
      catch (Exception e) {
         System.out.println("Server shut down unexpectedly.");
         System.out.println("Error:  " + e);
         return;
      }

   } // end main()


   /**
    * This method processes the connection with one client.
    * It creates streams for communicating with the client,
    * reads a command from the client, and carries out that
    * command.  The connection is also logged to standard output.
    * An output beginning with ERROR indicates that a network
    * error occurred.  A line beginning with OK means that
    * there was no network error, but does not imply that the
    * command from the client was a legal command.
    */
   private static void handleConnection(File directory, Socket connection) {
      Scanner incoming;       // For reading data from the client.
      PrintWriter outgoing;   // For transmitting data to the client.
      String command = "Command not read";
      try {
         incoming = new Scanner( connection.getInputStream() );
         outgoing = new PrintWriter( connection.getOutputStream() );
         command = incoming.nextLine();
         if (command.equalsIgnoreCase("index")) {
            sendIndex(directory, outgoing);
         }
         else if (command.toLowerCase().startsWith("get")){
            String fileName = command.substring(3).trim();
            sendFile(fileName, directory, outgoing);
         }
         else {
            outgoing.println("ERROR unsupported command");
            outgoing.flush();
         }
         System.out.println("OK    " + connection.getInetAddress()
               + " " + command);
      }
      catch (Exception e) {
         System.out.println("ERROR " + connection.getInetAddress()
               + " " + command + " " + e);
      }
      finally {
         try {
            connection.close();
         }
         catch (IOException e) {
         }
      }
   }

   /**
    * This is called by the handleConnection() method in response to an "INDEX" command
    * from the client.  Send the list of files in the server's directory.
    */
   private static void sendIndex(File directory, PrintWriter outgoing) throws Exception {
      String[] fileList = directory.list();
      for (int i = 0; i < fileList.length; i++)
         outgoing.println(fileList[i]);
      outgoing.flush();
      outgoing.close();
      if (outgoing.checkError())
         throw new Exception("Error while transmitting data.");
   }

   /**
    * This is called by the handleConnection() command in response to "GET <fileName>" 
    * command from the client.  If the file doesn't exist, send the message "error".
    * Otherwise, send the message "ok" followed by the contents of the file.
    */
   private static void sendFile(String fileName, File directory, PrintWriter outgoing) throws Exception {
      File file = new File(directory,fileName);
      if ( (! file.exists()) || file.isDirectory() ) {
         // (Note:  Don't try to send a directory, which
         // shouldn't be there anyway.)
         outgoing.println("ERROR");
      }
      else {
         outgoing.println("OK");
         BufferedReader fileIn = new BufferedReader( new FileReader(file) );
         while (true) {
            // Read and send lines from the file until
            // an end-of-file is encountered.
            String line = fileIn.readLine();
            if (line == null)
               break;
            outgoing.println(line);
         }
      }
      outgoing.flush(); 
      outgoing.close();
      if (outgoing.checkError())
         throw new Exception("Error while transmitting data.");
   }


} //end class FileServer
