import java.lang.Thread;

public class Manager
{ 
	public static void main(String[] args)
	{ 

		// created two threads to simulated tcp and udp server.
        Thread tcp = new Thread(new TCPServer()); 
        tcp.start(); 

		Thread udp = new Thread(new UDPServer());
		udp.start();
	}
}