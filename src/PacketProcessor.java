import java.util.*;

public class PacketProcessor implements Runnable {
    private static Map<Integer, Client> clientMap = new Hashtable<>();
    private Authorization authorization;
    private final Queue<Integer> sequence;
    private DatagramPort datagramPort;

    public PacketProcessor(Authorization authorization, Queue<Integer> sequence, DatagramPort port) {
        this.authorization = authorization;
        this.sequence = sequence;
        this.datagramPort= port;
    }

    @Override
    public void run() {


        int hash = hashCode(datagramPort);
        System.out.println(hash);
        if (isClientAlreadyInMap(hash)) {
            processClient(hash, datagramPort.getSocketPortNumber());
        } else {
            System.out.println("creating");
            Client client =
                    new Client(datagramPort.getPacket().getAddress(),
                            datagramPort.getPacket().getPort(),
                            new LinkedList<>(sequence));
            addClientToMap(hash, client);
            processClient(hash,datagramPort.getSocketPortNumber());
        }


    }

    private synchronized void processClient(int hash, int socketPortNumber) {
        Client client = getClientFromMap(hash);
        client.moveSequence(socketPortNumber);
        if (client.getState() == ClientState.AUTHORIZED) {
            System.out.println("Authorization granted");
            authorization.setAuthorizedClient(client);

        }
    }

    private synchronized static boolean isClientAlreadyInMap(int hash) {
        return clientMap.containsKey(hash);
    }

    private synchronized static Client getClientFromMap(int hash) {
        return clientMap.get(hash);
    }

    private synchronized static void addClientToMap(int hash, Client client) {
        clientMap.put(hash, client);
    }

    private synchronized static void  removeFromMap(int hash){
        System.out.println("removing");
        clientMap.remove(hash);

    }

    private int hashCode(DatagramPort port){
       return  (port.getPacket().getAddress().toString() + "/" + port.getPacket().getPort()).hashCode();
    }
}
