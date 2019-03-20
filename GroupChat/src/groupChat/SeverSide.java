package groupChat;

import java.net.*;
import java.io.*;
import java.util.*;

public class SeverSide {
	static ArrayList<Connector> connections = new ArrayList<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ArrayList<Socket> clients = new ArrayList<>();
		try {
			ServerSocket servSock = new ServerSocket(7000);
			System.out.println("Server started at "+ new Date() + '\n');
			
			GetMembers thread1 = new GetMembers(servSock);
			
			//thread1.run();
			thread1.start();
			
			while(true) {
				connections = thread1.getConections();
				//System.out.println(connections.size());
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
	
	public Socket getClient() {
		return client;
	}

	public DataInputStream getInputFromClient() {
		return inputFromClient;
	}

	public DataOutputStream getOutput2client() {
		return output2client;
	}
}

class GetMembers extends Thread{
	
	ArrayList<Connector> conections = new ArrayList<>();
	ServerSocket servSock;
	
	public GetMembers(ServerSocket servSock2) {
		servSock = servSock2;
		// TODO Auto-generated constructor stub
	}


	
	public ArrayList<Connector> getConections(){
		//System.out.println("Here");
		return conections;
	}
	
	
	public void run() {
		while(true) {
			try {
				Socket client = servSock.accept();
				Connector conection = new Connector(client);
				conections.add(conection);
				System.out.println("Added Member");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}