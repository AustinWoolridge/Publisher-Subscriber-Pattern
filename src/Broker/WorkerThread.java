package Broker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class WorkerThread implements Runnable{
	private Socket pubSocket;
	public WorkerThread(Socket s) {
		pubSocket = s;
	}
	public void run() {
		//taken from Server4SingleClient
		PrintWriter socketOut = null;
		BufferedReader socketIn = null;
		
		try {			
			//will use socketOut to send text to the broker over the socket
			socketOut = new PrintWriter(pubSocket.getOutputStream(), true);
			//will use socketIn to receive text from the broker over the socket
			socketIn = new BufferedReader(new InputStreamReader(pubSocket.getInputStream()));
		} catch (IOException e) {
			System.out.println("Cannot get I/O for the connection.");
			e.printStackTrace();
			System.exit(0);
		}
		
		/**
		 * Protocol (known to both parties): 
		 * Server blocks on a message from the client.
		 * Client sends a message and blocks on the server's response.
		 * Upon receipt of message, server will respond: "You said: " + message
		 * This continues for 3 rounds.
		 */
		Scanner input = new Scanner(System.in);
		/*for(int i = 0; i < 3; i++) {
			System.out.println("Round (" + (i+1) + ")");
			System.out.println("Waiting for a message from the Broker.");
			String message = null;
			try {
				message = socketIn.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
			
		System.out.println("Waiting for a message from the Broker.");
			String message = null;
			try {
				message = socketIn.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			socketOut.println("You said: " + message);
			System.out.println("Broker's message was: \n\t\"" + message + "\"");
			System.out.println();
			
		
		
		//close all streams
		socketOut.close();
		input.close();
		try {
			socketIn.close();
			pubSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
