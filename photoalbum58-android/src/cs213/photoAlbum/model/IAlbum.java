package cs213.photoAlbum.model;

import java.util.Calendar;
import java.util.Collection;
import java.util.Map;


/**
 * Album of a {@link IUser} containing multiple {@link IPhoto}s.
 *
 * @author brett
 */
public interface IAlbum {

	/**
	 * Recaptions the photo.
	 *
	 * @return name new name of photo
	 */
	abstract String recaptionPhoto();

	/**
	 * Returns the list of photos.
	 *
	 * @return photoList the list of photos
	 */
	abstract Collection<IPhoto> getPhotos();
	
	/**
	 * Gets the photo map.
	 *
	 * @return the photo map
	 */
	Map<String, IPhoto> getPhotoMap();

	/**
	 * Gets the photo.
	 *
	 * @param name the name
	 * @return the photo
	 */
	abstract IPhoto getPhoto(String name);

	/**
	 * Sets the name of the album.
	 *
	 * @param name name of album
	 */
	abstract void setAlbumName(String name);

	/**
	 * Returns the name of the album.
	 *
	 * @return name name of album
	 */
	abstract String getAlbumName();

	/**
	 * Generates the hash value.
	 *
	 * @return the integer hash value
	 */
	abstract int hashCode();

	/**
	 * Determines if two objects are equal.
	 *
	 * @param obj the object
	 * @return true, if successful
	 */
	abstract boolean equals(Object obj);

	/**
	 * Determines the max photo date in the album.
	 *
	 * @return the date of the latest photo taken
	 */
	abstract Calendar maxPhotoDate();

	/**
	 * Determines the minimum photo date.
	 *
	 * @return the date of the earliest taken photo
	 */
	abstract Calendar minPhotoDate();

}