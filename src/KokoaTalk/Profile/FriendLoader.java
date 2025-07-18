package KokoaTalk.Profile;

import java.util.*;
import java.io.*;
import java.net.Socket;

public class FriendLoader {
	public static List<String> loadFriendList(String userId) {
	    try (
	        Socket socket = new Socket("localhost", 7777);
	        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
	    ) {
	        out.writeObject("GET_FRIENDS");
	        out.writeObject(userId);
	        Object response = in.readObject();
	        if (response instanceof List<?>) {
	            List<?> list = (List<?>) response;
	            if (list.isEmpty() || list.get(0) instanceof String) {
	                return (List<String>) response;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return new ArrayList<>();
	}


	public static void saveFriendList(List<Friend> friends, String filename) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
	        for (Friend friend : friends) {
	            writer.write(friend.toString()); // toString() → 적당히 포맷
	            writer.newLine();
	        }
	    } catch (IOException e) { e.printStackTrace(); }
	}

}
