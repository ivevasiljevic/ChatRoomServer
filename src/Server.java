import java.net.*;
import java.util.ArrayList;
import java.io.*;

public class Server implements Runnable{
	
	private ArrayList<ClientServer> cs = new ArrayList<ClientServer>();
	private DatagramSocket s1;
	public int port;
	private Thread run,send1,receive,sendUsers;
	private boolean conn = false;
	
	public Server(int port) {
		this.port=port;
		try {
			s1 = new DatagramSocket(port);
		} 
		 catch (SocketException e) {
			e.printStackTrace();
		}
		run = new Thread(this,"Server");
		run.start();
	}
	
	public void run()
	{
		conn = true;
		System.out.println("Server started on port: " + port);
		sendUsers();
		receive();
	}
	
	private void sendUsers()
	{
		sendUsers = new Thread("sendu") {
			public void run()
			{
				while(conn)
				{
					if(cs.size() == 0) return;
						String user = "/u/";
		
						for(int i = 0;i<cs.size() -1 ;i++) {
							user += cs.get(i).name + "/n/";}
		
						user += cs.get(cs.size()-1).name + "/e/";
		
						SendToAll(user);
				}
			}
		};
		sendUsers.start();
	}
	
	 private void receive()
	 {
			receive = new Thread("Receive") {
				public void run()
				{
					while(conn)
					{
						byte[] data = new byte[1024];
						DatagramPacket packet =  new DatagramPacket(data,data.length);
						try {
							s1.receive(packet);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						process(packet);
						
					}
				}
			
			};
			receive.start();
	 }
	
	private void process(DatagramPacket packet)
	{
		String str = new String(packet.getData());
		
		
	
		
		if(str.startsWith("/c/"))
		{
			
			int id = UniqueInt.getID();
			cs.add(new ClientServer(str.split("/c/|/e")[1],packet.getAddress(),packet.getPort(),id));
			System.out.println(str.split("/c/|/e")[1] + " connected to the server" + " " + "ID: " + id);
			SendToAll(str);
		}
		else if(str.startsWith("/m/"))
		{
			SendToAll(str);
		}
		else if(str.startsWith("/d/"))
		{
			removeFromList(str.split("/d/|/e")[1]);	
			System.out.println(str.split("/d/|/e")[1] + " disconnected from the server ");
			SendToAll(str);
		}
		else if(str.startsWith("/l/"))
		{
			removeFromList(str.split("/l/|/e")[1]);
			System.out.println(str.split("/l/|/e")[1] + " logged out from the server");
			SendToAll(str);
		}
		
		else
		{
				System.out.println(str);
		}
	}

	private void removeFromList(String name)
	{
		for(int i = 0;i<cs.size();i++)
		{
			if(cs.get(i).name.equals(name))
			{
				cs.remove(i);
				break;
			}
		}
	}
	private void SendToAll(String msg)
	{
		for(int i = 0;i<cs.size();i++)
		{
			send(msg.getBytes(),cs.get(i).ip,cs.get(i).port);
		}
	}
	
	private void send(final byte[] data, final InetAddress ip ,final int port)
	{
		send1 = new Thread("Send") {
			public void run()
			{
				DatagramPacket packet = new DatagramPacket(data,data.length,ip,port);
				try {
					s1.send(packet);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			};
		send1.start();
	}
}
