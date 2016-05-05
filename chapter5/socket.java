#include <stdio.h>
 #include <errno.h>
 #include <sys/socket.h>
 #include <netinet/in.h>

 int main () {
     struct sockaddr_in saddr;
     int fd, ret_val, opt_len, opt_val = 1, sockbuf_val = 4096;
     struct linger set_linger, get_linger;

     /* First step is to open a socket  */
     fd = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);
     if (fd == -1) {
         fprintf(stderr, "socket call failed [%s]\n", strerror(errno));
         return -1;
     }

     /* Next, let us use setsockopt() to set some options */
     printf("Let us set some socket options\n");
     ret_val = setsockopt(fd, SOL_SOCKET, SO_KEEPALIVE, &opt_val, sizeof(int));
     if (ret_val != 0) {
         fprintf(stderr, "setsockopt [SO_KEEPALIVE] failed [%s]\n",
                 strerror(errno));
     } else {
         printf("setsockopt [SO_KEEPALIVE] succeeded for socket fd: %d\n", fd);
     }

     set_linger.l_onoff = 1;
     set_linger.l_linger = 10;
     ret_val = setsockopt(fd, SOL_SOCKET, SO_LINGER, &set_linger,
             sizeof(struct linger));
     if (ret_val != 0) {
         fprintf(stderr, "setsockopt [SO_LINGER] failed [%s]\n",
                 strerror(errno));
     } else {
         printf("setsockopt [SO_LINGER] succeeded for socket fd: %d\n", fd);
     }

     ret_val = setsockopt(fd, SOL_SOCKET, SO_RCVBUF, &sockbuf_val, sizeof(int));
     if (ret_val != 0) {
         fprintf(stderr, "setsockopt [SO_RCVBUF] failed [%s]\n",
                 strerror(errno));
     } else {
         printf("setsockopt [SO_RCVBUF] succeeded for socket fd: %d\n", fd);
     }

     /* After that, let us use getsockopt() to retrieve values of options */
     printf("\nLet us retrieve those options:\n");
     opt_len = sizeof(int);
     ret_val = getsockopt(fd, SOL_SOCKET, SO_KEEPALIVE, &opt_val, &opt_len);
     if (ret_val != 0) {
         fprintf(stderr, "getsockopt [SO_KEEPALIVE] failed [%s]\n",
                 strerror(errno));
     } else {
         printf("getsockopt [SO_KEEPALIVE]: value: %d len: %d\n",
             opt_val, opt_len);
     }

     opt_len = sizeof(struct linger);
     ret_val = getsockopt(fd, SOL_SOCKET, SO_LINGER, &get_linger, &opt_len);
     if (ret_val != 0) {
         fprintf(stderr, "getsockopt [SO_LINGER] failed [%s]\n",
                 strerror(errno));
     } else {
         printf("getsockopt [SO_LINGER]: l_onoff: %d l_linger: %d len: %d\n",
             get_linger.l_onoff, get_linger.l_linger, opt_len);
     }

     opt_len = sizeof(int);
     ret_val = getsockopt(fd, SOL_SOCKET, SO_RCVBUF, &opt_val, &opt_len);
     if (ret_val != 0) {
         fprintf(stderr, "getsockopt [SO_RCVBUF] failed [%s]\n",
                 strerror(errno));
     } else {
         printf("getsockopt [SO_RCVBUF]: value: %d len: %d\n",
             opt_val, opt_len);
     }

     /* Last step is to close the sockets  */
     close(fd);
     return 0;
 }
