package Framework;

public class BlufferPlayer {

	String nickName;
	String playerAns;
	int score;
	
	public BlufferPlayer(String nickName) {
		this.nickName = nickName;
		this.score = 0;
	}
	
	public void addToScore(int points)
	{
		this.score += points;
	}
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	

	public String getPlayerAns() {
		return playerAns;
	}
	public void setPlayerAns(String playerAns) {
		this.playerAns = playerAns;
	}
}
