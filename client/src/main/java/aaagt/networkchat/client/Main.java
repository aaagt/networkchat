package aaagt.networkchat.client;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Initializing client");
        var settings = Settings.getInstance();
        var client = new Client(settings.getServerAddress(), settings.getServerPort());
        client.connect();

        /*try (var clientSocket = new Socket(serverAddress, serverPort);
             var in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(
             var out = new PrintWriter(clientSocket.getOutputStream(), true);)))
        ) {
            System.out.printf("New connection accepted. Port: %d\n", clientSocket.getPort());
            while (clientSocket.isConnected()) {
                try {
                    String response = in.readLine();
                    System.out.println("\n" + response);
                } catch (IOException ex) {
                    System.out.println("Error reading from server: " + ex.getMessage());
                    ex.printStackTrace();
                    break;
                }


                Thread.sleep(1000);
            }*/
            /*response1 = in.readLine();
            response1 = in.readLine();
            System.out.println("response2" + response1);*/
        //out.println("Vasya");

        //var response2 = in.readLine();
        //System.out.println(response2);
        //out.println("no");

        //var response3 = in.readLine();
        //System.out.println(response3);
        //}
    }
}
