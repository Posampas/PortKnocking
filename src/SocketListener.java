import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketListener implements Runnable {
    private DatagramSocket socket;
    private final int PORT_NUMBER;
    private Authorization authorization;

    public SocketListener(DatagramSocket socket, int PORT_NUMBER, Authorization authorization) {
        this.socket = socket;
        this.authorization = authorization;
        this.PORT_NUMBER = PORT_NUMBER;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                DatagramPacket p = getNewPacket();
                System.out.println(p.getAddress() + " " + p.getPort());
                authorization.processDatagram(new DatagramPort(p, PORT_NUMBER));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private synchronized DatagramPacket getNewPacket() throws IOException {
        byte[] buf = new byte[1];
        DatagramPacket p = new DatagramPacket(buf, buf.length);
        socket.receive(p);
        return p;
    }


}
