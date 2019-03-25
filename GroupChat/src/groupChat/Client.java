
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
			Socket sock = new Socket("127.0.0.1", 7000);
			
			//create an output stream to send data to the server
			DataOutputStream data2server = new DataOutputStream(sock.getOutputStream());
			//create an input stream to receive data from server
			DataInputStream fromServer = new DataInputStream(sock.getInputStream());
			
			Scanner scan = new Scanner(System.in);
			System.out.print("Enter name: ");
			String name = scan.nextLine();
			
			data2server.writeUTF(name);
			Send send = new Send(data2server, name);
			send.start();
			//fromServer.readInt();
			
//			while(true) {
//				//System.out.println("Enter message: ");
//				//data2server.writeInt(scan.nextInt());
//				System.out.println(name + ": " + fromServer.readInt());
//			}
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
				+ "For a personal message type \"<name>: \" plus your message");
		while(true) {
			
//			switch(input.split(" ")[0]) {
//			
//			}
			try {
				System.out.println("Enter message: ");
				input = scan.nextLine();
				data2server.writeUTF(name+": "+input);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
