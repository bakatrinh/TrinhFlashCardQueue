import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JCheckBox;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Component;
import java.awt.Dialog;
import javax.swing.BorderFactory;
import javax.swing.Box;

public class ViewEditCardJDialog extends JDialog{
	/**
	 * Confirm button
	 */
	private JButton _btnConfirm;
	/**
	 * Cancel button
	 */
	private JButton _btnCancel;
	/**
	 * Delete card button
	 */
	private JButton _btnDelete;
	/**
	 * Create new card quickly button
	 */
	private JButton _btnNewCard;
	/**
	 * Text pane for entering in String for front and
	 * back data of the current card
	 */
	private JTextPane _textPane;
	/**
	 * Check box that when checked will keep all newly created cards the
	 * same color
	 */
	private JCheckBox _chckbxSameColorFor;
	/**
	 * Red color button to change card color to this color.
	 */
	private ViewEditCardColorButton _btnRed;
	/**
	 * Purple color button to change card color to this color.
	 */
	private ViewEditCardColorButton _btnPurple;
	/**
	 * Orange color button to change card color to this color.
	 */
	private ViewEditCardColorButton _btnOrange;
	/**
	 * Pink color button to change card color to this color.
	 */
	private ViewEditCardColorButton _btnPink;
	/**
	 * Yellow color button to change card color to this color.
	 */
	private ViewEditCardColorButton _btnYellow;
	/**
	 * Brown color button to change card color to this color.
	 */
	private ViewEditCardColorButton _btnBrown;
	/**
	 * Green color button to change card color to this color.
	 */
	private ViewEditCardColorButton _btnGreen;
	/**
	 * Graphite color button to change card color to this color.
	 */
	private ViewEditCardColorButton _btnGraphite;
	/**
	 * Blue color button to change card color to this color.
	 */
	private ViewEditCardColorButton _btnBlue;
	/**
	 * Purple color button to change card color to this color.
	 */
	private ViewEditCardColorButton _btnLightPurple;
	/**
	 * Teal color button to change card color to this color.
	 */
	private ViewEditCardColorButton _btnTeal;
	/**
	 * White color button to change card color to this color.
	 */
	private ViewEditCardColorButton _btnWhite;
	
	/**
	 * A reference to the main controller needed to call certain
	 * methods when a button is pushed
	 */
	private ControllerMain _mainController;
	
