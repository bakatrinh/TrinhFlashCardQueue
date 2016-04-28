import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Path2D;

/**
 * @author Trinh Nguyen
 * @Discription Paints a 100x100 JPanel that graphically shows the size of the deck.
 * It also shows small representation of each color in the order of the FlashCardDeck being accessed.
 */
public class ViewDeckIconJPanel extends JPanel {
	/**
	 * Used in the algorithm to decide that color order of the flash cards to paint
	 */
	private int _cardCount;
	/**
	 * Paints a "Selected" string in the corner if selected
	 */
	private String _isSelectedString;
	/**
	 * Used to paint the card count string in the corner
	 */
	private String _cardCountString;
	/**
	 * Used to check if the deck is selected
	 */
	private boolean _isSelected;
	/**
	 * Checks if the panel is being hovered over with a mouse so that the background color can be changed
	 */
	private boolean _isHover;
	/**
	 * Stores the current title of the deck to be drawn during repaint()
	 */
	private String _deckTitle;
	/**
	 * Graphics2D object used to paint everything
	 */
	private Graphics2D g2d;
	/**
	 * A copy of the current deck in reverse. Used by the painting algorithm to
	 * draw the flash cards in reverse order
	 */
	private ModelFlashCardDeck _currentDeck;
	/**
	 * Saved font for drawing the title
	 */
	private static final Font _deckTitleFont = new Font("Lucida Grande", Font.PLAIN, 11);
	/**
	 * Saved font for drawing the card count
	 */
	private static final Font _deckCardCountFont = new Font("Lucida Grande", Font.PLAIN, 14);
	
