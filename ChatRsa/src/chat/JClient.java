package chat;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.Date;
import javax.swing.JOptionPane;


public class JClient {
    JChatComm chat;
    public JClient() throws ClassNotFoundException, IOException {
    	
    	/*BufferedReader ans = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter IP address of server : ");*/
    	String ip = JOptionPane.showInputDialog(null, "Ingrese la IP del Servidor");
        this.callServer(ip, 5123);
    }

    public void callServer(String hostName,int portNumber) throws ClassNotFoundException, UnknownHostException, IOException {
     	//System.out.println("Trying to connect to a server ... ");
    	
        Socket echoSocket = new Socket(hostName, portNumber);
        
        //System.out.println("Connected to server : "+hostName);
        JOptionPane.showMessageDialog(null, "Conectado al servidor: "+hostName);
        ObjectOutputStream out =  new ObjectOutputStream(echoSocket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(echoSocket.getInputStream());
        
        //System.out.println("Streams established. Ready for a chat ...\n");
        JOptionPane.showMessageDialog(null, "Espere hasta que las llaves se compartan....", "CONEXION EXITOSA", JOptionPane.INFORMATION_MESSAGE);
        WaitingThread wait = new WaitingThread();
        chat = new JChatComm(echoSocket, in, out,"Cliente"); 
        JPacket pkt = new JPacket(new JMessage("Hola! ¿Quieres chatear? Mi clave publica es: " + chat.myPublicKey+" and "+chat.myModulus));
        System.out.println("Yo: "+pkt.core.text);
        System.out.println("<Enviado: "+pkt.timestamp+">\n");
        out.writeObject(pkt);
        wait.start();
        JPacket reply = (JPacket)(in.readObject());
        if ((reply.core.text).contains("Si")) {
	        System.out.println(hostName+ " : " + reply.core.text);
	        System.out.println("<Enviado: "+pkt.timestamp+">");
	        System.out.println("[Recibido:"+ new Timestamp((new Date()).getTime())+"]");
	        Sender sender = new Sender(chat, "Yo: ");
	        Receiver receiver = new Receiver(chat, hostName+" : ", hostName);
	        sender.start();
	        receiver.start();
        }
        else {
            echoSocket.close();
            System.out.println("Tiempo Finalizado!");
        }
 
    }
}
