#include <stdlib.h>
#include <boost/thread.hpp>
#include <boost/bind.hpp>
#include <boost/locale.hpp>
#include "../include/connectionHandler.h"
#include "../include/utf8.h"
#include "../include/encoder.h"


using namespace std;

void f2(ConnectionHandler* connectionHandler){

	while(1){
		ConnectionHandler* b = connectionHandler;
				std::string answer;

		        if (!b->getLine(answer)) {
		            std::cout << "Disconnected. Exiting...\n" << std::endl;
		            break;
		        }

				int len=answer.length();

		        answer.resize(len-1);
		        std::cout << answer << std::endl;

	}
}





/**
* This code assumes that the server replies the exact text the client sent it (as opposed to the practical session example)
*/
int main (int argc, char *argv[]) {



    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
       return 1;
    }


    		cout << "\nProtocol commands:\n-------------------------" << endl;
    		cout << "NICK - choose nick \nJOIN - join room \nMSG - send msg to all users inside that room \nLISTGAMES - games availabe in the server \nSTARTGAME <name> - start a specifig game in the channel \nTXTRESP - guess an answarer \nSELECTRESP -  choose an aswaer from collectin \nQUIT - close session \n "<< endl;


  boost::thread_group tgroup;
  tgroup.create_thread(boost::bind(f2,&connectionHandler));

  //boost::thread readThread(f2,&connectionHandler);


    while (1) {
        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
		std::string line(buf);
		int len=line.length();
        if (!connectionHandler.sendLine(line)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }
        /*
        std::string answer;

        if (!connectionHandler.getLine(answer)) {
            std::cout << "Disconnected. Exiting...\n" << std::endl;
            break;
        }

		len=answer.length();

        answer.resize(len-1);
        std::cout << answer << std::endl;
        */
    }
    return 0;
}


