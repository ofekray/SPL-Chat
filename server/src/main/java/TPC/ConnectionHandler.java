package TPC;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;

import encoding.Encoder;
 
public class ConnectionHandler implements Runnable {
 
    private final Socket _socket;
    private final Encoder _encoder;
    private final Tokenizer _tokenizer;
    private final ServerProtocol _protocol;
    
    
    
 
    public ConnectionHandler(Socket s, Encoder encoder, Tokenizer tokenizer, ServerProtocol protocol) {
 
        _socket = s;
        _encoder = encoder;
        _tokenizer = tokenizer;
        _protocol= protocol;
    }
    
    
 
    public void run() {

    	
        while (!_protocol.shouldClose() && !_socket.isClosed()) { 
        	
            if (!_tokenizer.isAlive()){
            	
			    _protocol.connectionTerminated();
            }
			else {
				
			    String msg;
			   
				try {
					msg = _tokenizer.nextToken();
			    _protocol.processMessage(msg,c->{
			    	String toSendMsg = (String)c;
			    		
			    	byte[] buf = _encoder.toBytes(toSendMsg);
			        try {
			        	_socket.getOutputStream().write(buf, 0, buf.length);
					} catch (IOException e) {
						_protocol.connectionTerminated();
						e.printStackTrace();
					}
			    		
			    		
			    });
			    
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
    }
     System.out.println("Quit!!!!");  
}
}
                

       
        