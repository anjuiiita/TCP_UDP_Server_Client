import java.net.*; 
import java.io.*;
import java.util.Date;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;

public class TCPServer implements Runnable {

    private static String OS = System.getProperty("os.name").toLowerCase();

	public void run() {
        try
        { 
            runServer(); 
        } 
        catch (Exception e) 
        {  
            System.out.println ("TCP::Exception: " + e); 
        } 
    }

    public static void runServer() throws IOException 
	{
		ServerSocket serverSocket = null;
		try
		{
			// create a socket, which connect to the server
			serverSocket = new ServerSocket(5000); 
			serverSocket.setReuseAddress(true); 
			System.out.println("TCP::Server started"); 
			while (true) {
			  
				System.out.println("TCP::Waiting for a client ..."); 

				Socket client = serverSocket.accept(); 
				System.out.println("TCP::Client accepted"); 

                TCPClientHandler tcpSock = new TCPClientHandler(client); 
                new Thread(tcpSock).start(); 

			}
		} catch (IOException e) { 
				e.printStackTrace(); 
			} 
			finally { 
				if (serverSocket != null) { 
					try { 
						serverSocket.close(); 
					} 
					catch (IOException e) { 
						e.printStackTrace(); 
					} 
				} 
			} 
	}

	// client handler class 
    private static class TCPClientHandler implements Runnable { 
        private final Socket tcpClient; 
  
        // Constructor 
        public TCPClientHandler(Socket client) 
        { 
            this.tcpClient = client; 
        } 
  
        public void run() 
        { 
            PrintWriter out = null; 
            BufferedReader in = null; 
			BufferedReader stdIn = null; 
            try { 
                    
                // takes input from the client socket 
				out = new PrintWriter(tcpClient.getOutputStream(),true);
				in = new BufferedReader(new InputStreamReader(tcpClient.getInputStream()));
				stdIn = new BufferedReader(new InputStreamReader(System.in));

				String line = in.readLine();
				line = line.toLowerCase();
				String GetLocalOS = "getlocalos";
				String GetLocalTime = "getlocaltime";
				String BYE = "bye";
				while(!(line.indexOf(BYE) >=0))
				{	
					System.out.println("Client:- " + line);
					out.flush();
					if (line.indexOf(GetLocalOS) >= 0) {
                        if (OS.indexOf("win") >= 0) {
							out.println("OS name is Windows: " + OS);
						} else if (OS.indexOf("mac") >= 0) {
							out.println("OS name is Mac: " + OS);
						} else if (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0) {
							out.println("OS name is Linux: " + OS);
						} else if (OS.indexOf("sunos") >= 0) {
							out.println("OS name is Solaris: " + OS);
						} else {
							out.println("Sorry, Couldnot recognise OS name!");
						}
					} else if (line.indexOf(GetLocalTime) >= 0) {
						Date date = new GregorianCalendar().getTime();   
						out.println(new SimpleDateFormat("yyyy.MM.dd  HH:mm").format(date));
					} else {
						System.out.println("Enter a string :");
						line = stdIn.readLine();
						out.println(line);
					}
					System.out.println("Waiting for respose from client..");
					line = in.readLine();
					line = line.toLowerCase();
				}
            } 
            catch (IOException e) { 
                e.printStackTrace(); 
            } 
            finally { 
                try { 
                    if (out != null) { 
                        out.close(); 
                    } 
					if (stdIn != null) { 
                        stdIn.close(); 
                    } 
                    if (in != null) { 
                        in.close(); 
                        tcpClient.close(); 
                    } 
                } 
                catch (IOException e) { 
                    e.printStackTrace(); 
                } 
            } 
        } 
    }
}
