package cs213.photoAlbum.model;

import java.io.File;
import java.util.List;


/**
 * IBackend is the interface to perform backend functions such as storage/retrieval of files and saving the user data.
 * @author dheeptha
 * 
 */
public interface IBackend {

	/**
	 * Retrieve the user's photo file.
	 *
	 * @param photo the photo
	 * @param user the user
	 * @return the file
	 */
	File retrievePhotoFile(IPhoto photo, IUser user);

	/**
	 * Reads the user.
	 *
	 * @param userId the users id
	 * @return the user
	 */
	IUser readUser(String userId);

	/**
	 * Writes a new user.
	 *
	 * @param u the user
	 * @return true, if successful
	 */
	boolean writeUser(IUser u);

	/**
	 * Deletes the user.
	 *
	 * @param userId the user id
	 * @return true, if successful
	 */
	boolean deleteUser(String userId);

	/**
	 * Gets the user according to the userids.
	 *
	 * @param userIds the user ids
	 * @return the user
	 */
	List<User> getUser(List<String> userIds);

	/**
	 * Gets the file.
	 *
	 * @param fileName the file name
	 * @return the file
	 */
	File getFile(String fileName);

}
