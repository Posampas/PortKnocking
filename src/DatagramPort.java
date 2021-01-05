import java.net.DatagramPacket;
import java.util.Objects;

public class DatagramPort {
    private DatagramPacket packet;
    private int socketPortNumber;

    public DatagramPort(DatagramPacket packet, int socketPortNumber) {
        this.packet = packet;
        this.socketPortNumber = socketPortNumber;
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public int getSocketPortNumber() {
        return socketPortNumber;
    }


    @Override
    public int hashCode() {
        return Objects.hash(packet, socketPortNumber);
    }
}
