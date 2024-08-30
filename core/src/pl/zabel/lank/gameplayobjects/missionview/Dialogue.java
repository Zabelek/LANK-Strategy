package pl.zabel.lank.gameplayobjects.missionview;

public class Dialogue {
	
	private String character;
	private String text;
	public Dialogue(String character, String text)
	{
		this.text = text;
		this.character = character;
		
	}
	public String getCharacter() {
		return character;
	}
	public void setCharacter(String character) {
		this.character = character;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
