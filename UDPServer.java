import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.time.Instant;

public class UDPServer implements Runnable {

    public void run() {
        try
        { 
            runServer(); 
        } 
        catch (Exception e) 
        { 
            System.out.println ("UDP::Exception: " + e); 
        } 
        
    }
    
    // udp client handler
    public static void runServer() throws IOException 
	{
        DatagramSocket udpSocket = null;
		try
		{
            udpSocket = new DatagramSocket(1233); 
            List<Integer> activeAgents = new ArrayList<Integer>();
            DatagramPacket udpPacket = null;
            byte[] buffer = new byte[1024];
            while(true) {
                int now_time = (int)Instant.now().getEpochSecond();
                udpPacket = new DatagramPacket(buffer, buffer.length); 
                udpSocket.receive(udpPacket); 
                Beacon beacon = decodeBeacon(udpPacket);
                int ID = beacon.getID();
                boolean isFound = activeAgents.contains(ID);
                if (!isFound) {
                    System.out.println("New agent found with ID " + ID);
                    activeAgents.add(ID);
                }
                System.out.println("UDP::Message received from agent : " + beacon.getID());
                int send_time = beacon.getstartUpTime();
                int interval = beacon.getTimeInterval();

                // if the receive time is greater than interval then conside client dead.
                if (send_time - now_time > interval) {
                    System.out.println("UDP::Client Dead"); 
                }
                now_time = send_time;
                    
            }
        }
        catch(IOException exception) 
        { 
            System.out.println("UDP::Exception: " + exception); 
        }
	}

    // function to convert byte array into string
	public static StringBuilder readintoString(byte[] bytes) 
	{ 
		if (bytes == null) 
			return null; 
		StringBuilder data = new StringBuilder(); 
		int i = 0; 
		while (bytes[i] > 0) 
		{ 
            char c = (char) bytes[i];
			data.append(c);
			i++; 
		} 
		return data;
	} 

    // function to convert byte array into integer
	public static int readIntoInteger(byte[] bytes) {
        return ByteBuffer.wrap(bytes).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt();
    }

    // function to decode udp packet it beacon
    public static Beacon decodeBeacon(DatagramPacket DpReceive) {
        Beacon beacon = null;
        try {
            byte[] data = DpReceive.getData();

            byte[] ID_bytes = Arrays.copyOfRange(data, 0, 4);
            byte[] startUpTime_bytes = Arrays.copyOfRange(data, 4, 8);
            byte[] timeInterval_bytes = Arrays.copyOfRange(data, 8, 12);
            byte[] cmdPort_bytes = Arrays.copyOfRange(data, 12, 16);
            byte[] IP_bytes = Arrays.copyOfRange(data, 16, 24);
 
            int ID = readIntoInteger(ID_bytes);
            int startUpTime = readIntoInteger(startUpTime_bytes);
            int timeInterval = readIntoInteger(timeInterval_bytes);
            int cmdPort = readIntoInteger(cmdPort_bytes);
            StringBuilder IP = readintoString(IP_bytes);

            beacon = new Beacon(ID, startUpTime, timeInterval, IP, cmdPort); 
        
        } catch (Exception i) {
            i.printStackTrace();
        }
        return beacon;
    }
} 
