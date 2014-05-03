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
	 */
	public UserController(){
		this.backend = new Backend();		
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IUserController#listUsers()
	 */
	@Override
	public List<String> listUsers() {
		return backend.listUsers();
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IUserController#addUser(java.lang.String, java.lang.String)
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
	public boolean writeUser(IUser u){
		return backend.writeUser(u);		
	}
	

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IUserController#deleteUser(java.lang.String)
	 */
	@Override
	public boolean deleteUser(String userId) {
		return backend.deleteUser(userId);
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IUserController#login(java.lang.String)
	 */
	@Override
	public IUser login(String userId) {
		return backend.readUser(userId);
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IUserController#logout(cs213.photoAlbum.model.IUser)
	 */
	@Override
	public void logout(IUser u) {
		backend.writeUser(u);		
	}

}
