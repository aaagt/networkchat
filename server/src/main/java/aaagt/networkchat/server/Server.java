package aaagt.networkchat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

public class Server {

    private final int serverPort;
    private final Set<ClientHandler> clients = new HashSet<>();

    public Server(int serverPort) {
        this.serverPort = serverPort;
    }

    public void startServer() throws IOException {
        System.out.printf("Starting server on port %s\n", serverPort);
        try (var serverSocket = new ServerSocket(serverPort)) {
            startAcceptSocketRequests(serverSocket);
        }
        System.out.println("Server stopped");
    }

    private void startAcceptSocketRequests(ServerSocket serverSocket) throws IOException {
        System.out.println("Starting to accept socket requests");
        while (true) {
            System.out.println("Waiting for new connections...");
            var clientSocket = serverSocket.accept();
            System.out.println("Socket accepted");
            var client = new ClientHandler(clientSocket, this);
            clients.add(client);
            client.start();
            //client.join();
        }
    }

    public void sendMessageToAllClients(String msg) {
        for (ClientHandler o : clients) {
            o.sendMessage(msg);
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}
