import java.rmi.RemoteException;
import java.util.Scanner;

public class ChatClientMain {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String message = "";
            System.out.println("Cual es tu nombre");
            message = scanner.nextLine();
            String cleanedUserName = message.replaceAll("\\s+","_");
            cleanedUserName = message.replaceAll("\\W+","_");
            try {
                ChatClient client = new ChatClient(cleanedUserName);
                client.startClient();
                while (true){
                    message = scanner.nextLine();
                    client.serverIF.updateChat(cleanedUserName,message);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}

