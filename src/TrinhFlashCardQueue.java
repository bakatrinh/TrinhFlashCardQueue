import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * @author Trinh Nguyen
 * @Discription TrinhFlashCardQueue, uses the queue model as the framework
 * for this Flash Card viewing program
 */
public class TrinhFlashCardQueue {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				JFrame main = new JFrame();
				ControllerFlashCardQueueMain mainController = new ControllerFlashCardQueueMain(main);
				main.setLayout(new BorderLayout());
				main.add(mainController.getMainScreen());
				main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				main.pack();
				//main.setSize(670, 350);
				main.setResizable(false);
				main.setLocationRelativeTo(null);
				main.setVisible(true);
			}
		});
	}
}
