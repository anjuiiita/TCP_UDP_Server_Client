// Client side implementation of UDP client-server model 
#include <stdio.h> 
#include <stdlib.h> 
#include <unistd.h> 
#include <string.h> 
#include <sys/types.h> 
#include <sys/socket.h> 
#include <arpa/inet.h> 
#include <netinet/in.h> 
#include <sys/time.h>
#include <signal.h>


#define UDP_PORT	 1233 
#define MAXLINE 1024 

struct BEACON { 
	int ID;  // randomly generated during startup
	int startUpTime; // the time when the client starts              
	int        timeInterval; // the time period that this beacon will be repeated 
	int cmdPort;       // the client listens to this port for manager commands 
	char     IP[8];               // the IP address of this client
};

int main() { 
	
	int sockudp; 
	char buffer[MAXLINE]; 
	struct sockaddr_in	servaddr; 
	
	// Creating socket file descriptor 
	if ( (sockudp = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) { 
		perror("socket connection failed"); 
		exit(EXIT_FAILURE); 
	}
	
	// Filling udp server information 
	servaddr.sin_family = AF_INET; 
	servaddr.sin_port = htons(UDP_PORT); 
	servaddr.sin_addr.s_addr = INADDR_ANY; 

	// socket ip address
	char * ip_addr = inet_ntoa(((struct sockaddr_in *)&servaddr.sin_addr)->sin_addr);
	
	// generateing random id
	int ID = time(0) % 1000; 
	int counter = 0;
	struct BEACON beacon;
	while (1) {
		beacon.ID = ID;
		beacon.startUpTime = time(0);
		beacon.timeInterval = 2;
		beacon.cmdPort = UDP_PORT;
		strcpy(beacon.IP, ip_addr);
		sendto(sockudp, (const struct BEACON*)&beacon, sizeof(beacon), 0, (const struct sockaddr *) &servaddr, 
				sizeof(servaddr)); 
		printf("message sent.\n");

		// counter to test when the send and receive time is greater then interval.
		counter++;
		if (counter > 2) {
			sleep(5);
		} else {
			sleep(2);
		}
	}
	close(sockudp); 
	return 0; 
}