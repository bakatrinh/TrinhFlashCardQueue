import java.io.Serializable;

// Stores three FlashCardDecks that is used to represent the data stored to the user
public class ModelFlashCardAllDecks implements Serializable {
private ModelFlashCardDeck _mainDeck;
private ModelFlashCardDeck _workingDeck;
private ModelFlashCardDeck _memorizedDeck;

public ModelFlashCardAllDecks() {
	_mainDeck = new ModelFlashCardDeck();
	_memorizedDeck = new ModelFlashCardDeck();
	_workingDeck = new ModelFlashCardDeck();
}

public ModelFlashCardDeck getMainDeck() {
	return _mainDeck;
}

public ModelFlashCardDeck getMemorizedDeck() {
	return _memorizedDeck;
}

public ModelFlashCardDeck getWorkingDeck() {
	return _workingDeck;
}

}
