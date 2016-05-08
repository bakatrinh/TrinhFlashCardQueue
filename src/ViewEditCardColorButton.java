import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Trinh Nguyen
 * Custom JPanel to be used as Color buttons by ViewEditCardJDialog
 */
public class ViewEditCardColorButton extends JPanel {
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
