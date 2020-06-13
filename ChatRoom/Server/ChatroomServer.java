import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/*
 * Simple Proxy Server program
 */

public class ChatroomServer {
	public static final int DEFAULT_PORT = 1134;

	public static void main(String[] args) throws IOException {
		Executor exec = Executors.newCachedThreadPool(); // construct a thread pool for concurrency
		ServerSocket sock = null;
		try {

			// establish the socket
			sock = new ServerSocket(DEFAULT_PORT);
			while (true) {
				/**
				 * now listen for connections and service the connection in a separate thread.
				 */
				System.out.println("Waiting for connections ...");
				Runnable task = new Connection(sock.accept());
				exec.execute(task);
			}
		} catch (IOException ioe) {

		} finally {
			if (sock != null)
				sock.close();
		}

	}
}
