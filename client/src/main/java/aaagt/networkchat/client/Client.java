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

    public void connect() throws IOException {
        System.out.printf("Connecting to server: %s:%d\n", serverAddress, serverPort);
        Socket socket = null;
        try {
            socket = new Socket(serverAddress, serverPort);

            var readThread = new ReadThread(socket, this);
            readThread.start();

            var writeThread = new WriteThread(socket, this);
            writeThread.start();

            // Если в потоке пишушем сообщения подана команда на выход, то читающий поток тоже прервать
            writeThread.join();
            readThread.interrupt();
            readThread.join();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Closing connection");
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}
