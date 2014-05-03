package cs213.photoAlbum.control;

import java.util.Calendar;
import java.util.List;
import java.util.SortedSet;

import cs213.photoAlbum.model.IPhoto;
import cs213.photoAlbum.model.IUser;
import cs213.photoAlbum.model.Photo;



/**
 * Controller interface for {@link Photo} management, such as adding/deleting tags, get photos by date range/tag.
 * @author dheeptha
 * 
 */
public interface IPhotoController {

	/**
	 * Adds a tag to the user's photo.
	 *
	 * @param fileName the file name
	 * @param tagType the tag type
	 * @param tagValue the tag value
	 * @param user the user
	 * @return true, if successful
	 */
	boolean addTag(String fileName, String tagType, String tagValue, IUser user);

	/**
	 * Delete a tag associated with the user's photo.
	 *
	 * @param fileName the file name
	 * @param tagType the tag type
	 * @param tagValue the tag value
	 * @param user the user
	 * @return true, if successful
	 */
	boolean deleteTag(String fileName, String tagType, String tagValue, IUser user);

	/**
	 * Gets the photos by date range for the user, from start to end.
	 *
	 * @param start the start date
	 * @param end the end date
	 * @param user the user
	 * @return the photos by date
	 */
	SortedSet<IPhoto> getPhotosByDate(Calendar start, Calendar end, IUser user);

	/**
	 * Gets the photos by tag for the user.
	 *
	 * @param tagNames the list of tag names
	 * @param tagValues the list tag values
	 * @param user the user
	 * @return the photos by tag
	 */
	SortedSet<IPhoto> getPhotosByTag(List<String> tagNames, List<String> tagValues , IUser user);

	/**
	 * Determines if the user already has the photo.
	 *
	 * @param fileName the file name
	 * @param user the user
	 * @return true, if successful
	 */
	boolean containsPhoto(String fileName, IUser user);	

	/**
	 * Determines if the specified file exists.
	 *
	 * @param fileName the file name
	 * @return true, if successful
	 */
	public boolean fileExists(String fileName);
}
