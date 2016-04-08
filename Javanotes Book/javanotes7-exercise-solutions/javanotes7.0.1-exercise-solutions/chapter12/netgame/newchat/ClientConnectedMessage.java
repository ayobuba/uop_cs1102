package netgame.newchat;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * A message of this type will be sent by the hub to all
 * connected clients when a new client joins the chat room
 */
public class ClientConnectedMessage implements Serializable {
    
    public int newClientID;  // The ID number of the client who has connected.
    public TreeMap<Integer,String> nameMap;  // Map of all connected client IDs to their names.
    
    public ClientConnectedMessage(int newClientID, TreeMap<Integer,String> nameMap) {
        this.newClientID = newClientID;
        this.nameMap = nameMap;
    }

}
