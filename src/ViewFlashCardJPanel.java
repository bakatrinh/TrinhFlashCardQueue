import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

// Class that draws a graphical representation of the current flash card to the user.
public class ViewFlashCardJPanel extends JPanel{
	private JLabel _cardText;
	private JLabel _lblCardSide;
	private JLabel _lblCardID;
	
	public ViewFlashCardJPanel() {
        setPreferredSize(new Dimension(430, 230));
        setMinimumSize(new Dimension(430,230));
        _cardText = new JLabel();
        
        Component horizontalStrut = Box.createHorizontalStrut(20);
        
        Component horizontalStrut_1 = Box.createHorizontalStrut(20);
        
        Component verticalStrut = Box.createVerticalStrut(20);
        
        Component verticalStrut_1 = Box.createVerticalStrut(20);
        
        _lblCardSide = new JLabel("");
        _lblCardSide.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
        _lblCardSide.setForeground(Color.BLACK);
        
        _lblCardID = new JLabel("");
        _lblCardID.setHorizontalAlignment(SwingConstants.RIGHT);
        _lblCardID.setForeground(Color.BLACK);
        _lblCardID.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addComponent(verticalStrut, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(horizontalStrut, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addComponent(_cardText, GroupLayout.PREFERRED_SIZE, 390, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(horizontalStrut_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        		.addGroup(groupLayout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(_lblCardSide)
        			.addGap(69)
        			.addComponent(verticalStrut_1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
        			.addComponent(_lblCardID, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
        			.addGap(14))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(verticalStrut, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(horizontalStrut, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
        				.addComponent(_cardText, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
        				.addComponent(horizontalStrut_1, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(_lblCardSide)
        				.addComponent(verticalStrut_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(_lblCardID, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        setLayout(groupLayout);
	}
	
	// Sets the text field of the current card with html code to add text wrapping
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
	
	// Sets the color of the current card being displayed
	public void setColor(Color currentColor) {
		setBackground(currentColor);
	}
	
	// Called by other classes to draw a main screen on the program
	public void drawTitle() {
		setBackground(new Color(179, 216, 253));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		_lblCardID.setText("");
		_lblCardSide.setText("");
		_cardText.setText("<html>Trinh's Flash Card Queue</html>");
        _cardText.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        _cardText.setForeground(Color.BLACK);
        _cardText.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	// Used to draw the appearance of the currently given card
	public void drawNormalCard(Color currentColor, String text, int ID) {
		setBackground(currentColor);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        _lblCardID.setText(Integer.toString(ID+1));
        _lblCardSide.setText("front");
		_cardText.setText("<html>" + text + "</html>");
        _cardText.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        _cardText.setForeground(Color.BLACK);
        _cardText.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	// Used to draw an empty box to show the user that there is no more cards
	public void drawEmpty() {
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
		_lblCardID.setText("");
		_lblCardSide.setText("");
		_cardText.setText("<html>empty</html>");
        _cardText.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        _cardText.setForeground(Color.LIGHT_GRAY);
        _cardText.setHorizontalAlignment(SwingConstants.CENTER);
	}
}
