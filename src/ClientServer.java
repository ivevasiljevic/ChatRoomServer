import java.net.InetAddress;

public class ClientServer {
	public String name;
	public InetAddress ip;
	public int port;
	public final int id;
	
	public ClientServer(String name,InetAddress ip,int port,final int id)
	{
		this.name = name;
		this.ip = ip;
		this.port = port;
		this.id  = id;
	}
	
}
