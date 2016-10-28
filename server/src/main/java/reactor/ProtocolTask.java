package reactor;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;

import protocol.*;
import tokenizer.*;

/**
 * This class supplies some data to the protocol, which then processes the data,
 * possibly returning a reply. This class is implemented as an executor task.
 * 
 */
public class ProtocolTask<T> implements Runnable {

	private final TBGProtocolForReactor _protocol;
	private final MessageTokenizer<StringMessage> _tokenizer;
	private final ConnectionHandler<T> _handler;

	public ProtocolTask(final ServerProtocol<String> protocol, final MessageTokenizer<T> tokenizer, final ConnectionHandler<T> h) {
		this._protocol = (TBGProtocolForReactor)protocol;
		this._tokenizer = (MessageTokenizer<StringMessage>)tokenizer;
		this._handler = h;
	}

	// we synchronize on ourselves, in case we are executed by several threads
	// from the thread pool.
	public synchronized void run() {
      // go over all complete messages and process them.
      while (_tokenizer.hasMessage()) {
    	  StringMessage msg = (StringMessage)_tokenizer.nextMessage();
         
		    this._protocol.processMessage(msg.getMessage(),c->{
		    	String toSendString = (String)c;
	               ByteBuffer bytes = _tokenizer.getBytesForMessage(new StringMessage(toSendString));
	               this._handler.addOutData(bytes);	
		    });
      }
	}

	public void addBytes(ByteBuffer b) {
		_tokenizer.addBytes(b);
	}
}
