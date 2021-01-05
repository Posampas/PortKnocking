import javax.tools.JavaCompiler;
import java.net.SocketException;
import java.util.Objects;


public class Server {
    private  int[] portNumbers;
    private Authorization authorization;
    private AuthorizedCommunication authComm;

    public static void main(String[] args) throws SocketException {
        int[] ports = {5000,6000,7000,8000,9000,10000};
        Server server = new Server();
        Authorization authorization = new Authorization(ports, server);
        AuthorizedCommunication authComm = new AuthorizedCommunication(server);
        server.setPortNumbers(ports);
        server.setAuthorization(authorization);
        server.setAuthComm(authComm);

    }

    public Server() {

    }


    void setAuthorizedClient(Client client){
        authComm.makeConnection(client);
    }

    public String getRecourse(String inputLine) {
        return "SECRET";
    }

    public void setPortNumbers(int[] portNumbers) {
        this.portNumbers = portNumbers;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }

    public void setAuthComm(AuthorizedCommunication authComm) {
        this.authComm = authComm;
    }
}
