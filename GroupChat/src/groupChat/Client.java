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

public class Client {
	public static void main(String[] args) {
		try {
			
			Scanner scan = new Scanner(System.in);
			System.out.print("Enter name: ");
			String name = scan.nextLine();
			
			Socket sock = new Socket("127.0.0.1", 7000);
			
			//create an output stream to send data to the server
			DataOutputStream data2server = new DataOutputStream(sock.getOutputStream());
			//create an input stream to receive data from server
			DataInputStream fromServer = new DataInputStream(sock.getInputStream());
			
			data2server.writeUTF(name);
			Send send = new Send(data2server, name);
			send.start();
			Recieve recieve = new Recieve(fromServer);
			recieve.start();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Server not found");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Cannot Connect");
		}
	}

}

class Recieve extends Thread{
	DataInputStream fromServer;
	
	public Recieve(DataInputStream fromServer) {
		this.fromServer = fromServer;
	}
	
	public void run() {
		String strFromServer;
		int errorCount = 0;
		
		while(true) {
			try {
				strFromServer = fromServer.readUTF();
				//****************************************************************
				// This is for GUI if implemented
				//****************************************************************
//				if(strFromServer.split(":")[1].equalsIgnoreCase("GROUP")) {
//					System.out.println("Group");
//				}
//				else {
//					System.out.println("Personal");
//				}
				System.out.println(strFromServer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("Error reading from server");
				
				if(errorCount < 10) {
					errorCount++;
				}
				else{
					System.out.println("Too many reading errors disconnecting");
					System.exit(0);
				}
				
			}
		}
	}
}

class Send extends Thread{
	Scanner scan;
	DataOutputStream data2server;
	String name;
	public Send(DataOutputStream data2server, String name) {
		scan = new Scanner(System.in);
		this.data2server = data2server;
		this.name = name;
	}
	
	public void run() {
		String input;
		System.out.println("For group message type \"group: \" plus your message\n"
				+ "or type your message with no \":\"\n"
				+ "For a personal message type \"<name>: \" plus your message\n"
				+ "To quit type \"quit\"");
		while(true) {
			try {
				//System.out.println("Enter message: ");
				input = scan.nextLine();
				data2server.writeUTF(name + ": " + input);
				if(input.trim().equalsIgnoreCase("Quit")) {
					System.exit(0);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
