package cs213.photoAlbum.util;

import javax.swing.DefaultComboBoxModel;


/**
 * The Class DefaultComboBoxModelAction.
 * @author Truong Pham
 */
public class DefaultComboBoxModelAction {

	/**
	 * New combo box.
	 *
	 * @param model the model
	 * @param date the date
	 */
	public void newComboBox(DefaultComboBoxModel<String> model, int[] date) {
		
		model.addElement("");
		for(int i = 0; i < date.length; i++) {
			model.addElement(Integer.toString(date[i]));
		}
	}
	
	/**
	 * Sets the year combo box.
	 *
	 * @param model the model
	 * @param max the max
	 * @param min the min
	 */
	public void setYearComboBox(DefaultComboBoxModel<String> model, int max, int min) {
		
		model.removeAllElements();
		model.addElement("");
		for(int i = min; i  <= max; i++) {
			model.addElement(Integer.toString(i));
		}
	}
	
	/**
	 * Sets the hour combo box.
	 *
	 * @param model the new hour combo box
	 */
	public void setHourComboBox(DefaultComboBoxModel<String> model) {
		
		model.addElement("");
		for(int i = 0; i < 24; i++) {
			model.addElement(Integer.toString(i));
		}
	}
	
	/**
	 * Sets the minute_ seconds combo box.
	 *
	 * @param model the model
	 * @param model2 the model2
	 */
	public void setMinute_SecondsComboBox(DefaultComboBoxModel<String> model, DefaultComboBoxModel<String> model2) {
		
		model.addElement("");
		model2.addElement("");
		for(int i = 0; i < 60; i++) {
			model.addElement(Integer.toString(i));
			model2.addElement(Integer.toString(i));
		}
	}
}
