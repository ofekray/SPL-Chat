package Framework;
import java.io.IOException;
import java.net.Socket;
import java.nio.channels.SocketChannel;


public class OnlineUser {

	public String nickName;
	private ProtocolCallback<String> callback;
	
	
	public OnlineUser(String nickName, ProtocolCallback<String> callback) {
		this.nickName = nickName;
		this.callback = callback;
	}
	public OnlineUser(SocketChannel socketChannel, String nickName) {
		this.nickName = nickName;
	}
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public void sendMsg(String msg)
	{
		try {
			this.callback.sendMessage(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}
