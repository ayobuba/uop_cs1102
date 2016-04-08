import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.io.*;

import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * This program lets the user keep a persistent "phone book" that
 * contains names and phone numbers.  The data for the phone book
 * is stored in a file in the user's home directory.  This is
 * a GUI version of the program, with the phonebook displayed
 * in a table that the user can edit.  The data files for this
 * version of the program are in XML format. 
 *
 * WARNING:  This program will save a file named ".phone_book_xml_demo" 
 * in the home directory of the user who runs it.  On some computers,
 * this will be a "hidden" file.
 */
public class PhoneDirectoryGUI {
   
   /**
    * The name of the file in which the phone book data is kept.  The
    * file is stored in the user's home directory.  The "." at the
    * beginning of the file name means that the file will be a
    * "hidden" file on Unix-based computers, including Linux and
    * Mac OS X.
    */
   private static String DATA_FILE_NAME = ".phone_book_xml_demo";
   
   /**
    * A File object created from the absolute path name of the file.
    */
   private static File dataFile;
   
   /**
    * Holds the phone book data as it was read from the data file 
    * when the program started.  The data in this map is not modified
    * after it has been read.
    */
   private static TreeMap<String,String> phoneBook;
   
   
   public static void main(String[] args) {
            
      phoneBook = new TreeMap<String,String>();      
      File userHomeDirectory = new File( System.getProperty("user.home") );
      dataFile = new File( userHomeDirectory, DATA_FILE_NAME );
      
      /* If the data file already exists, then the data in the file is
       * read and is used to initialize the phone directory.  The user
       * is informed before the file is created and is given a chance to
       * exit the program immediately.
       */
      
      if ( ! dataFile.exists() ) {
         int response = JOptionPane.showConfirmDialog(null, 
               "No phone book data file found.  To create a new one,\n" 
                    + "click OK.  To exit the program now, click CANCEL.\n\n"
                  + "(The name of the file will be:\n      "
                  + dataFile.getAbsolutePath() + ")", 
               "Create phonebook?", JOptionPane.OK_CANCEL_OPTION);
         if (response == JOptionPane.CANCEL_OPTION)
            System.exit(1);
      }
      else {
         System.out.println("Reading phone book data...");
         try {
            DocumentBuilder docReader = 
               DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmldoc = docReader.parse(dataFile);
            Element root = xmldoc.getDocumentElement();
            if (! root.getTagName().equals("phone_directory"))
               throw new Exception();
            NodeList nodes = root.getChildNodes();
            for (int i = 0; i < nodes.getLength(); i++) {
               if ( nodes.item(i) instanceof Element ) {
                  Element entry = (Element)nodes.item(i);
                  if (! entry.getTagName().equals("entry"))
                     throw new Exception();
                  String entryName = entry.getAttribute("name");
                  String entryNumber = entry.getAttribute("number");
                  if (entryName.length() == 0)
                     throw new Exception();
                  phoneBook.put(entryName,entryNumber);
               }
            }
         }
         catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                  "An error occurred while trying to read\n"
                  + "the phone directory from the file:\n   "
                  + dataFile.getAbsolutePath()
                  +"\n\nThis program cannot continue.");
            System.exit(1);
         }
      }
      
      /* The phone book has been read successfully (if it existed).  Open
       * a window where the user can view and edit the phone directory.
       */
      
      JFrame window = new JFrame("PhoneBook");
      final PhoneDirectoryPanel panel = new PhoneDirectoryPanel(phoneBook);
      window.setContentPane( panel );
      window.pack();
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      window.setLocation( (screenSize.width - window.getWidth())/2, 80 );
      window.setVisible(true);
      window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      window.addWindowListener( new WindowAdapter() {
            // When the user clicks the close box of the window,
            // the window will be disposed (that is, closed), and the
            // windowClosed method in this WindowListener will be 
            // called.  This method saves the phone book data and
            // calls System.exit() to terminate the program.
         public void windowClosed(WindowEvent evt) {
            saveData(panel.getPhoneBook());
            System.exit(0);
         }
      });
      
   } // end main()
      
   
   /** 
    * Before ending the program, write the current contents of the
    * phone directory, but only if some changes have been made to
    * the directory.  This is called by a window listener when the
    * window is closed.  If an error occurs while the data is being
    * saved, a pop-up box will inform the user (but this is unlikely).
    * @param newPhoneBook the phone book data that has possibly been
    *    edited by the user.  If this is the same as the data that
    *    was read from the file originally, this method does not
    *    re-save the unchanged data.  If the data has changed, the
    *    new data is written to the file.
    */
   private static void saveData(TreeMap<String,String> newPhoneBook) {
      if (phoneBook.equals(newPhoneBook) ) 
         System.out.println("No changes to phone book.");
      else{
         System.out.println("Saving phone directory changes to file " + 
               dataFile.getAbsolutePath() + " ...");
         PrintWriter out;
         try {
            out = new PrintWriter( new FileWriter(dataFile) );
         }
         catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Whoops!  Some error occurred while\n"
                  + "trying to save your phone book.  Sorry.");
            return;
         }
         out.println("<?xml version=\"1.0\"?>");
         out.println("<phone_directory>");
         for ( Map.Entry<String,String> entry : newPhoneBook.entrySet() ) {
            out.print("  <entry name='");
            out.print(entry.getKey());
            out.print("' number='");
            out.print(entry.getValue());
            out.println("'/>");
         }
         out.println("</phone_directory>");
         out.close();
         if (out.checkError()) {
            JOptionPane.showMessageDialog(null,"Whoops!  Some error occurred while\n"
                  + "trying to save your phone book.  Sorry.");
         }
      }
   }
   
   
   /**
    * This class defines the GUI for viewing and editing the phone
    * book.  The main program adds an object of this type to a frame.
    */
   private static class PhoneDirectoryPanel extends JPanel {
      
      private JTable phoneTable;        // For showing/editing the phone book.
      private DefaultTableModel model;  // The model for the table.  (This is
                                        //   where the data is actually stored.)
      
      /**
       * Constructor creates the table and shows it with an "Add row"
       * and a "Delete Row" button beneath it.
       * @param initialPhoneBook contains the phone book data that is initially
       *    added to the table.  (This comes from the main program and
       *    contains the data that was read from the file.)
       */
      public PhoneDirectoryPanel(TreeMap<String,String> initialPhoneBook) {
         
         /* First create the arrays that hold the data for the table
          * and the names of its columns.  These arrays are used to
          * create the table model.
          */
         
         int entryCount = initialPhoneBook.size();
         String[][] entries;
         if (entryCount == 0)
             entries = new String[1][2];
         else {
            entries = new String[entryCount][2];
            int index = 0;
            for (Map.Entry<String, String> entry : initialPhoneBook.entrySet()) {
               entries[index][0] = entry.getKey();
               entries[index][1] = entry.getValue();
               index++;
            }
         }
         String[] columnHeads = new String[] { "Name", "Number" };
         
         /* Create the table model from the data and column name arrays,
          * and use it to create the JTable.  After this, the official
          * phone book data is what's in the table, and the arrays and
          * initialPhoneBook are no longer used.
          */
         
         model = new DefaultTableModel(entries,columnHeads);
         phoneTable = new JTable(model);
         
         /* Customize the appearance of the table.
          */
         
         phoneTable.setRowHeight(27);
         phoneTable.setShowGrid(true);
         phoneTable.setGridColor(Color.BLACK);
         phoneTable.getTableHeader().setReorderingAllowed(false);
         
         /* Create the two buttons, and set up listeners to respond
          * when the user clicks them.
          */
         
         JButton addRowButton = new JButton("Add Row");
         addRowButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                   // Add a row at the end of the table.  Also, select
                   // that row and set the first cell in that row to
                   // be the cell that is currently being edited.
               stopEditing();
               model.addRow( new String[] { null, null } );
               int newRow = model.getRowCount() - 1;
               phoneTable.setRowSelectionInterval(newRow, newRow);
               phoneTable.editCellAt( newRow, 0 );
               Component c = phoneTable.getEditorComponent();
               if (c != null) // (Should not be null.)
                  c.requestFocus();
            }
         });
         
         JButton deleteRowButton = new JButton("Delete Row");
         deleteRowButton.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                   // Delete the selected row, if there is one.  If more than
                   // one cell is selected, the first selected row is deleted.
               stopEditing();
               int selectedRow = phoneTable.getSelectedRow();
               if (selectedRow >= 0)
                  model.removeRow(selectedRow);
            }
         });
         
         /* Build the layout for the panel.
          */
         
         setLayout(new BorderLayout(2,2));
         setBackground(Color.GRAY);
         setBorder(BorderFactory.createLineBorder(Color.GRAY,2));
         add( new JScrollPane(phoneTable), BorderLayout.CENTER );
         JPanel buttonBar = new JPanel();
         buttonBar.add(addRowButton);
         buttonBar.add(deleteRowButton);
         add( buttonBar, BorderLayout.SOUTH ); 
         
      } // end constructor
      
      
      /**
       * This method is called before modifying the table or getting
       * the data out of the table.  If a cell is currently being 
       * edited, it turns off editing of that cell and changes the
       * data model to match the current content of the editor.  (Note
       * that the table model does not change while the cell is being 
       * edited.)
       */
      private void stopEditing() {
         if (phoneTable.getCellEditor() != null)
            phoneTable.getCellEditor().stopCellEditing();
      }

      
      /**
       * This method is called when the program ends to get the phone
       * book data, which might have been edited by the user.  The
       * data is in the table model.  This method gets the data from
       * the table model and puts it into a TreeMap, which is then
       * returned as the value of the method.  Note that if a row in
       * the table contains an empty name, it is ignored.  However,
       * if there is an empty number for a non-empty name, the number
       * is changed to "(unknown)" and the row is added to the TreeMap.
       * Note that by using a TreeMap, we allow only one number for
       * a given name.  If the user has used the same name in more than
       * one row, the first row with that name will be lost;
       * it would probably be better to warn the user about this or to
       * take some other, more reasonable answer (like adding a number
       * to the end of the duplicate name).
       */
      public TreeMap<String, String> getPhoneBook() {
         stopEditing();
         TreeMap<String,String> phoneBook = new TreeMap<String,String>();
         for (int row = 0; row < model.getRowCount(); row++) {
            String name = (String)model.getValueAt(row, 0);
            String number = (String)model.getValueAt(row, 1);
            if (number == null || number.trim().length() == 0)
               number = "(unknown)";
            if (name != null && name.trim().length() > 0)
               phoneBook.put(name,number);
         }
         return phoneBook;
      }
      
   }
   

}
