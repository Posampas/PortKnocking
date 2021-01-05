import java.net.InetAddress;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class Client {
    private ClientState state = ClientState.UNAUTHORIZED;
    private final InetAddress address;
    private final int clientPortNumber;
    private Queue<Integer> sequence;

    public Client(InetAddress address, int clientPortNumber, Queue<Integer> sequence) {
        this.address = address;
        this.clientPortNumber = clientPortNumber;
        this.sequence = sequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return clientPortNumber == client.clientPortNumber &&
                address.equals(client.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, clientPortNumber);
    }

    public ClientState getState() {
        return state;
    }

    public void moveSequence(int socketPortNumber) {

        if (state != ClientState.UNAUTHORIZED) {
            return;
        }

        if (sequence.peek() == socketPortNumber) {
            sequence.poll();

            if (sequence.isEmpty()) state = ClientState.AUTHORIZED;
        } else {
             state = ClientState.BLOCKED;
        }
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getClientPortNumber() {
        return clientPortNumber;
    }
}
