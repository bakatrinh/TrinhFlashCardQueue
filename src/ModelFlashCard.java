import java.awt.Color;
import java.io.Serializable;

// A single FlashCard, contains strings for front and back of card, color of the card, and ID of the card
public class ModelFlashCard  implements Serializable {
	private Color _cardColor;
	private String _frontData;
	private String _backData;
	private int _ID;
	
	public ModelFlashCard(Color cardColor, int ID) {
		_frontData = "";
		_backData = "";
		_cardColor = cardColor;
		_ID = ID;
	}
	
	public void setID(int ID) {
		_ID = ID;
	}
	
	public int getID() {
		return _ID;
	}
	
	public void setFrontData(String frontData) {
		_frontData = frontData;
	}
	
	public String getFrontData() {
		return _frontData;
	}
	
	public void setBackData(String backData) {
		_backData = backData;
	}
	
	public String getBackData() {
		return _backData;
	}
	
	public void setColors(Color cardColor) {
		_cardColor = cardColor;
	}
	
	public Color getCardColor() {
		return _cardColor;
	}
}
