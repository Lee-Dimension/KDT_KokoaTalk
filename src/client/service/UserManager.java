package client.service;

import java.io.*;
import model.User;

public class UserManager {
    private static final String USER_DIR = "src/data/user/";

    // 유저 저장
    public static void saveUser(User user) {
        File dir = new File(USER_DIR);
        if (!dir.exists()) dir.mkdirs();

        String path = USER_DIR + user.getId() + ".ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(user);
            System.out.println("[UserManager] 저장 완료: " + user.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 유저 로딩
    public static User loadUser(String userId) {
        String path = USER_DIR + userId + ".ser";
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            return (User) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[UserManager] 불러오기 실패: " + userId);
            return null;
        }
    }

    // 유저 존재 여부 확인
    public static boolean userExists(String userId) {
        File f = new File(USER_DIR + userId + ".ser");
        return f.exists();
    }
}
