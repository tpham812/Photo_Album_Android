package cs213.photoAlbum.simpleview;

import javax.swing.UIManager;

import cs213.photoAlbum.control.IAlbumController;
import cs213.photoAlbum.control.IPhotoController;
import cs213.photoAlbum.control.IUserController;


/**
 * GuiView class is the Gui interface for managing the album.
 * @author dheeptha
 */
public class GuiView {
	
	/** The login. */
	protected Login login;
	
	/** The admin. */
	protected Admin admin;
	
	/** The albums. */
	protected Albums albums;
	
	/** The sp. */
	protected SearchPhotos sp;

	/**
	 * Controller to manage user admin. 
	 */
	protected IUserController userController;

	/** The photo controller. */
	protected IPhotoController photoController;

	/** The album controller. */
	protected IAlbumController albumController;
	
	/** The view container. */
	protected ViewContainer viewContainer;
	
	/**
	 * Instantiates a new gui view.
	 */
	public GuiView() {
		super();
		viewContainer = new ViewContainer();
		this.login = new Login(this);
		this.admin = new Admin(this);
		this.albums = new Albums(this);
		this.sp = new SearchPhotos(this);
		
	}

	/**
	 * Show gui.
	 */
	protected void showGUI() {
		login.show();
		
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		final GuiView guiView = new GuiView();
		
		/* Use an appropriate Look and Feel */
		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				guiView.showGUI();
			}
		});
	}

}
