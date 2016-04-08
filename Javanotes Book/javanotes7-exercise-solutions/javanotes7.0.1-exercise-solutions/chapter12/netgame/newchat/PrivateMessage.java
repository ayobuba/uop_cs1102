package netgame.newchat;

import java.io.Serializable;

/**
 * Represents a string sent as a message from one client
 * to another client.  Note:  The ChatRoomHub will set the
 * senderID of a PrivateMessage to be the ID number of the
 * client who actually sent the message, in order to avoid
 * the possibility of rogue clients that try to forge 
 * messages that appear to come from other clients.
 */
public class PrivateMessage implements Serializable {
    
    public int senderID;    // The ID number of the sender.
    public int recipientID; // The ID number of the recipient.
    public String message;  // The message.

    /**
     *  Create a private message from one user to another.
     *  The senderID of the message will be set by the hub.
     */
    public PrivateMessage(int recipientID, String message) {
        this.recipientID = recipientID;
        this.message = message;
    }

}
