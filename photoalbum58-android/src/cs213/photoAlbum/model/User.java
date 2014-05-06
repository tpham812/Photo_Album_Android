package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;



/**
 * Represents the User, contains a unique ID, their full name, and the
 * album(s) of the user.
 *
 * @author brett
 */
public class User implements Serializable, IUser {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** ID of the user. */
	private String id;

	/** full name of the user. */
	private String fname;

	/** List of albums. */
	public Map<String, IAlbum> albumList;

	/** User's photos indexed by the name of the file. */
	Map<String, IPhoto> photos;

	/**
	 * Constructor for the User object of the User class.
	 * 
	 * @param id The users unique string ID
	 * @param fname The users full name
	 */
	public User(String id, String fname) {
		/** Users ID */
		this.id = id;
		/** Users full name */
		this.fname = fname;
		/** Instantiates the albumList */
		this.albumList = new LinkedHashMap<String, IAlbum>();
		/** Instantiates the photo list*/
		this.photos = new LinkedHashMap<String, IPhoto>();
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#editAlbum(java.lang.String, java.lang.String)
	 */
	/**
	 * Edits the album.
	 *
	 * @param newAlbumName the new album name
	 * @param oldAlbumName the old album name
	 */
	@Override
	public void editAlbum(String newAlbumName, String oldAlbumName) {
		
		IAlbum al = albumList.get(oldAlbumName);
		al.setAlbumName(newAlbumName);
		albumList.remove(oldAlbumName);
		albumList.put(newAlbumName, al);
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#addAlbum(cs213.photoAlbum.model.Album)
	 */
	/**
	 * Adds the album.
	 *
	 * @param album the album
	 */
	@Override
	public void addAlbum(Album album) {		
		
		albumList.put(album.getAlbumName(), album);				
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#containsAlbum(java.lang.String)
	 */
	/**
	 * Contains album.
	 *
	 * @param albumName the album name
	 * @return true, if successful
	 */
	@Override
	public boolean containsAlbum(String albumName) {
		return albumList.containsKey(albumName);
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#getAlbum(java.lang.String)
	 */
	/**
	 * Gets the album.
	 *
	 * @param albumName the album name
	 * @return the album
	 */
	@Override
	public IAlbum getAlbum(String albumName) {
		return albumList.get(albumName);
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#addPhoto(cs213.photoAlbum.model.Photo, cs213.photoAlbum.model.IAlbum)
	 */
	/**
	 * Adds the photo.
	 *
	 * @param photo the photo
	 * @param album the album
	 */
	@Override
	public void addPhoto(Photo photo, IAlbum album) {
		
		photos.put(photo.getName(), photo);		
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#deleteAlbum(java.lang.String)
	 */
	/**
	 * Delete album.
	 *
	 * @param albumName the album name
	 * @return the i album
	 */
	@Override
	public IAlbum deleteAlbum(String albumName) {				
		return albumList.remove(albumName);
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#rename(cs213.photoAlbum.model.IAlbum)
	 */
	/**
	 * Rename.
	 *
	 * @param album the album
	 * @return the string
	 */
	@Override
	public String rename(IAlbum album) {
		return id;

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return id;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#setUserID(java.lang.String)
	 */
	/**
	 * Sets the user id.
	 *
	 * @param id the new user id
	 */
	@Override
	public void setUserID(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#setUserFullName(java.lang.String)
	 */
	/**
	 * Sets the user full name.
	 *
	 * @param fname the new user full name
	 */
	@Override
	public void setUserFullName(String fname) {
		this.fname = fname;
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#getUserID()
	 */
	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	@Override
	public String getUserID() {
		return id;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#getUserFullName()
	 */
	/**
	 * Gets the user full name.
	 *
	 * @return the user full name
	 */
	@Override
	public String getUserFullName() {
		return fname;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#getAlbums()
	 */
	/**
	 * Gets the albums.
	 *
	 * @return the albums
	 */
	@Override
	public Collection<IAlbum> getAlbums() {		
		return albumList.values(); 		
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#getPhotos()
	 */
	/**
	 * Gets the photos.
	 *
	 * @return the photos
	 */
	@Override
	public Map<String, IPhoto> getPhotos() {
		return photos;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#setPhotos(java.util.Map)
	 */
	/**
	 * Sets the photos.
	 *
	 * @param photos the photos
	 */
	@Override
	public void setPhotos(Map<String, IPhoto> photos) {
		this.photos = photos;
	}

}