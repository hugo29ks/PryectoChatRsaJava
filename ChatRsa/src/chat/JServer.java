package chat;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class JServer {
    JChatComm chat;
    ServerSocket serverSocket;
    public JServer(int portNumber) throws IOException, ClassNotFoundException{
        serverSocket = new ServerSocket(portNumber);
        System.out.println("Esperando que un cliente se conecte...  ");
        while(true) {
			acceptConnection();
        }
    }
    
    private void acceptConnection() throws IOException, ClassNotFoundException {
        Socket clientSocket = serverSocket.accept();
        String address =  (clientSocket.getInetAddress()).getHostAddress();
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        JPacket temp;
			temp = (JPacket) (in.readObject());
			System.out.println(address + " : " +temp.core.text);
        if (temp.core.text.contains("Hola! �Quieres chatear?")) {
        	
        	chat = new JChatComm(clientSocket,in,out,"Server");
            temp.core.text = "Si. Comencemos. Mi clave publica es: " + chat.myPublicKey+ " and "+chat.myModulus;
            out.writeObject(temp);
            System.out.println("Yo: "+temp.core.text);
        Sender sender = new Sender(chat, "Yo: ");
        Receiver receiver = new Receiver(chat, "Cliente: ","");
        sender.start();
        receiver.start();
        } else {
            System.out.println("ola k ase");
            clientSocket.close();            
        }
        
    }
}
