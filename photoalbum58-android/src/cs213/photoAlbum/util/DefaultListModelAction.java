package cs213.photoAlbum.util;

import javax.swing.DefaultListModel;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultListModelAction.
 * @author Truong Pham
 */
public class DefaultListModelAction {

	
	/**
	 * New list.
	 *
	 * @param model the model
	 * @param users the users
	 */
	public void newList(DefaultListModel<String> model, String[] users) {
		
		model.clear();
		for(int count = 0; count < users.length; count++) {
			model.addElement(users[count]);
		}
	}
}