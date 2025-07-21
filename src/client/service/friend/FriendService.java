package client.service.friend;
//FriendListPanel에 필요한 정보를 서버에서 받아서 넘겨줌
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import model.User;

public class FriendService {
	
	//내 정보 받아오기
    public static User getUser(String id) {
        try (
            Socket socket = new Socket("localhost", 7777);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            out.writeObject("LOAD_USER");
            out.writeObject(id);
            Object response = in.readObject();
            if (response instanceof User) {
                return (User) response;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //친구 목록 불러오기
    public static ArrayList<String> getFriendList(String id) {
        try (
            Socket socket = new Socket("localhost", 7777);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            out.writeObject("GET_FRIEND");
            out.writeObject(id);
            Object response = in.readObject();
            if (response instanceof ArrayList<?>) {
                ArrayList<?> list = (ArrayList<?>) response;
                if (!list.isEmpty() && list.get(0) instanceof String) {
                    return (ArrayList<String>) response;
                }
                if (list.isEmpty()) {
                    return new ArrayList<>();
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
    //즐겨찾기 추가 요청
    public static boolean addFavorite(String myId, String friendId) {
        try (
            Socket socket = new Socket("localhost", 7777);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            out.writeObject("ADD_FAVORITE");
            out.writeObject(myId);
            out.writeObject(friendId);
            String response = (String) in.readObject();
            return "OK".equals(response);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //즐겨찾기 제거 요청
    public static boolean removeFavorite(String myId, String friendId) {
        try (
            Socket socket = new Socket("localhost", 7777);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            out.writeObject("REMOVE_FAVORITE");
            out.writeObject(myId);
            out.writeObject(friendId);
            String response = (String) in.readObject();
            return "OK".equals(response);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // 서버에 친구 추가 요청
    public static boolean addFriend(String myId, String friendId) {
        try (
            Socket socket = new Socket("localhost", 7777);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            out.writeObject("ADD_FRIEND");
            out.writeObject(myId);
            out.writeObject(friendId);

            String response = (String) in.readObject();
            return "OK".equals(response);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //친구 제거 요청
    public static boolean removeFriend(String myId, String friendId) {
        try (
            Socket socket = new Socket("localhost", 7777);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            out.writeObject("REMOVE_FRIEND");
            out.writeObject(myId);
            out.writeObject(friendId);
            String response = (String) in.readObject();
            return "OK".equals(response);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
