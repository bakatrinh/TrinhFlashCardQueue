import java.awt.Color;
import java.io.Serializable;


/**
 * @author Trinh Nguyen
 * @Discription A single FlashCard, contains strings for front and back of card,
 * color of the card, and ID of the card
 */
public class ModelFlashCard  implements Serializable {
	/**
	 * The color of the card
	 */
	private Color _cardColor;
	/**
	 * The String of the front of the card
	 */
	private String _frontData;
	/**
	 * The String of the back of the card
	 */
	private String _backData;
	/**
	 * The ID of the card, assigned by the deck it
	 * was added to. Used for finding and deletion.
	 * Can never be changed once assigned.
	 */
	private final int _ID;
	
	/**
	 * Constructor, creates a blank card with an assigned color
	 * and ID
	 * @param cardColor The color the card will be displayed as
	 * @param ID The ID of the card at the time it is created
	 */
	public ModelFlashCard(Color cardColor, int ID) {
		_frontData = "";
		_backData = "";
		_cardColor = cardColor;
		_ID = ID;
	}
	
	/**
	 * Getter for card ID
	 * @return Returns current card ID
	 */
	public int getID() {
		return _ID;
	}
	
	/**
	 * Setter for the String data of the front of the card
	 * @param frontData The string that the _frontData will
	 * be changed to
	 */
	public void setFrontData(String frontData) {
		_frontData = frontData;
	}
	
	/**
	 * Getter for the String of the data of the front of the card
	 * @return Returns _frontData
	 */
	public String getFrontData() {
		return _frontData;
	}
	
	/**
	 * Setter for the String data of the back of the card
	 * @param backData The string that the _backData will
	 * be changed to
	 */
	public void setBackData(String backData) {
		_backData = backData;
	}
	
	/**
	 * Getter for the String of the data of the front of the card
	 * @return Returns _backData
	 */
	public String getBackData() {
		return _backData;
	}
	
	/**
	 * Setter for the color of the card
	 * @param cardColor The color that _cardColor will be
	 * changed to
	 */
	public void setColors(Color cardColor) {
		_cardColor = cardColor;
	}
	
	/**
	 * Getter for the color of the card
	 * @return Returns _cardColor
	 */
	public Color getCardColor() {
		return _cardColor;
	}
}
