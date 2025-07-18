package KokoaTalk.Profile;

import java.io.*;
import java.net.*;

public class UserFileManager {
	public static void saveUser(UserClass user) {
	    try (
	        Socket socket = new Socket("localhost", 7777);
	        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
	    ) {
	        out.writeObject("SIGNUP");
	        out.writeObject(user);
	        // 서버 응답 처리 등
	        String result = (String)in.readObject();
	        if (result.equals("OK")) {
	            // 성공 처리
	        }
	    } catch (Exception e) { e.printStackTrace(); }
	}

	public static UserClass loadUser(String userId) {
	    try (
	        Socket socket = new Socket("localhost", 7777); // 서버 주소/포트 맞춰서
	        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
	        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
	    ) {
	        // 1. 명령어 먼저 보냄
	        out.writeObject("LOAD_USER");
	        // 2. userId 보냄
	        out.writeObject(userId);

	        // 3. 서버 응답 대기 (UserClass or null)
	        Object response = in.readObject();
	        if (response instanceof UserClass) {
	            return (UserClass) response;
	        } else {
	            // 못찾음 또는 에러(서버가 null 보낼 수도)
	            return null;
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}