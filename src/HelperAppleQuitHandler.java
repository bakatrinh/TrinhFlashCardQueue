import javax.swing.JOptionPane;
import com.apple.eawt.AppEvent;
import com.apple.eawt.Application;
import com.apple.eawt.QuitHandler;
import com.apple.eawt.QuitResponse;

/**
 * @Discription This class is needed to make OSX command+q shortcut ask for
 * confirmation before closing the program
 */
public class HelperAppleQuitHandler {
	public static void DoAppleQuit(ControllerMain mainController) {
		Application a = Application.getApplication();
		a.setQuitHandler(new QuitHandler() {
			@Override
			public void handleQuitRequestWith(AppEvent.QuitEvent quitEvent, QuitResponse quitResponse) {
				if (!mainController.getIsChanged()) {
					quitResponse.performQuit();
				}
				else {
					Object[] options = {"Save", "Don't Save", "Cancel"};
					int response = JOptionPane.showOptionDialog(null,
							"Save changes?","", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					if (response == JOptionPane.NO_OPTION) {
						quitResponse.performQuit();
					} else if (response == JOptionPane.YES_OPTION) {
						mainController.saveDeck();
						mainController.confirmExit();
					}
					else {
						quitResponse.cancelQuit();
					}
				}
				quitResponse.cancelQuit();
			}
		});
	}
}