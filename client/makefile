# All Targets
#LDFLAGS: lboost_system -lboost_locale -lpthread
#LDFLAGS2 -lboost_system -lboost_thread -lboost_filesystem -lboost_locale
#LDFLAGS3 -lboost_system -lboost_filesystem -lpthread -lboost_thread -llog4cpp
#valgrind --leak-check=full -v bin/cyber
all: Client

# Tool invocations
Client: bin/echoClient.o bin/connectionHandler.o bin/encoder.o
	@echo 'Building target: Client'
	@echo 'Invoking: C++ Linker'
	g++ -o bin/Client bin/echoClient.o bin/connectionHandler.o bin/encoder.o -lboost_system -lboost_filesystem -lpthread -lboost_thread -lboost_locale
	@echo 'Finished building target: Client'
	@echo ' '

# Depends on the source and header files
bin/echoClient.o: src/echoClient.cpp
	g++ -g -Wall -Weffc++ -c -Linclude -o bin/echoClient.o src/echoClient.cpp -lboost_system -lboost_filesystem -lpthread -lboost_thread -lboost_locale

# Depends on the source and header files 
bin/connectionHandler.o: src/connectionHandler.cpp
	g++ -g -Wall -Weffc++ -c -Linclude -o bin/connectionHandler.o src/connectionHandler.cpp -lboost_system -lboost_filesystem -lpthread -lboost_thread -lboost_locale
	
# Depends on the source and header files 
bin/encoder.o: src/encoder.cpp
	g++ -g -Wall -Weffc++ -c -Linclude -o bin/encoder.o src/encoder.cpp -lboost_system -lboost_filesystem -lpthread -lboost_thread -lboost_locale

#Clean the build directory
clean: 
	rm -f bin/*