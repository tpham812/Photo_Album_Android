package cs213.photoAlbum.control;

import java.util.List;

import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.model.IUser;
import cs213.photoAlbum.model.User;



/**
 * Controller interface for {@link User} has functions such as login, logout, add/delete/list users.
 *
 * @author dheeptha
 */
public class UserController implements IUserController {
	
	/** The backend. */
	private Backend backend;
	
	/**
	 * Instantiates a new user controller.
	 *
	 * @param dataFolder the data folder
	 */
	public UserController(String dataFolder){
		this.backend = new Backend(dataFolder);		
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IUserController#listUsers()
	 */
	/**
	 * List users.
	 *
	 * @return the list
	 */
	@Override
	public List<String> listUsers() {
		return backend.listUsers();
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IUserController#addUser(java.lang.String, java.lang.String)
	 */
	/**
	 * Adds the user.
	 *
	 * @param userId the user id
	 * @param userName the user name
	 * @return true, if successful
	 */
	@Override
	public boolean addUser(String userId, String userName) {

		IUser u = backend.readUser(userId);
		if(u != null){
			return false;
		}
		
		u = new User(userId, userName);		
		return backend.writeUser(u);		
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IUserController#writeUser(cs213.photoAlbum.model.IUser)
	 */
	/**
	 * Write user.
	 *
	 * @param u the u
	 * @return true, if successful
	 */
	public boolean writeUser(IUser u){
		return backend.writeUser(u);		
	}
	

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IUserController#deleteUser(java.lang.String)
	 */
	/**
	 * Delete user.
	 *
	 * @param userId the user id
	 * @return true, if successful
	 */
	@Override
	public boolean deleteUser(String userId) {
		return backend.deleteUser(userId);
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IUserController#login(java.lang.String)
	 */
	/**
	 * Login.
	 *
	 * @param userId the user id
	 * @return the i user
	 */
	@Override
	public IUser login(String userId) {
		return backend.readUser(userId);
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IUserController#logout(cs213.photoAlbum.model.IUser)
	 */
	/**
	 * Logout.
	 *
	 * @param u the u
	 */
	@Override
	public void logout(IUser u) {
		backend.writeUser(u);		
	}

}
