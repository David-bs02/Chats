import interfaces.ChatClientInterface;
import interfaces.ChatServerInterface;

import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {

    private static final long serialVersionUID = 7468891722773409712L;
    private String hostName = "127.0.0.1";
    private String serviceName = "Chat";
    private String clientServiceName;
    private String name;
    protected ChatServerInterface serverIF;
    protected boolean connectionProblem = false;


    public ChatClient(String name) throws RemoteException {
        this.name = name;
        this.clientServiceName = "ClientListenService_" + name;
    }

    /**
     * Register our own listening service/interface
     * lookup the server RMI interface, then send our details
     * @throws RemoteException
     */
    public void startClient() throws RemoteException {
        String[] details = {name, hostName, clientServiceName};

        try {
            Registry registry = LocateRegistry.getRegistry("127.0.0.1",9090);
            registry.rebind(clientServiceName,this);
            serverIF = ( ChatServerInterface )registry.lookup(serviceName);
        }
        catch (ConnectException e) {
            System.err.println(e.getMessage());
            connectionProblem = true;
            e.printStackTrace();
        }
        catch(NotBoundException me){
            connectionProblem = true;
            me.printStackTrace();
        }
        if(!connectionProblem){
            registerWithServer(details);
        }
        System.out.println("Client Listen\n");
    }

    /**
     * pass our username, hostname and RMI service name to
     * the server to register out interest in joining the chat
     * @param details
     */
    public void registerWithServer(String[] details) {
        try{
            serverIF.passIDentity(this.ref);//now redundant ??
            serverIF.registerListener(details);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Receive a string from the chat server
     * this is the clients RMI method, which will be used by the server
     * to send messages to us
     */
    @Override
    public void messageFromServer(String message) throws RemoteException {
        System.out.println( message );
    }

}
