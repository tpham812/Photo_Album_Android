package cs213.photoAlbum.control;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.model.IPhoto;
import cs213.photoAlbum.model.IUser;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.util.CalendarUtils;

/**
 * Controller interface for {@link Photo} management, such as adding/deleting tags, get photos by date range/tag.
 * @author dheeptha
 * 
 */
public class PhotoController implements IPhotoController {

	/** The backend. */
	private Backend backend;

	/**
	 * Instantiates a new photo controller.
	 */
	public PhotoController() {
		this.backend = new Backend();
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IPhotoController#containsPhoto(java.lang.String, cs213.photoAlbum.model.IUser)
	 */
	@Override
	public boolean containsPhoto(String fileName, IUser user) {
		return user.getPhotos().containsKey(fileName);
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IPhotoController#addTag(java.lang.String, java.lang.String, java.lang.String, cs213.photoAlbum.model.IUser)
	 */
	@Override
	public boolean addTag(String fileName, String tagType, String tagValue, IUser user) {

		IPhoto photo = user.getPhotos().get(fileName);
		if (photo == null) {
			return false;
		}

		Map<String, SortedSet<String>> tagMap = photo.getTags();
		SortedSet<String> tags = tagMap.get(tagType);
		if (tags == null) {
			tags = new TreeSet<String>();
			tagMap.put(tagType, tags);
		}

		if (tagType.equals("location") && tags.size() == 1) {
			return false;
		}

		if (tags.contains(tagValue)) {
			return false;
		}

		tags.add(tagValue);
		return true;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IPhotoController#deleteTag(java.lang.String, java.lang.String, java.lang.String, cs213.photoAlbum.model.IUser)
	 */
	@Override
	public boolean deleteTag(String fileName, String tagType, String tagValue, IUser user) {

		IPhoto photo = user.getPhotos().get(fileName);
		if (photo == null) {
			return false;
		}

		Map<String, SortedSet<String>> tagMap = photo.getTags();
		Set<String> tags = tagMap.get(tagType);
		if (tags == null) {
			return false;
		}

		if (tags.remove(tagValue)) {

			if (tags.isEmpty()) {
				tagMap.remove(tagType);
			}
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IPhotoController#getPhotosByDate(java.util.Calendar, java.util.Calendar, cs213.photoAlbum.model.IUser)
	 */
	@Override
	public SortedSet<IPhoto> getPhotosByDate(Calendar start, Calendar end, IUser user) {
		
		
		
		SortedSet<IPhoto> result = new TreeSet<IPhoto>(new PhotoComparator());
		for (IPhoto p : user.getPhotos().values()) {
			if (p.getDateTime().equals(start) || p.getDateTime().equals(end)
					|| (p.getDateTime().after(start) && p.getDateTime().before(end))) {

				result.add(p);
			}
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IPhotoController#getPhotosByTag(java.util.List, java.util.List, cs213.photoAlbum.model.IUser)
	 */
	@Override
	public SortedSet<IPhoto> getPhotosByTag(List<String> tagNames, List<String> tagValues, IUser user) {

		SortedSet<IPhoto> result = new TreeSet<IPhoto>(new PhotoComparator());

		boolean m = false;

		for (IPhoto p : user.getPhotos().values()) {
			m = true;

			for (int i = 0; i < tagNames.size(); i++) {
				String tagName = tagNames.get(0);
				String tagValue = tagValues.get(0);

				if (!matches(p, tagName, tagValue)) {
					m = false;
					break;
				}
			}
			if (m) {
				result.add(p);
			}
		}

		return result;
	}

	/**
	 * Determines if the photos tag values match.
	 *
	 * @param p the p
	 * @param tagName the tag name
	 * @param tagValue the tag value
	 * @return true, if successful
	 */
	private boolean matches(IPhoto p, String tagName, String tagValue) {

		if (tagName.isEmpty()) {
			for (SortedSet<String> vals : p.getTags().values()) {
				if (vals.contains(tagValue)) {
					return true;
				}
			}
		} else {
			SortedSet<String> s = p.getTags().get(tagName);
			if (s != null) {
				return s.contains(tagValue);
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see cs213.photoAlbum.control.IPhotoController#fileExists(java.lang.String)
	 */
	@Override
	public boolean fileExists(String fileName) {
		return backend.fileExists(fileName);
	}

}
