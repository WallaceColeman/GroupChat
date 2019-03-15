package groupChat;

import java.net.*;
import java.io.*;
import java.util.*;

public class SeverSide {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			ServerSocket servSock = new ServerSocket(7000);
			System.out.println("Server started at "+ new Date() + '\n');
			Socket client = servSock.accept();
			System.out.println("Initiating connection");
			DataInputStream inputFromClient = new DataInputStream(client.getInputStream());
			DataOutputStream output2client = new DataOutputStream(client.getOutputStream());
			while(true) {
				int num = inputFromClient.readInt();
				output2client.writeInt(num*2);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
