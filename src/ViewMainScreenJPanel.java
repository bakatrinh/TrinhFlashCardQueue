import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Dimension;


/**
 * @author Trinh Nguyen
 * @Discription Main Screen class. Displays representation of the current cards and decks. Allows the user
 * to edit, save, delete, and move cards around
 */
public class ViewMainScreenJPanel extends JPanel{
	/**
	 * Open saved deck Button
	 */
	private JButton _btnOpen;
	/**
	 * Save current deck button
	 */
	private JButton _btnSave;
	/**
	 * Save deck to specific location button
	 */
	private JButton _btnSaveAs;
	/**
	 * Reset button
	 */
	private JButton _btnReset;
	/**
	 * Make new card button
	 */
	private JButton _btnNewCard;
	/**
	 * Edit card button
	 */
	private JButton _btnEditCard;
	/**
	 * Clear the memorized deck button
	 */
	private JButton _btnClearMemorized;
	/**
	 * Move card to the memorized deck button
	 */
	private JButton _btnMoveToMemorized;
	/**
	 * Flip between the front and back of the card button
	 */
	private JButton _btnFlip;
	/**
	 * Go to next card button
	 */
	private JButton _btnNext;
	/**
	 * Make new deck button
	 */
	private JButton _btnNew;
	/**
	 * Reload the deck to the file button
	 */
	private JButton _btnRevertToFile;
	/**
	 * Exit program button
	 */
	private JButton _btnExit;
	/**
	 * The main JFrame this window is contained in
	 */
	private JFrame _topFrame;
	/**
	 * File browsing component
	 */
	private JFileChooser _fileChooser;
	/**
	 * Reference to the main controller that manage the appearance of this
	 * JPanel
	 */
	private ControllerFlashCardQueueMain _mainController;
	/**
	 * Reference to any file that is loaded or saved to
	 */
	private File _file;
	/**
	 * Reference to the center JPanel that displays contents of the current
	 * viewing card
	 */
	private ViewFlashCardJPanel _flashCardPanel;
	/**
	 * Reference to the edit card JDialog window
	 */
	private ViewEditCardJDialog _editCardJDialog;
	/**
	 * Reference to the JPanel that displayed graphical representation of the
	 * working deck
	 */
	private ViewDeckIconJPanel _workingDeckIconJPanel;
	/**
	 * Reference to the JPanel that displayed graphical representation of the
	 * memorized deck
	 */
	private ViewDeckIconJPanel _memorizedDeckIconJPanel;
	/**
	 * Constructor, links itself to main controller and main JFrame, makes a
	 * new JDialog card editing window as well and then initializes all components
	 * @param mainController Reference to the main controller
	 * @param topFrame Reference to the main JFrame
	 */
	public ViewMainScreenJPanel(ControllerFlashCardQueueMain mainController, JFrame topFrame) {
		_mainController = mainController;
		_topFrame = topFrame;
		_editCardJDialog = new ViewEditCardJDialog(_mainController, _topFrame);
		initComponents();
	}
	
