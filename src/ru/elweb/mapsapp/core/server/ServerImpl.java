package ru.elweb.mapsapp.core.server;

public class ServerImpl implements Server {

	@Override
	public void runServer() {
		// TODO Auto-generated method stub
		
	}
	
	private final class ServerThread extends Thread {
		
		private static final String TAG = "ServerThread";
		private static final int DEFAULT_MAX_CONNECTIONS = 40;
		private final int maxConnections;
		
		
		public ServerThread(final int maxConnections) {
			this.maxConnections = maxConnections;
		}
		
		public ServerThread() {
			this.maxConnections = DEFAULT_MAX_CONNECTIONS;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
		
		}
		
	}

}
