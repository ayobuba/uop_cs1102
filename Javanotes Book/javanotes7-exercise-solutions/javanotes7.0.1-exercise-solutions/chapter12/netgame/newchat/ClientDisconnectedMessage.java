package netgame.newchat;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * A message of this type will be sent by the hub to all
 * remaining connected clients when a client leaves the
 * chat room.
 */
public class ClientDisconnectedMessage implements Serializable {
    
    public int departingClientID;  // The ID number of the client who has left the chat room.
    public String departingClientName;  // The name of the departing client
    public TreeMap<Integer,String> nameMap;  // Map of all connected client IDs to their names.
                                             //  (Note that the departing client is not included.)

    public ClientDisconnectedMessage(int departingClientID,
            String departingClientName, TreeMap<Integer,String> nameMap) {
        this.departingClientID = departingClientID;
        this.departingClientName = departingClientName;
        this.nameMap = nameMap;
    }
    

}
