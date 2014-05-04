package cs213.photoAlbum.simpleview;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;

import cs213.photoAlbum.util.DefaultListModelAction;



/**
 * Creates the GUI for Admin.
 *
 * @author Truong Pham
 */
public class Admin {

	/** The gui view. */
	GuiView guiView;
	
	/** The frame2. */
	JFrame frame, frame2;
	
	/** The button listener. */
	ActionListener buttonListener;
	
	/** The panel. */
	JPanel[] panel = new JPanel[6];
	
	/** The button. */
	JButton[] button = new JButton[3];
	
	/** The close button. */
	JButton closeButton;
	
	/** The label. */
	JLabel[] label = new JLabel[2];
	
	/** The error label. */
	JLabel errorLabel;
	
	/** The tf. */
	JTextField[] tf = new JTextField[2];
	
	/** The list. */
	JList<DefaultListModel<String>> list;
	
	/** The sp. */
	JScrollPane sp;
	
	/** The user model. */
	DefaultListModel<String> userModel = new DefaultListModel<String>();
	
	/** The model action. */
	DefaultListModelAction modelAction = new DefaultListModelAction();
	
	
	/**
	 * Constructor that initializes GUI components
	 *  
	 * @param gv guiView object
	 */
	public Admin(GuiView gv) {
		
		guiView = gv;
		frame = new JFrame("Admin");
		buttonListener = new ButtonListener(this);
		panel[0] = new JPanel();
		panel[0].setLayout(new BoxLayout(panel[0], BoxLayout.Y_AXIS));
		panel[1] = new JPanel();
		panel[1].setLayout(new BoxLayout(panel[1], BoxLayout.X_AXIS));
		panel[2] = new JPanel();
		panel[2].setLayout(new BoxLayout(panel[2], BoxLayout.X_AXIS));
		panel[3] = new JPanel();
		panel[3].setLayout(new BoxLayout(panel[3], BoxLayout.X_AXIS));
		panel[4] = new JPanel();
		panel[4].setLayout(new BoxLayout(panel[4], BoxLayout.X_AXIS));
		button[0] = new JButton("Logout");
		button[0].addActionListener(buttonListener);
		button[1] = new JButton("Add User");
		button[1].addActionListener(buttonListener);
		button[2] = new JButton("Delete");
		button[2].addActionListener(buttonListener);
		tf[0] = new JTextField();
		tf[1] = new JTextField();
		List<String> listUsers = guiView.viewContainer.listUser();
		String[] users = new String[listUsers.size()];
		listUsers.toArray(users);
		modelAction.newList(userModel, users);
		list = new JList(userModel);
		if(!userModel.isEmpty())
			list.setSelectedIndex(0);
		sp = new JScrollPane(list);
		label[0] = new JLabel("User ID:   ");
		label[1] = new JLabel("Full Name:   ");
		createAdminPanel();
		createUserExistErrorPanel();
	}
	
	/**
	 * Creates the admin panel.
	 */
	public void createAdminPanel() {
		
		frame.setSize(500, 515);
		frame.setMaximumSize(new Dimension(500,515));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tf[0].setEditable(true);
		tf[0].setMaximumSize(new Dimension(125,20));
		tf[1].setEditable(true);
		tf[1].setMaximumSize(new Dimension(125,20));
		
		sp.setMaximumSize(new Dimension(350, 350));
		
		panel[1].add(Box.createRigidArea(new Dimension(420,0)));
		panel[1].add(button[0]);
		
		panel[2].add(label[0]);
		panel[2].add(tf[0]);
		panel[2].add(Box.createRigidArea(new Dimension(10,0)));
		panel[2].add(button[1]);
		
		panel[3].add(Box.createRigidArea(new Dimension(17,0)));
		panel[3].add(sp);
		panel[3].add(Box.createRigidArea(new Dimension(20,0)));
		panel[3].add(button[2]);
		
		panel[4].add(label[1]);
		panel[4].add(tf[1]);
		panel[4].add(Box.createRigidArea(new Dimension(110,0)));
		
		panel[0].add(panel[1]);
		panel[0].add(Box.createRigidArea(new Dimension(0,15)));
		panel[0].add(panel[4]);
		panel[0].add(Box.createRigidArea(new Dimension(0,10)));
		panel[0].add(panel[2]);
		panel[0].add(Box.createRigidArea(new Dimension(0,15)));
		panel[0].add(panel[3]);
		
		frame.add(panel[0]);
		frame.setVisible(false);
	}
	
