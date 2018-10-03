
public class ServerMain {
	
	private int port;
	private Server s;
	public ServerMain(int port)
	{
		this.port = port;
		s = new Server(port);
	}
	public static void main(String[] args) {
		int port;
		if(args.length > 1)
		{
			System.out.println("Usage: java -jar ServerMain.jar [port]");
			return;
		}
		port = Integer.parseInt(args[0]);
		new ServerMain(port);
		
	}

}
