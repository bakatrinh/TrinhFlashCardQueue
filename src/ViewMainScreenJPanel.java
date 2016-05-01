import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.GridLayout;

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
	private JButton _btnNewDeck;
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
	private JFrame _mainJFrame;
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
	 * Reference to the center JPanel that displays contents of the current
	 * viewing card
	 */
	private ViewFlashCardJPanel _centerJPanelFlashCard;
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
	 * @param mainJFrame Reference to the main JFrame
	 */
	public ViewMainScreenJPanel(ControllerFlashCardQueueMain mainController, JFrame mainJFrame) {
		_mainController = mainController;
		_mainJFrame = mainJFrame;
		_editCardJDialog = new ViewEditCardJDialog(_mainController, _mainJFrame);
		
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);
		
		_mainJFrame.setTitle("Untitled Deck");
		_mainJFrame.addWindowListener(new java.awt.event.WindowAdapter() {
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
				if (f.equals(_mainController.getFile())) {
					if(getDialogType() == SAVE_DIALOG){
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
		
		JPanel _middleJPanelCenter = new JPanel();
		add(_middleJPanelCenter, BorderLayout.CENTER);
		_middleJPanelCenter.setLayout(new BorderLayout(0, 0));
		
		JPanel _middleJPanelSouth = new JPanel();
		_middleJPanelCenter.add(_middleJPanelSouth, BorderLayout.SOUTH);
		_middleJPanelSouth.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel _middleJPanelSouthInner = new JPanel();
		_middleJPanelSouth.add(_middleJPanelSouthInner);
		_middleJPanelSouthInner.setLayout(new BorderLayout(0, 0));
		
		JPanel _middleJPanelSouthInnerCenter = new JPanel();
		_middleJPanelSouthInner.add(_middleJPanelSouthInnerCenter);
		_middleJPanelSouthInnerCenter.setLayout(new GridLayout(0, 3, 1, 1));
		
		_btnFlip = new JButton("Flip");
		_btnFlip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.flip();
			}
		});
		_btnFlip.setEnabled(false);
		_btnFlip.setFocusable(false);
		_middleJPanelSouthInnerCenter.add(_btnFlip);
		
		_btnNext = new JButton("Next");
		_btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.nextCard();
			}
		});
		_btnNext.setEnabled(false);
		_btnNext.setFocusable(false);
		_middleJPanelSouthInnerCenter.add(_btnNext);
		
		_btnMoveToMemorized = new JButton("Memorized");
		_btnMoveToMemorized.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.moveCurrentCardToOrFromMain();
			}
		});
		_btnMoveToMemorized.setEnabled(false);
		_btnMoveToMemorized.setFocusable(false);
		_middleJPanelSouthInnerCenter.add(_btnMoveToMemorized);
		
		Component verticalStrut_6 = Box.createVerticalStrut(5);
		_middleJPanelSouthInner.add(verticalStrut_6, BorderLayout.NORTH);
		
		Component verticalStrut_7 = Box.createVerticalStrut(5);
		_middleJPanelSouthInner.add(verticalStrut_7, BorderLayout.SOUTH);
		
		JPanel _middleJPanelCenterFlashCard = new JPanel();
		_middleJPanelCenter.add(_middleJPanelCenterFlashCard, BorderLayout.CENTER);
		
		Component _middleJPanelTopStrut = Box.createVerticalStrut(20);
		_middleJPanelCenter.add(_middleJPanelTopStrut, BorderLayout.NORTH);
		
		_centerJPanelFlashCard = new ViewFlashCardJPanel();
		_centerJPanelFlashCard.drawTitle();
		_centerJPanelFlashCard.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent e) {
				_mainController.flashCardPanelClicked(e);
			}
		});

		_middleJPanelCenter.add(_centerJPanelFlashCard, BorderLayout.CENTER);
		
		JPanel _leftJPanel = new JPanel();
		add(_leftJPanel, BorderLayout.WEST);
		_leftJPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel _leftJPanelTop = new JPanel();
		_leftJPanel.add(_leftJPanelTop, BorderLayout.NORTH);
		_leftJPanelTop.setLayout(new BorderLayout(0, 0));
		
		JPanel _leftJPanelTopGrid = new JPanel();
		
		JPanel box1 = new JPanel();
		box1.setLayout(new BorderLayout(0, 0));
		box1.add(_leftJPanelTopGrid);
		box1.setPreferredSize(new Dimension(110,190));
		
		_leftJPanelTop.add(box1, BorderLayout.CENTER);
		_leftJPanelTopGrid.setLayout(new GridLayout(0, 1, 1, 1));
		
		_btnNewDeck = new JButton("New Deck");
		_btnNewDeck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.newDeck();
			}
		});
		_btnNewDeck.setFocusable(false);
		_leftJPanelTopGrid.add(_btnNewDeck);
		
		_btnOpen = new JButton("Open");
		_btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.openDeck();
			}
		});
		_btnOpen.setFocusable(false);
		_leftJPanelTopGrid.add(_btnOpen);
		
		_btnSave = new JButton("Save");
		_btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.saveDeck();
			}
		});
		_btnSave.setFocusable(false);
		_leftJPanelTopGrid.add(_btnSave);
		
		_btnSaveAs = new JButton("Save As...");
		_btnSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.saveAsDeck();
			}
		});
		_btnSaveAs.setFocusable(false);
		_leftJPanelTopGrid.add(_btnSaveAs);
		
		_btnRevertToFile = new JButton("Revert To File");
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
		_leftJPanelTopGrid.add(_btnRevertToFile);
		
		Component horizontalStrut_4 = Box.createHorizontalStrut(104);
		_leftJPanelTopGrid.add(horizontalStrut_4);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		_leftJPanelTop.add(verticalStrut_4, BorderLayout.NORTH);
		
		JPanel _leftJPanelBottom = new JPanel();
		_leftJPanel.add(_leftJPanelBottom, BorderLayout.SOUTH);
		_leftJPanelBottom.setLayout(new BorderLayout(0, 0));
		
		
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
		_workingDeckIconJPanel.setDeckTitle("Main Deck");
		_workingDeckIconJPanel.setSelected(true);

		_leftJPanelBottom.add(_workingDeckIconJPanel, BorderLayout.CENTER);
		
		Component verticalStrut = Box.createVerticalStrut(4);
		_leftJPanelBottom.add(verticalStrut, BorderLayout.NORTH);
		
		Component horizontalStrut = Box.createHorizontalStrut(4);
		_leftJPanelBottom.add(horizontalStrut, BorderLayout.WEST);
		
		Component verticalStrut_1 = Box.createVerticalStrut(4);
		_leftJPanelBottom.add(verticalStrut_1, BorderLayout.SOUTH);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(4);
		_leftJPanelBottom.add(horizontalStrut_1, BorderLayout.EAST);
		
		JPanel _rightJPanel = new JPanel();
		add(_rightJPanel, BorderLayout.EAST);
		_rightJPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel _rightJPanelTop = new JPanel();
		_rightJPanel.add(_rightJPanelTop, BorderLayout.NORTH);
		_rightJPanelTop.setLayout(new BorderLayout(0, 0));
		
		JPanel _rightJPanelTopGrid = new JPanel();
		
		JPanel box2 = new JPanel();
		box2.setLayout(new BorderLayout(0, 0));
		box2.add(_rightJPanelTopGrid);
		box2.setPreferredSize(new Dimension(110,190));
		
		_rightJPanelTop.add(box2, BorderLayout.CENTER);
		_rightJPanelTopGrid.setLayout(new GridLayout(0, 1, 0, 0));
		
		_btnNewCard = new JButton("New Card");
		_btnNewCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.addNewCardMainScreen();
			}
		});
		_btnNewCard.setFocusable(false);
		_rightJPanelTopGrid.add(_btnNewCard);
		
		_btnEditCard = new JButton("Edit Card");
		_btnEditCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.popUpEditJDialog();
			}
		});
		_btnEditCard.setEnabled(false);
		_btnEditCard.setFocusable(false);
		_rightJPanelTopGrid.add(_btnEditCard);
		
		_btnReset = new JButton("Reset");
		_btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.resetDeck();
			}
		});
		_btnReset.setEnabled(false);
		_btnReset.setFocusable(false);
		_rightJPanelTopGrid.add(_btnReset);
		
		_btnClearMemorized = new JButton("Restore");
		_btnClearMemorized.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.restore();
			}
		});
		_btnClearMemorized.setEnabled(false);
		_btnClearMemorized.setFocusable(false);
		_rightJPanelTopGrid.add(_btnClearMemorized);
		
		_btnExit = new JButton("Exit");
		_btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.confirmExit();
			}
		});
		_btnExit.setFocusable(false);
		_rightJPanelTopGrid.add(_btnExit);
		
		Component horizontalStrut_5 = Box.createHorizontalStrut(140);
		_rightJPanelTopGrid.add(horizontalStrut_5);
		
		Component verticalStrut_5 = Box.createVerticalStrut(20);
		_rightJPanelTop.add(verticalStrut_5, BorderLayout.NORTH);
		
		JPanel _rightJPanelBottom = new JPanel();
		_rightJPanel.add(_rightJPanelBottom, BorderLayout.SOUTH);
		_rightJPanelBottom.setLayout(new BorderLayout(0, 0));
		
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
		
		_memorizedDeckIconJPanel.updateDeckStatusRepaint(_mainController.getAllDecks().getMemorizedDeck(), _mainController.getCard());
		_memorizedDeckIconJPanel.setDeckTitle("Memorized Deck");
		_memorizedDeckIconJPanel.setSelected(false);
		
		_rightJPanelBottom.add(_memorizedDeckIconJPanel, BorderLayout.CENTER);
		
		Component verticalStrut_2 = Box.createVerticalStrut(4);
		_rightJPanelBottom.add(verticalStrut_2, BorderLayout.NORTH);
		
		Component horizontalStrut_2 = Box.createHorizontalStrut(4);
		_rightJPanelBottom.add(horizontalStrut_2, BorderLayout.EAST);
		
		Component horizontalStrut_3 = Box.createHorizontalStrut(4);
		_rightJPanelBottom.add(horizontalStrut_3, BorderLayout.WEST);
		
		Component verticalStrut_3 = Box.createVerticalStrut(4);
		_rightJPanelBottom.add(verticalStrut_3, BorderLayout.SOUTH);
		
		_workingDeckIconJPanel.updateDeckIcon(_mainController.getAllDecks().getWorkingDeck().getCardCounter());
		_memorizedDeckIconJPanel.updateDeckIcon(_mainController.getAllDecks().getMemorizedDeck().getCardCounter());
	}
	
	/**
	 * Getter for main JFrame
	 * @return Returns _mainJFrame
	 */
	public JFrame getMainJFrame() {
		return _mainJFrame;
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
		return _centerJPanelFlashCard;
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
