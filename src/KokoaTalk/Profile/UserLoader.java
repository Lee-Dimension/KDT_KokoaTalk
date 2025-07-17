package KokoaTalk.Profile;

import java.io.*;

public class UserLoader {
    public static UserClass loadUserFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if (line != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 3) {
                    String id = tokens[0];
                    String name = tokens[1];
                    String status = tokens[2];
                    return new UserClass(id, name, status);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
