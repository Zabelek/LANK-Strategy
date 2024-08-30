package pl.zabel.lank.gameplayobjects.missionview;

public class DialogueSequence {

	private String character, text;
	
	public DialogueSequence(String character, String text)
	{
		this.character = character;
		this.text = text;
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
