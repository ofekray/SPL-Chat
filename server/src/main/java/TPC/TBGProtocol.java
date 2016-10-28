package TPC;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import Framework.OnlineUser;
import Framework.ProtocolCallback;
import Framework.Room;
import Framework.RoomHandler;
import json.Questions;


 
public class TBGProtocol implements ServerProtocol<String> {
	
	boolean _shouldClose;
	String nickName;
	String roomName;
	RoomHandler roomHandler;
	
	
	public TBGProtocol(){
		_shouldClose = false;
		roomHandler = RoomHandler.getInstance();
	}
	

	@Override
	public void processMessage(String msg, ProtocolCallback<String> callback) {
		
		if (isEnd(msg))
		{
			if (roomName != null)
			{
				roomHandler.leaveRoom(roomName, nickName);
			}
			_shouldClose = true;
			return;
		}
		if(msg.startsWith("NICK ")){
			String cmd = msg.substring(5);
			if (roomHandler.registerNick(cmd) && nickName==null)
			{
				String c = "SYSMSG NICK ACCEPTED\n";
				try {
					callback.sendMessage(c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nickName = cmd;
			}
			else
			{
				String c = "SYSMSG NICK REJECTED\n";
				try {
					callback.sendMessage(c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return;
			
		}
		
		
		
		if(msg.startsWith("JOIN ")){
			String cmd = msg.substring(5);
			if(roomName == null)
			{
				OnlineUser u = new OnlineUser(nickName, callback);
				if (roomHandler.joinRoom(cmd, u))
				{
					String c = "SYSMSG JOIN ACCEPTED\n";
					try {
						callback.sendMessage(c);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					roomName = cmd;
				}
				else
				{
					String c = "SYSMSG JOIN REJECTED\n";
					try {
						callback.sendMessage(c);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else
			{
				OnlineUser u = new OnlineUser(nickName, callback);
				if (roomHandler.leaveRoom(roomName, nickName))
				{
					if (roomHandler.joinRoom(cmd, u))
					{
						String c = "SYSMSG JOIN ACCEPTED\n";
						try {
							callback.sendMessage(c);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						roomName = cmd;
					}
					else
					{
						String c = "SYSMSG JOIN REJECTED\n";
						try {
							callback.sendMessage(c);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						roomName = null;
					}
				}
				else
				{
					String c = "SYSMSG JOIN REJECTED\n";
					try {
						callback.sendMessage(c);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			return;
			
		}
		
		
		
		
		
		
		
		
		
		if(msg.startsWith("MSG ")){
			if (roomName == null)
			{
				String c = "SYSMSG MSG REJECTED\n";
				try {
					callback.sendMessage(c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return;
			}
			String cmd = msg.substring(4);
			
			String c = "SYSMSG MSG ACCEPTED\n";
			try {
				callback.sendMessage(c);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			CopyOnWriteArrayList<OnlineUser> onlineUsers = roomHandler.getUsersInRoom(this.roomName);
			for (OnlineUser u : onlineUsers)
			{
				if (u.getNickName() != nickName)
				{
					u.sendMsg("USRMSG " + this.nickName + ": " + cmd + "\n");
				}
			}
			
			return;
		}
		
		
		
		
		
		
		
		
		
		if(msg.equals("LISTGAMES")){
			String gamesString = roomHandler.supportedGames();
			String c = "SYSMSG LISTGAMES ACCEPTED " + gamesString + "\n";
			try {
				callback.sendMessage(c);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		if(msg.startsWith("STARTGAME ")){
			
			String cmd = msg.substring(10);
			
			if (roomName ==null || !roomHandler.isValidGame(cmd))
			{
				
				String c = "SYSMSG STARTGAME REJECTED\n";
				try {
					callback.sendMessage(c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				Room r = roomHandler.getRoom(roomName);
				r.startGame();
				String c = "SYSMSG STARTGAME ACCEPTED\n";
				try {
					callback.sendMessage(c);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Questions q = r.getGame().getCurrentQuestion();
				CopyOnWriteArrayList<OnlineUser> onlineUsers = roomHandler.getUsersInRoom(this.roomName);
				for (OnlineUser u : onlineUsers)
				{
					u.sendMsg("ASKTXT " + q.getQuestionText() + "\n");
				}
			}
			return;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		if(msg.startsWith("TXTRESP ")){
			String cmd = msg.substring(8);
			Room r = roomHandler.getRoom(roomName);
			r.getGame().PlayerAnswerToQuestion(nickName, cmd);
			String c = "SYSMSG TXTRESP ACCEPTED\n";
			try {
				callback.sendMessage(c);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (r.getGame().everyoneAnswerd())
			{
				String ans = r.getGame().getShuffledAnswersString();
				CopyOnWriteArrayList<OnlineUser> onlineUsers = roomHandler.getUsersInRoom(this.roomName);
				for (OnlineUser u : onlineUsers)
				{
					u.sendMsg("ASKCHOICES " + ans + "\n");
				}
				
			}
			return;
		}
		
		
		
		
		
		
		
		
		
		
		if(msg.startsWith("SELECTRESP ")){
			String cmd = msg.substring(11);
			Room r = roomHandler.getRoom(roomName);
			String c = "SYSMSG SELECTRESP ACCEPTED\n";
			try {
				callback.sendMessage(c);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			if (!r.getGame().validGuessNumber(cmd))
			{
				String c1 = "SYSMSG SELECTRESP REJECTED\n";
				try {
					callback.sendMessage(c1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return;
			}
			
			String c3 = "GAMEMSG The correct answer is: " + r.getGame().getCurrentQuestion().getRealAnswer() + "\n";;
			try {
				callback.sendMessage(c3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			boolean GoodGuess = r.getGame().PlayerGuessRealAnswerToQuestion(nickName, cmd);
			if (GoodGuess)
			{
				String c1 = "GAMEMSG Correct! +10pts\n";
				try {
					callback.sendMessage(c1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				String c1 = "GAMEMSG Wrong!\n";
				try {
					callback.sendMessage(c1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (r.getGame().everyoneGuessed())
			{
				if (r.getGame().moveToNextQuestion())
				{
					Questions q = r.getGame().getCurrentQuestion();
					CopyOnWriteArrayList<OnlineUser> onlineUsers1 = roomHandler.getUsersInRoom(this.roomName);
					for (OnlineUser u : onlineUsers1)
					{
						u.sendMsg("ASKTXT " + q.getQuestionText() + "\n");
					}
				}
				else //Game end
				{
					String summary = r.getGame().getGameSummary();
					CopyOnWriteArrayList<OnlineUser> onlineUsers = roomHandler.getUsersInRoom(this.roomName);
					for (OnlineUser u : onlineUsers)
					{
						u.sendMsg("GAMEMSG Summary: " + summary + "\n");
					}
					r.setInGame(false);
				}
			}

			return;
		}
		
		
		
		else
		{
			String c = "SYSMSG " + msg +" UNIDENTIFIED\n";
			try {
				callback.sendMessage(c);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public boolean isEnd(String msg) {
		if(msg.equals("QUIT")){
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldClose() {
		return _shouldClose;
	}

	@Override
	public void connectionTerminated() {
		_shouldClose = true;
		
	}
	


	
	
}







