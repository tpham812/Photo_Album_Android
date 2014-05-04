package cs213.photoAlbum.control;

import java.util.List;

import cs213.photoAlbum.model.IUser;
import cs213.photoAlbum.model.User;



/**
 * Controller interface for {@link User} functions such as login, logout, add/delete/list users.
 *
 * @author dheeptha
 */
public interface IUserController {

	/**
	 * Provides a list of all current users.
	 *
	 * @return the list
	 */
	List<String> listUsers();

	/**
	 * Adds the user.
	 *
	 * @param userId the user id
	 * @param userName the user name
	 * @return true, if successful
	 */
	boolean addUser(String userId, String userName);

	/**
	 * Deletes the user.
	 *
	 * @param userId the user id
	 * @return true, if successful
	 */
	boolean deleteUser(String userId);

	/**
	 * Logs in the user.
	 *
	 * @param userId the user id
	 * @return the user
	 */
	IUser login(String userId);

	/**
	 * Logs out the users.
	 *
	 * @param u the IUser object
	 */
	void logout(IUser u);
	
	/**
	 * Write user.
	 *
	 * @param u the u
	 * @return true, if successful
	 */
	boolean writeUser(IUser u);

}
