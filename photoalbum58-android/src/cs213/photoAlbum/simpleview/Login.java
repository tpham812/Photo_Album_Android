package cs213.photoAlbum.simpleview;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs213.photoAlbum.model.IUser;


/**
 * Creates the GUI for login .
 *
 * @author Truong Pham
 */
public class Login {

	/** The gui view. */
	GuiView guiView;
	
	/** The frame2. */
	JFrame frame, frame2;
	
	/** The button listener. */
	ActionListener buttonListener;
	
	/** The panel. */
	JPanel[] panel = new JPanel[4];
	
	/** The close button. */
	JButton button, closeButton;
	
	/** The error label. */
	JLabel label, errorLabel;
	
	/** The tf. */
	JTextField tf;
	
	
	/**
	 * Constructor that initializes GUI components.
	 *
	 * @param gv GuiView object
	 */
	public Login(GuiView gv) {
		
		guiView = gv;
		frame = new JFrame("Login");
		panel[0] = new JPanel();
		panel[0].setLayout(new BoxLayout(panel[0], BoxLayout.Y_AXIS));
		panel[1] = new JPanel();
		panel[1].setLayout(new BoxLayout(panel[1], BoxLayout.Y_AXIS));
		panel[2] = new JPanel();
		panel[2].setLayout(new BoxLayout(panel[2], BoxLayout.X_AXIS));
		button = new JButton("Login");
		button.addActionListener(new ButtonListener(this));
		createLoginPanel();
		createErrorPanel();
	}
		
	/**
	 * Create the login panel.
	 */
	public void createLoginPanel() {	

		frame.setSize(500, 150);
		frame.setMaximumSize(new Dimension(500,200));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tf = new JTextField();
		tf.setEditable(true);
		tf.setMaximumSize(new Dimension(125,20));
		
		label = new JLabel("User ID:     ");
		label.setFont(new Font(null, Font.BOLD, 12));
		
		panel[1].add(Box.createRigidArea(new Dimension(0,25)));
		panel[2].add(label);
		panel[2].add(tf);
		panel[2].add(Box.createRigidArea((new Dimension(15,0))));
		panel[2].add(button);
		panel[0].add(panel[1]);
		panel[0].add(Box.createRigidArea(new Dimension(0,25)));
		panel[0].add(panel[2]);
		
		frame.add(panel[0]);
		frame.setVisible(false);
	}
	
	/**
	 * Creates the error panel for error handling.
	 */
	public void createErrorPanel() {
		
		frame2 = new JFrame("Error");
		panel[3] = new JPanel();
		panel[3].setLayout(new BoxLayout(panel[3], BoxLayout.Y_AXIS));
		closeButton = new JButton("Close");
		closeButton.addActionListener(new ButtonListener(this));
		errorLabel = new JLabel("User does not exist");
		errorLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		closeButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		panel[3].add(Box.createRigidArea(new Dimension (0, 30)));
		panel[3].add(errorLabel);
		panel[3].add(Box.createRigidArea(new Dimension(0, 35)));
		panel[3].add(closeButton);
		frame2.add(panel[3]);
		frame2.setSize(new Dimension(200,150));
		frame2.setLocationRelativeTo(null);
		frame2.setResizable(false);
		frame2.setVisible(false);
		frame2.addWindowListener(new panelListener(this));
	}
	
	/**
	 * Shows the login panel.
	 */
	public void show() {
		
		frame.setVisible(true);
	}
	
	/**
	 * The listener interface for receiving button events.
	 * The class that is interested in processing a button
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addButtonListener<code> method. When
	 * the button event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see ButtonEvent
	 */
	class ButtonListener implements ActionListener {
		
		/** The login. */
		Login login;
		
		/**
		 * Instantiates a new button listener.
		 *
		 * @param l the l
		 */
		public ButtonListener(Login l) {
			
			this.login = l;
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == login.button) {
				String userId = login.tf.getText().trim();
				if(!userId.isEmpty()) {
					IUser u = login.guiView.viewContainer.login(userId);
					if(u != null) {
						login.frame.setVisible(false);
						if(u.getUserID().equals("admin")) {
							login.tf.setText(null);
							login.guiView.admin.show();	
							
						} else {
							login.tf.setText(null);
							login.guiView.viewContainer.setUser(u);
							login.guiView.albums.show();
						
						}	
					}
					else {
						login.tf.setText(null);
						login.frame.disable();
						login.frame2.setVisible(true);
					}
				}
			}
			else {
				login.frame.enable();
				login.frame2.setVisible(false);
			}
		}
	}
	
	/**
	 * The listener interface for receiving panel events.
	 * The class that is interested in processing a panel
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addpanelListener<code> method. When
	 * the panel event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see panelEvent
	 */
	class panelListener implements WindowListener {

		/** The login. */
		Login login;
		
		/**
		 * Instantiates a new panel listener.
		 *
		 * @param l the l
		 */
		public panelListener(Login l) {
			
			login = l;
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
		 */
		@SuppressWarnings("deprecation")
		public void windowClosing(WindowEvent arg0) {
			
			login.frame.enable();
		}

		/* (non-Javadoc)
		 * @see java.awt.event.WindowListener#windowActivated(java.awt.event.WindowEvent)
		 */
		public void windowActivated(WindowEvent arg0) {}

		/* (non-Javadoc)
		 * @see java.awt.event.WindowListener#windowClosed(java.awt.event.WindowEvent)
		 */
		public void windowClosed(WindowEvent arg0) {}
		
		/* (non-Javadoc)
		 * @see java.awt.event.WindowListener#windowDeactivated(java.awt.event.WindowEvent)
		 */
		public void windowDeactivated(WindowEvent arg0) {}

		/* (non-Javadoc)
		 * @see java.awt.event.WindowListener#windowDeiconified(java.awt.event.WindowEvent)
		 */
		public void windowDeiconified(WindowEvent arg0) {}

		/* (non-Javadoc)
		 * @see java.awt.event.WindowListener#windowIconified(java.awt.event.WindowEvent)
		 */
		public void windowIconified(WindowEvent arg0) {}

		/* (non-Javadoc)
		 * @see java.awt.event.WindowListener#windowOpened(java.awt.event.WindowEvent)
		 */
		public void windowOpened(WindowEvent arg0) {}
	}
}