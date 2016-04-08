package netgame.newchat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeMap;

import netgame.common.*;

/**
 *  This class defines the "hub" that acts as a server for the
 *  chat room application.  It extends the basic Hub class in
 *  order to support names for clients, as well as ID numbers.
 */
public class NewChatRoomHub extends Hub {
    
    /**
     * This map keeps track of the names of all connected clients.
     * It maps client ID numbers to client names.
     */
    private TreeMap<Integer,String> nameMap = new TreeMap<Integer,String>();

    /**
     * Create a NewChatRoomHub, which will listen for connections on
     * a specified port.
     * @param port the port on which to listen for connections
     * @throws IOException if it is not possible to create a listening socket
     */
    public NewChatRoomHub(int port) throws IOException {
        super(port);
    }

    /**
     * This method is called as part of the connection setup between this hub
     * and a client that has requested a connection.  It is overridden in this
     * class so that a name can be assigned to the client as part of the setup
     * process.  This method works in cooperation with the extraHandshake()
     * method in the client class (which is defined as a nested class inside
     * NewChatRoomWindow).  In this method, the Hub reads a string from the
     * user that contains the name that the client wants to use.  The name
     * can be modified to make sure that it is non-null, 15 characters or less.
     * The resulting name is further modified by adding a suffix such as
     * "#2" or "#3" if the name is already in use by another client.  Finally,
     * the possibly modified name is sent back to the client, which will use
     * the returned value as the name that identifies the client in the chat
     * room.
     */
    protected void extraHandshake(int playerID, 
                      ObjectInputStream in, ObjectOutputStream out) throws IOException {
        try {
            String name = (String)in.readObject();
            if (name == null)
                name = "noname";
            if (name.length() > 15)
                name = name.substring(0,15).trim();
            if (name.equals(""))
                name = "noname";
            synchronized(nameMap) {
                if (nameMap.containsValue(name)) {
                    String approvedName = name;
                    int num = 2;
                    while (nameMap.containsValue(approvedName)) {
                        approvedName = name + "#" + num;
                        num++;
                    }
                    name = approvedName;
                }
            }
            out.writeObject(name);
            nameMap.put(playerID,name);
        }
        catch (Exception e) {
            throw new IOException("Error while setting up connection: " + e);
        }
    }

    /**
     * This method is overridden to provide support for PrivateMessages.
     * If a PrivateMessage is received from some client, this method
     * will set the senderID field in the message to be the ID number
     * of the client who sent the message.  It will then send the
     * message on to the specified recipient.  If some other type
     * of message is received, it is handled by the messageReceived()
     * method in the superclass (which will wrap it in a ForwardedMessage
     * and send it to all connected clients).
     */
    protected void messageReceived(int playerID, Object message) {
        if (message instanceof PrivateMessage) {
            PrivateMessage pm = (PrivateMessage)message;
            pm.senderID = playerID;
            sendToOne(pm.recipientID, pm);
        }
        else
            super.messageReceived(playerID, message);
    }

    /**
     *  This method is called when a new client connects.  It is called
     *  after the extraHandshake() method has been called, so that the
     *  client's name has already been added to nameMap.  This method
     *  creates a ClientConnectedMessage and sends it to all connected
     *  clients to announce the new participant in the chat room.
     */
    protected void playerConnected(int playerID) {
        resetOutput(); // Reset the output stream before resending nameMap.
        sendToAll(new ClientConnectedMessage(playerID,nameMap));
    }

    /**
     * This method is called when a client has been disconnected from
     * this hub.  It removes the client from the nameMap and sends
     * a ClientDisconnectedMessage to all connected players to
     * announce the fact that the client has left the chat room. 
     */
    protected void playerDisconnected(int playerID) {
        String name = nameMap.get(playerID); // Get the departing player's name.
        nameMap.remove(playerID);  // Remove the player from nameMap.
        resetOutput(); // Reset the output stream before resending nameMap.
        sendToAll(new ClientDisconnectedMessage(playerID, name, nameMap));
    }
    
}
