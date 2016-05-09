import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Trinh Nguyen
 * <br>Description: Custom JPanel to be used as Color buttons by ViewEditCardJDialog
 */
public class ViewEditCardColorButton extends JPanel {
	/**
	 * Constructor that make's a colored JPanel with a black 1 pixel
	 * border. Also adds a mouse released listener to itself so that
	 * when clicked, it will set the color of itself to the current
	 * flash card.
	 * <br>Complexity: O(1)
	 * @param mainController A reference to the main controller so that it can run a method
	 * for the mouse listener
	 * @param buttonColor The color this JPanel's background will be and also what color
	 * the current flash card will be changed to when clicked
	 */
	public ViewEditCardColorButton(ControllerMain mainController, Color buttonColor) {
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setBackground(buttonColor);
		setLayout(new GridLayout(1, 1, 0, 0));
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				mainController.setColor(buttonColor);
			}
		});
		setFocusable(false);
	}
}
