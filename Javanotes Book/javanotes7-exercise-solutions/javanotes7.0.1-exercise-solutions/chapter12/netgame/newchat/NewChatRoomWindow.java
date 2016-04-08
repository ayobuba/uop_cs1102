package netgame.newchat;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeMap;

import javax.swing.*;
import netgame.common.*;

/**
 * This class represents a client for a "chat room" application.  The chat
 * room is hosted by a server running on some computer.  The user of this
 * program must know the host name (or IP address) of the computer that
 * hosts the chat room.  When this program is run, it asks for that
 * information and for the name that the user wants to use in the chat
 * room.  Then, it opens a window that has an input box where the
 * user can enter messages to be sent to the chat room.  The message is 
 * sent when the user presses return in the input box or when the
 * user clicks a Send button.  There is also a text area that shows 
 * a transcript of all messages from participants in the chat room.
 * <p>The user can also send private messages to individual users.
 * The user selects the recipient's name from a pop-up list of
 * connected users.
 * <p>Participants in the chat room are represented by ID numbers
 * that are assigned to them by the server when they connect. They
 * also have names which they select.
 */
public class NewChatRoomWindow extends JFrame {
    
    private final static int PORT = 37830; // The ChatRoom port number; can't be 
                                           // changed here unless the ChatRoomServer
                                           // program is also changed.