	/**
	 * Initializes each components and adds actionlistener to corresponding buttons
	 */
	private void initComponents() {
		_topFrame.setTitle("Untitled Deck");
		_topFrame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				_mainController.confirmExit();
			}
		});


		// Displays a file explorer window to allow the user to open or save files
		_fileChooser = new JFileChooser(){
			@Override
			public void approveSelection(){
				File f = getSelectedFile();
				if (!f.equals(_file)) {
					if(f.exists() && getDialogType() == SAVE_DIALOG){
						int result = JOptionPane.showConfirmDialog(this,"Overwrite file?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
						switch(result){
						case JOptionPane.YES_OPTION:
							super.approveSelection();
							return;
						case JOptionPane.NO_OPTION:
							return;
						case JOptionPane.CLOSED_OPTION:
							return;
						case JOptionPane.CANCEL_OPTION:
							cancelSelection();
							return;
						}
					}
				}
				super.approveSelection();
			}        
		};
		File workingDirectory = new File(System.getProperty("user.dir"));
		_fileChooser.setCurrentDirectory(workingDirectory);
		_fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		_fileChooser.setFileFilter(new FileNameExtensionFilter("Trinh's FCQ Decks", "fcq"));

		_btnOpen = new JButton("Open");
		_btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.openDeck();
			}
		});
		_btnOpen.setFocusable(false);

		_btnSave = new JButton("Save");
		_btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.saveDeck();
			}
		});
		_btnSave.setFocusable(false);

		_btnSaveAs = new JButton("Save As...");
		_btnSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.saveAsDeck();
			}
		});
		_btnSaveAs.setFocusable(false);

		_btnReset = new JButton("Reset");
		_btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.resetDeck();
			}
		});
		_btnReset.setEnabled(false);
		_btnReset.setFocusable(false);

		_btnNewCard = new JButton("New Card");
		_btnNewCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.addNewCardMainScreen();
			}
		});
		_btnNewCard.setFocusable(false);

		_btnEditCard = new JButton("Edit Card");
		_btnEditCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.popUpEditJDialog();
			}
		});
		_btnEditCard.setEnabled(false);
		_btnEditCard.setFocusable(false);

		_btnClearMemorized = new JButton("Restore");
		_btnClearMemorized.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.restore();
			}
		});
		_btnClearMemorized.setEnabled(false);
		_btnClearMemorized.setFocusable(false);

		_btnMoveToMemorized = new JButton("Memorized");
		_btnMoveToMemorized.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.moveCurrentCardToOrFromMain();
			}
		});
		_btnMoveToMemorized.setEnabled(false);
		_btnMoveToMemorized.setFocusable(false);

		_btnFlip = new JButton("Flip");
		_btnFlip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.flip();
			}
		});
		_btnFlip.setEnabled(false);
		_btnFlip.setFocusable(false);

		_btnNext = new JButton("Next");
		_btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.nextCard();
			}
		});
		_btnNext.setEnabled(false);
		_btnNext.setFocusable(false);

		_flashCardPanel = new ViewFlashCardJPanel();
		_flashCardPanel.drawTitle();
		_flashCardPanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				_mainController.flashCardPanelClicked(e);
			}
		});

		setBackground(Color.WHITE);

		_btnNew = new JButton("New");
		_btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.newDeck();
			}
		});
		_btnNew.setFocusable(false);

		_btnRevertToFile = new JButton("Revert to File");
		_btnRevertToFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				int response = JOptionPane.showConfirmDialog(null, "Revert to the last time you saved?", "",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (response == JOptionPane.NO_OPTION) {

				} else if (response == JOptionPane.YES_OPTION) {
					_mainController.open();
				} else if (response == JOptionPane.CLOSED_OPTION) {

				}
			}
		});
		_btnRevertToFile.setEnabled(false);
		_btnRevertToFile.setFocusable(false);

		_workingDeckIconJPanel = new ViewDeckIconJPanel();
		_workingDeckIconJPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				_mainController.switchToWorkingDeck();
			}
		});
		_workingDeckIconJPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				_workingDeckIconJPanel.setMouseHover(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				_workingDeckIconJPanel.setMouseHover(false);
			}
		});
		_workingDeckIconJPanel.updateDeckStatusRepaint(_mainController.getAllDecks().getWorkingDeck(), _mainController.getCard());
		_workingDeckIconJPanel.setSize(new Dimension(100, 100));
		_workingDeckIconJPanel.setDeckTitle("Main Deck");
		_workingDeckIconJPanel.setSelected(true);

		_memorizedDeckIconJPanel = new ViewDeckIconJPanel();
		_memorizedDeckIconJPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				_mainController.switchToMemorized();
			}
		});
		_memorizedDeckIconJPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				_memorizedDeckIconJPanel.setMouseHover(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				_memorizedDeckIconJPanel.setMouseHover(false);
			}
		});
		_workingDeckIconJPanel.updateDeckStatusRepaint(_mainController.getAllDecks().getWorkingDeck(), _mainController.getCard());
		_memorizedDeckIconJPanel.setSize(new Dimension(100, 100));
		_memorizedDeckIconJPanel.setDeckTitle("Memorized Deck");
		_memorizedDeckIconJPanel.setSelected(false);

		_workingDeckIconJPanel.updateDeckIcon(_mainController.getAllDecks().getWorkingDeck().getCardCounter());
		_memorizedDeckIconJPanel.updateDeckIcon(_mainController.getAllDecks().getMemorizedDeck().getCardCounter());

		_btnExit = new JButton("Exit");
		_btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.confirmExit();
			}
		});
		_btnExit.setFocusable(false);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(_btnRevertToFile, GroupLayout.PREFERRED_SIZE, 108, Short.MAX_VALUE)
								.addComponent(_workingDeckIconJPanel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addComponent(_btnNew, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
								.addComponent(_btnOpen, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
								.addComponent(_btnSave, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
								.addComponent(_btnSaveAs, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(_flashCardPanel, GroupLayout.PREFERRED_SIZE, 430, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
										.addComponent(_btnFlip, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
										.addGap(33)
										.addComponent(_btnNext, GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(_btnMoveToMemorized, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
										.addGap(18)))
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(_btnClearMemorized, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
								.addComponent(_btnEditCard, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
								.addComponent(_btnNewCard, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
								.addComponent(_memorizedDeckIconJPanel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
								.addComponent(_btnReset, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
								.addComponent(_btnExit, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
						.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
						.addContainerGap(28, Short.MAX_VALUE)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addGap(20)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(_btnOpen)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(_btnSave)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(_btnSaveAs)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(_btnRevertToFile)
														.addGap(104))
												.addGroup(groupLayout.createSequentialGroup()
														.addComponent(_flashCardPanel, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
														.addGap(18)))
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(_btnMoveToMemorized)
												.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
														.addComponent(_btnFlip)
														.addComponent(_btnNext))))
								.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(_btnNew)
												.addPreferredGap(ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
												.addComponent(_workingDeckIconJPanel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
												.addComponent(_btnNewCard)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(_btnEditCard)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(_btnReset)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(_btnClearMemorized)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(_btnExit)
												.addGap(52)
												.addComponent(_memorizedDeckIconJPanel, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))))
						.addGap(52))
				);
		setLayout(groupLayout);
	}
	
	/**
	 * Getter for main JFrame
	 * @return Returns _topFrame
	 */
	public JFrame getTopFrame() {
		return _topFrame;
	}
	/**
	 * Getter for next card button
	 * @return Returns _btnNext
	 */
	public JButton getBtnNext() {
		return _btnNext;
	}
	/**
	 * Getter for the Flip card button
	 * @return Returns _btnFlip
	 */
	public JButton getBtnFlip() {
		return _btnFlip;
	}
	/**
	 * Getter for move to memorized deck button
	 * @return Returns _btnMoveToMemorized
	 */
	public JButton getBtnMoveToMemorized() {
		return _btnMoveToMemorized;
	}
	/**
	 * Getter for making new card button
	 * @return Returns _btnNewCard
	 */
	public JButton getBtnNewCard() {
		return _btnNewCard;
	}
	/**
	 * Getter for edit card button
	 * @return _btnEditCard
	 */
	public JButton getBtnEditCard() {
		return _btnEditCard;
	}
	/**
	 * Getter for clear the memorized deck button
	 * @return Returns _btnClearMemorized
	 */
	public JButton getBtnClearMemorized() {
		return _btnClearMemorized;
	}
	/**
	 * Getter for reset button
	 * @return Returns _btnReset
	 */
	public JButton getBtnReset() {
		return _btnReset;
	}
	/**
	 * Getter for revert to the currently loaded file button
	 * @return Returns _btnRevertToFile
	 */
	public JButton getBtnRevertToFile() {
		return _btnRevertToFile;
	}
	/**
	 * Getter for the file choosing window
	 * @return Returns _fileChooser
	 */
	public JFileChooser getFileChooser() {
		return _fileChooser;
	}
	/**
	 * Getter for the current flash card JPanel
	 * @return Returns _flashCardPanel
	 */
	public ViewFlashCardJPanel getFlashCardPanel() {
		return _flashCardPanel;
	}
	/**
	 * Getter for Working Deck Icon JPanel
	 * @return Returns _workingDeckIconJPanel
	 */
	public ViewDeckIconJPanel getWorkingDeckIconJPanel() {
		return _workingDeckIconJPanel;
	}
	/**
	 * Getter for Memorized Deck Icon JPanel
	 * @return Returns _memorizedDeckIconJPanel
	 */
	public ViewDeckIconJPanel getMemorizedDeckIconJPanel() {
		return _memorizedDeckIconJPanel;
	}
	/**
	 * Getter for edit card JDialog
	 * @return Returns _editCardJDialog
	 */
	public ViewEditCardJDialog getEditCardJDialog() {
		return _editCardJDialog;
	}
}
