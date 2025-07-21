package client.service.chat;

import model.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MessageStorageUtil {
    private static final String BASE_DIR = "src/data/messages/";

    public static void saveMessage(Message msg) {
        try {
            File dir = new File(BASE_DIR);
            if (!dir.exists()) dir.mkdirs();

            File file = new File(BASE_DIR + msg.getRoomId() + ".ser");
            List<Message> messages = new ArrayList<>();

            if (file.exists()) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    messages = (List<Message>) ois.readObject();
                }
            }

            messages.add(msg);

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(messages);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Message> loadMessages(String roomId) {
        File file = new File(BASE_DIR + roomId + ".ser");
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Message>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
