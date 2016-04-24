import java.io.Serializable;

// Class that stores multiple FlashCard objects, the cards are managed using the Queue model
// Cards are added, removed, and manipulated using queue and dequeue methods.
public class ModelFlashCardDeck implements Serializable {
	private static final int DEFAULT_CAPACITY = 10;
	private ModelFlashCard[] _deck;
	private int _cardCounter;
	private int _cardID;

	public ModelFlashCardDeck() {
		_deck = new ModelFlashCard[DEFAULT_CAPACITY];
		_cardCounter = 0;
		_cardID = 0;
	}
	
	// Used by other classes to check if the current deck is empty
	public boolean isEmpty() {
		if (_cardCounter == 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	// Used to add new cards to the deck, also increases memory when needed
	public void enqueue(ModelFlashCard card) {
		if (_cardCounter == _deck.length) {
			increaseCapacity(_cardCounter * 2);
		}
		_deck[_cardCounter] = card;
		_cardCounter++;
	}
	
	// Used by other classes to check the contents of the card first in line
	public ModelFlashCard peek() {
		return _deck[0];
	}

	// Used to create actual new cards
	public void enqueueWithID(ModelFlashCard card) {
		if (_cardCounter == _deck.length) {
			increaseCapacity(_cardCounter * 2);
		}
		_deck[_cardCounter] = card;
		_cardCounter++;
		_cardID++;
	}
	
	// Used to remove the card in the front of the line
	public ModelFlashCard dequeue() {
		ModelFlashCard temp = _deck[0];
		shiftDown();
		return temp;
	}
	
	// Called to shift all the FlashCards up whenever a card is dequeued
	private void shiftDown() {
		for (int i = 0; i < _cardCounter-1; i++) {
			_deck[i] = _deck[i+1];
		}
		_cardCounter--;
	}
	
	// Increases the memory of the deck to allow the user to store more cards
	private void increaseCapacity(int size) {
		ModelFlashCard[] old = _deck;
		_deck = new ModelFlashCard[size];
		for (int i = 0; i < old.length; i++)
			_deck[i] = old[i];
	}
	
	// Returns the number of cards in the current deck
	public int getCardCounter() {
		return _cardCounter;
	}
	
	// Returns the ID of the card
	public int getCardID() {
		return _cardID;
	}
	
	// Empties the deck by dequeue-ing every FlashCard
	public void removeAll() {
		while (_cardCounter > 0) {
			dequeue();
		}
	}
	
	// Used by other classes to make enqueue the current card and dequeue until it is back to the beginning
	public void enqueueBacktoCurrent() {
		int cardCounter = _cardCounter;
		cardCounter--;
		cardCounter--;
		cardCounter--;
		for (; cardCounter > 0; cardCounter--) {
			ModelFlashCard temp = dequeue();
			enqueue(temp);
		}
	}
	
	// Used by other classes to make enqueue the current card and dequeue until it is the card after next in line
	public void enqueueBacktoNext() {
		int cardCounter = _cardCounter;
		cardCounter--;
		for (; cardCounter > 0; cardCounter--) {
			ModelFlashCard temp = dequeue();
			enqueue(temp);
		}
	}
	
	// Used by other classes to make enqueue the current card and dequeue until it is the next card in line
	public void enqueueBacktoFirst() {
		int cardCounter = _cardCounter;
		cardCounter--;
		cardCounter--;
		for (; cardCounter > 0; cardCounter--) {
			ModelFlashCard temp = dequeue();
			enqueue(temp);
		}
	}
	
	// Used to move the card from one deck to another
	public void transferCards(ModelFlashCardDeck other) {
		while (!isEmpty()) {
			ModelFlashCard temp = dequeue();
			other.enqueue(temp);
		}
	}
	
	// When a card data is updated, this method cycles through the entire deck and update the deck on the
	// corresponding card ID
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
	
	// Deletes the card with the matching ID and then returns to the card the user was previously on
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
	
	// Used to copy the content of one FlashCardDeck to another
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
