public class ChessServer extends Thread {
	public static void main(String[] args) throws Exception {
		ChessServer server = new ChessServer();
		server.start();
		// ServerManager servManager = new ServerManager(server);
		// servManager.start();
	}
}