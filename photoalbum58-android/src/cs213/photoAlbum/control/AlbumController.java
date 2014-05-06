package cs213.photoAlbum.control;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.model.IAlbum;
import cs213.photoAlbum.model.IBackend;
import cs213.photoAlbum.model.IPhoto;
import cs213.photoAlbum.model.IUser;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.util.Utils;


/**
 * The Class AlbumController.
 * @author dheeptha
 */
public class AlbumController implements IAlbumController {

	/** The backend. */
	private IBackend backend;
	
	/**
	 * Instantiates a new album controller.
	 *
	 * @param dataFolder the data folder
	 */
	public AlbumController(String dataFolder) {
		this.backend = new Backend(dataFolder);
	}
	

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IAlbumController#listAlbums(cs213.photoAlbum.model.IUser)
	 */
	/**
	 * List albums.
	 *
	 * @param user the user
	 * @return the sorted set
	 */
	@Override
	public SortedSet<IAlbum> listAlbums(IUser user) {
		
		SortedSet<IAlbum> albums = new TreeSet<IAlbum>(new AlbumComparator());		
		albums.addAll(user.getAlbums());
		return albums;
	}


	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IAlbumController#createAlbum(java.lang.String, cs213.photoAlbum.model.IUser)
	 */
	/**
	 * Creates the album.
	 *
	 * @param albumName the album name
	 * @param user the user
	 * @return true, if successful
	 */
	@Override
	public boolean createAlbum(String albumName, IUser user) {

		if (user.containsAlbum(albumName)) {
			return false;
		}

		user.addAlbum(new Album(albumName));
		return true;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IAlbumController#editAlbum(java.lang.String, java.lang.String, cs213.photoAlbum.model.IUser)
	 */
	/**
	 * Edits the album.
	 *
	 * @param newAlbumName the new album name
	 * @param oldAlbumName the old album name
	 * @param user the user
	 */
	@Override
	public void editAlbum(String newAlbumName, String oldAlbumName, IUser user) {
		
		user.editAlbum(newAlbumName, oldAlbumName);
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IAlbumController#deleteAlbum(java.lang.String, cs213.photoAlbum.model.IUser)
	 */
	/**
	 * Delete album.
	 *
	 * @param albumName the album name
	 * @param user the user
	 * @return true, if successful
	 */
	@Override
	public boolean deleteAlbum(String albumName, IUser user) {

		if (!user.containsAlbum(albumName)) {
			return false;
		}

		IAlbum album = user.deleteAlbum(albumName);
		for(IPhoto p : album.getPhotos()) {
			removePhotoIfNotInAnAlbum(p.getName(), user);		
		}
		
		return true;
	}


	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IAlbumController#listPhotos(java.lang.String, cs213.photoAlbum.model.IUser)
	 */
	/**
	 * List photos.
	 *
	 * @param albumName the album name
	 * @param user the user
	 * @return the collection
	 */
	@Override
	public Collection<IPhoto> listPhotos(String albumName, IUser user) {

		return user.getAlbum(albumName).getPhotos();
	}
	
	/**
	 * Determines if the specified album contains the photo.
	 *
	 * @param fileName the file name
	 * @param albumName the album name
	 * @param user the user
	 * @return true, if successful
	 */
	public boolean containsPhoto(String fileName, String albumName, IUser user) {
		IAlbum album = user.getAlbum(albumName);		
		if(album == null || !album.getPhotoMap().containsKey(fileName)) {
			return false;
		}
		return true;
	}


	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IAlbumController#addPhoto(java.lang.String, java.lang.String, java.lang.String, cs213.photoAlbum.model.IUser)
	 */
	/**
	 * Adds the photo.
	 *
	 * @param fileName the file name
	 * @param caption the caption
	 * @param albumName the album name
	 * @param user the user
	 * @return the i photo
	 */
	@Override
	public IPhoto addPhoto(String fileName, String caption, String albumName, IUser user) {
		
		IPhoto photo = null;
		
		if(user.getPhotos().containsKey(fileName)) {
			photo = user.getPhotos().get(fileName);
			
		} else {
			photo = new Photo();
			photo.setName(fileName);
			photo.setCaption(caption);
			
			File file = backend.getFile(fileName);			
			photo.setDateTime(Utils.toCalendar(file.lastModified()));
			
			user.getPhotos().put(fileName, photo);
		}

		IAlbum album = user.getAlbum(albumName);		
		if(album == null || album.getPhotoMap().containsKey(fileName)) {
			return null;
		} else {
			album.getPhotoMap().put(fileName, photo);
		}		
		return photo;
	}


	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IAlbumController#movePhoto(java.lang.String, java.lang.String, java.lang.String, cs213.photoAlbum.model.IUser)
	 */
	/**
	 * Move photo.
	 *
	 * @param fileName the file name
	 * @param oldAlbumName the old album name
	 * @param newAlbumName the new album name
	 * @param user the user
	 * @return true, if successful
	 */
	@Override
	public boolean movePhoto(String fileName, String oldAlbumName, String newAlbumName, IUser user) {
		
		IPhoto p = getPhoto(fileName,oldAlbumName,user);
		
		if (p == null) {
			return false;
		}
		removePhoto(fileName, oldAlbumName, user);
		p = addPhoto(fileName, p.getCaption(), newAlbumName, user);
		
		return p != null;
	}
	
	/**
	 * Gets the photo.
	 *
	 * @param fileName the file name
	 * @param albumName the album name
	 * @param user the user
	 * @return the photo
	 */
	public IPhoto getPhoto(String fileName, String albumName, IUser user){
		
		IAlbum album = user.getAlbum(albumName);		

		if (album == null) {
			return null;
		} 
		
		return album.getPhoto(fileName);
	}


	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IAlbumController#removePhoto(java.lang.String, java.lang.String, cs213.photoAlbum.model.IUser)
	 */
	/**
	 * Removes the photo.
	 *
	 * @param fileName the file name
	 * @param albumName the album name
	 * @param user the user
	 * @return true, if successful
	 */
	@Override
	public boolean removePhoto(String fileName, String albumName, IUser user) {
		
		Map<String, IPhoto> photos = user.getAlbum(albumName).getPhotoMap();
		if(!photos.containsKey(fileName)) {
			return false;
		} else {
			photos.remove(fileName);
		}
		
		removePhotoIfNotInAnAlbum(fileName, user);

		return true;
	}

	/**
	 * Removes the photo if not in an album.
	 *
	 * @param fileName the file name
	 * @param user the user
	 */
	private void removePhotoIfNotInAnAlbum(String fileName, IUser user) {
		
		boolean photoExists = false;
		for(IAlbum a: user.getAlbums()){
			
			if(a.getPhotoMap().containsKey(fileName)){
				photoExists = true;
				break;
			}
		}
		
		if(!photoExists) {
			user.getPhotos().remove(fileName);
		}
	}


	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IAlbumController#getAlbum(java.lang.String, cs213.photoAlbum.model.IUser)
	 */
	/**
	 * Gets the album.
	 *
	 * @param albumName the album name
	 * @param user the user
	 * @return the album
	 */
	@Override
	public IAlbum getAlbum(String albumName, IUser user) {
		return user.getAlbum(albumName);
	}
}
