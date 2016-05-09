import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;

/**
 * @author Trinh Nguyen
 * <br>Description: Class that draws a graphical representation of the current flash card to the user.
 */
public class ViewFlashCardJPanel extends JPanel{
	/**
	 * The text that will be shown on the JPanel, could be text of the front or back data.
	 */
	private JLabel _cardText;
	/**
	 * Indicated whether the card is showing contents of the front of the card or the back.
	 */
	private JLabel _lblCardSide;
	/**
	 * Indicates the ID of the card being displayed.
	 */
	private JLabel _lblCardID;
	/**
	 * Lower JPanel used for storing _lblCardSide and _lblCardID.
	 */
	private JPanel _southPanel;
	/**
	 * Constructor to set up appearance and attributes for the card to be
	 * displayed.
	 * <br>Complexity: O(1)
	 */
	public ViewFlashCardJPanel() {
        setPreferredSize(new Dimension(430, 230));
        setMinimumSize(new Dimension(430,230));
        _cardText = new JLabel();
        
        Component horizontalStrut = Box.createHorizontalStrut(20);
        
        Component horizontalStrut_1 = Box.createHorizontalStrut(20);
        
        Component verticalStrut = Box.createVerticalStrut(20);
        setLayout(new BorderLayout(0, 0));
        add(verticalStrut, BorderLayout.NORTH);
        add(horizontalStrut, BorderLayout.WEST);
        add(_cardText, BorderLayout.CENTER);
        add(horizontalStrut_1, BorderLayout.EAST);
        
        _southPanel = new JPanel();
        add(_southPanel, BorderLayout.SOUTH);
        _southPanel.setLayout(new BoxLayout(_southPanel, BoxLayout.LINE_AXIS));
        
        _lblCardSide = new JLabel("");
        _southPanel.add(_lblCardSide);
        _lblCardSide.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
        _lblCardSide.setForeground(Color.BLACK);
        
        _southPanel.add(Box.createHorizontalGlue());
        
        _lblCardID = new JLabel("");
        _southPanel.add(_lblCardID);
        _lblCardID.setHorizontalAlignment(SwingConstants.RIGHT);
        _lblCardID.setForeground(Color.BLACK);
        _lblCardID.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
	}
	
	/**
	 * Sets the text field of the current card with html code.
	 * Html coded strings adds text wrapping when displayed
	 * on a JLabel.
	 * <br>Complexity: O(1)
	 * @param text The text to be displayed on the JPanel
	 * @param isFront if true, _lblCardSide will be set to "front" else, "back"
	 * @param ID Increments this ID by 1 and converts it to a string
	 * and set _lblCardID to this number
	 */
	public void setText(String text, boolean isFront, int ID) {
		_cardText.setText("<html>" + text + "</html>");
		_lblCardID.setText(Integer.toString(ID+1));
		if (isFront) {
			_lblCardSide.setText("front");
		}
		else {
			_lblCardSide.setText("back");
		}
	}
	
	/**
	 * Sets the color of the current card being displayed.
	 * <br>Complexity: O(1)
	 * @param currentColor The color the JLabel background will be set to
	 */
	public void setColor(Color currentColor) {
		setBackground(currentColor);
	}
	
	/**
	 * Called by other classes to draw a main screen on the program.
	 * <br>Complexity: O(1)
	 */
	public void drawTitle() {
		setBackground(new Color(179, 216, 253));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		_lblCardID.setText("");
		_lblCardSide.setText("");
		_cardText.setText("<html>Trinh's Flash Card Queue</html>");
        _cardText.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        _cardText.setForeground(Color.BLACK);
        _cardText.setHorizontalAlignment(SwingConstants.CENTER);
        _southPanel.setBackground(new Color(179, 216, 253));
	}
	
	/**
	 * Used to draw the appearance of the currently given card.
	 * HTML codes added to the String text to create text wrapping.
	 * <br>Complexity: O(1)
	 * @param currentColor Color for the background to be changed to
	 * @param text String of the text to be shown on the JPanel
	 * @param ID The ID of the card to be displayed on the JPanel
	 */
	public void drawNormalCard(Color currentColor, String text, int ID) {
		setBackground(currentColor);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        _lblCardID.setText(Integer.toString(ID+1));
        _lblCardSide.setText("front");
		_cardText.setText("<html>" + text + "</html>");
        _cardText.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        _cardText.setForeground(Color.BLACK);
        _cardText.setHorizontalAlignment(SwingConstants.CENTER);
        _southPanel.setBackground(currentColor);
	}
	
	/**
	 * Used to draw an empty box to show the user that there is no more cards.
	 * <br>Complexity: O(1)
	 */
	public void drawEmpty() {
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		_lblCardID.setText("");
		_lblCardSide.setText("");
		_cardText.setText("<html>empty</html>");
        _cardText.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        _cardText.setForeground(Color.LIGHT_GRAY);
        _cardText.setHorizontalAlignment(SwingConstants.CENTER);
        _southPanel.setBackground(Color.WHITE);
	}
}
