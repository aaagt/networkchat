package aaagt.networkchat.server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Initializing server");
        final var settings = Settings.getInstance();

        final var server = new Server(settings.getServerPort());
        server.startServer();
    }


}
