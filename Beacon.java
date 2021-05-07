import java.io.*;

// Beacon that udp send every certain interval to server
public class Beacon implements Serializable {
    public int ID; 
    public int startUpTime;  
    public int timeInterval;
    public StringBuilder IP;
    public int    cmdPort; 
    private static final long serialVersionUID = 1L;

    Beacon(int ID, int startUpTime, int timeInterval, StringBuilder IP,  int cmdPort) {
        this.ID = ID;
        this.startUpTime = startUpTime;
        this.timeInterval = timeInterval;
        this.IP = IP;
        this.cmdPort = cmdPort;
    }
    
    // Getter
    public int getID() {
        return ID;
    }
    public int getstartUpTime() {
        return startUpTime;
    }
    public int getTimeInterval() {
        return timeInterval;
    }
    public StringBuilder getIP() {
        return IP;
    }
    public int getcmdPort() {
        return cmdPort;
    }

    // Setter
    public void setID(int newID) {
        this.ID = newID;
    }
    public void setStartUpTime(int newStartUpTime) {
        this.startUpTime = newStartUpTime;
    }
    public void setTimeInterval(int newTimeInterval) {
        this.timeInterval = newTimeInterval;
    }
    public void setIP(StringBuilder newIP) {
        this.IP = newIP;
    }
    public void setCmdPort(int newCmdPort) {
        this.cmdPort = newCmdPort;
    }

    public static void main(String[] args) throws IOException {
    }
}
