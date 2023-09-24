package aaagt.networkchat.server;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class ClientHandler extends Thread {

    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String HISTORY_FILE_NAME = "chat_history.txt";

    final Socket socket;
    final Server server;
    final PrintWriter out;
    final BufferedReader in;

    String name = "";

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    private static void writeMessage(String message) {
        try (var fileWriter = new FileWriter(HISTORY_FILE_NAME, true)) {
            fileWriter.write(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        System.out.printf("New connection accepted. Port: %d\n", socket.getPort());
        try {
            proccessRequest();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.printf("Connection closed. Port: %d\n", socket.getPort());
            close();
        }
    }

    private void proccessRequest() throws IOException {

        server.sendMessageToAllClients("Hello and welcome to our chat!");

        sendMessage("Write your name");
        name = in.readLine();

        server.sendMessageToAllClients("%s connected to this chat".formatted(name));

        String line = "";
        while ((line = in.readLine()) != null) {
            server.sendMessageToAllClients("%s: %s".formatted(name, line));
        }
    }

    public void sendMessage(String message) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String text = String.format("%s [%s] %s\n", TIME_FORMAT.format(timestamp), name, message);
        System.out.print("sending: " + text);
        writeMessage(text);
        out.print(text);
    }

    public void close() {
        server.removeClient(this);
        server.sendMessageToAllClients("%s disconnected".formatted(name));
        System.out.printf("%s disconnected\n", name);
        try {
            in.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        out.close();
    }
}
