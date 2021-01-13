import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.Queue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Authorization {


    private final Queue<Integer> sequence = new LinkedList<>();
    private ExecutorService packetProcessors;
    private Server server;



    public Authorization(int[] portNumbers, Server server) throws SocketException {
        this.server =server;
        constructSequence(portNumbers);
        ExecutorService socketListeners = Executors.newFixedThreadPool(portNumbers.length);
        packetProcessors = Executors.newCachedThreadPool();
        DatagramSocket[] sockets = new DatagramSocket[portNumbers.length];

        for (int i = 0; i < portNumbers.length ; i++) {
            sockets[i] = new DatagramSocket(portNumbers[i]);
            socketListeners.execute(new SocketListener(sockets[i], portNumbers[i], this));
        }

    }

    private void constructSequence(int[] portNumbers){
        for ( int port : portNumbers) {
            sequence.add(port);
        }
    }

    void setAuthorizedClient(Client client){
        server.setAuthorizedClient(client);
    }

    void processDatagram(DatagramPort data){
       packetProcessors.submit( new PacketProcessor(this,sequence,data));
    }

}
