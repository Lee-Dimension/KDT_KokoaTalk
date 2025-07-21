package server;

import java.io.IOException;

public class RunServer {
    public static void main(String[] args) throws IOException {
        int port = 7777;
        MainServer server = new MainServer();
        server.start(port);
    }
}
