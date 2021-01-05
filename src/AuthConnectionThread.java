import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class AuthConnectionThread implements Runnable {

    private Server server;
    private Client client;
    private int portNumber;

    public AuthConnectionThread(Server server, Client client, int portNumber) {
        this.server = server;
        this.client = client;
        this.portNumber = portNumber;
    }

    @Override
    public void run() {
        TcpCommunication();

    }

    private void TcpCommunication() {


        try (

                ServerSocket serverSocket = new ServerSocket(0);
                DatagramSocket socketUDP = new DatagramSocket();

        ) {


                byte[] portMessage = String.valueOf(serverSocket.getLocalPort()).getBytes();
                byte[] buf = new byte[256];
                DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, client.getAddress(), client.getClientPortNumber());
                datagramPacket.setData(portMessage);
                socketUDP.send(datagramPacket);

            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine, outputLine;
            while ((inputLine = in.readLine()) != null) {
                if ((outputLine = server.getRecourse(inputLine)) != null) {
                    out.println(outputLine);
                    break;
                }
            }

            clientSocket.close();
            out.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendPortNumberByUDP() {
        try (DatagramSocket socket = new DatagramSocket()) {
            byte[] portMessage = String.valueOf(portNumber).getBytes();
            byte[] buf = new byte[256];
            DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length, client.getAddress(), client.getClientPortNumber());
            datagramPacket.setData(portMessage);
            socket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
