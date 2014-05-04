package cs213.photoAlbum.control;

import java.util.Collection;
import java.util.SortedSet;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.IAlbum;
import cs213.photoAlbum.model.IPhoto;
import cs213.photoAlbum.model.IUser;
import cs213.photoAlbum.model.Photo;



/**
 * Controller interface to create/delete {@link Album}, and to manage {@link Photo} in the {@link Album}.
 *
 * @author dheeptha
 * .
 */
public interface IAlbumController {

	/**
	 * List the user's albums.
	 *
	 * @param user the user
	 * @return the list of albums
	 */
	SortedSet<IAlbum> listAlbums(IUser user);

	
	/**
	 * Edits the name of an album
	 *  
	 * @param newAlbumName the new album name
	 * @param oldAlbumName the old album name
	 * @param user the current user
	 */
	public void editAlbum(String newAlbumName, String oldAlbumName, IUser user);
	
	/**
	 * Creates an album for the user.
	 *
	 * @param albumName the album name
	 * @param user the user
	 * @return true, if successful
	 */
	boolean createAlbum(String albumName, IUser user);

	
	/**
	 * Gets the album.
	 *
	 * @param albumName the album name
	 * @param user the user
	 * @return the specified album
	 */
	IAlbum getAlbum(String albumName, IUser user);
	
	
	/**
	 * Deletes the user's album.
	 *
	 * @param albumName the album name
	 * @param user the user
	 * @return true, if successful
	 */
	boolean deleteAlbum(String albumName, IUser user);

	/**
	 * Lists all the photos in the specified album.
	 *
	 * @param albumName the album name
	 * @param user the user
	 * @return the list of photos
	 */
	Collection<IPhoto> listPhotos(String albumName, IUser user);

	/**
	 * Adds the photo to the specified user's album.
	 *
	 * @param fileName the file name
	 * @param caption the caption
	 * @param albumName the album name
	 * @param user the user
	 * @return true, if successful
	 */
	IPhoto addPhoto(String fileName, String caption, String albumName, IUser user);

	/**
	 * Moves the user's photo to a different album.
	 *
	 * @param fileName the file name
	 * @param oldAlbumName the old album name
	 * @param newAlbumName the new album name
	 * @param user the user
	 * @return true, if successful
	 */
	boolean movePhoto(String fileName, String oldAlbumName, String newAlbumName, IUser user);

	/**
	 * Removes the user's photo from the album.
	 *
	 * @param fileName the file name
	 * @param albumName the album name
	 * @param user the user
	 * @return true, if successful
	 */
	boolean removePhoto(String fileName, String albumName, IUser user);
	
	/**
	 * Determines if the specified album contains the photo.
	 *
	 * @param fileName the file name
	 * @param albumName the album name
	 * @param user the user
	 * @return true, if successful
	 */
	boolean containsPhoto(String fileName, String albumName, IUser user);

	
}
