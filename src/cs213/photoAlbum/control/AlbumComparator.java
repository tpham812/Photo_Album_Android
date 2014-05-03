package cs213.photoAlbum.control;

import java.util.Comparator;

import cs213.photoAlbum.model.IAlbum;


/**
 * AlbumComparator to sort by album name.
 * @author dheeptha
 */
public class AlbumComparator implements Comparator<IAlbum> {

	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	/**
	 * Compares two albums and returns an integer value
	 * 
	 * @param o1 IAlbum
	 * @param o2 IAlbum
	 * @return int
	 */
	public int compare(IAlbum o1, IAlbum o2) {		
		return o1.getAlbumName().compareTo(o2.getAlbumName());
	}

}
