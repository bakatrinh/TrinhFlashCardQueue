import java.io.Serializable;


/**
 * @author Trinh Nguyen
 * @Discription Stores three FlashCardDecks that is used to
 * represent the data stored to the user.
 * This class will be what is stored as a file to
 * be loaded back later
 */
public class ModelFlashCardAllDecks implements Serializable {
	
	/**
	 * The main deck, stores all created card in order. _workingDeck copies
	 * all the cards from here when the user wants to start over
	 */
	private ModelFlashCardDeck _mainDeck;
	/**
	 * A changeable deck that stores created card that is
	 * also stored in main deck. Cards here can be dequeued
	 * and enqueued for displaying and moving back and forth
	 * between _memorizedDeck
	 */
	private ModelFlashCardDeck _workingDeck;
	/**
	 * A changeable deck that allows the user to move cards from _workingDeck
	 * into. The user can also move cards out of here back into _workingDeck
	 */
	private ModelFlashCardDeck _memorizedDeck;

	/**
	 * Constructor, makes a new instance for all three decks
	 */
	public ModelFlashCardAllDecks() {
		_mainDeck = new ModelFlashCardDeck();
		_memorizedDeck = new ModelFlashCardDeck();
		_workingDeck = new ModelFlashCardDeck();
	}
	
	/**
	 * Getter for _mainDeck
	 * @return Returns _mainDeck
	 */
	public ModelFlashCardDeck getMainDeck() {
		return _mainDeck;
	}
	
	/**
	 * Getter for _workingDeck
	 * @return Returns _workingDeck
	 */
	public ModelFlashCardDeck getWorkingDeck() {
		return _workingDeck;
	}
	
	/**
	 * Getter for _memorizedDeck
	 * @return Returns _memorizedDeck
	 */
	public ModelFlashCardDeck getMemorizedDeck() {
		return _memorizedDeck;
	}
}
