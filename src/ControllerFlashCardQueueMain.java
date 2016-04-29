import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.commons.io.FilenameUtils;

/**
 * @author Trinh Nguyen
 * @version 1.0
 * 
 * @Discription controller class that controls the navigation, addition, editing, saving,
 * and organization f the Flash Card data
 */
public class ControllerFlashCardQueueMain {

	/**
	 * Stores all the flash card decks used in the program. This is what gets saved and loaded from file
	 */
	private ModelFlashCardAllDecks _allDecks;
	/**
	 * Stores the data of the current card being displayed
	 */
	private ModelFlashCard _currentCard;
	/**
	 * The main GUI that controls all visual components
	 */
	private ViewMainScreenJPanel _mainScreenJPanel;
	/**
	 * Holds old card text data, reverts to this if cancel button is pushed during editing
	 */
	private String _tempOldText;
	/**
	 * Holds old card color data, reverts to this if cancel button is pushed during editing
	 */
	private Color _tempOldColor;
	/**
	 * Holds the color that all new cards will be created by if _colorLock is true
	 */
	private Color _chosenColor;
	/**
	 * Stores the file being opened or saved to
	 */
	private File _file;
	/**
	 * Returns true if the card is currently showing data of the front of the card
	 */
	private boolean _front;
	/**
	 * Returns true if the user is currently viewing the working deck and not the memorized deck
	 */
	private boolean _viewingWorkingDeck;
	/**
	 * Returns true if the user made some changes to the deck. Used by different methods to
	 * confirm to the user if they want to save.
	 */
	private boolean _isChanged;
	/**
	 * Returns true if the deck is currently a new deck and was not loaded from a file
	 */
	private boolean _newFile;
	/**
	 * Returns true if the user wants all new cards created to have the same color
	 */
	private boolean _colorLock;
	/**
	 * Used by the editing JDialog window to determine when to show text for front or back of _currentCard
	 */
	private boolean _editFront;
	/**
	 * Returns true if there is a message or window currently opened. Used to
	 * disable hotkeys
	 */
	private boolean _popUpMessages;
	/**
	 * Constructor, sets all attributes to default values. Creates blank default decks
	 * for storing data and the GUI to display the information
	 * @param mainJFrame reference to the main JFrame that will be storing all GUI components
	 */
	public ControllerFlashCardQueueMain(JFrame mainJFrame) {
		_allDecks = new ModelFlashCardAllDecks();
		_mainScreenJPanel = new ViewMainScreenJPanel(this, mainJFrame);
		String OS = System.getProperty("os.name").toLowerCase();
		if (OS.contains("mac")) {
			HelperAppleQuitHandler.DoAppleQuit(this);
		}
		_currentCard = null;
		_front = true;
		_viewingWorkingDeck = true;
		_colorLock = false;
		_isChanged = false;
		_newFile = true;
		_editFront = true;
		_popUpMessages = false;

		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		.addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getID() == KeyEvent.KEY_RELEASED) {
					if (!_mainScreenJPanel.getEditCardJDialog().isVisible() && !_popUpMessages)
					{
						if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
							nextCard();
							return false;
						}
						if (e.getKeyCode() == KeyEvent.VK_SPACE) {
							flip();
							return false;
						}
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							popUpEditJDialog();
							return false;
						}
						if (e.getKeyCode() == KeyEvent.VK_TAB) {
							if (_viewingWorkingDeck) {
								switchToMemorized();
							}
							else {
								switchToWorkingDeck();
							}
							return false;
						}
						if (e.getKeyCode() == KeyEvent.VK_N) {
							if (_viewingWorkingDeck) {
								addNewCardMainScreen();
							}
							return false;
						}
						if (e.getKeyCode() == KeyEvent.VK_M) {
							moveCurrentCardToOrFromMain();
							return false;
						}
					}
					else {
						if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
							cancelButton();
							return false;
						}
						
					}
				}
				return false;
			}
		});
	}

	/**
	 * Used by controller to dequeue the deck and display that card
	 */
	public void nextCard() {
		if (_viewingWorkingDeck) {
			if (!_allDecks.getWorkingDeck().isEmpty()) {
				_mainScreenJPanel.getBtnNext().setEnabled(true);
				_mainScreenJPanel.getBtnFlip().setEnabled(true);
				if (_currentCard != null) {
					_allDecks.getWorkingDeck().enqueue(_currentCard);
				}
				_currentCard = null;
				_currentCard = _allDecks.getWorkingDeck().dequeue();
				_mainScreenJPanel.getFlashCardPanel().drawNormalCard(_currentCard.getCardColor(), _currentCard.getFrontData(), _currentCard.getID());
			}
			else {
				if (_currentCard == null)
				{
					_mainScreenJPanel.getFlashCardPanel().drawEmpty();
					_mainScreenJPanel.getBtnNext().setEnabled(false);
					_mainScreenJPanel.getBtnFlip().setEnabled(false);
				}
			}
			_mainScreenJPanel.getWorkingDeckIconJPanel().updateDeckStatusRepaint(_allDecks.getWorkingDeck(), _currentCard);
			_mainScreenJPanel.getMemorizedDeckIconJPanel().updateDeckStatusRepaint(_allDecks.getMemorizedDeck(), null);
		}
		else {
			if (!_allDecks.getMemorizedDeck().isEmpty()) {
				_mainScreenJPanel.getBtnNext().setEnabled(true);
				_mainScreenJPanel.getBtnFlip().setEnabled(true);
				if (_currentCard != null) {
					_allDecks.getMemorizedDeck().enqueue(_currentCard);
				}
				_currentCard = null;
				_currentCard = _allDecks.getMemorizedDeck().dequeue();
				_mainScreenJPanel.getFlashCardPanel().drawNormalCard(_currentCard.getCardColor(), _currentCard.getFrontData(), _currentCard.getID());
			}
			else {
				if (_currentCard == null)
				{
					_mainScreenJPanel.getFlashCardPanel().drawEmpty();
					_mainScreenJPanel.getBtnNext().setEnabled(false);
					_mainScreenJPanel.getBtnFlip().setEnabled(false);
				}
			}
			_mainScreenJPanel.getWorkingDeckIconJPanel().updateDeckStatusRepaint(_allDecks.getWorkingDeck(), null);
			_mainScreenJPanel.getMemorizedDeckIconJPanel().updateDeckStatusRepaint(_allDecks.getMemorizedDeck(), _currentCard);
		}
		if (_currentCard != null) {
			resetEditCardWindow();
		}
		_front = true;
		refreshButtonsAndCounters();
		setFrameTitleUnsaved();
		_isChanged = true;
	}

	/**
	 * Used by other classes to update the card in the main deck with new changes
	 */
	public void updateCard() {
		_allDecks.getMainDeck().replaceCard(_currentCard);
	}

	/**
	 * Empties the Memorized Deck and Working Deck and recopies all cards from the Main Deck
	 */
	public void resetDeck() {
		_popUpMessages = true;
		int response = JOptionPane.showConfirmDialog(null, "Restore all memorized cards back to Main Deck and sort cards in order by ID?", "",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.NO_OPTION) {
			_popUpMessages = false;
		} else if (response == JOptionPane.YES_OPTION) {
			_allDecks.getWorkingDeck().copyDeck(_allDecks.getMainDeck());
			_allDecks.getMemorizedDeck().removeAll();
			_currentCard = null;
			_mainScreenJPanel.getBtnMoveToMemorized().setText("Memorized");
			_mainScreenJPanel.getWorkingDeckIconJPanel().setSelected(true);
			_mainScreenJPanel.getMemorizedDeckIconJPanel().setSelected(false);
			_viewingWorkingDeck = true;
			_mainScreenJPanel.getBtnNewCard().setEnabled(true);
			_isChanged = true;
			setFrameTitleUnsaved();
			nextCard();
			if (_currentCard != null) {
				resetEditCardWindow();
			}
		}
		else if (response == JOptionPane.CLOSED_OPTION) {
			
		}
		_popUpMessages = true;
	}

	/**
	 * Used by FlashCardJDialog when the user hits the cancel button. This restores the data as it was before
	 * Any changes was made
	 */
	public void cancelEditChanges() {
		if (!_isChanged) {
			_currentCard.setFrontData(_tempOldText);
			_currentCard.setColors(_tempOldColor);
			enqueueBackToCurrent();
			_mainScreenJPanel.getFlashCardPanel().setColor(_tempOldColor);
			_mainScreenJPanel.getFlashCardPanel().setText(_currentCard.getFrontData(), true, _currentCard.getID());
			_isChanged = false;
			if (_file != null) {
				_mainScreenJPanel.getMainJFrame().setTitle(_file.getName());
			}
			else {
				_mainScreenJPanel.getMainJFrame().setTitle("Untitled Deck");
			}
		}
		else {
			_currentCard.setFrontData(_tempOldText);
			_currentCard.setColors(_tempOldColor);
			enqueueBackToCurrent();
			_mainScreenJPanel.getFlashCardPanel().setColor(_tempOldColor);
			_mainScreenJPanel.getFlashCardPanel().setText(_currentCard.getFrontData(), true, _currentCard.getID());
			_isChanged = true;
			setFrameTitleUnsaved();
		}
		_front = true;
	}

	/**
	 * Used by other methods and class to give access to the current card being shown to the user
	 * @return Returns the current card that is being shown to the user
	 */
	public ModelFlashCard getCard() {
		return _currentCard;
	}

	/**
	 * Permanently removes the card from the Main deck and Working deck
	 */
	public void deleteFlashCard() {
		int deleteID = _currentCard.getID();
		_currentCard = null;
		_allDecks.getMainDeck().deleteCard(deleteID);
		_isChanged = true;
		setFrameTitleUnsaved();
		nextCard();
	}

	/**
	 * Makes the editing JDialog visible with correct data based on the deck being worked on
	 */
	public void popUpEditJDialog() {
		if (_currentCard != null) {
			resetEditCardWindow();
			_tempOldText = _currentCard.getFrontData();
			_tempOldColor = _currentCard.getCardColor();
			_mainScreenJPanel.getEditCardJDialog().setVisible(true);
			resetEditCardWindow();
		}
	}

	/**
	 * Flips the card
	 */
	public void flip() {
		if (_currentCard != null) {
			if (_front) {
				_mainScreenJPanel.getFlashCardPanel().setText(_currentCard.getBackData(), false, _currentCard.getID());
				_front = false;
			}
			else {
				_mainScreenJPanel.getFlashCardPanel().setText(_currentCard.getFrontData(), true, _currentCard.getID());
				_front = true;
			}
		}
	}

	/**
	 * Used by other methods to tell the program if new cards will have the same color
	 * @param isColorLock Returns true if the "same color for new card" check box is checked, else return false
	 */
	public void setColorLock(boolean isColorLock) {
		_colorLock = isColorLock;
	}

	/**
	 * Used to enqueue the current card until it is back to the current card being displaeyed
	 */
	public void enqueueBackToCurrent() {
		if (_viewingWorkingDeck) {
			_allDecks.getWorkingDeck().enqueue(_currentCard);
			_currentCard = null;
			_allDecks.getWorkingDeck().enqueueBacktoNext();
		}
		else {
			_allDecks.getMemorizedDeck().enqueue(_currentCard);
			_currentCard = null;
			_allDecks.getMemorizedDeck().enqueueBacktoNext();
		}
		_currentCard = null;
		nextCard();
	}

	/**
	 * Used to make the current card enqueue until it is the next next one in line
	 */
	public void enqueueBackToNext() {
		_allDecks.getWorkingDeck().enqueueBacktoNext();
		nextCard();
	}

	/**
	 * Used to make the current card enqueue until it is the next in line
	 */
	public void enqueueBackToFirst() {
		_allDecks.getWorkingDeck().enqueueBacktoFirst();
		nextCard();
	}

	/**
	 * Used by fileOpener to set the current deck to the one chosen. Once deck is loaded, nextCard() is
	 * automatically called
	 * @param newDeck reference to the file that contains the deck the user wants to load
	 */
	public void newDeck(ModelFlashCardAllDecks newDeck) {
		_allDecks = newDeck;
		_currentCard = null;
		nextCard();
	}

	/**
	 * Used to restore color to what the card color would be if it was not locked
	 * @return Returns a color from HelperCardColors based on the index of the main deck
	 */
	public Color unlockColor() {
		return new HelperCardColors().getColor(_allDecks.getMainDeck().getCardCounter()-1);
	}

	/**
	 * Used to create new cards and update the FlashCardJPanel to that of the new card
	 */
	public void quickAddNewCard() {
		addNewCardMainScreen();
		_tempOldText = _currentCard.getFrontData();
		_tempOldColor = _currentCard.getCardColor();
	}

	/**
	 * Used by other methods and class to determine if changes were made
	 * @param isChanged if the current deck has been changed, make isChanged = true,
	 * else isChanged = false
	 */
	public void setIsChanged(boolean isChanged) {
		_isChanged = isChanged;
	}

	/**
	 * Returns true if the deck has been changed
	 * @return returns true if the deck has been modified, else returns false
	 */
	public boolean getIsChanged() {
		return _isChanged;
	}

	/**
	 * Adds a new card and sets the appropriate data
	 */
	public void addNewCardMainScreen() {
		if (_currentCard != null) {
			_allDecks.getWorkingDeck().enqueue(_currentCard);
		}
		_currentCard = null;

		if (!_colorLock) {
			_currentCard = new ModelFlashCard(new HelperCardColors().getColor(_allDecks.getMainDeck().getCardCounter()), _allDecks.getMainDeck().getCardID());
		}
		else {
			_currentCard = new ModelFlashCard(_chosenColor, _allDecks.getMainDeck().getCardID());
		}
		_currentCard.setFrontData("New Card #"+(_currentCard.getID()+1)+" (front)<br /><br />Right Click to Edit");
		_currentCard.setBackData("New Card #"+(_currentCard.getID()+1)+" (back)<br /><br />Right Click to Edit");
		_allDecks.getMainDeck().enqueueWithID(_currentCard);
		_allDecks.getWorkingDeck().enqueueWithID(_currentCard);
		_currentCard = null;
		_allDecks.getWorkingDeck().enqueueBacktoNext();
		_isChanged = true;
		setFrameTitleUnsaved();
		nextCard();
		if (_currentCard != null) {
			resetEditCardWindow();
		}
	}

	/**
	 * Adds a "*" to the title if changes were made and not saved
	 */
	public void setFrameTitleUnsaved() {
		if (!_mainScreenJPanel.getMainJFrame().getTitle().contains("*")) {
			_mainScreenJPanel.getMainJFrame().setTitle(_mainScreenJPanel.getMainJFrame().getTitle()+"*");
		}
	}

	/**
	 * Dequeue all the cards in memorized deck and move them over to the working deck
	 */
	public void restore() {
		_popUpMessages = true;
		int response = JOptionPane.showConfirmDialog(null, "Move all memorized cards in the Memorized Deck back into Main Deck?", "",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.NO_OPTION) {

		} else if (response == JOptionPane.YES_OPTION) {
			if (_viewingWorkingDeck) {
				_allDecks.getMemorizedDeck().transferCards(_allDecks.getWorkingDeck());
				if (_currentCard == null) {
					nextCard();
				}
				_mainScreenJPanel.getWorkingDeckIconJPanel().updateDeckStatusRepaint(_allDecks.getWorkingDeck(), _currentCard);
				_mainScreenJPanel.getMemorizedDeckIconJPanel().updateDeckStatusRepaint(_allDecks.getMemorizedDeck(), null);
				refreshButtonsAndCounters();
			}
			else {
				if (_currentCard != null) {
					_allDecks.getMemorizedDeck().enqueue(_currentCard);
					_currentCard = null;
				}
				_allDecks.getMemorizedDeck().transferCards(_allDecks.getWorkingDeck());
				nextCard();
				_mainScreenJPanel.getWorkingDeckIconJPanel().updateDeckStatusRepaint(_allDecks.getWorkingDeck(), null);
				_mainScreenJPanel.getMemorizedDeckIconJPanel().updateDeckStatusRepaint(_allDecks.getMemorizedDeck(), null);
			}
			_isChanged = true;
		}
		else if (response == JOptionPane.CLOSED_OPTION) {

		}
		_popUpMessages = false;
	}

	/**
	 * Called every time the user clicks on the Memorized Deck pile
	 */
	public void switchToMemorized() {
		if (_viewingWorkingDeck) {
			_mainScreenJPanel.getBtnMoveToMemorized().setText("Not Memorized");
			if (_currentCard != null) {
				_allDecks.getWorkingDeck().enqueue(_currentCard);
			}
			_allDecks.getWorkingDeck().enqueueBacktoNext();
			_currentCard = null;
			_mainScreenJPanel.getWorkingDeckIconJPanel().setSelected(false);
			_mainScreenJPanel.getMemorizedDeckIconJPanel().setSelected(true);
			_viewingWorkingDeck = false;
			_mainScreenJPanel.getBtnNewCard().setEnabled(false);
			nextCard();
		}
	}

	/**
	 * Called every time the user clicks on the working deck pile
	 */
	public void switchToWorkingDeck() {
		if (!_viewingWorkingDeck) {
			_mainScreenJPanel.getBtnMoveToMemorized().setText("Memorized");
			if (_currentCard != null) {
				_allDecks.getMemorizedDeck().enqueue(_currentCard);
			}
			_allDecks.getMemorizedDeck().enqueueBacktoNext();
			_currentCard = null;
			_mainScreenJPanel.getWorkingDeckIconJPanel().setSelected(true);
			_mainScreenJPanel.getMemorizedDeckIconJPanel().setSelected(false);
			_viewingWorkingDeck = true;
			_mainScreenJPanel.getBtnNewCard().setEnabled(true);
			nextCard();
		}
	}

	/**
	 * Used to move cards into Memorized pile or out of it
	 */
	public void moveCurrentCardToOrFromMain() {
		if (_viewingWorkingDeck) {
			if (_currentCard != null) {
				_allDecks.getMemorizedDeck().enqueue(_currentCard);
				_currentCard = null;
			}
			nextCard();
		}
		else {
			if (_currentCard != null) {
				_allDecks.getWorkingDeck().enqueue(_currentCard);
				_currentCard = null;
			}
			nextCard();
		}
		_isChanged = true;
		setFrameTitleUnsaved();
	}

	/**
	 * Refreshes the buttons to enable or disable based on what the user is allowed to do.
	 * Updates different components to represent the Flash Card Deck data
	 */
	public void refreshButtonsAndCounters() {
		if (_viewingWorkingDeck) {
			if (_currentCard == null) {
				_mainScreenJPanel.getWorkingDeckIconJPanel().updateDeckIcon(_allDecks.getWorkingDeck().getCardCounter());
				_mainScreenJPanel.getMemorizedDeckIconJPanel().updateDeckIcon(_allDecks.getMemorizedDeck().getCardCounter());
				_mainScreenJPanel.getBtnMoveToMemorized().setEnabled(false);
				_mainScreenJPanel.getBtnEditCard().setEnabled(false);
			}
			else {
				_mainScreenJPanel.getWorkingDeckIconJPanel().updateDeckIcon(_allDecks.getWorkingDeck().getCardCounter()+1);
				_mainScreenJPanel.getMemorizedDeckIconJPanel().updateDeckIcon(_allDecks.getMemorizedDeck().getCardCounter());
				_mainScreenJPanel.getBtnMoveToMemorized().setEnabled(true);
				_mainScreenJPanel.getBtnEditCard().setEnabled(true);
			}
			if (_allDecks.getMemorizedDeck().isEmpty()) {
				_mainScreenJPanel.getBtnClearMemorized().setEnabled(false);
			}
			else {
				_mainScreenJPanel.getBtnClearMemorized().setEnabled(true);
			}
		}
		else {
			if (_currentCard == null) {
				_mainScreenJPanel.getWorkingDeckIconJPanel().updateDeckIcon(_allDecks.getWorkingDeck().getCardCounter());
				_mainScreenJPanel.getMemorizedDeckIconJPanel().updateDeckIcon(_allDecks.getMemorizedDeck().getCardCounter());
				_mainScreenJPanel.getBtnMoveToMemorized().setEnabled(false);
				_mainScreenJPanel.getBtnEditCard().setEnabled(false);
				if (_allDecks.getMemorizedDeck().isEmpty()) {
					_mainScreenJPanel.getBtnClearMemorized().setEnabled(false);
				}
				else {
					_mainScreenJPanel.getBtnClearMemorized().setEnabled(true);
				}
			}
			else {
				_mainScreenJPanel.getWorkingDeckIconJPanel().updateDeckIcon(_allDecks.getWorkingDeck().getCardCounter());
				_mainScreenJPanel.getMemorizedDeckIconJPanel().updateDeckIcon(_allDecks.getMemorizedDeck().getCardCounter()+1);
				_mainScreenJPanel.getBtnMoveToMemorized().setEnabled(true);
				_mainScreenJPanel.getBtnEditCard().setEnabled(true);
			}
		}
		if (_allDecks.getMainDeck().isEmpty()) {
			_mainScreenJPanel.getBtnReset().setEnabled(false);
		}
		else {
			_mainScreenJPanel.getBtnReset().setEnabled(true);
		}
	}

	/**
	 * Used by other classes and method to determine if the user is on the WorkingDeck
	 * @return returns True if the user is currently viewing the working deck, else returns False (user is viewing
	 * memorized deck)
	 */
	public boolean getIsViewingWorkingDeck() {
		return _viewingWorkingDeck;
	}

	/**
	 * Sets the current deck to the deck specified. If no deck (allDecks is null) is specified,
	 * then a new deck is made
	 * @param allDecks reference to the deck loaded by the File, if allDecks is null, a new deck is
	 * created instead.
	 */
	public void setNewDecks(ModelFlashCardAllDecks allDecks) {
		if (allDecks == null) {
			_allDecks = new ModelFlashCardAllDecks();
			newDeck(_allDecks);
		}
		else {
			newDeck(allDecks);
		}
		switchToWorkingDeck();
	}

	/**
	 * Makes a new deck, ask for confirmation if changes were made
	 */
	public void newDeck() {

		if (!_isChanged) {
			setNewDecks(null);
			_newFile = true;
			_isChanged = false;
			_mainScreenJPanel.getBtnRevertToFile().setEnabled(false);
			_mainScreenJPanel.getFlashCardPanel().drawTitle();
			_mainScreenJPanel.getMainJFrame().setTitle("Untitled Deck");
		}
		else {
			_popUpMessages = true;
			Object[] options = {"Save", "Don't Save", "Cancel"};
			int response = JOptionPane.showOptionDialog(null,
					"Save changes?","", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (response == JOptionPane.NO_OPTION) {
				setNewDecks(null);
				_newFile = true;
				_isChanged = false;
				_mainScreenJPanel.getBtnRevertToFile().setEnabled(false);
				_mainScreenJPanel.getFlashCardPanel().drawTitle();
				_mainScreenJPanel.getMainJFrame().setTitle("Untitled Deck");
			} else if (response == JOptionPane.YES_OPTION) {
				saveDeck();
				newDeck();
			}
			_popUpMessages = false;
		}


	}

	/**
	 * Opens the file that has deck data
	 */
	public void openDeck() {
		if (!_isChanged) {
			_popUpMessages = true;
			int returnVal = _mainScreenJPanel.getFileChooser().showOpenDialog(_mainScreenJPanel.getMainJFrame());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				_file = _mainScreenJPanel.getFileChooser().getSelectedFile();
				open();
			}
		}
		else {
			_popUpMessages = true;
			Object[] options = {"Save", "Don't Save", "Cancel"};
			int response = JOptionPane.showOptionDialog(null,
					"Save changes?","", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (response == JOptionPane.NO_OPTION) {
				int returnVal = _mainScreenJPanel.getFileChooser().showOpenDialog(_mainScreenJPanel.getMainJFrame());

				if (returnVal == JFileChooser.APPROVE_OPTION) {
					_file = _mainScreenJPanel.getFileChooser().getSelectedFile();
					open();
				}
			} else if (response == JOptionPane.YES_OPTION) {
				saveDeck();
				openDeck();
			}
			_popUpMessages = false;
		}
	}

	/**
	 * Actual open method used by OpenDeck and other methods
	 */
	public void open() {
		ModelFlashCardAllDecks newDeck = new ModelFlashCardAllDecks();
		ObjectInputStream inputStream = null;
		try {
			inputStream = new ObjectInputStream(new FileInputStream(_file));
			newDeck = (ModelFlashCardAllDecks) inputStream.readObject();
		}
		catch (EOFException eofException) {

		}
		catch (ClassNotFoundException classNotFoundException) {
			System.err.println("Object creation failed.");
		}
		catch (IOException ioException) {
			newDeck = null;
		}
		finally
		{
			try {
				if (inputStream != null)
					inputStream.close();
			}
			catch (IOException ioException) {
				System.err.println("Error closing file. (DeSerialize)");
				ioException.printStackTrace();
			}
		}
		if (newDeck != null) {
			setNewDecks(newDeck);
			_mainScreenJPanel.getBtnRevertToFile().setEnabled(true);
			nextCard();
			_newFile = false;
			_isChanged = false;
			if (_currentCard != null) {
				resetEditCardWindow();
			}
			_mainScreenJPanel.getMainJFrame().setTitle(_file.getName());
		}
	}

	/**
	 * Save or Save As the current deck
	 */
	public void saveDeck() {
		if (!_newFile) {
			save();
		}
		else {
			saveAsDeck();
		}
	}

	/**
	 * Main core saving method, called when there is an active file to save to
	 */
	public void save() {
		if (_viewingWorkingDeck) {
			if (_currentCard != null) {
				_allDecks.getWorkingDeck().enqueue(_currentCard);
			}
			_currentCard = null;
			_allDecks.getWorkingDeck().enqueueBacktoFirst();
		}
		else {
			if (_currentCard != null) {
				_allDecks.getMemorizedDeck().enqueue(_currentCard);
			}
			_currentCard = null;
			_allDecks.getMemorizedDeck().enqueueBacktoFirst();
		}

		ObjectOutputStream outStream = null;
		try
		{
			outStream = new ObjectOutputStream(new FileOutputStream(_file));
			outStream.writeObject(_allDecks);
		}
		catch (IOException ioException)
		{
			System.err.println("Error saving file. (Serialize)");
			ioException.printStackTrace();
		}
		finally
		{
			try
			{
				if (outStream != null)
					outStream.close();
			}
			catch (IOException ioException)
			{
				System.err.println("Error closing file. (Serialize)");
			}
		}
		_mainScreenJPanel.getBtnRevertToFile().setEnabled(true);
		nextCard();
		nextCard();
		_mainScreenJPanel.getMainJFrame().setTitle(_file.getName());
		_isChanged = false;
		_newFile = false;
	}

	/**
	 * Ask the user for the location of where they want to save
	 */
	public void saveAsDeck() {
		_popUpMessages = true;
		int returnVal = _mainScreenJPanel.getFileChooser().showSaveDialog(_mainScreenJPanel.getMainJFrame());
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			_file = _mainScreenJPanel.getFileChooser().getSelectedFile();
			if (FilenameUtils.getExtension(_file.getName()).equalsIgnoreCase("fcq")) {
				// filename is OK as-is
			} else {
				_file = new File(_file.toString() + ".fcq");
				_file = new File(_file.getParentFile(), FilenameUtils.getBaseName(_file.getName())+".fcq");
			}
			save();
		}
		_popUpMessages = false;
	}

	/**
	 * Make sure the use wants to save before exiting the program
	 */
	public void confirmExit() {
		if (!_isChanged) {
			System.exit(0);
		}
		else {
			_popUpMessages = true;
			Object[] options = {"Save", "Don't Save", "Cancel"};
			int response = JOptionPane.showOptionDialog(null,
					"Save changes?","", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (response == JOptionPane.NO_OPTION) {
				System.exit(0);
			} else if (response == JOptionPane.YES_OPTION) {
				saveDeck();
				confirmExit();
			}
			else {
				_popUpMessages = false;
				String OS = System.getProperty("os.name").toLowerCase();
				if (OS.contains("mac")) {

				}
			}
			_popUpMessages = false;
		}
	}
	
	/**
	 * Called when the user left or right clicks on the currently displaying Flash Card JPanel
	 * @param e if 'e' is a right mouse click, then popUpEditJDialog() is called, else (left mouse click) flip() is called
	 */
	public void flashCardPanelClicked(MouseEvent e) {
		if (_currentCard != null) {
			if (SwingUtilities.isRightMouseButton(e)) {
				popUpEditJDialog();
			}
			else {
				flip();
			}
		}
	}

	/**
	 * Used by outside classes to update the colors and text on the JDialog before making it visible
	 */
	public void resetEditCardWindow() {
		if (_currentCard != null) {
			if (getIsViewingWorkingDeck()) {
				_mainScreenJPanel.getEditCardJDialog().getBtnNewCard().setVisible(true);
				_mainScreenJPanel.getEditCardJDialog().getChckbxSameColorFor().setEnabled(true);
			}
			else {
				_mainScreenJPanel.getEditCardJDialog().getBtnNewCard().setVisible(false);
				_mainScreenJPanel.getEditCardJDialog().getChckbxSameColorFor().setEnabled(false);
			}
			_mainScreenJPanel.getEditCardJDialog().getBtnNewCard().setEnabled(false);
			_mainScreenJPanel.getEditCardJDialog().getBtnConfirm().setText("Edit Back");
			_chosenColor = _currentCard.getCardColor();
			setTextPane(convertFromCardString(_currentCard.getFrontData()));
			setTextPaneBackground();
			_editFront = true;
			JFrame frame = (JFrame)SwingUtilities.getAncestorOfClass(JFrame.class, _mainScreenJPanel.getEditCardJDialog());
			Rectangle r = frame.getBounds();
			int x = r.x + (r.width - _mainScreenJPanel.getEditCardJDialog().getSize().width)/2;
			int y = r.y + (r.height - _mainScreenJPanel.getEditCardJDialog().getSize().height)/2;
			_mainScreenJPanel.getEditCardJDialog().setLocation(x, y);
		}
	}

	/**
	 * Used to write out the string on the text pane if there are data or no data on the FlashCard
	 * currently being read
	 * @param text The string of what the TextPane of the edit JDialog will show
	 */
	private void setTextPane(String text) {
		if (text.equals("") || text.equals("Enter Text for Front") || text.equals("Enter Text for Back")) {
			if (_editFront) {
				_mainScreenJPanel.getEditCardJDialog().getTextPane().setText("Enter Text for Front");
				_mainScreenJPanel.getEditCardJDialog().getTextPane().selectAll();
			}
			else {
				_mainScreenJPanel.getEditCardJDialog().getTextPane().setText("Enter Text for Back");
				_mainScreenJPanel.getEditCardJDialog().getTextPane().selectAll();
			}
		}
		else if (text.contains("New Card #") && text.contains("Right Click to Edit")) {
			if (_editFront) {
				_mainScreenJPanel.getEditCardJDialog().getTextPane().setText("Enter Text for Front");
				_mainScreenJPanel.getEditCardJDialog().getTextPane().selectAll();
			}
			else {
				_mainScreenJPanel.getEditCardJDialog().getTextPane().setText("Enter Text for Back");
				_mainScreenJPanel.getEditCardJDialog().getTextPane().selectAll();
			}
		}
		else {
			_mainScreenJPanel.getEditCardJDialog().getTextPane().setText(text);
		}
		_mainScreenJPanel.getEditCardJDialog().getTextPane().requestFocus();
	}

	/**
	 * FlashCard strings are stored with html code, this method converts it from html to regular text
	 * @param text Converts the string stored in the Flash Card data as html code to regular text
	 * @return returns the converted regular text as a String
	 */
	private String convertFromCardString(String text) {
		text = text.replace("<br />", "\r\n").replace("<br />", "\n");
		return text;
	}

	/**
	 * Sets the background color of the textpane
	 */
	private void setTextPaneBackground() {
		_mainScreenJPanel.getEditCardJDialog().getTextPane().setBackground(_currentCard.getCardColor());
	}

	/**
	 * Sets the color being selected by the user. It is stored in case the user turns on the
	 * option to keep all new card colors the same
	 * @param color The current color that the user selected.
	 */
	public void setColor(Color color) {
		_chosenColor = color;
		_currentCard.setColors(_chosenColor);
		enqueueBackToCurrent();
		_mainScreenJPanel.getEditCardJDialog().getTextPane().setBackground(_chosenColor);
		_mainScreenJPanel.getEditCardJDialog().getTextPane().requestFocus();
	}

	/**
	 * Used by editing JDialog window when Cancel button is pushed.
	 * Revert changes done during editing
	 */
	public void cancelButton() {
		cancelEditChanges();
		_mainScreenJPanel.getEditCardJDialog().setVisible(false);
	}

	/**
	 * Used by editing JDialog window to change the function of the "Next" button
	 */
	public void nextButtonEditJDialog() {
		if (_editFront) {
			_mainScreenJPanel.getEditCardJDialog().getBtnConfirm().setText("Save & Close");
			String text = _mainScreenJPanel.getEditCardJDialog().getTextPane().getText();
			text = text.replace("\r\n", "<br />").replace("\n", "<br />");
			_currentCard.setFrontData(text);
			_mainScreenJPanel.getFlashCardPanel().setText(_currentCard.getFrontData(), true, _currentCard.getID());
			_editFront = false;
			setTextPane(convertFromCardString(_currentCard.getBackData()));
			_mainScreenJPanel.getEditCardJDialog().getBtnNewCard().setEnabled(true);
		}
		else {
			String text = _mainScreenJPanel.getEditCardJDialog().getTextPane().getText();
			text = text.replace("\r\n", "<br />").replace("\n", "<br />");
			_currentCard.setBackData(text);
			_currentCard.setColors(_chosenColor);
			_editFront = true;
			_mainScreenJPanel.getFlashCardPanel().setText(_currentCard.getFrontData(), true, _currentCard.getID());
			updateCard();
			enqueueBackToCurrent();
			setIsChanged(true);
			setFrameTitleUnsaved();
			_mainScreenJPanel.getEditCardJDialog().setVisible(false);
			_mainScreenJPanel.getEditCardJDialog().getBtnConfirm().setText("Next");
		}
	}

	/**
	 * Used by JDialog window to quickly make changes to a card and then add a new card
	 */
	public void newCardEditCard() {
		String text = _mainScreenJPanel.getEditCardJDialog().getTextPane().getText();
		text = text.replace("\r\n", "<br />").replace("\n", "<br />");
		_currentCard.setBackData(text);
		_currentCard.setColors(_chosenColor);
		updateCard();
		enqueueBackToCurrent();
		setIsChanged(true);
		setFrameTitleUnsaved();
		_editFront = true;
		quickAddNewCard();
		resetEditCardWindow();
	}

	/**
	 * If the state of the checkBox is checked, then makes all new card have the same color
	 * @param select is select is true, check box is checked, sets colorLock true, saves the color
	 * the user selected to the _chosenColor. Else, sets colorLock to false, restore to the color
	 * that is next on the HelperCardColors
	 */
	public void setSameColorCheckBox(boolean select) {
		if (select) {
			setColorLock(true);
			setColor(_chosenColor);
			_mainScreenJPanel.getEditCardJDialog().getTextPane().requestFocus();
		}
		else {
			setColorLock(false);
			setColor(unlockColor());
			_mainScreenJPanel.getEditCardJDialog().getTextPane().requestFocus();
		}
	}

	/**
	 * Used by JDialog edit window. When the delete button is pushed,
	 * confirms to the user if they want to delete the current card
	 */
	public void delete() {
		int response = JOptionPane.showConfirmDialog(null, "Permanently delete this card?", "",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.NO_OPTION) {
			_mainScreenJPanel.getEditCardJDialog().getTextPane().requestFocus();
		} else if (response == JOptionPane.YES_OPTION) {
			deleteFlashCard();
			if (_currentCard == null) {
				_mainScreenJPanel.getEditCardJDialog().setVisible(false);
			}
			else {
				_mainScreenJPanel.getEditCardJDialog().setVisible(false);
				popUpEditJDialog();
			}
		} else if (response == JOptionPane.CLOSED_OPTION) {
			_mainScreenJPanel.getEditCardJDialog().getTextPane().requestFocus();
		}
	}

	/**
	 * Allows other class to access the current deck
	 * @return returns the current deck the user is working on
	 */
	public ModelFlashCardAllDecks getAllDecks() {
		return _allDecks;
	}

	/**
	 * Used by TrinhFlashCardQueue to load in the Main Screen into the JFrame
	 * @return
	 */
	public ViewMainScreenJPanel getMainScreen() {
		return _mainScreenJPanel;
	}
	/**
	 * Getter method for currently loaded file
	 * @return Returns _file
	 */
	public File getFile() {
		return _file;
	}
}
