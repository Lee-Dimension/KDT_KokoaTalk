package KokoaTalk.Profile;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.net.Socket;
import java.io.*;

import KokoaTalk.ScrollbarUI;
import KokoaTalk.Colors;

public class FriendPanel extends JPanel{
    private String currentUserId;
    private JPanel mainPanel;
    private CardLayout cardLayout;
	
    public FriendPanel(String id, JPanel mainPanel, CardLayout cardLayout) {
        this.currentUserId = id;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
    	
        setLayout(new BorderLayout());
        setBackground(Colors.BGROUND);

        // 리스트 전체를 담을 패널(세로)
        JPanel friendListPanel = new JPanel();
        friendListPanel.setBackground(Colors.BGROUND);
        friendListPanel.setLayout(new BoxLayout(friendListPanel, BoxLayout.Y_AXIS));
        friendListPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- 내 프로필 ---
        UserClass user = getUserFromServer(id);
        System.out.println("[클라] 서버에서 받은 user = " + user);
        String name = "이름 없음";
        String statusMsg = "";

        // (즐겨찾기 친구 리스트 가정)
        ArrayList<String> favoriteSet = new ArrayList<>();

        if (user != null) {
            name = user.getName();
            statusMsg = user.getStatusMsg();
            if(user.getFavoriteFriends() != null)
                favoriteSet = user.getFavoriteFriends();
        }

        MyProfilePanel myProfilePanel = new MyProfilePanel(name, statusMsg);
        friendListPanel.add(myProfilePanel);
        friendListPanel.add(myProfilePanel.getDetailPanel());
        myProfilePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // --- 서버에서 친구 목록 받아오기 ---
        ArrayList<String> allFriendIds = getFriendListFromServer(id);

        // --- 즐겨찾기 친구 표시 ---
        friendListPanel.add(new FriendSectionLabel("즐겨찾기"));
        for (String friendId : allFriendIds) {
            if (favoriteSet.contains(friendId)) {
                UserClass friendUser = getUserFromServer(friendId);
                if (friendUser != null) {
                    FriendItemPanel fip = new FriendItemPanel(friendUser.getName(), friendUser.getStatusMsg());
                    friendListPanel.add(fip);
                    friendListPanel.add(createDetailPanel(fip, true, currentUserId, friendUser.getId()));
                }
            }
        }

        // --- 모든 친구(=일반+즐찾) 표시 ---
        friendListPanel.add(new FriendSectionLabel("친구"));
        for (String friendId : allFriendIds) {
            UserClass friendUser = getUserFromServer(friendId);
            if (friendUser != null) {
                boolean isFavorite = favoriteSet.contains(friendId);
                FriendItemPanel fip = new FriendItemPanel(friendUser.getName(), friendUser.getStatusMsg());
                friendListPanel.add(fip);
                friendListPanel.add(createDetailPanel(fip, isFavorite, currentUserId, friendUser.getId()));
            }
        }

        friendListPanel.add(Box.createVerticalGlue());

        // --- 스크롤바로 감싸기 ---
        JScrollPane scrollPane = new JScrollPane(friendListPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new ScrollbarUI());
        scrollPane.getHorizontalScrollBar().setUI(new ScrollbarUI());
        add(scrollPane, BorderLayout.CENTER);
    }

