package KokoaTalk.Profile;

import java.io.*;
import java.util.*;

public class UserLoader {
	public static List<UserClass> loadAllUsers(String folderPath) {
	    List<UserClass> userList = new ArrayList<>();
	    File folder = new File(folderPath);
	    for (File file : folder.listFiles()) {
	        userList.add(UserFileManager.loadUser(file.getPath()));
	    }
	    return userList;
	}

}
