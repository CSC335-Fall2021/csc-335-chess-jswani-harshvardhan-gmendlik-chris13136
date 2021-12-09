
/**
 * @filename ChessServer.java
 * @author Garrison Mendlik 12/8/2021
 * @author Jasnam Swani
 * @author Harshvardhan Bhatnagar
 * @author Chris Brinkley
 * @purpose Implements a server for networking.
 */

public class ChessServer extends Thread {
	public static void main(String[] args) throws Exception {
		ChessServer server = new ChessServer();
		server.start();
		// ServerManager servManager = new ServerManager(server);
		// servManager.start();
	}
}