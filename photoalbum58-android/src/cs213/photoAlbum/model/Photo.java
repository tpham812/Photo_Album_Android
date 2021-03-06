package cs213.photoAlbum.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedSet;



/**
 * Photo owned by a {@link User} and part of multiple {@link Album}s.
 * @author dheeptha
 * 
 **/
public class Photo implements Serializable, IPhoto {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Name of the photo. */
	private String name;

	/** Photo caption. */
	private String caption;

	/**  Date time when the photo was taken. */
	private Calendar dateTime;

	/**  Tags associated with the photo. */
	private Map<String,SortedSet<String>> tags;
	
	/**
	 * Instantiates a new photo.
	 */
	public Photo(){
		this.tags = new LinkedHashMap<String, SortedSet<String>>();
	}
	
	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IPhoto#getName()
	 */
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IPhoto#setName(java.lang.String)
	 */
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IPhoto#getCaption()
	 */
	/**
	 * Gets the caption.
	 *
	 * @return the caption
	 */
	@Override
	public String getCaption() {
		return caption;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IPhoto#setCaption(java.lang.String)
	 */
	/**
	 * Sets the caption.
	 *
	 * @param caption the new caption
	 */
	@Override
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IPhoto#getDateTime()
	 */
	/**
	 * Gets the date time.
	 *
	 * @return the date time
	 */
	@Override
	public Calendar getDateTime() {
		return dateTime;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IPhoto#setDateTime(java.util.Calendar)
	 */
	/**
	 * Sets the date time.
	 *
	 * @param dateTime the new date time
	 */
	@Override
	public void setDateTime(Calendar dateTime) {
		this.dateTime = dateTime;
		this.dateTime.set(Calendar.MILLISECOND, 0);
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IPhoto#getTags()
	 */
	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	@Override
	public Map<String, SortedSet<String>> getTags() {
		return tags;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.model.IPhoto#setTags(java.util.Map)
	 */
	/**
	 * Sets the tags.
	 *
	 * @param tags the tags
	 */
	@Override
	public void setTags(Map<String, SortedSet<String>> tags) {
		this.tags = tags;
	}

}
