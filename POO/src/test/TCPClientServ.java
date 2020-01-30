package test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class TCPClientServ {

	final static int port = 9632;

	public static void main(String[] args) {

		Socket socket;
		DataInputStream userInput;
		PrintStream theOutputStream;

		try {
			InetAddress serveur = InetAddress.getByName(args[0]);
			int port = Integer.parseInt(args[1]);
			socket = new Socket(serveur, port);

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream out = new PrintStream(socket.getOutputStream());

			out.println(args[1]);
			System.out.println(in.readLine());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

/*
 * public class EchoServer { public void start(int port) { serverSocket = new
 * ServerSocket(port); clientSocket = serverSocket.accept(); out = new
 * PrintWriter(clientSocket.getOutputStream(), true); in = new
 * BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
 * 
 * String inputLine; while ((inputLine = in.readLine()) != null) { if
 * (".".equals(inputLine)) { out.println("good bye"); break; }
 * out.println(inputLine); } }
 */