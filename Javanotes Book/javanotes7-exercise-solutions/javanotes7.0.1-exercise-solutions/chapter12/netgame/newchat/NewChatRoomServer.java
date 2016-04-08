package netgame.newchat;

import java.io.IOException;

/**
 * This class contains just a small main class that creates a NewChatRoomHub
 * and starts it listening on port 37830.  This port is used
 * by the NewChatRoomWindow application.  This program should be run
 * on the computer that "hosts" the chat room.  See the NewChatRoomWindow
 * class for more details.  Once the server starts listening, it
 * will listens for connection requests from clients until the
 * NewChatRoomServer program is terminated (for example by a 
 * Control-C).
 */
public class NewChatRoomServer {

    private final static int PORT = 37830;
    
    public static void main(String[] args) {
        try {
            new NewChatRoomHub(PORT);
        }
        catch (IOException e) {
            System.out.println("Can't create listening socket.  Shutting down.");
        }
    }
    
}
