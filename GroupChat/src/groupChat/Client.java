package groupChat;

import java.net.*;
import java.io.*;
import java.util.*;

public class Client {

	public static void main(String[] args) {
		try {
			Socket sock = new Socket("127.0.0.1", 7000);
			
			//create an output stream to send data to the server
			DataOutputStream data2server = new DataOutputStream(sock.getOutputStream());
			//create an input stream to receive data from server
			DataInputStream resultFromServer = new DataInputStream(sock.getInputStream());
			
			Scanner scan = new Scanner(System.in);
			System.out.print("Enter name: ");
			String name = scan.nextLine();
			
			while(true) {
				System.out.println("Enter message: ");
				data2server.writeInt(scan.nextInt());
				System.out.println(name + ": " + resultFromServer.readInt());
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Server not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Input in wrong format");
		}
	}

}

class Recieve extends Thread{
	
}

class Send extends Thread{
	
}
