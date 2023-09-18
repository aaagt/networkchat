package aaagt.networkchat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Initializing server");
        final var settings = Settings.getInstance();
        final var serverPort = settings.getServerPort();

        System.out.printf("Starting server on port %s\n", serverPort);
        startServer(serverPort);
    }

    private static void startServer(int serverPort) throws IOException {
        try (var serverSocket = new ServerSocket(serverPort)) {
            System.out.println("server started");

            while (true) {
                try (Socket clientSocket = serverSocket.accept(); // ждем подключения
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
                ) {
                    execRequest(clientSocket, out, in);
                }
            }
        }
    }

    private static void execRequest(Socket clientSocket, PrintWriter out, BufferedReader in) throws IOException {
        System.out.printf("New connection accepted. Port: %d%n", clientSocket.getPort());

        out.println("Write your name");
        final String name = in.readLine();

        out.println("Are you child? (yes/no)");
        final String isChild = in.readLine();

        if (isChild.equals("yes")) {
            out.println(String.format("Welcome to the kids area, %s! Let's play!", name));
        } else if (isChild.equals("no")) {
            out.println(String.format("Welcome to the adult zone, %s! Have a good rest, or a good working day!", name));
        } else {
            out.println(String.format("Buy %s, your port is %d", name, clientSocket.getPort()));
        }
    }
}
