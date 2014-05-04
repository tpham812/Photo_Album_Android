package cs213.photoAlbum.model;

import java.util.Collection;
import java.util.Map;


/**
 * Represents the IUser, containing a unique ID, their full name, and the
 * album(s) of the user.
 *
 * @author brett
 */
public interface IUser {
	
	/**
	 * Change the name of the album	.
	 *
	 * @param newAlbumName new album name
	 * @param oldAlbumName old album name
	 */
	
	public void editAlbum(String newAlbumName, String oldAlbumName);

	/**
	 * Adds new album to the list of albums.
	 *
	 * @param album the album
	 */
	abstract void addAlbum(Album album);

	/**
	 * Determines if the user has a album with the specified albumName.
	 *
	 * @param albumName the album name
	 * @return true, if successful
	 */
	abstract boolean containsAlbum(String albumName);

	/**
	 * Gets the album.
	 *
	 * @param albumName the album name
	 * @return the album
	 */
	abstract IAlbum getAlbum(String albumName);

	/**
	 * Adds the photo to the album.
	 *
	 * @param photo the photo
	 * @param album the album
	 */
	abstract void addPhoto(Photo photo, IAlbum album);

	/**
	 * deletes the album.
	 *
	 * @param albumName the album name
	 * @return the IAlbum
	 */
	abstract IAlbum deleteAlbum(String albumName);

	/**
	 * Renames the specified album.
	 * 
	 * @param album The album  which is to be renamed
	 * @return album The newly created album title
	 */
	abstract String rename(IAlbum album);

	/**
	 * To string method.
	 *
	 * @return id The users unique string ID
	 */
	abstract String toString();

	/**
	 * Sets the users ID.
	 * 
	 * @param id Users ID
	 */
	abstract void setUserID(String id);

	/**
	 * Sets the users full name.
	 *
	 * @param fname  Users full name
	 */
	abstract void setUserFullName(String fname);

	/**
	 * Gets the users id.
	 *
	 * @return id users id
	 */
	abstract String getUserID();

	/**
	 * Gets the users full name.
	 * 
	 * @return fname Users full name
	 */
	abstract String getUserFullName();

	/**
	 * Gets the albums associated to the user.
	 *
	 * @return the albums
	 */
	abstract Collection<IAlbum> getAlbums();

	/**
	 * Gets the photos.
	 *
	 * @return the photos
	 */
	abstract Map<String, IPhoto> getPhotos();

	/**
	 * Sets the photos.
	 * 
	 * @param photos the map of photos
	 */
	abstract void setPhotos(Map<String, IPhoto> photos);

}