	/**
	 * Constructor, initializes the JPanel and attributes
	 */
	public ViewDeckIconJPanel() {
		_cardCount = 0;
		setBackground(Color.WHITE);
		setSize(new Dimension(100, 100));
		_isSelected = true;
		_isHover = false;
		_deckTitle = "";
		_isSelectedString = "";
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 96, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGap(0, 96, Short.MAX_VALUE));
		setLayout(groupLayout);
	}

	/**
	 * Sets deck title string in the top of this panel when painted
	 * @param title The title the deck should be set to
	 */
	public void setDeckTitle(String title) {
		_deckTitle = title;
	}

	/**
	 * Called by other classes to tell this panel that it is selected
	 * @param isSelected If true, "Selected" will be displayed during repaint(). Else no
	 * text is displayed
	 */
	public void setSelected(boolean isSelected) {
		_isSelected = isSelected;
		repaint();
	}
	
	/**
	 * Called by other classes to let this panel know that a mouse is hovering over it
	 * @param hover Sets _isHover to this value and repaints. The background
	 * color is repainted in different color based on the boolean value
	 */
	public void setMouseHover(boolean hover) {
		_isHover = hover;
		repaint();
	}
	
	/**
	 * Called by other classes every time there is an update the the FlashCardDeck
	 * data so that this panel can Repaint everything to visually represent the changes.
	 * @param cardCount The number to be displayed on the JPanel during repaint to indicate
	 * how many cards are in the current deck
	 */
	public void updateDeckIcon(int cardCount) {
		_cardCount = cardCount;
		_cardCountString = Integer.toString(cardCount);
		repaint();
	}
	
	/**
	 * Repaints the different components of the JPanel with
	 * corresponding booleans for displayed certain text, card counts,
	 * and colors of each cards
	 */
	public void paintComponent(Graphics g) {
		g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(hints);

		if (_isSelected) {
			g2d.setColor(new Color(149, 193, 241));
			_isSelectedString = "Selected";
		} else {
			g2d.setColor(new Color(233, 244, 253));
			_isSelectedString = "";
		}
		if (_isHover) {
			g2d.setColor(new Color(31, 113, 189));
		}
		g2d.fillRect(2, 2, 96, 96);
		
		// Some base cases on where to position the first few cards if the deck is small. The algorithm
		// and positioning is the same for all deck sizes after 5
		switch (_cardCount) {
		case 0:
			drawEmptyDeck(g2d);
			break;
		case 1:
			drawOneCard(g2d, _currentDeck);
			break;
		case 2:
			drawTwoCard(g2d, _currentDeck);
			break;
		case 3:
			drawThreeCard(g2d, _currentDeck);
			break;
		case 4:
			drawFourCard(g2d, _currentDeck);
			break;
		case 5:
			drawFiveCard(g2d, _currentDeck);
			break;
		default:
			drawManyCard(g2d, _currentDeck);
			break;
		}

		FontMetrics metrics = getFontMetrics(_deckTitleFont);
		int d = metrics.getAscent();
		whiteOutlinedDrawString(g2d, _deckTitleFont, _deckTitle, 3, d + 1);
		if (_isSelected) {
			whiteOutlinedDrawString(g2d, _deckTitleFont, _isSelectedString, 3, 96);
		}

		whiteOutlinedDrawString(g2d, _deckCardCountFont, _cardCountString, 91 - metrics.stringWidth(_cardCountString),
				92);

		g2d.setColor(Color.BLACK);
		int thickness = 2;
		Stroke oldStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(thickness));
		g2d.drawRect(1, 1, 98, 98);
		g2d.setStroke(oldStroke);

	}
	
	/**
	 * Instructions for how to draw a single card with the given color and location
	 * @param g2d Graphics2D object linked to super for repainting
	 * @param cardColor Color of the card
	 * @param startingWidth Width position of the card in the JPanel
	 * @param startingHeight Height position of the card in the JPanel
	 */
	private void drawShape(Graphics2D g2d, Color cardColor, double startingWidth, double startingHeight) {
		Path2D.Double shape = new Path2D.Double();
		shape.moveTo(startingWidth, startingHeight);
		shape.lineTo(startingWidth + 54, startingHeight + 20);
		shape.lineTo(startingWidth + 54, startingHeight + 70);
		shape.lineTo(startingWidth, startingHeight + 50);
		shape.closePath();
		g2d.setColor(cardColor);
		g2d.fill(shape);
		g2d.setColor(Color.BLACK);
		g2d.draw(shape);
	}
	
	/**
	 * Used when drawing strings on the panel to add an outline to the text for better visibility
	 * @param g2d Graphics2D object linked to super for repainting
	 * @param font The font of the String being drawn
	 * @param text The text of the String being drawn
	 * @param width The width position of the String on the JPanel
	 * @param height The height position of the String on the JPanel
	 */
	private void whiteOutlinedDrawString(Graphics2D g2d, Font font, String text, int width, int height) {
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);
		g2d.drawString(text, width - 1, height);
		g2d.drawString(text, width + 1, height);
		g2d.drawString(text, width, height + 1);
		g2d.drawString(text, width, height - 1);
		g2d.setColor(Color.BLACK);
		g2d.drawString(text, width, height);
	}
	
	/**
	 * Instructions on how to draw when deck is empty
	 * @param g2d The Graphics2D object linked to super for repainting
	 */
	private void drawEmptyDeck(Graphics2D g2d) {
		drawShape(g2d, Color.LIGHT_GRAY, 25, 20);
		g2d.setFont(_deckTitleFont);
		whiteOutlinedDrawString(g2d, _deckTitleFont, "Empty Deck", 20, 56);
	}
	
	/**
	 * Instructions on how to draw when deck has 1 card, can be used later on to adjust the locations
	 * @param g2d The Graphics2D object linked to super for repainting
	 * @param copyDeck Copy of the deck that is being analyzed to determine the color and order
	 * it would be displayed on the JPanel
	 */
	private void drawOneCard(Graphics2D g2d, ModelFlashCardDeck copyDeck) {
		drawCardPlacementGenerator(g2d, copyDeck, 20, 25);
	}
	/**
	 * Instructions on how to draw when deck has 2 cards, can be used later on to adjust the locations
	 * @param g2d The Graphics2D object linked to super for repainting
	 * @param copyDeck Copy of the deck that is being analyzed to determine the color and order
	 * it would be displayed on the JPanel
	 */
	private void drawTwoCard(Graphics2D g2d, ModelFlashCardDeck copyDeck) {
		drawCardPlacementGenerator(g2d, copyDeck, 15, 28);
	}
	/**
	 * Instructions on how to draw when deck has 3 cards, can be used later on to adjust the locations
	 * @param g2d The Graphics2D object linked to super for repainting
	 * @param copyDeck Copy of the deck that is being analyzed to determine the color and order
	 * it would be displayed on the JPanel
	 */
	private void drawThreeCard(Graphics2D g2d, ModelFlashCardDeck copyDeck) {
		drawCardPlacementGenerator(g2d, copyDeck, 13, 28);
	}
	/**
	 * Instructions on how to draw when deck has 4 cards, can be used later on to adjust the locations
	 * @param g2d The Graphics2D object linked to super for repainting
	 * @param copyDeck Copy of the deck that is being analyzed to determine the color and order
	 * it would be displayed on the JPanel
	 */
	private void drawFourCard(Graphics2D g2d, ModelFlashCardDeck copyDeck) {
		drawCardPlacementGenerator(g2d, copyDeck, 9, 29);
	}
	/**
	 * Instructions on how to draw when deck has 5 cards, can be used later on to adjust the locations
	 * @param g2d The Graphics2D object linked to super for repainting
	 * @param copyDeck Copy of the deck that is being analyzed to determine the color and order
	 * it would be displayed on the JPanel
	 */
	private void drawFiveCard(Graphics2D g2d, ModelFlashCardDeck copyDeck) {
		drawCardPlacementGenerator(g2d, copyDeck, 7, 32);
	}
	/**
	 * Instructions on how to draw when deck has 6 or more cards, can be used later on to adjust the locations
	 * @param g2d The Graphics2D object linked to super for repainting
	 * @param copyDeck Copy of the deck that is being analyzed to determine the color and order
	 * it would be displayed on the JPanel
	 */
	private void drawManyCard(Graphics2D g2d, ModelFlashCardDeck copyDeck) {
		drawCardPlacementGenerator(g2d, copyDeck, 6, 34);
	}
	
	/**
	 * Used by all the method to read in the given deck data and convert it to a graphical representation
	 * @param g2d The Graphics2D object linked to super for repainting
	 * @param copyDeck Copy of the deck with all the cards to be analyzed for colors and order
	 * @param width The width of where the repainted card will be drawn on the JPanel
	 * @param height The height of where the repainted card will be drawn on the JPanel
	 */
	private void drawCardPlacementGenerator(Graphics2D g2d, ModelFlashCardDeck copyDeck, int width, int height) {
		int tempCardCount = _cardCount;
		int width2 = width + (tempCardCount * 5);
		int height2 = height - (tempCardCount * 5);
		Color cardColor;
		for (int i = 0; i < tempCardCount; i++) {
			ModelFlashCard temp = copyDeck.dequeue();
			cardColor = temp.getCardColor();
			copyDeck.enqueue(temp);
			drawShape(g2d, cardColor, width2, height2);
			width2 = width2 - 5;
			height2 = height2 + 5;
		}
	}
	
	/**
	 * Used by other classes to update the painting in this class to represent changes
	 * @param currentDeck The deck to copy from
	 * @param currentCard If there is also a card currently being viewed, add a copy of
	 * that card to the deck to be analyzed as well, otherwise this can be null
	 */
	public void updateDeckStatusRepaint(ModelFlashCardDeck currentDeck, ModelFlashCard currentCard) {
		ModelFlashCardDeck sequentialCopy = new ModelFlashCardDeck();

		sequentialCopy.copyDeck(currentDeck);
		if (currentCard != null) {
			sequentialCopy.enqueue(currentCard);
			sequentialCopy.enqueueBacktoNext();
		}
	
		ModelFlashCardDeck reverseOrderedDeck = new ModelFlashCardDeck();
		if (!sequentialCopy.isEmpty()) {
			recursiveReverseDeckCopy(reverseOrderedDeck, sequentialCopy);
		}
		_currentDeck = reverseOrderedDeck;
		repaint();
	}
	
	/**
	 * Recursive method to copy the deck in reverse. Called by updatedDeckStatusRepaint
	 * @param currentDeck Should be a new empty deck which will have cards enqueued into this
	 * @param copyDeck Copy of the deck that will have its cards dequeued and enqueued into currentDeck
	 * but in reverse order
	 */
	private void recursiveReverseDeckCopy(ModelFlashCardDeck currentDeck, ModelFlashCardDeck copyDeck) {
		ModelFlashCard temp = copyDeck.dequeue();
		if (!copyDeck.isEmpty()) {
			recursiveReverseDeckCopy(currentDeck, copyDeck);
		}
		currentDeck.enqueue(temp);
	}
}
