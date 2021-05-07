// Client side implementation of TCP client-server model 
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <netinet/in.h>

#define TCP_PORT 5000
#define MAX 80 
#define SA struct sockaddr 

// function to listen to server and reponding to server.
void tcp_channel(int socktcp)
{
    char buff[MAX];
    int n;
    while (1) {
        bzero(buff, sizeof(buff));
        printf("Enter the getlocalos or getlocaltime or anything : ");
        n = 0;
        while ((buff[n++] = getchar()) != '\n');
        write(socktcp, buff, sizeof(buff));
        bzero(buff, sizeof(buff));
        read(socktcp, buff, sizeof(buff));
        printf("Response from Server : %s", buff);
        if ((strncmp(buff, "Bye", 4)) == 0) {
            break;
        }
    }
}



int main() {
        int sockudp, socktcp;
        struct sockaddr_in       servaddr, cli;

        socktcp = socket(AF_INET, SOCK_STREAM, 0);
        if (socktcp == -1) {
            printf("Socket connection failed\n");
            exit(0);
        } else
            printf("Socket connetion succesful\n");

        bzero(&servaddr, sizeof(servaddr));

        // Filling tcp server information 
        servaddr.sin_family = AF_INET;
        servaddr.sin_port = htons(TCP_PORT);
        servaddr.sin_addr.s_addr = inet_addr("127.0.0.1");

        // connect the client socket to server socket 
        if (connect(socktcp, (SA*)&servaddr, sizeof(servaddr)) != 0) {
            printf("TCP connection failed\n");
            exit(0);
        } else
            printf("TCP connection successful\n");

        tcp_channel(socktcp);

        close(socktcp);
        return 0;
}
