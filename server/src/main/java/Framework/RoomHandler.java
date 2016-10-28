package Framework;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;


public class RoomHandler {

	
	ConcurrentHashMap<String, Room> rooms;
	CopyOnWriteArrayList<String> registerdNickNames;
	String[] supportedGames = new String[]{"BLUFFER"};
	
    private static class RoomHolder {
        private static RoomHandler instance = new RoomHandler();
     }
    
    private RoomHandler(){
    	rooms = new ConcurrentHashMap<String, Room>();
    	registerdNickNames = new CopyOnWriteArrayList<String>();
    }
     
     public static RoomHandler getInstance() {
         return RoomHolder.instance;
     }
     
     public String supportedGames()
     {
    	 StringBuilder sb = new StringBuilder();
    	 for (String s : supportedGames)
    	 {
    		 sb.append("[" + s + "]");
    	 }
    	 return sb.toString();
     }
     
     public Room getRoom(String roomName)
     {
    	 return this.rooms.get(roomName);
     }
     
     public boolean isValidGame(String gameName)
     {
    	
    	 for (int i=0; i<this.supportedGames.length; i++)
    	 {
    		 
    		 if (this.supportedGames[i].equals(gameName)){
    			
    			 return true;
    		 }
    	 }
    	 return false;
     }
     
     public boolean registerNick(String nickName)
     {
    	 if (this.registerdNickNames.contains(nickName))
    		 return false;
    	 else
    	 {
    		 this.registerdNickNames.add(nickName);
    		 return true;
    	 }
     }
     
     public CopyOnWriteArrayList<OnlineUser> getUsersInRoom(String roomName)
     {
    	 return rooms.get(roomName).getRoomates();
     }
     
     public boolean joinRoom(String roomToJoin, OnlineUser u) {
    	 if (rooms.containsKey(roomToJoin))
    	 {
    		 Room r = rooms.get(roomToJoin);
    		 if (r.isInGameNow())
    		 {
    			 return false;
    		 }
    		 rooms.get(roomToJoin).AddUserToRoom(u);
    	 }
    	 else
    	 {
    		 Room room = new Room(roomToJoin);
    		 room.AddUserToRoom(u);
    		 rooms.put(roomToJoin, room);
    	 }
    	 return true;
     }
     
     public boolean leaveRoom(String lastRoom, String nick)
     {
    	 Room lastR = rooms.get(lastRoom);
    	 if (lastR.isInGameNow())
    	 {
    		 return false;
    	 }
    	 rooms.get(lastRoom).DeleteFromRoom(nick);
    	 return true;
     }
}
