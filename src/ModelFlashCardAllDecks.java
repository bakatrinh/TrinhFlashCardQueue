import java.io.Serializable;


/**
 * @author Trinh Nguyen
 * <br>Description: Stores three FlashCardDecks that is used to
 * represent the data stored to the user.
 * This class will be what is stored as a file to
 * be loaded back later.
 */
public class ModelFlashCardAllDecks implements Serializable {
	
	/**
	 * The complete deck, stores all created card in order. _completeDeck copies
	 * all the cards stored here to the main deck when a user clicks the reset button.
	 */
	private ModelFlashCardDeck _completeDeck;
	/**
	 * A changeable deck that stores created card that is
	 * also stored in main deck. Cards here can be dequeued
	 * and enqueued for displaying and moving back and forth.
	 * between _memorizedDeck and this.
	 */
	private ModelFlashCardDeck _mainDeck;
	/**
	 * A changeable deck that allows the user to move cards from _mainDeck
	 * into this. The user can also move cards out of here back into _mainDeck
	 */
	private ModelFlashCardDeck _memorizedDeck;

	/**
	 * Constructor, makes a new instance for all three decks.
	 * <br>Complexity: O(1)
	 */
	public ModelFlashCardAllDecks() {
		_completeDeck = new ModelFlashCardDeck();
		_memorizedDeck = new ModelFlashCardDeck();
		_mainDeck = new ModelFlashCardDeck();
	}
	
	/**
	 * Getter for _mainDeck.
	 * <br>Complexity: O(1)
	 * @return Returns _mainDeck
	 */
	public ModelFlashCardDeck getCompleteDeck() {
		return _completeDeck;
	}
	
	/**
	 * Getter for _mainDeck
	 * <br>Complexity: O(1)
	 * @return Returns _mainDeck
	 */
	public ModelFlashCardDeck getMainDeck() {
		return _mainDeck;
	}
	
	/**
	 * Getter for _memorizedDeck
	 * <br>Complexity: O(1)
	 * @return Returns _memorizedDeck
	 */
	public ModelFlashCardDeck getMemorizedDeck() {
		return _memorizedDeck;
	}
}
