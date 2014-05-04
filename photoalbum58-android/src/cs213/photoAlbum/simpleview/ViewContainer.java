package cs213.photoAlbum.simpleview;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;

import cs213.photoAlbum.control.AlbumController;
import cs213.photoAlbum.control.IAlbumController;
import cs213.photoAlbum.control.IPhotoController;
import cs213.photoAlbum.control.IUserController;
import cs213.photoAlbum.control.PhotoController;
import cs213.photoAlbum.control.UserController;
import cs213.photoAlbum.model.IAlbum;
import cs213.photoAlbum.model.IPhoto;
import cs213.photoAlbum.model.IUser;
import cs213.photoAlbum.util.CalendarUtils;


/**
 * The Class ViewContainer.
 * @author dheeptha
 */
public class ViewContainer {

	/**
	 * Controller to manage user admin.
	 */
	protected IUserController userController;

	/** The photo controller. */
	protected IPhotoController photoController;

	/** The album controller. */
	protected IAlbumController albumController;

	/** The user. */
	private IUser user;
	
	/** The album. */
	private IAlbum album;

	/** The photos. */
	protected List<IPhoto> photos;

	/**
	 * Instantiates a new view container.
	 */
	public ViewContainer() {

		this.userController = new UserController();
		this.photoController = new PhotoController();
		this.albumController = new AlbumController();
	}

	/**
	 * Login.
	 *
	 * @param userId the user id
	 * @return the i user
	 */
	public IUser login(String userId) {

		user = userController.login(userId);
		return user;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public IUser getUser() {
		return user;
	}

	/**
	 * List user.
	 *
	 * @return the list
	 */
	public List<String> listUser() {

		return userController.listUsers();
	}

	/**
	 * Adds the user.
	 *
	 * @param userId the user id
	 * @param userName the user name
	 * @return true, if successful
	 */
	public boolean addUser(String userId, String userName) {

		return userController.addUser(userId, userName);
	}

	/**
	 * Delete user.
	 *
	 * @param userId the user id
	 * @return true, if successful
	 */
	public boolean deleteUser(String userId) {

		return userController.deleteUser(userId);
	}

	/**
	 * Creates the album.
	 *
	 * @param albumName the album name
	 * @return true, if successful
	 */
	public boolean createAlbum(String albumName) {

		return albumController.createAlbum(albumName, user);
	}

	/**
	 * Gets the album.
	 *
	 * @param albumName the album name
	 * @return the album
	 */
	public IAlbum getAlbum(String albumName) {

		return albumController.getAlbum(albumName, user);
	}

	/**
	 * Checks if is album exist.
	 *
	 * @param albumName the album name
	 * @return true, if is album exist
	 */
	public boolean isAlbumExist(String albumName) {

		return user.containsAlbum(albumName);
	}

	/**
	 * Edits the album.
	 *
	 * @param newAlbumName the new album name
	 * @param oldAlbumName the old album name
	 */
	public void editAlbum(String newAlbumName, String oldAlbumName) {

		albumController.editAlbum(newAlbumName, oldAlbumName, user);
	}

	/**
	 * Logout.
	 */
	public void logout() {

		userController.logout(user);
	}

	/**
	 * Delete album.
	 *
	 * @param albumName the album name
	 * @return true, if successful
	 */
	public boolean deleteAlbum(String albumName) {

		return albumController.deleteAlbum(albumName, user);
	}

	/**
	 * List albums.
	 *
	 * @return the collection
	 */
	public Collection<IAlbum> listAlbums() {
		Collection<IAlbum> albums = albumController.listAlbums(user);

		return albums;
	}

	/**
	 * Save user.
	 *
	 * @return true, if successful
	 */
	public boolean saveUser() {
		return userController.writeUser(user);
	}

	/**
	 * Gets the album.
	 *
	 * @return the album
	 */
	public IAlbum getAlbum() {
		return album;
	}

	/**
	 * Sets the album.
	 *
	 * @param album the new album
	 */
	public void setAlbum(IAlbum album) {
		this.album = album;
		
		if(album != null) {
			this.photos = new ArrayList<IPhoto>(album.getPhotos());
		} else {
			this.photos = null;
		}
	}

	/**
	 * Gets the photos.
	 *
	 * @return the photos
	 */
	public List<IPhoto> getPhotos() {
		return photos;
	}

	/**
	 * Sets the photos.
	 *
	 * @param photos the new photos
	 */
	public void setPhotos(Collection<IPhoto> photos) {
		this.photos = new ArrayList<IPhoto>(photos);
	}

	/**
	 * Gets the photos by date.
	 *
	 * @param start the start
	 * @param end the end
	 * @return the photos by date
	 */
	public SortedSet<IPhoto> getPhotosByDate(Calendar start, Calendar end) {
		
		return  photoController.getPhotosByDate(start, end, user);
	}
	
	/**
	 * Gets the photos by tag.
	 *
	 * @param tagNames the tag names
	 * @param tagValues the tag values
	 * @return the photos by tag
	 */
	public SortedSet<IPhoto> getPhotosByTag(List<String> tagNames, List<String> tagValues) {
		
		return photoController.getPhotosByTag(tagNames, tagValues, user);
	}
	
	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(IUser user) {
		this.user = user;
	}

}
