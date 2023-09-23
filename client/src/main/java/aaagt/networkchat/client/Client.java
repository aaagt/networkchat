package aaagt.networkchat.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    final String serverAddress;
    final int serverPort;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void connect() {
        System.out.printf("Connecting to server: %s:%d\n", serverAddress, serverPort);
        try {
            Socket socket = new Socket(serverAddress, serverPort);
            var readThread = new ReadThread(socket, this);
            readThread.start();
            readThread.join();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