    // --- 서버에서 내 정보 받아오기 ---
    private UserClass getUserFromServer(String id) {
    	System.out.println("[클라이언트] LOAD_USER 요청: " + id);
    	
        try (
            Socket socket = new Socket("localhost", 7777);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ) {
            out.writeObject("LOAD_USER");
            out.writeObject(id);
            Object response = in.readObject();
            if (response instanceof UserClass) {
                return (UserClass) response;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // --- 서버에서 친구 목록 받아오기 ---
    private ArrayList<String> getFriendListFromServer(String id) {
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
    

    // 디테일 패널(버튼들) 생성 함수 (변경 없음)
    public JPanel createDetailPanel(FriendItemPanel fip, boolean isFavorite, String currentUserId, String friendId) {
        JPanel detailPanel = fip.getDetailPanel();
        detailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        detailPanel.setMaximumSize(new Dimension(380, 60));
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.X_AXIS));
        detailPanel.setBackground(Colors.BGROUNDDEEP);
        detailPanel.add(Box.createHorizontalGlue());

        JButton talkBtn = new JButton("대화하기");
        HideBtnDesign.apply(talkBtn);
        detailPanel.add(talkBtn);
        detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        JButton nickChange = new JButton("별명변경");
        HideBtnDesign.apply(nickChange);
        detailPanel.add(nickChange);
        detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));

        if(isFavorite) {
            JButton delFf = new JButton("즐찾제거");
            HideBtnDesign.apply(delFf);
            delFf.addActionListener(e -> {
                boolean success = removeFavoriteFromServer(currentUserId, friendId);
                if(success) {
                    JOptionPane.showMessageDialog(this, "즐겨찾기에서 제거되었습니다.");
                    // UI 갱신: 새 FriendPanel로 교체
                    mainPanel.remove(this); // 기존 FriendPanel 제거
                    FriendPanel newFriendPanel = new FriendPanel(currentUserId, mainPanel, cardLayout);
                    mainPanel.add(newFriendPanel, "FRIEND");
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    cardLayout.show(mainPanel, "FRIEND");
                } else {
                    JOptionPane.showMessageDialog(this, "오류가 발생했습니다.");
                }
            });
            
            
            detailPanel.add(delFf);
            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        } else {
            JButton plusFf = new JButton("즐찾추가");
            HideBtnDesign.apply(plusFf);
            
            plusFf.addActionListener(e -> {
                boolean success = addFavoriteToServer(currentUserId, friendId);
                if(success) {
                    JOptionPane.showMessageDialog(this, "즐겨찾기에 추가되었습니다.");
                    // UI 갱신: 새 FriendPanel로 교체 등
                    mainPanel.remove(FriendPanel.this); // 기존 FriendPanel 제거
                    FriendPanel newFriendPanel = new FriendPanel(currentUserId, mainPanel, cardLayout);
                    mainPanel.add(newFriendPanel, "FRIEND");
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    cardLayout.show(mainPanel, "FRIEND");
                    
                } else {
                    JOptionPane.showMessageDialog(this, "오류가 발생했습니다.");
                }
            });
            detailPanel.add(plusFf);
            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));

            JButton delF = new JButton("친구삭제");
            HideBtnDesign.apply(delF);
            delF.addActionListener(e -> {
                boolean success = removeFriendFromServer(currentUserId, friendId);
                if (success) {
                    JOptionPane.showMessageDialog(this, "친구가 삭제되었습니다.");
                    // UI 갱신: 새 FriendPanel로 교체
                    mainPanel.remove(FriendPanel.this);  // 현재 FriendPanel 제거
                    FriendPanel newFriendPanel = new FriendPanel(currentUserId, mainPanel, cardLayout);
                    mainPanel.add(newFriendPanel, "FRIEND");
                    mainPanel.revalidate();
                    mainPanel.repaint();
                    cardLayout.show(mainPanel, "FRIEND");
                } else {
                    JOptionPane.showMessageDialog(this, "친구 삭제 중 오류가 발생했습니다.");
                }
            });
            detailPanel.add(delF);
            detailPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        }

        detailPanel.add(Box.createHorizontalGlue());
        return detailPanel;
    }
 // 즐겨찾기 추가 요청
    private boolean addFavoriteToServer(String myId, String friendId) {
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

    // 즐겨찾기 제거 요청
    private boolean removeFavoriteFromServer(String myId, String friendId) {
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
    // 친구 삭제 요청 메서드
    private boolean removeFriendFromServer(String myId, String friendId) {
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
