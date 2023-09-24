package aaagt.networkchat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {
    final Socket socket;
    final Client client;
    final BufferedReader reader;

    public ReadThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;
        try {
            InputStream input = socket.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        while (true) {
            try {
                if (reader.ready()) {
                    String response = reader.readLine();
                    System.out.println(response);
                }
                if (Thread.interrupted()) {
                    // We've been interrupted: no more crunching.
                    return;
                }
                Thread.sleep(100);
            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            } catch (InterruptedException e) {
                System.err.println(e);
                return;
            }
        }
    }
}
