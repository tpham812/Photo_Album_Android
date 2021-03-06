package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;



/**
 * Album of a {@link User} containing multiple {@link Photo}s.
 *
 * @author brett
 */
public class Album implements Serializable, IAlbum {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**  Name of the album. */
	private String name; 
		
	/**  List of the photos. */
	public Map<String, IPhoto> photos; 
	
	/**
	 * Constructor for the album object. Creates new HashMap for the photos
	 *
	 * @param name name of the album
	 */
	public Album(String name){
		this.name = name; 
		this.photos = new LinkedHashMap<String, IPhoto>();
	}
	
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IAlbum#recaptionPhoto()
	 */
	/**
	 * Recaption photo.
	 *
	 * @return the string
	 */
	@Override
	public String recaptionPhoto(){
		return name; 
		
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IAlbum#getPhotos()
	 */
	/**
	 * Gets the photos.
	 *
	 * @return the photos
	 */
	@Override
	public Collection<IPhoto> getPhotos(){
		return photos.values(); 
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IAlbum#getPhoto(java.lang.String)
	 */
	/**
	 * Gets the photo.
	 *
	 * @param name the name
	 * @return the photo
	 */
	@Override
	public IPhoto getPhoto(String name) {
		return photos.get(name);
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IAlbum#setAlbumName(java.lang.String)
	 */
	/**
	 * Sets the album name.
	 *
	 * @param name the new album name
	 */
	@Override
	public void setAlbumName(String name){
		this.name=name;
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IAlbum#getAlbumName()
	 */
	/**
	 * Gets the album name.
	 *
	 * @return the album name
	 */
	@Override
	public String getAlbumName(){
		return name; 
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Album other = (Album) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IAlbum#maxPhotoDate()
	 */
	/**
	 * Max photo date.
	 *
	 * @return the calendar
	 */
	@Override
	public Calendar maxPhotoDate(){
		
		Calendar cal = null;
		
		for(IPhoto p: photos.values()){
			
			if(cal == null) {
				cal = p.getDateTime();
			} else if (p.getDateTime().after(cal)){
				cal = p.getDateTime();
			}			
		}
		
		return cal;		
	}

	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IAlbum#minPhotoDate()
	 */
	/**
	 * Min photo date.
	 *
	 * @return the calendar
	 */
	@Override
	public Calendar minPhotoDate(){
		
		Calendar cal = null;
		
		for(IPhoto p: photos.values()){
			
			if(cal == null) {
				cal = p.getDateTime();
			} else if (p.getDateTime().before(cal)){
				cal = p.getDateTime();
			}			
		}		
		return cal;		
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IAlbum#getPhotoMap()
	 */
	/**
	 * Gets the photo map.
	 *
	 * @return the photo map
	 */
	@Override
	public Map<String, IPhoto> getPhotoMap() {
		return photos;
	}
	
	
}
