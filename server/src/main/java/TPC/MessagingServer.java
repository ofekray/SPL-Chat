package TPC;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import encoding.Encoder;
import encoding.SimpleEncoder;

 
public class MessagingServer  {
    public static void mainRun(String[] args) 
    /* args[0] is the port to bind */
        throws NumberFormatException, IOException 
    {
        if (args.length < 1) {
            System.err.println("please supply only one argument, the port to bind.");
            return;
        }

        Encoder encoder = new SimpleEncoder("UTF-8");
        ServerSocket socket = new ServerSocket(Integer.parseInt(args[0]));
        System.out.println("Server is on: " + socket);
        System.out.println("Listening to port " + args[0] + "....");
        while (true) {
            Socket s = socket.accept();
            System.out.println(s);
            //Tokenizer tokenizer = new MessageTokenizer(new InputStreamReader(s.getInputStream(),encoder.getCharset()),'\n');
            Tokenizer tokenizer = new MessageTokenizer(new InputStreamReader(s.getInputStream(),encoder.getCharset()),'\n');
            
            
            ServerProtocol protocol = new TBGProtocol();
           
            Runnable connectionHandler = new ConnectionHandler(s, encoder, tokenizer, protocol);
           
            (new Thread(connectionHandler)).start();
            
        }
    }
}