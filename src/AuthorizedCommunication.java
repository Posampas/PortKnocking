import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthorizedCommunication {
    int count = 0;
    private ExecutorService authCommunicationThreads;
    private Server server;

    public AuthorizedCommunication(Server server) {
        authCommunicationThreads = Executors.newCachedThreadPool();
        this.server = server;
    }

    public int getRandomPortNumber() {
        return (int) (Math.random() * 62000) + 10000;
    }


    public void makeConnection(Client client) {
        count++;
        authCommunicationThreads.submit(new AuthConnectionThread(server, client, getRandomPortNumber()));
    }
}
