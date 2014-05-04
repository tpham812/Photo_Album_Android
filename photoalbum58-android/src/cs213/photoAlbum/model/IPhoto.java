package cs213.photoAlbum.model;

import java.util.Calendar;
import java.util.Map;
import java.util.SortedSet;


/**
 * Photo owned by a {@link IUser} and part of multiple {@link IAlbum}s.
 * @author dheeptha
 * 
 **/
public interface IPhoto {

	/**
	 * Gets the name of the photo.
	 *
	 * @return the name
	 */
	abstract String getName();

	/**
	 * Sets the name of the photo.
	 *
	 * @param name name of photo
	 */
	abstract void setName(String name);

	/**
	 * Gets the caption of the photo.
	 *
	 * @return the caption
	 */
	abstract String getCaption();

	/**
	 * Sets the caption of the photo.
	 *
	 * @param caption caption of the photo
	 */
	abstract void setCaption(String caption);

	/**
	 * Gets the time and date the photo was taken.
	 *
	 * @return the date and time
	 */
	abstract Calendar getDateTime();

	/**
	 * Sets the date and time the photo was taken.
	 *
	 * @param dateTime the date and time
	 */
	abstract void setDateTime(Calendar dateTime);

	/**
	 * Gets the tags of the photo.
	 *
	 * @return the tags
	 */
	abstract Map<String, SortedSet<String>> getTags();

	/**
	 * Sets the tags of a photo.
	 *
	 * @param tags the tags
	 */
	abstract void setTags(Map<String, SortedSet<String>> tags);

}