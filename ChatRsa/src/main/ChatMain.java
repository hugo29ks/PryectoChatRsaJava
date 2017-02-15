package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.swing.JOptionPane;
import chat.JClient;
import chat.JServer;

public class ChatMain {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		String[] respuesta = {
	            "Cliente",
	            "Servidor"
	        };
	      
		String resp = (String) JOptionPane.showInputDialog(null, "Seleccione que quiere ser: ", "INICIO", JOptionPane.DEFAULT_OPTION, null, respuesta, respuesta[0]);

		/*System.out.print("Who are you? Server or Client? : ");
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		String ans = bf.readLine();*/
		
		if(resp.equalsIgnoreCase("servidor")){
			 JServer server = new JServer(5123);
		}
		else{
			JClient client = new JClient();
		}
	}

}
