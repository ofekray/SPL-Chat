# SPL-Chat
Built in SPL Course.

The server was built using Java and supports ThreadPerClient and Reactor Protocols.

The Client was built using C++.

The server supports many clients and chat rooms.

## Run using:
mvn install
## and then:
mvn exec:java -Dexec.args="4000 7"

OR

mvn exec:java -Dexec.mainClass="spl_assignment3.App" -Dexec.args="4000 7"


## Question file should be called:
bluffer.json

[and you should put it a the same folder as src]

## Json example:
See bluffer.json.example

## Note:
Windows and Linux NewLine are diffrenet.

(So for the server to work in Windows we need to put -1 in tokenizer\FixedSeparatorMessageTokenizer.java
and in TPC\MessageTokenizer.java)
