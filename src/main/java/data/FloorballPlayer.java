package data;

public class FloorballPlayer {
	private int id;
	private String player;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getPlayer() {
		return player;
	}
	public void setPlayer(String player) {
		this.player = player;
	}
	public String toString() {
		return id+" "+player+"\n";
	}
}