import java.io.Serializable;

public class PokeSerializable implements Serializable{
	
	private String nickname = "";
	private String monster = "";
	private int candy = 0;
	
	public PokeSerializable() {
		this("", "", 0);
	}
	public PokeSerializable(String nickname, String monster, int candy){
		this.nickname = nickname;
		this.monster = monster;
		this.candy = candy;
	}
	
	public void setNickname(String new_nick){
		this.nickname = new_nick;
	}
	public void setMonster(String new_mon){
		this.monster = new_mon;
	}
	public void setCandy(int new_candy){
		this.candy = new_candy;
	}
	
	public String getNickname(){
		return this.nickname;
	}
	public String getMonster(){
		return this.monster;
	}
	public int getCandy(){
		return this.candy;
	}
	
}