	/**
	 * Constructor to set up the components in the JDialog
	 * @param mainController Reference to main controller
	 * @param mainJFrame Reference to the main JFrame
	 */
	public ViewEditCardJDialog(ControllerMain mainController, JFrame mainJFrame) {
		super(mainJFrame, "", Dialog.ModalityType.DOCUMENT_MODAL);
		_mainController = mainController;
		setResizable(false);
		setModal(true);
		setUndecorated(true);
		setLocationRelativeTo(mainJFrame);
		setSize(new Dimension(360, 300));
		((JPanel)getContentPane()).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		
		JPanel top = new JPanel();
		getContentPane().add(top, BorderLayout.NORTH);
		
		_textPane = new JTextPane();
		
		JPanel topSecondary = new JPanel();
		top.add(topSecondary);
		topSecondary.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		topSecondary.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setPreferredSize(new Dimension(330, 145));
		_textPane = new JTextPane();
		_textPane.setText("");
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		scrollPane.setViewportView(_textPane);
		
		Component verticalStrut = Box.createVerticalStrut(5);
		topSecondary.add(verticalStrut, BorderLayout.NORTH);
		
		JPanel middle = new JPanel();
		getContentPane().add(middle, BorderLayout.CENTER);
		middle.setLayout(new BorderLayout(0, 0));
		
		JPanel middleTop = new JPanel();
		middle.add(middleTop, BorderLayout.NORTH);
		middleTop.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		_chckbxSameColorFor = new JCheckBox("Same Color for All New Cards");
		_chckbxSameColorFor.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					_mainController.setSameColorCheckBox(true);
				} else {
					_mainController.setSameColorCheckBox(false);
				};
			}
		});
		middleTop.add(_chckbxSameColorFor);
		
		JPanel middleCenter = new JPanel();
		middle.add(middleCenter, BorderLayout.CENTER);
		
		JPanel middleCenterMain = new JPanel();
		middleCenter.add(middleCenterMain);
		middleCenterMain.setLayout(new BorderLayout(0, 0));
		
		JPanel middleCenterSecondary = new JPanel();
		middleCenterMain.add(middleCenterSecondary, BorderLayout.NORTH);
		
		JPanel middleCenterButtons = new JPanel();
		middleCenterSecondary.add(middleCenterButtons);
		middleCenterButtons.setLayout(new GridLayout(2, 6, 2, 2));
		middleCenterButtons.setPreferredSize(new Dimension(300, 40));
		
		_btnRed = new ViewEditCardColorButton(_mainController, new Color(254, 187, 185));
		middleCenterButtons.add(_btnRed);
		
		_btnOrange = new ViewEditCardColorButton(_mainController, new Color(254, 222, 182));
		middleCenterButtons.add(_btnOrange);
		
		_btnYellow = new ViewEditCardColorButton(_mainController, new Color(254, 238, 179));
		middleCenterButtons.add(_btnYellow);
		
		_btnGreen = new ViewEditCardColorButton(_mainController, new Color(193, 245, 176));
		middleCenterButtons.add(_btnGreen);
		
		_btnBlue = new ViewEditCardColorButton(_mainController, new Color(179, 216, 253));
		middleCenterButtons.add(_btnBlue);
		
		_btnPurple = new ViewEditCardColorButton(_mainController, new Color(246, 213, 254));
		middleCenterButtons.add(_btnPurple);
		
		_btnPink = new ViewEditCardColorButton(_mainController, new Color(254, 192, 210));
		middleCenterButtons.add(_btnPink);
		
		_btnBrown = new ViewEditCardColorButton(_mainController, new Color(237, 222, 203));
		middleCenterButtons.add(_btnBrown);
		
		_btnGraphite = new ViewEditCardColorButton(_mainController, new Color(216, 216, 220));
		middleCenterButtons.add(_btnGraphite);
		
		_btnLightPurple = new ViewEditCardColorButton(_mainController, new Color(204, 204, 255));
		middleCenterButtons.add(_btnLightPurple);
		
		_btnTeal = new ViewEditCardColorButton(_mainController, new Color(153, 255, 204));
		middleCenterButtons.add(_btnTeal);
		
		_btnWhite = new ViewEditCardColorButton(_mainController, Color.WHITE);
		middleCenterButtons.add(_btnWhite);
		
		JPanel middleBottomButtons = new JPanel();
		middleCenterMain.add(middleBottomButtons, BorderLayout.SOUTH);
		middleBottomButtons.setLayout(new GridLayout(2, 2, 2, 2));
		middleBottomButtons.setPreferredSize(new Dimension(350, 50));
		
		_btnCancel = new JButton("Cancel");
		_btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.cancelButton();
			}
		});
		_btnCancel.setFocusable(false);
		middleBottomButtons.add(_btnCancel);
		
		_btnConfirm = new JButton("Edit Back");
		_btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.nextButtonEditJDialog();
			}
		});
		_btnConfirm.setFocusable(false);
		middleBottomButtons.add(_btnConfirm);
		
		_btnDelete = new JButton("Delete");
		_btnDelete.setForeground(Color.RED);
		_btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.delete();
			}
		});
		_btnDelete.setFocusable(false);
		middleBottomButtons.add(_btnDelete);
		
		_btnNewCard = new JButton("Save & Create New Card");
		_btnNewCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.newCardEditCard();
			}
		});
		_btnNewCard.setEnabled(false);
		_btnNewCard.setFocusable(false);
		middleBottomButtons.add(_btnNewCard);
	}
	
	/**
	 * Getter for Create New Card button
	 * @return returns _btnNewCard
	 */
	public JButton getBtnNewCard() {
		return _btnNewCard;
	}
	/**
	 * Getter for Check Box same color button
	 * @return returns _chckbxSameColorFor
	 */
	public JCheckBox getChckbxSameColorFor() {
		return _chckbxSameColorFor;
	}
	/**
	 * Getter for Confirm button
	 * @return Returns _btnConfirm
	 */
	public JButton getBtnConfirm() {
		return _btnConfirm;
	}
	/**
	 * Getter for text pane used to enter the text you want to store
	 * for the current card
	 * @return returns _textPane
	 */
	public JTextPane getTextPane() {
		return _textPane;
	}
}
