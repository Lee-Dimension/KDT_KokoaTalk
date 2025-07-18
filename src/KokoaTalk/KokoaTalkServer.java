package KokoaTalk;

import java.net.*;
import java.io.*;
import java.util.*;
import KokoaTalk.Profile.UserClass;

public class KokoaTalkServer {
    private static Map<String, UserClass> userMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
    	loadUsersFromFile();
        ServerSocket serverSocket = new ServerSocket(7777);
        System.out.println("서버 시작!");
        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }
    private static void loadUsersFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.ser"))) {
            userMap = (HashMap<String, UserClass>) in.readObject();
            System.out.println("[서버] users.ser에서 유저 정보 불러옴!");
        } catch (Exception e) {
            System.out.println("[서버] users.ser 파일이 없거나, 처음 실행입니다.");
            userMap = new HashMap<>();
        }
    }
    
    private static void saveUsersToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("users.ser"))) {
            out.writeObject(userMap);
            System.out.println("[서버] users.ser 파일 저장됨!");  // 저장 로그
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    static class ClientHandler implements Runnable {
        private Socket socket;
        public ClientHandler(Socket socket) { this.socket = socket; }

        @Override
        public void run() {
            try (
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ) {
                String cmd = (String) in.readObject();
                System.out.println("[서버] 받은 명령: " + cmd);
                if ("SIGNUP".equals(cmd)) {
                    UserClass newUser = (UserClass) in.readObject();
                    String id = newUser.getId();
                    if (!userMap.containsKey(id)) {
                        userMap.put(id, newUser);
                        saveUsersToFile();  // ★ 저장!
                        out.writeObject("OK");
                        System.out.println("[서버] 회원가입 성공: " + id);
                    } else {
                        out.writeObject("FAIL");
                    }
                } else if ("LOGIN".equals(cmd)) {
                    String id = (String) in.readObject();
                    UserClass user = userMap.get(id);
                    if (user != null) {
                        out.writeObject(user);
                        System.out.println("로그인 성공: " + id);
                    } else {
                        out.writeObject(null);
                        System.out.println("로그인 실패: " + id);
                    }
                }else if ("ADD_FRIEND".equals(cmd)) {
                        String myId = (String) in.readObject();
                        String friendId = (String) in.readObject();
                        UserClass me = userMap.get(myId);
                        UserClass friend = userMap.get(friendId);
                        if (me != null && friend != null && !me.getFriends().contains(friendId)) {
                            me.getFriends().add(friendId);
                            saveUsersToFile();  // ★ 저장!
                            out.writeObject("OK");
                            System.out.println("[서버] 친구 추가: " + myId + " → " + friendId);
                        } else {
                            out.writeObject("FAIL");
                        }
                }else if ("LOAD_USER".equals(cmd)) {
                    String id = (String) in.readObject();
                    System.out.println("[서버] LOAD_USER 요청: " + id);
                    System.out.println("[서버] userMap.keySet() = " + userMap.keySet());  // ★추가
                    UserClass user = userMap.get(id);
                    System.out.println("[서버] user = " + user);  // ★추가
                    out.writeObject(user);
                }else if ("GET_FRIEND".equals(cmd)) {
                    String id = (String) in.readObject();
                    UserClass user = userMap.get(id);
                    System.out.println("[서버] GET_FRIENDS 요청: " + id);
                    List<String> friends = (user != null) ? user.getFriends() : new ArrayList<>();
                    out.writeObject(friends);
                }else if ("ADD_FAVORITE".equals(cmd)) {
                    String myId = (String) in.readObject();
                    String friendId = (String) in.readObject();
                    UserClass me = userMap.get(myId);
                    if (me != null) {
                        if (!me.getFavoriteFriends().contains(friendId)) {
                            me.getFavoriteFriends().add(friendId);
                            saveUsersToFile();
                            out.writeObject("OK");
                            System.out.println("[서버] 즐겨찾기 추가: " + myId + " → " + friendId);
                        } else {
                            out.writeObject("ALREADY");
                        }
                    } else {
                        out.writeObject("FAIL");
                    }
                } else if ("REMOVE_FAVORITE".equals(cmd)) {
                    String myId = (String) in.readObject();
                    String friendId = (String) in.readObject();
                    UserClass me = userMap.get(myId);
                    if (me != null) {
                        if (me.getFavoriteFriends().contains(friendId)) {
                            me.getFavoriteFriends().remove(friendId);
                            saveUsersToFile();
                            out.writeObject("OK");
                            System.out.println("[서버] 즐겨찾기 제거: " + myId + " → " + friendId);
                        } else {
                            out.writeObject("NOT_FOUND");
                        }
                    } else {
                        out.writeObject("FAIL");
                    }
                }else if ("REMOVE_FRIEND".equals(cmd)) {
                    String myId = (String) in.readObject();
                    String friendId = (String) in.readObject();
                    UserClass me = userMap.get(myId);
                    if (me != null && me.getFriends().contains(friendId)) {
                        me.getFriends().remove(friendId);
                        saveUsersToFile();
                        out.writeObject("OK");
                        System.out.println("[서버] 친구 삭제: " + myId + " → " + friendId);
                    } else {
                        out.writeObject("FAIL");
                    }
                }

                } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
