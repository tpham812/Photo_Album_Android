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
import cs213.photoAlbum.util.Utils;



/**
 * The Class ViewContainer.
 * @author dheeptha
 */
public class ViewContainer {

	/**
	 * Controller to manage user admin.
	 */
	public IUserController userController;

	/** The photo controller. */
	public IPhotoController photoController;

	/** The album controller. */
	public IAlbumController albumController;

	/** The user. */
	private IUser user;
	
	/** The album. */
	private IAlbum album;

	/** The photos. */
	protected List<IPhoto> photos;
	
	/** The photo. */
	protected IPhoto photo;
	

	/** The container. */
	private static ViewContainer container;

	/**
	 * Instantiates a new view container.
	 *
	 * @param dataFolder the data folder
	 */
	public ViewContainer(String dataFolder) {

		this.userController = new UserController(dataFolder);
		this.photoController = new PhotoController(dataFolder);
		this.albumController = new AlbumController(dataFolder);
	}
	
	/**
	 * Inits the.
	 *
	 * @param dataFolder the data folder
	 */
	public static void init(String dataFolder) {
		container = new ViewContainer(dataFolder);
		
		if(container.login("default") == null){
			container.addUser("default", "default");
		}
		container.login("default");
	}
	
	/**
	 * Gets the single instance of ViewContainer.
	 *
	 * @return single instance of ViewContainer
	 */
	public static ViewContainer getInstance(){
		return container;
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
	 * Move photo.
	 *
	 * @param fileName the file name
	 * @param oldAlbumName the old album name
	 * @param newAlbumName the new album name
	 */
	public void movePhoto(String fileName, String oldAlbumName, String newAlbumName) {
		
		albumController.movePhoto(fileName, oldAlbumName, newAlbumName, user);
	}

	/**
	 * Sets the album.
	 *
	 * @param album the new album
	 */
	public void setAlbum(IAlbum album) {
		this.album = album;	
	}

	/**
	 * Gets the photos.
	 *
	 * @return the photos
	 */
	public List<IPhoto> getPhotos() {
		
		if(album != null) {
			this.photos = new ArrayList<IPhoto>(album.getPhotos());
		} else {
			return this.photos;
		}
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
	

	/**
	 * Gets the photo.
	 *
	 * @return the photo
	 */
	public IPhoto getPhoto() {
		return photo;
	}

	/**
	 * Sets the photo.
	 *
	 * @param photo the new photo
	 */
	public void setPhoto(IPhoto photo) {
		this.photo = photo;
	}

}
