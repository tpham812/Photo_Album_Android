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
	@Override
	public void addAlbum(Album album) {		
		
		albumList.put(album.getAlbumName(), album);				
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#containsAlbum(java.lang.String)
	 */
	@Override
	public boolean containsAlbum(String albumName) {
		return albumList.containsKey(albumName);
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#getAlbum(java.lang.String)
	 */
	@Override
	public IAlbum getAlbum(String albumName) {
		return albumList.get(albumName);
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#addPhoto(cs213.photoAlbum.model.Photo, cs213.photoAlbum.model.IAlbum)
	 */
	@Override
	public void addPhoto(Photo photo, IAlbum album) {
		
		photos.put(photo.getName(), photo);		
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#deleteAlbum(java.lang.String)
	 */
	@Override
	public IAlbum deleteAlbum(String albumName) {				
		return albumList.remove(albumName);
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#rename(cs213.photoAlbum.model.IAlbum)
	 */
	@Override
	public String rename(IAlbum album) {
		return id;

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return id;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#setUserID(java.lang.String)
	 */
	@Override
	public void setUserID(String id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#setUserFullName(java.lang.String)
	 */
	@Override
	public void setUserFullName(String fname) {
		this.fname = fname;
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#getUserID()
	 */
	@Override
	public String getUserID() {
		return id;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#getUserFullName()
	 */
	@Override
	public String getUserFullName() {
		return fname;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#getAlbums()
	 */
	@Override
	public Collection<IAlbum> getAlbums() {		
		return albumList.values(); 		
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#getPhotos()
	 */
	@Override
	public Map<String, IPhoto> getPhotos() {
		return photos;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IUser#setPhotos(java.util.Map)
	 */
	@Override
	public void setPhotos(Map<String, IPhoto> photos) {
		this.photos = photos;
	}

}