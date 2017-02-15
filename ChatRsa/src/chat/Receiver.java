package chat;
import java.io.EOFException;
import java.io.IOException;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.Date;


public class Receiver extends Thread {
    String prompt;
    JChatComm chat;
    String serverAdd;
    
    public Receiver(JChatComm chat, String prompt, String serverAdd) {
        this.chat = chat;
        this.serverAdd = serverAdd;
        this.prompt = prompt;
    }
    
    public void run() {       
        JPacket received;
        int count=0;
        String publicKey;
        String Modulus;
        try {
            while((received = chat.receiveMessage()) != null) {    
                if (count==0){
                	publicKey=received.core.text;
                	chat.hisPublic = publicKey;
                }
                else if (count==1){
                	Modulus=received.core.text;
                	chat.hisModulus = Modulus;
                	if (!(prompt.equalsIgnoreCase("Cliente: "))){
                		System.out.println("\nServidor: "+serverAdd+"\nListo para chatear..");
                	}
                	else {
                		System.out.println("Claves Intercambiadas!\n");
                	}
                }
                else{
                	           	
                	System.out.println("<Enviado: "+received.timestamp+">");
        	        System.out.println("[Recibido: "+ new Timestamp((new Date()).getTime())+"]");
	            }
            count+=1;
            }
        } catch (SocketException e) {
            if (prompt.equalsIgnoreCase("Cliente: ")){
            	System.out.println("Sesión Terminada!\nEsperando que un cliente se conecte...");
            }
                      
        } catch (EOFException e) {
        	if (prompt.equalsIgnoreCase("Cliente: ")){
            	System.out.println("Sesión Terminada!\nEsperando que un cliente se conecte...");
            }
        } catch (IOException e) {
            e.printStackTrace();
    
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
