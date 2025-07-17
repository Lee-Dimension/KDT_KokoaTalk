package KokoaTalk.Profile;

import java.util.*;
import java.io.*;

public class FriendLoader {
    public static ArrayList<Friend> loadFriendsFromFile(String filePath) {
        ArrayList<Friend> friends = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                if(tokens.length < 4) continue; // skip 잘못된 라인
                String id = tokens[0];
                String name = tokens[1];
                String status = tokens[2];
                boolean isFavorite = tokens[3].trim().equals("1");
                friends.add(new Friend(id, name, status, isFavorite));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return friends;
    }
}
