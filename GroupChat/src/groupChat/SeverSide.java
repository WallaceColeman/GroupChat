/*
 * Course: CS 4345
 * Semester: Spring 2019
 * Assignment: Assignment 3
 * Names: Wallace Coleman, Savon Jackson, Amanda Seasholtz
 */

package groupChat;

import java.net.*;
import java.io.*;
import java.util.*;

public class SeverSide {
	static ArrayList<Connector> connections = new ArrayList<>();
	public static void main(String[] args) {
		
		//ArrayList<Socket> clients = new ArrayList<>();
		try {
			ServerSocket servSock = new ServerSocket(7000);
			System.out.println("Server started at "+ new Date() + '\n');
			
			GetMembers thread1 = new GetMembers(servSock);
			
			//thread1.run();
			thread1.start();
			
			while(true) {
				connections = thread1.getConections();
				try {
					//System.out.println("here");
//					for (int i = 0; i < connections.size(); i++) {
//						System.out.println("here");
//						System.out.println(connections.get(i).getInputFromClient().readUTF());
//					}
					String message;
					for(Connector i: connections) {
						if(i.getInputFromClient().available() != 0) {
							message = i.getInputFromClient().readUTF();
							if(message.split(":")[1].trim().equalsIgnoreCase("QUIT")) {
								for(Connector j: connections) {
									j.getOutput2client().writeUTF(i.getName() + " has disconnected");
								}
								connections.remove(i);
							}
							else {
								if(message.split(":")[1].trim().equalsIgnoreCase("GROUP")) {
									for(Connector j: connections) {
										j.getOutput2client().writeUTF(message);
									}
								}
								else {
									for(Connector j: connections) {
										if(j.getName().trim().equals(message.split(":")[1].trim())) {
											j.getOutput2client().writeUTF(message);
										}
									}
								}
								System.out.println(message);
							}
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class Connector{
	Socket client;
	DataInputStream inputFromClient;
	DataOutputStream output2client;
	String name;

	public Connector(Socket client, ArrayList<Connector> connections) throws IOException {
		this.client = client;
		inputFromClient = new DataInputStream(client.getInputStream());
		output2client = new DataOutputStream(client.getOutputStream());
		name = inputFromClient.readUTF();
		int count = 1;
		boolean wasTaken = false;
		for(Connector i: connections) {
			if(i.getName().trim().equalsIgnoreCase(name)) {
				if(count == 1) {
					name = name.substring(0, name.length()-(count/10)) +"_"+ count;
				}
				else {
					name = name.substring(0, name.length()-(2+count/10)) +"_"+ count;
				}
				
				count++;
				wasTaken = true;
			}
		}
		if(wasTaken) {
			output2client.writeUTF("Name taken. Your user name is: " + name);
		}
		System.out.println(name);
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
		return conections;
	}
	
	public void run() {
		while(true) {
			try {
				Socket client = servSock.accept();
				Connector conection = new Connector(client, conections);
				conections.add(conection);
				System.out.println("Added Member");
				for(Connector i: conections) {
					i.getOutput2client().writeUTF(conections.get(conections.size()-1).getName()+" has joined the group");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}

//class Server extends Thread{
//	ArrayList<Connector> connections = new ArrayList<>();
//	public Server() {
//		
//	}
//	
//	public void run() {
//		while(true) {
//			connections 
//		}
//	}
//}