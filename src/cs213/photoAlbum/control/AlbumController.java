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
import cs213.photoAlbum.util.CalendarUtils;

/**
 * The Class AlbumController.
 * @author dheeptha
 */
public class AlbumController implements IAlbumController {

	/** The backend. */
	private IBackend backend;
	
	/**
	 * Instantiates a new album controller.
	 */
	public AlbumController() {
		this.backend = new Backend();
	}
	

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IAlbumController#listAlbums(cs213.photoAlbum.model.IUser)
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
	@Override
	public void editAlbum(String newAlbumName, String oldAlbumName, IUser user) {
		
		user.editAlbum(newAlbumName, oldAlbumName);
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IAlbumController#deleteAlbum(java.lang.String, cs213.photoAlbum.model.IUser)
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
			photo.setDateTime(CalendarUtils.toCalendar(file.lastModified()));
			
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
	@Override
	public IAlbum getAlbum(String albumName, IUser user) {
		return user.getAlbum(albumName);
	}
}
