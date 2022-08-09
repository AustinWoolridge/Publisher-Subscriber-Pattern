package Broker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Broker {
	//the port on which server will listen for connections 
		public static int portNumber = 4445;
		public static int portNumber2 = 4446;
		
		public static void main(String[] args) {
			
			ServerSocket brokerSocket = null;
			ServerSocket brokerSocket2 = null;
			
			try {
				//initialize server socket
				brokerSocket = new ServerSocket(portNumber);
				brokerSocket2 = new ServerSocket(portNumber2);
				System.out.println("Server sockets initialized.\n");
			} catch (IOException e) { //if this port is busy, an IOException is fired
				System.out.println("Cannot listen on port " + portNumber);
				e.printStackTrace();
				System.exit(0);
			}
			
			Socket pubSocket = null;
			Socket subSocket =null;
			
			try {
				System.out.println("Waiting for a Publisher to connect.");
				System.out.println();
				System.out.println("Waiting for a Subscriber to connect.");
				System.out.println();
				while(true) { //infinite loop - terminate manually
					//wait for pub connections
					
					
					try {
						pubSocket = brokerSocket.accept();
						subSocket = brokerSocket2.accept();
					} catch (IOException e) {
						e.printStackTrace();
						System.exit(0);
					}
					
					
					
					//let us see who connected
					String pubName = pubSocket.getInetAddress().getHostName();
					System.out.println(pubName + " established a connection.");
					System.out.println();
					String subName = subSocket.getInetAddress().getHostName();
					System.out.println();
					
					//assign a worker thread
					WorkerThread w = new WorkerThread(pubSocket);
					new Thread(w).start();
				}
			} finally {
				//make sure that the socket is closed upon termination
				try {
					brokerSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
}
