package Framework;
import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


public class Room {

	private String roomName;
	private BlufferGame game;
	private boolean inGameNow;
	private  CopyOnWriteArrayList<OnlineUser> roomates;
	
	public Room(String roomName) {
		this.roomName = roomName;
		this.roomates = new CopyOnWriteArrayList<OnlineUser>();
		inGameNow = false;
	}
	
	public boolean isInGameNow()
	{
		return this.inGameNow;
	}
	
	public void setInGame(boolean inGame)
	{
		this.inGameNow = inGame;
	}
	
	
	public void startGame()
	{
		this.inGameNow = true;
		ArrayList<BlufferPlayer> bluffPlayers = new ArrayList<BlufferPlayer>();
		for (OnlineUser u : roomates)
		{
			BlufferPlayer p = new BlufferPlayer(u.getNickName());
			bluffPlayers.add(p);
		}
		try {
			this.game = new BlufferGame("bluffer.json", bluffPlayers);
		} catch (FileNotFoundException e) {
			System.out.println("in ROOM.java You forget to insert a correct path to the JSON QB");
		}

	}
	
	public void AddUserToRoom(OnlineUser u)
	{
		this.roomates.add(u);
	}
	
	public void DeleteFromRoom(String nick)
	{
		for (int i=0; i<this.roomates.size(); i++)
		{
			if (this.roomates.get(i).getNickName().equals(nick))
			{
				this.roomates.remove(i);
				break;
			}
		}
	}
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public BlufferGame getGame() {
		return game;
	}
	public void setGame(BlufferGame game) {
		this.game = game;
	}
	public CopyOnWriteArrayList<OnlineUser> getRoomates() {
		return roomates;
	}
	public void setRoomates(CopyOnWriteArrayList<OnlineUser> roomates) {
		this.roomates = roomates;
	}
	
	
	
}
