package aaagt.networkchat.client;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Initializing client");
        var settings = Settings.getInstance();
        var client = new Client(settings.getServerAddress(), settings.getServerPort());
        client.connect();
    }

}
