package cs213.photoAlbum.control;

import java.util.Comparator;

import cs213.photoAlbum.model.IPhoto;



/**
 * PhotoComparator to sort photo by DateTime, name.
 * @author dheeptha
 */
public class PhotoComparator implements Comparator<IPhoto> {
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	/**
	 * Compare.
	 *
	 * @param o1 the o1
	 * @param o2 the o2
	 * @return the int
	 */
	@Override
	public int compare(IPhoto o1, IPhoto o2) {			
		int i = o1.getDateTime().compareTo(o2.getDateTime());
		
		if (i == 0) {
			i = o1.getName().compareTo(o2.getName());
		}
		
		return i;
	}
}