	/**
	 * Creates the error panel for error handling.
	 */
	public void createUserExistErrorPanel() {
		
		frame2 = new JFrame("Error");
		panel[5] = new JPanel();
		panel[5].setLayout(new BoxLayout(panel[5], BoxLayout.Y_AXIS));
		closeButton = new JButton("Close");
		closeButton.addActionListener(new ButtonListener(this));
		errorLabel = new JLabel();
		errorLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		closeButton.setAlignmentX(JButton.CENTER_ALIGNMENT);
		panel[5].add(Box.createRigidArea(new Dimension (0, 30)));
		panel[5].add(errorLabel);
		panel[5].add(Box.createRigidArea(new Dimension(0, 35)));
		panel[5].add(closeButton);
		frame2.add(panel[5]);
		frame2.setLocationRelativeTo(null);
		frame2.setResizable(false);
		frame2.setVisible(false);
		frame2.addWindowListener(new PanelListener(this));
	}
	
	/**
	 * Shows the admin panel.
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

		/** The admin. */
		Admin admin;
		
		/**
		 * Instantiates a new button listener.
		 *
		 * @param ad the ad
		 */
		public ButtonListener(Admin ad) {
			
			admin = ad;
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("deprecation")
		public void actionPerformed(ActionEvent e) {
			
			if(e.getSource() == admin.button[0]) {
				admin.tf[0].setText(null);
				admin.tf[1].setText(null);
				admin.frame.setVisible(false);;
				admin.guiView.login.show();
			}
			
			else if(e.getSource() == admin.button[1]) {
				String userID = admin.tf[0].getText().trim();
				String fullName = admin.tf[1].getText().trim();
				if(userID.equals("") || fullName.equals("")) {
					admin.errorLabel.setText("Must enter in User ID and Full Name");
					admin.frame.disable();
					admin.frame2.setSize(250, 150);
					admin.frame2.setVisible(true);
				}
				else {
					boolean isExist = admin.guiView.viewContainer.addUser(userID, fullName);
					if(!isExist) {
						admin.tf[0].setText(null);
						admin.tf[1].setText(null);
						admin.errorLabel.setText("User already exists");
						admin.frame2.setSize(200, 150);
						admin.frame.disable();
						admin.frame2.setVisible(true);
					}
					else {
						List<String> users = admin.guiView.viewContainer.listUser();
						String[] tempUser = new String[users.size()];
						users.toArray(tempUser);
						admin.modelAction.newList(admin.userModel, tempUser);
						admin.list.setSelectedIndex(0);
						admin.tf[0].setText(null);
						admin.tf[1].setText(null);
					}
				}
			}
			else if(e.getSource() == admin.button[2]) {
			
				int index = admin.list.getSelectedIndex();
				String user = admin.userModel.get(index);
				admin.guiView.viewContainer.deleteUser(user);
				List<String> users = admin.guiView.viewContainer.listUser();
				String[] tempUser = new String[users.size()];
				users.toArray(tempUser);
				admin.modelAction.newList(admin.userModel, tempUser);
				if(!admin.userModel.isEmpty())
					admin.list.setSelectedIndex(0);
				
			}
			else if(e.getSource() == admin.closeButton) {
				admin.frame.enable();
				admin.frame2.setVisible(false);
			}
		}
	}
	
	/**
	 * The listener interface for receiving panel events.
	 * The class that is interested in processing a panel
	 * event implements this interface, and the object created
	 * with that class is registered with a component using the
	 * component's <code>addPanelListener<code> method. When
	 * the panel event occurs, that object's appropriate
	 * method is invoked.
	 *
	 * @see PanelEvent
	 */
	class PanelListener implements WindowListener {

		/** The admin. */
		Admin admin;
		
		/**
		 * Instantiates a new panel listener.
		 *
		 * @param ad the ad
		 */
		public PanelListener(Admin ad) {
			
			admin = ad;
		}
		
		/* (non-Javadoc)
		 * @see java.awt.event.WindowListener#windowClosing(java.awt.event.WindowEvent)
		 */
		@SuppressWarnings("deprecation")
		public void windowClosing(WindowEvent arg0) {
			
			admin.frame.enable();
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