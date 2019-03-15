package groupChat;

import java.net.*;
import java.io.*;
import java.util.*;

public class SeverSide {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ArrayList<Socket> clients = new ArrayList<>();
		try {
			ServerSocket servSock = new ServerSocket(7000);
			System.out.println("Server started at "+ new Date() + '\n');
			Socket client = servSock.accept();
			System.out.println("Initiating connection");
			DataInputStream inputFromClient = new DataInputStream(client.getInputStream());
			DataOutputStream output2client = new DataOutputStream(client.getOutputStream());
			while(true) {
				client = servSock.accept();
				int num = inputFromClient.readInt();
				output2client.writeInt(num*2);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Connector{
	Socket client;
	DataInputStream inputFromClient;
	DataOutputStream output2client;
	
	public Connector(Socket client) throws IOException {
		this.client = client;
		inputFromClient = new DataInputStream(client.getInputStream());
		output2client = new DataOutputStream(client.getOutputStream());
	}
}

class GetMembers extends Thread{
	
	ArrayList<Connector> conections = new ArrayList<>();
	
	public void run(ServerSocket servSock) throws IOException {
		while(true) {
			Socket client = servSock.accept();
			Connector conection = new Connector(client);
			conections.add(conection);
		}
	}
	
	public ArrayList<Connector> getConections(){
		return conections;
	}
	
}