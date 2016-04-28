import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;


/**
 * @author Trinh Nguyen
 * @Discription This class is used by MainScreenJPanel to edit, delete, and add new cards to the deck
 */
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
	 * Scroll Pane to add scroll bar to the textPane
	 */
	private JScrollPane _scrollPane;
	/**
	 * Text pane for entering in String for front and
	 * back data of the current card
	 */
	private JTextPane _textPane;
	/**
	 * Red color button to change card color to this color.
	 */
	private JButton _btnRed;
	/**
	 * Purple color button to change card color to this color.
	 */
	private JButton _btnPurple;
	/**
	 * Orange color button to change card color to this color.
	 */
	private JButton _btnOrange;
	/**
	 * Pink color button to change card color to this color.
	 */
	private JButton _btnPink;
	/**
	 * Yellow color button to change card color to this color.
	 */
	private JButton _btnYellow;
	/**
	 * Brown color button to change card color to this color.
	 */
	private JButton _btnBrown;
	/**
	 * Green color button to change card color to this color.
	 */
	private JButton _btnGreen;
	/**
	 * Graphite color button to change card color to this color.
	 */
	private JButton _btnGraphite;
	/**
	 * Blue color button to change card color to this color.
	 */
	private JButton _btnBlue;
	/**
	 * Purple color button to change card color to this color.
	 */
	private JButton _btnLightPurple;
	/**
	 * Teal color button to change card color to this color.
	 */
	private JButton _btnTeal;
	/**
	 * White color button to change card color to this color.
	 */
	private JButton _btnWhite;
	/**
	 * Delete card button
	 */
	private JButton _btnDelete;
	/**
	 * Check box that when checked will keep all newly created cards the
	 * same color
	 */
	private JCheckBox _chckbxSameColorFor;
	/**
	 * Create new card quickly button
	 */
	private JButton _btnNewCard;
	/**
	 * A reference to the main JFrame this JDialog is tied to
	 * Needed for centering this JDialog on the center of the JFrame
	 */
	private JFrame _topFrame;
	/**
	 * A reference to the main controller needed to call certain
	 * methods when a button is pushed
	 */
	private ControllerFlashCardQueueMain _mainController;
	
	/**
	 * Constructor to set up the components in the JDialog
	 * @param mainController Reference to main controller
	 * @param topFrame Reference to the main JFrame
	 */
	public ViewEditCardJDialog(ControllerFlashCardQueueMain mainController, JFrame topFrame) {
		super(topFrame, "", Dialog.ModalityType.DOCUMENT_MODAL);
		_topFrame = topFrame;
		_mainController = mainController;
		setResizable(false);
		setModal(true);
		setUndecorated(true);
		pack();
		setSize(new Dimension(310, 310));
		setLocationRelativeTo(topFrame);

		_btnCancel = new JButton("Cancel");
		_btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.cancelButton();
			}
		});
		_btnCancel.setFocusable(false);

		_btnConfirm = new JButton("Edit Back");
		_btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.nextButtonEditJDialog();
			}
		});
		_btnConfirm.setFocusable(false);

		_btnNewCard = new JButton("Save & Create New Card");
		_btnNewCard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.newCardEditCard();
			}
		});
		_btnNewCard.setEnabled(false);
		_btnNewCard.setFocusable(false);

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
		
		_btnDelete = new JButton("Delete");
		_btnDelete.setForeground(Color.RED);
		_btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.delete();
			}
		});
		_btnDelete.setFocusable(false);

		_scrollPane = new JScrollPane();
		
		// These buttons changes the color of the card when they are clicked on
		_btnRed = new JButton("");
		_btnRed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.setColor(new Color(254, 187, 185));
			}
		});
		_btnRed.setBackground(new Color(254, 187, 185));
		_btnRed.setOpaque(true);
		_btnRed.setBorderPainted(false);
		_btnRed.setFocusable(false);

		_btnPurple = new JButton("");
		_btnPurple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.setColor(new Color(246, 213, 254));
			}
		});
		_btnPurple.setBackground(new Color(246, 213, 254));
		_btnPurple.setOpaque(true);
		_btnPurple.setBorderPainted(false);
		_btnPurple.setFocusable(false);

		_btnOrange = new JButton("");
		_btnOrange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.setColor(new Color(254, 222, 182));
			}
		});
		_btnOrange.setBackground(new Color(254, 222, 182));
		_btnOrange.setOpaque(true);
		_btnOrange.setBorderPainted(false);
		_btnOrange.setFocusable(false);

		_btnPink = new JButton("");
		_btnPink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.setColor(new Color(254, 192, 210));
			}
		});
		_btnPink.setBackground(new Color(254, 192, 210));
		_btnPink.setOpaque(true);
		_btnPink.setBorderPainted(false);
		_btnPink.setFocusable(false);

		_btnYellow = new JButton("");
		_btnYellow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.setColor(new Color(254, 238, 179));
			}
		});
		_btnYellow.setBackground(new Color(254, 238, 179));
		_btnYellow.setOpaque(true);
		_btnYellow.setBorderPainted(false);
		_btnYellow.setFocusable(false);

		_btnBrown = new JButton("");
		_btnBrown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.setColor(new Color(237, 222, 203));
			}
		});
		_btnBrown.setBackground(new Color(237, 222, 203));
		_btnBrown.setOpaque(true);
		_btnBrown.setBorderPainted(false);
		_btnBrown.setFocusable(false);

		_btnGreen = new JButton("");
		_btnGreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.setColor(new Color(193, 245, 176));
			}
		});
		_btnGreen.setBackground(new Color(193, 245, 176));
		_btnGreen.setOpaque(true);
		_btnGreen.setBorderPainted(false);
		_btnGreen.setFocusable(false);

		_btnGraphite = new JButton("");
		_btnGraphite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.setColor(new Color(216, 216, 220));
			}
		});
		_btnGraphite.setBackground(new Color(216, 216, 220));
		_btnGraphite.setOpaque(true);
		_btnGraphite.setBorderPainted(false);
		_btnGraphite.setFocusable(false);

		_btnBlue = new JButton("");
		_btnBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.setColor(new Color(179, 216, 253));
			}
		});
		_btnBlue.setBackground(new Color(179, 216, 253));
		_btnBlue.setOpaque(true);
		_btnBlue.setBorderPainted(false);
		_btnBlue.setFocusable(false);

		_btnLightPurple = new JButton("");
		_btnLightPurple.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.setColor(new Color(204, 204, 255));
			}
		});
		_btnLightPurple.setBackground(new Color(204, 204, 255));
		_btnLightPurple.setOpaque(true);
		_btnLightPurple.setBorderPainted(false);
		_btnLightPurple.setFocusable(false);

		_btnTeal = new JButton("");
		_btnTeal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.setColor(new Color(153, 255, 204));
			}
		});
		_btnTeal.setBackground(new Color(153, 255, 204));
		_btnTeal.setOpaque(true);
		_btnTeal.setBorderPainted(false);
		_btnTeal.setFocusable(false);

		_btnWhite = new JButton("");
		_btnWhite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_mainController.setColor(Color.WHITE);
			}
		});
		_btnWhite.setBackground(Color.WHITE);
		_btnWhite.setOpaque(true);
		_btnWhite.setBorderPainted(false);
		_btnWhite.setFocusable(false);

		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(20)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(_chckbxSameColorFor, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(_btnCancel, 0, 0, Short.MAX_VALUE)
										.addComponent(_btnDelete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(_btnNewCard, GroupLayout.PREFERRED_SIZE, 174, Short.MAX_VALUE)
										.addComponent(_btnConfirm, GroupLayout.DEFAULT_SIZE, 174, Short.MAX_VALUE)))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(_scrollPane)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(_btnPink, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(_btnBrown, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(_btnGraphite, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(_btnLightPurple, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(_btnRed, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(_btnOrange, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(_btnYellow, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(_btnGreen, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(_btnTeal, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(_btnWhite, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(_btnBlue, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(_btnPurple, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)))))))
					.addContainerGap(187, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(19)
					.addComponent(_scrollPane, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(_btnPurple)
								.addComponent(_btnBlue))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(_btnTeal)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(_chckbxSameColorFor))
								.addComponent(_btnWhite)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(_btnRed)
								.addComponent(_btnOrange)
								.addComponent(_btnYellow)
								.addComponent(_btnGreen))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(_btnGraphite)
								.addComponent(_btnBrown)
								.addComponent(_btnPink)
								.addComponent(_btnLightPurple))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(_btnCancel)
						.addComponent(_btnConfirm))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(_btnDelete)
						.addComponent(_btnNewCard))
					.addContainerGap())
		);

		_textPane = new JTextPane();
		_textPane.setText("");
		_scrollPane.setViewportView(_textPane);
		_scrollPane.setMinimumSize(new Dimension(260, 0));
		_scrollPane.setMaximumSize(new Dimension(260, 0));
		getContentPane().setLayout(groupLayout);
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
