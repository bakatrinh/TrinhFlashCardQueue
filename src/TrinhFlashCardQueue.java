import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * @author Trinh Nguyen
 * <br>Desription: TrinhFlashCardQueue, uses the queue model data structure
 * for this Flash Card viewing program.
 */
public class TrinhFlashCardQueue {
	
	/**
	 * Launches the program.
	 * <br>Complexity: O(1).
	 * @param args No parameters is used to run this program.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame main = new JFrame();
				ControllerMain mainController = new ControllerMain(main);
				main.setLayout(new BorderLayout());
				main.add(mainController.getMainScreen());
				main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				main.pack();
				main.setResizable(true);
				main.setLocationRelativeTo(null);
				main.setVisible(true);
			}
		});
	}
}