    /**
     * Gets the host name (or IP address) of the chat room server from the
     * user, gets the user's name, and opens a NewChatRoomWindow.  The program ends 
     * when the user closes the window.
     */
    public static void main(String[] args) {
        String host = JOptionPane.showInputDialog(
                       "Enter the host name of the\ncomputer that hosts the chat room:");
        if (host == null || host.trim().length() == 0)
            return;
        String name = JOptionPane.showInputDialog(
                       "Enter that name that you want\nto use in the chat room.");
        if (name == null || name.trim().length() == 0)
            return;
        NewChatRoomWindow window = new NewChatRoomWindow(host,name.trim());
        window.setLocation(200,100);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    /**
     * A ChatClient connects to the Hub and is used to send messages to
     * and receive messages from a Hub.  Four types of message are
     * received from the Hub.  A ForwardedMessage represents a message
     * that was entered by some user and sent to all users of the
     * chat room.  A PrivateMessage represents a message that was
     * sent by another user only to this user.  A ClientConnectedMessage
     * is sent when a new user enters the room.  A ClientDisconnectedMessage
     * is sent when a user leaves the room.
     */
    private class ChatClient extends Client {
        
        /**
         * Opens a connection the chat room server on a specified computer.
         */
        ChatClient(String host) throws IOException {
            super(host, PORT);
        }
        
        /**
         * Responds when a message is received from the server.
         */
        protected void messageReceived(Object message) {
            if (message instanceof ForwardedMessage) {
                ForwardedMessage fm = (ForwardedMessage)message;
                String senderName = clientNameMap.get(fm.senderID);
                addToTranscript(senderName + " SAYS:  " + fm.message);
            }
            else if (message instanceof PrivateMessage) {
                PrivateMessage pm = (PrivateMessage)message;
                String senderName = clientNameMap.get(pm.senderID);
                addToTranscript("PRIVATE MESSAGE FROM " + senderName + ":  " + pm.message);
            }
            else if (message instanceof ClientConnectedMessage) {
                ClientConnectedMessage cm = (ClientConnectedMessage)message;
                addToTranscript('"' + cm.nameMap.get(cm.newClientID) + "\" HAS JOINED THE CHAT ROOM.");
                newNameMap(cm.nameMap);
            }
            else if (message instanceof ClientDisconnectedMessage) {
                ClientDisconnectedMessage dm = (ClientDisconnectedMessage)message;
                addToTranscript('"' + clientNameMap.get(dm.departingClientID) + "\" HAS LEFT THE CHAT ROOM.");
                newNameMap(dm.nameMap);
            }
        }
        
        /**
         * This method is part of the connection set up.  It sends the user's selected
         * name to the hub by writing that name to the output stream.  The hub will
         * respond by sending the name back to this client, possibly modified if someone
         * is the chat room is already using the selected name.
         */
        protected void extraHandshake(ObjectInputStream in, ObjectOutputStream out) throws IOException {
            try {
                out.writeObject(myName);  // Send user's name request to the server. 
                myName = (String)in.readObject();  // Get the actual, possibly modified, name from the server.
            }
            catch (Exception e) {
                throw new IOException("Error while setting up connection: " + e);
            }
        }

        /**
         * Called when the connection to the client is shut down because of some
         * error message.  (This will happen if the server program is terminated.)
         */
        protected void connectionClosedByError(String message) {
            addToTranscript("Sorry, communication has shut down due to an error:\n     " + message);
            sendButton.setEnabled(false);
            messageInput.setEnabled(false);
            messageInput.setEditable(false);
            messageInput.setText("");
            sendPrivateButton.setEnabled(false);
            privateMessageInput.setEnabled(false);
            privateMessageInput.setEditable(false);
            connected = false;
            connection = null;
        }
        
        // Note:  the methods playerConnected() and playerDisconnected(), which where present here
        // in ChatRoomWindow, were removed, since their functionality (to announce arrivals
        // and departures) has been taken over by ClientConnectedMessage and ClientDisconnectedMessage.

    } // end nested class ChatClient
    

    private volatile String myName;   // The name that this client uses in the chat room.
                                      // Originally selected by the user, but might be modified
                                      // if there is already a client of the same name connected
                                      // to the Hub.
    
    private volatile TreeMap<Integer,String> clientNameMap = new TreeMap<Integer, String>();
                 // The clientNameMap maps client ID numbers to the names that they are
                 // using in the chat room.  Every time a client connects or disconnects,
                 // the Hub sends a new, modified name map to each connected client.  When
                 // that message is received, the clientNameMap is replaced with the new value,
                 // and the content of the clientList is replaced with info from the nameMap.
    
    private JComboBox clientList;      // List of connected client names, where the user can select
                                       //   the client who is to receive the private message.

    private JTextField messageInput;   // For entering messages to be sent to the chat room
    private JButton sendButton;        // Sends the contents of the messageInput.
    private JButton quitButton;        // Leaves the chat room cleanly, by sending a DisconnectMessage
    
    private JTextField privateMessageInput;  // For entering messages to be set to individual clients.
    private JButton sendPrivateButton; // Sends the contents of privateMesssageInput to the user selected
                                       //   in the clientList.

    private JTextArea transcript;      // Contains all messages sent by chat room participant, as well
                                       // as a few additional status messages, such as when a new user arrives.
    
    private ChatClient connection;      // Represents the connection to the Hub; used to send messages;
                                        // also receives and processes messages from the Hub.
    
    private volatile boolean connected; // This is true while the client is connected to the hub.
    
    
    /**
     * Constructor creates the window and starts the process of connecting
     * to the hub; the actual connection is done in a separate thread.
     * @param host  The IP address or host name of the computer where the server is running.
     */
    private NewChatRoomWindow(final String host, String clientName) {
        super("Chat Room");
        myName = clientName;
        setBackground(Color.BLACK);
        setLayout(new BorderLayout(2,2));
        transcript = new JTextArea(30,60);
        transcript.setLineWrap(true);
        transcript.setWrapStyleWord(true);
        transcript.setMargin(new Insets(5,5,5,5));
        transcript.setEditable(false);
        add(new JScrollPane(transcript), BorderLayout.CENTER);
        sendButton = new JButton("send to all");
        quitButton = new JButton("quit");
        sendPrivateButton = new JButton("send to one");
        messageInput = new JTextField(40);
        messageInput.setMargin(new Insets(3,3,3,3));
        privateMessageInput = new JTextField(40);
        privateMessageInput.setMargin(new Insets(3,3,3,3));
        clientList = new JComboBox();
        clientList.addItem("(No one available)");
        ActionHandler ah = new ActionHandler();
        sendButton.addActionListener(ah);
        quitButton.addActionListener(ah);
        sendPrivateButton.addActionListener(ah);
        messageInput.addActionListener(ah);
        privateMessageInput.addActionListener(ah);
        sendButton.setEnabled(false);
        sendPrivateButton.setEnabled(false);
        messageInput.setEditable(false);
        messageInput.setEnabled(false);
        privateMessageInput.setEditable(false);
        privateMessageInput.setEnabled(false);
        JPanel bottom = new JPanel();
        bottom.setBackground(Color.LIGHT_GRAY);
        bottom.add(new JLabel("You say:"));
        bottom.add(messageInput);
        bottom.add(sendButton);
        bottom.add(Box.createHorizontalStrut(30));
        bottom.add(quitButton);
        JPanel bottom2 = new JPanel();
        bottom2.setBackground(Color.LIGHT_GRAY);
        bottom2.add(new JLabel("Say:"));
        bottom2.add(privateMessageInput);
        bottom2.add(new JLabel(" to: "));
        bottom2.add(clientList);
        bottom2.add(sendPrivateButton);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1,5,5));
        panel.add(bottom);
        panel.add(bottom2);
        add(panel,BorderLayout.SOUTH);
        pack();
        addWindowListener( new WindowAdapter() { // calls doQuit if user closes window
            public void windowClosing(WindowEvent e) {
                doQuit();
            }
        });
        new Thread() {
                // This is a thread that opens the connection to the server.  Since
                // that operation can block, it's not done directly in the constructor.
                // Once the connection is established, the user interface elements are
                // enabled so the user can send messages.  The Thread dies after
                // the connection is established or after an error occurs.
            public void run() {
                try {
                    addToTranscript("Connecting to " + host + " ...");
                    connection = new ChatClient(host);
                    connected = true;
                    messageInput.setEditable(true);
                    messageInput.setEnabled(true);
                    sendButton.setEnabled(true);
                    messageInput.requestFocus();
                    setTitle("Chat Room: Connected as " + myName);
                }
                catch (IOException e) {
                    addToTranscript("Connection attempt failed.");
                    addToTranscript("Error: " + e);
                }
            }
        }.start();
    }
    
    
    /**
     * Adds a string to the transcript area, followed by a blank line.
     */
    private void addToTranscript(String message) {
        transcript.append(message);
        transcript.append("\n\n");
            // The following line is a nasty kludge that was the only way I could find to force
            // the transcript to scroll so that the text that was just added is visible in
            // the window.  Without this, text can be added below the bottom of the visible area
            // of the transcript.
        transcript.setCaretPosition(transcript.getDocument().getLength());
    }
    
    
    /**
     * Called when the user clicks the Quit button or closes
     * the window by clicking its close box.
     */
    private void doQuit() {
        if (connected)
            connection.disconnect();  // Sends a DisconnectMessage to the server.
        dispose();
        try {
            Thread.sleep(1000); // Time for DisconnectMessage to actually be sent.
        }
        catch (InterruptedException e) {
        }
        System.exit(0);
    }

    /**
     * This method is called when a ClientConnectedMessage or ClientDisconnectedMessage
     * is received from the hub.  Its job is to save the nameMap that is part of the
     * message and use it to rebuild the contents of the JComboBox, clientList, where
     * the user selects the recipient of a private message.  It also enables or
     * disables the private message input box and send button, depending on whether
     * there are any possible message recipients.
     * @param nameMap the new nameMap, which will replace the value of clientNameMap.
     */
    private void newNameMap(final TreeMap<Integer,String> nameMap) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                clientNameMap = nameMap;
                clientList.removeAllItems();
                boolean someoneIsThere = false;
                for (String str: nameMap.values()) {
                    if (!str.equals(myName)) {
                        clientList.addItem(str);
                        someoneIsThere = true;
                    }
                }
                privateMessageInput.setEditable(someoneIsThere);
                privateMessageInput.setEnabled(someoneIsThere);
                sendPrivateButton.setEnabled(someoneIsThere);
                if (!someoneIsThere)
                    clientList.addItem("(no one available)");
            }
        });
    }
    
    /**
     * Defines the object that handles all ActionEvents for the program.
     */
    private class ActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent evt) {
            Object src = evt.getSource();
            if (src == quitButton) {  // Disconnect from the server and end the program.
                doQuit();
            }
            else if (src == sendButton || src == messageInput) {
                   // Send the string entered by the user as a message
                   // to the Hub, using the ChatClient that handles communication
                   // for this ChatRoomWindow.  Note that the string is not added
                   // to the transcript here.  It will get added after the Hub
                   // receives the message and broadcasts it to all clients,
                   // including this one.
                String message = messageInput.getText();
                if (message.trim().length() == 0)
                    return;
                connection.send(message);
                messageInput.selectAll();
                messageInput.requestFocus();
            }
            else if (src == sendPrivateButton || src == privateMessageInput) {
                    // Send a private message to a specified recipient.
                    // If the private message inputbox is empty, nothing is done.
                String message = privateMessageInput.getText();
                if (message.trim().length() == 0)
                    return;
                String recipient = (String)clientList.getSelectedItem(); // name of recipient.
                int recipientID = -1;  // The ID number of the recipient
                for (int id : clientNameMap.keySet()) {
                        // Search the clientNameMap to find the ID number
                        // corresponding to the specified recipient name.
                    if (recipient.equals(clientNameMap.get(id))) {
                        recipientID = id;
                        break;
                    }
                }
                if (recipientID == -1) {
                    JOptionPane.showMessageDialog(NewChatRoomWindow.this,
                            "Funny... The selected recipient\ndoesn't seem to exit???");
                    return;
                }
                connection.send(new PrivateMessage(recipientID,message));
                addToTranscript("Sent to " + recipient + ":  " + message);
            }
        }
    }
    

}
