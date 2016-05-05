#include <lwip/sockets.h>

#define SENDER_PORT_NUM 6000
#define SENDER_IP_ADDR "192.136.23.20"

#define SERVER_PORT_NUM 6001
#define SERVER_IP_ADDRESS "192.136.23.21"


void main(void)
{

int socket_fd,accept_fd;
int addr_size,sent_data; char data_buffer[80]; struct sockaddr_in sa,ra,isa;

/* Creates an TCP socket (SOCK_STREAM) with Internet Protocol Family (PF_INET).
 * Protocol family and Address family related. For example PF_INET Protocol Family and AF_INET family are coupled.
*/

socket_fd = socket(PF_INET, SOCK_STREAM, 0);

if ( socket_fd < 0 )
{

printf("socket call failed");
exit(0);
}

memset(&sa, 0, sizeof(struct sockaddr_in));
sa.sin_family = AF_INET;
sa.sin_addr.s_addr = inet_addr(SENDER_IP_ADDR);
sa.sin_port = htons(SENDER_PORT_NUM);


/* Bind the TCP socket to the port SENDER_PORT_NUM and to the current
* machines IP address (Its defined by SENDER_IP_ADDR).
* Once bind is successful for UDP sockets application can operate
* on the socket descriptor for sending or receiving data.
*/
if (bind(socket_fd, (struct sockaddr *)&sa, sizeof(sa)) == -1)
{
printf("Bind to Port Number %d ,IP address %s failed\n",SENDER_PORT_NUM,SENDER_IP_ADDR);
close(socket_fd);
exit(1);
}

listen(socket_fd,5);
addr_size = sizeof(isa);
accept_fd = accept(socket_fd, (struct sockaddr*)&isa,&addr_size);
if(accept_fd < 0)
{

printf("accept failed\n");
close(socket_fd);
exit(2);
}
strcpy(data_buffer,"Hello World\n");
sent_data = send(accept_fd, data_buffer,sizeof("Hello World",0));

if(sent_data < 0 )
{

printf("send failed\n");
close(socket_fd);
exit(3);
}

close(socket_fd);
}
