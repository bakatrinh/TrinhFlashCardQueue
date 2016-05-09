import java.io.Serializable;


/**
 * @author Trinh Nguyen
 * <br>Description: Class that stores multiple FlashCard objects, the cards are managed using the Queue model Cards
 * are added, removed, and manipulated using queue and dequeue methods.
 */
public class ModelFlashCardDeck implements Serializable {
	/**
	 * Initial memory size of new array deck.
	 */
	private static final int DEFAULT_CAPACITY = 10;
	/**
	 * The array used to store all single flash cards.
	 */
	private ModelFlashCard[] _deck;
	/**
	 * The total counts currently stored in _deck.
	 */
	private int _cardCounter;
	/**
	 * The current ID that was last assigned to a newly created
	 * card. This value will never decrement since it is
	 * required for card searching and deletion. There
	 * cannot be two cards with the same ID.
	 */
	private int _cardID;
	
	/**
	 * The constructor. Creates a new _deck array with default
	 * memory capacity and sets _cardCounter and _cardID to 0.
	 * <br>Complexity: O(1)
	 */
	public ModelFlashCardDeck() {
		_deck = new ModelFlashCard[DEFAULT_CAPACITY];
		_cardCounter = 0;
		_cardID = 0;
	}
	
	/**
	 * Used by other classes to check if the current deck is empty.
	 * <br>Complexity: O(1)
	 * @return returns true if _cardCounter is 0, else returns false
	 */
	public boolean isEmpty() {
		if (_cardCounter == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Used to add new cards to the deck or to put back in old cards,
	 * also increases memory when needed.
	 * <br>Complexity: Amortized analysis of O(1). O(N) if resizing occurs
	 * @param card The card to be enqueued
	 */
	public void enqueue(ModelFlashCard card) {
		if (_cardCounter == _deck.length) {
			increaseCapacity(_cardCounter * 2);
		}
		_deck[_cardCounter] = card;
		_cardCounter++;
	}
	
	/**
	 * Used by other classes to check the contents of the card first in line
	 * without dequeuing.
	 * <br>Complexity: O(1).
	 * @return Returns _deck[0]
	 */
	public ModelFlashCard peek() {
		return _deck[0];
	}

	/**
	 * Used to create actual new cards by incrementing _cardID.
	 * <br>Complexity: Amortized analysis of O(1). O(N) if resizing occurs
	 * @param The card to be added to the deck
	 */
	public void enqueueWithID(ModelFlashCard card) {
		if (_cardCounter == _deck.length) {
			increaseCapacity(_cardCounter * 2);
		}
		_deck[_cardCounter] = card;
		_cardCounter++;
		_cardID++;
	}
	
	/**
	 * Used to remove the card in the front of the line.
	 * <br>Complexity: O(N)
	 * @return Returns the card that was removed in case
	 * it is currently being used to display on the GUI
	 */
	public ModelFlashCard dequeue() {
		ModelFlashCard temp = _deck[0];
		shiftDown();
		return temp;
	}
	
	/**
	 * Called to shift all the FlashCards up whenever a card is dequeued.
	 * <br>Complexity: O(N)
	 */
	private void shiftDown() {
		for (int i = 0; i < _cardCounter-1; i++) {
			_deck[i] = _deck[i+1];
		}
		_cardCounter--;
	}
	
	/**
	 * Increases the memory of the deck to allow the user to store additional cards.
	 * <br>Complexity: O(N)
	 * @param size
	 */
	private void increaseCapacity(int size) {
		ModelFlashCard[] old = _deck;
		_deck = new ModelFlashCard[size];
		for (int i = 0; i < old.length; i++)
			_deck[i] = old[i];
	}
	
	/**
	 * Returns the number of cards in the current deck.
	 * <br>Complexity: O(1)
	 * @return Returns _cardCounter
	 */
	public int getCardCounter() {
		return _cardCounter;
	}
	
	/**
	 * Returns the ID of the card
	 * <br>Complexity: O(1)
	 * @return Returns _cardID
	 */
	public int getCardID() {
		return _cardID;
	}
	
	/**
	 * Empties the deck by dequeuing every FlashCard. The deck still exist
	 * in memory in case more cards are being added.
	 * <br>Complexity: O(N)
	 */
	public void removeAll() {
		while (_cardCounter > 0) {
			dequeue();
		}
	}
	
	/**
	 * Used by other classes to make enqueue the current card and
	 * dequeue until it is the card after next in line. Often used
	 * when switching between main deck and memorized deck.
	 * <br>Complexity: O(N)
	 */
	public void enqueueBacktoNext() {
		int cardCounter = _cardCounter - 1;
		for (; cardCounter > 0; cardCounter--) {
			ModelFlashCard temp = dequeue();
			enqueue(temp);
		}
	}
	
	/**
	 * Used by other classes to enqueue until 
	 * the next card is the previous card of the current card
	 * Used by the previous card JButton.
	 * <br>Complexity: O(N)
	 */
	public void enqueueBacktoPrevious() {
		int cardCounter = _cardCounter - 2;
		for (; cardCounter > 0; cardCounter--) {
			ModelFlashCard temp = dequeue();
			enqueue(temp);
		}
	}
	
	/**
	 * Used to move the card from one deck to another.
	 * <br>Complexity: O(N)
	 * @param other The deck the current card will be transferred to
	 */
	public void transferCards(ModelFlashCardDeck other) {
		while (!isEmpty()) {
			ModelFlashCard temp = dequeue();
			other.enqueue(temp);
		}
	}
	
	/**
	 * When a card data is updated, this method cycles through the entire deck and
	 * update the deck on the corresponding card ID.
	 * <br>Complexity: O(N)
	 * @param card The card that was updated and to be saved onto the deck
	 */
	public void replaceCard(ModelFlashCard card) {
		int cardCounter = _cardCounter;
		ModelFlashCard current;
		for (int i = 0; i < cardCounter; i++) {
			current = dequeue();
			if (current.getID() == card.getID()) {
				current = card;
			}
			enqueue(current);
		}
	}
	
	/**
	 * Deletes the card with the matching ID and then returns to the card the user was previously on.
	 * Used on the complete deck to delete a specific card.
	 * <br>Complexity: O(N)
	 * @param ID The ID of the card to be deleted
	 */
	public void deleteCard(int ID) {
		int cardCounter = _cardCounter;
		ModelFlashCard current;
		for (int i = 0; i < cardCounter; i++) {
			current = dequeue();
			if (current.getID() != ID) {
				enqueue(current);
			}
		}
	}
	
	/**
	 * Used to copy the content of one FlashCardDeck to another.
	 * <br>Complexity: O(N)
	 * @param otherDeck The deck that is being changed by having contents
	 * copied onto it
	 */
	public void copyDeck(ModelFlashCardDeck otherDeck) {
		removeAll();
		int otherDeckCardCounter = otherDeck.getCardCounter();
		for (; otherDeckCardCounter > 0; otherDeckCardCounter--) {
			ModelFlashCard temp = otherDeck.dequeue();
			enqueue(temp);
			otherDeck.enqueue(temp);
		}
	}
}
