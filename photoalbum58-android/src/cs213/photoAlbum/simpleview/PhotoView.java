package cs213.photoAlbum.simpleview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedSet;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.SwingConstants;

import cs213.photoAlbum.model.IAlbum;
import cs213.photoAlbum.model.IPhoto;
import cs213.photoAlbum.simpleview.SearchPhotos.PanelListener;
import cs213.photoAlbum.util.CalendarUtils;


/**
 * Creates the Gui for viewing photos.
 * @author dheeptha
 */
public class PhotoView extends JFrame {

	/** The view container. */
	ViewContainer viewContainer;

	/** The error label. */
	private JLabel errorLabel;

	/** The icons pane. */
	private JScrollPane iconsPane;

	/** The photo label. */
	private JLabel photoLabel = new JLabel();

	/** The edit panel. */
	private JPanel editPanel;

	/** The photo. */
	private IPhoto photo;

	/** The photo index. */
	private int photoIndex;

	/** The tag table. */
	private JTable tagTable;

	/** The albums list. */
	private JList<String> albumsList;

	/** The caption field. */
	private JTextField captionField;

	/** The gui view. */
	private GuiView guiView;

	/** The display actions. */
	private List<DisplayPhotoAction> displayActions;

	/** The display photo index. */
	public int displayPhotoIndex;

	/** The make album field. */
	private JTextField makeAlbumField;

	/** The error frame. */
	private JFrame errorFrame;

	/** The panel listener. */
	private PanelListener panelListener;

	/**
	 * Instantiates a new photo view.
	 *
	 * @param gv the gv
	 */
	public PhotoView(GuiView gv) {

		this.guiView = gv;
		this.viewContainer = gv.viewContainer;
		this.editPanel = new JPanel(new GridBagLayout());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("");
		setMinimumSize(new Dimension(1200, 700));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);

		
		photoLabel.setVerticalTextPosition(JLabel.TOP);
		photoLabel.setHorizontalTextPosition(JLabel.CENTER);
		photoLabel.setHorizontalAlignment(JLabel.CENTER);
		photoLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		add(photoLabel, BorderLayout.CENTER);

		add(editPanel, BorderLayout.EAST);

		iconsPane = new JScrollPane();
		JToolBar iconsBar = new JToolBar();
		iconsPane.getViewport().add(iconsBar);
		iconsPane.setPreferredSize(new Dimension(200, 150));
		add(iconsPane, BorderLayout.NORTH);

		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.setMaximumSize(new Dimension(100, 100));
		// panel.setSize(100, 100);

		JButton button = null;

		button = new JButton(" Logout ");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				guiView.viewContainer.logout();
				dispose();
				guiView.login.show();
			}
		});
		button.setMinimumSize(new Dimension(10, 100));
		panel.add(button, 0);

		button = new JButton(" < Albums ");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				dispose();
				guiView.viewContainer.setAlbum(null);
				guiView.albums.show();
			}
		});
		button.setMinimumSize(new Dimension(10, 100));
		panel.add(button, 1);

		if (viewContainer.getAlbum() != null) {

			button = new JButton(" Add photo ");
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					dispose();

					AddPhoto addPhoto = new AddPhoto(viewContainer.getAlbum(),
							guiView);
				}
			});
			button.setMinimumSize(new Dimension(10, 100));
			panel.add(button, 2);

			iconsBar.add(panel, 0);
		} else {

			iconsBar.add(panel, 0);

			panel = new JPanel(new GridLayout(3, 1));
			panel.setMaximumSize(new Dimension(120, 100));

			makeAlbumField = new JTextField();
			makeAlbumField.setMaximumSize(new Dimension(10, 10));
			panel.add(makeAlbumField, 0);

			button = new JButton("Make Album");
			button.setMaximumSize(new Dimension(10, 100));

			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					String txt = makeAlbumField.getText();
					if (txt != null && !txt.isEmpty()) {

						if (viewContainer.createAlbum(txt)) {
							for (IPhoto p : viewContainer.getPhotos()) {
								viewContainer.albumController.addPhoto(
										p.getName(), p.getCaption(), txt,
										viewContainer.getUser());
							}
							viewContainer.saveUser();

							dispose();
							guiView.viewContainer.setAlbum(viewContainer
									.getAlbum(txt));
							new PhotoView(guiView).setVisible(true);
						}
					}

				}
			});
			button.setMinimumSize(new Dimension(10, 100));

			panel.add(button, 1);

			panel.add(new JLabel(), 2);

			iconsBar.add(panel, 1);

		}

		iconsBar.add(Box.createHorizontalStrut(10));

		if (!viewContainer.getPhotos().isEmpty()) {

			JButton navButton = new JButton(" <<< Prev photo ");
			navButton.setBorder(BorderFactory.createLineBorder(Color.black));
			navButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					if (displayPhotoIndex > 0) {
						displayActions.get(--displayPhotoIndex).showPhoto();
					}
				}
			});
			iconsBar.add(navButton, iconsBar.getComponentCount());
			iconsBar.add(Box.createHorizontalStrut(10));
			

			iconsBar.add(Box.createHorizontalStrut(10));

			navButton = new JButton(" Next photo >>> ");
			navButton.setBorder(BorderFactory.createLineBorder(Color.black));
			navButton.setBorderPainted(true);
			navButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					if (displayPhotoIndex < displayActions.size() - 1) {
						displayActions.get(++displayPhotoIndex).showPhoto();
					}

				}
			});

			iconsBar.add(navButton, iconsBar.getComponentCount());
			iconsBar.add(Box.createHorizontalStrut(10));




		}

		DisplayPhotoAction photo1Action = null;
		displayActions = new ArrayList<PhotoView.DisplayPhotoAction>();
		int i = 0;
		for (IPhoto photo : viewContainer.getPhotos()) {

			ImageIcon fullIcon = getIcon(photo.getName(), photo.getCaption());

			if (fullIcon != null) {

				ImageIcon tIcon = new ImageIcon(makeThumbnail(
						fullIcon.getImage(), 100, 100));
				tIcon.setDescription(photo.getCaption());

				DisplayPhotoAction thumbAction = new DisplayPhotoAction(
						fullIcon, tIcon, photo, i++, this);

				if (photo1Action == null) {
					photo1Action = thumbAction;
				}

				JButton thumbButton = new JButton(thumbAction);
				thumbButton.setText(photo.getCaption());
				thumbButton.setVerticalTextPosition(SwingConstants.BOTTOM);
				thumbButton.setHorizontalTextPosition(SwingConstants.CENTER);

				thumbAction.setIconButton(thumbButton);
				iconsBar.add(thumbButton, iconsBar.getComponentCount());

				displayActions.add(thumbAction);
			}
		}

		if (!viewContainer.getPhotos().isEmpty()) {
			displayActions.get(0).showPhoto();
		}

	
	}
	
	

	/**
	 * Gets the icon.
	 *
	 * @param path the path
	 * @param description the description
	 * @return the icon
	 */
	protected ImageIcon getIcon(String path, String description) {

		try {
			return new ImageIcon((new File(path)).toURI().toURL(), description);
		} catch (MalformedURLException e) {

		}

		return null;
	}

	/**
	 * Make thumbnail.
	 *
	 * @param srcImg the src img
	 * @param w the w
	 * @param h the h
	 * @return the image
	 */
	private Image makeThumbnail(Image srcImg, int w, int h) {
		BufferedImage resizedImg = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = resizedImg.createGraphics();
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return resizedImg;
	}

	/**
	 * The Class DisplayPhotoAction.
	 */
	private class DisplayPhotoAction extends AbstractAction {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The full icon. */
		private ImageIcon fullIcon;
		
		/** The photo view. */
		private PhotoView photoView;
		
		/** The photo. */
		private IPhoto photo;
		
		/** The photo index. */
		private int photoIndex;
		
		/** The icon button. */
		private JButton iconButton;
		
		/** The img dim. */
		private Dimension imgDim;
		
		
		/**
		 * Sets the icon button.
		 *
		 * @param iconButton the new icon button
		 */
		public void setIconButton(JButton iconButton) {
			this.iconButton = iconButton;
		}

		/**
		 * Instantiates a new display photo action.
		 *
		 * @param fullIcon the full icon
		 * @param thumb the thumb
		 * @param photo the photo
		 * @param photoIndex the photo index
		 * @param photoView the photo view
		 */
		public DisplayPhotoAction(ImageIcon fullIcon, Icon thumb, IPhoto photo,
				int photoIndex, PhotoView photoView) {
			this.fullIcon = fullIcon;
			putValue(LARGE_ICON_KEY, thumb);
			putValue(SHORT_DESCRIPTION, photo.getCaption());
			this.photoView = photoView;
			this.photo = photo;
			this.photoIndex = photoIndex;
			
			imgDim = new Dimension(fullIcon.getImage().getWidth(null), fullIcon.getImage().getHeight(null));
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {

			showPhoto();
		}

		/**
		 * Show photo.
		 */
		public void showPhoto() {
			
			Dimension dim = photoLabel.getBounds().getSize();
			
			if(dim.getWidth() == 0 || dim.getHeight() == 0 ){
				Toolkit toolkit = Toolkit.getDefaultToolkit();
			    dim = toolkit.getScreenSize();
			}
			
			dim = getPhotoDim(dim);			
			
			if(imgDim.getHeight() < dim.getHeight() && imgDim.getWidth() < dim.getWidth()){

				photoLabel.setIcon(fullIcon);

			} else {
				
				photoLabel.setIcon(new ImageIcon(makeThumbnail(fullIcon.getImage(), (int)dim.getWidth(),(int)dim.getHeight())));
			}
			
			photoLabel.setText(photo.getCaption());
			photoView.photo = this.photo;
			photoView.displayPhotoIndex = this.photoIndex;
			setTitle(photo.getCaption());

			editPanel.removeAll();
			createErrorPanel();

			GridBagConstraints c;

			JPanel ep2 = new JPanel(new GridBagLayout());

			c = new GridBagConstraints();
			c.insets = new Insets(5, 0, 5, 0);
			c.gridx = 0;
			c.gridy = 0;
			ep2.add(new JLabel("Pathname: "), c);

			c = new GridBagConstraints();
			c.insets = new Insets(5, 0, 5, 0);
			c.gridx = 1;
			c.gridy = 0;
			
			String name = photo.getName();
			
			if(name.length() > 40) {
				name = "..." + name.substring(name.length() - 40);
			}
			
			ep2.add(new JLabel(name), c);

			c = new GridBagConstraints();
			c.insets = new Insets(5, 0, 5, 0);
			c.gridx = 0;
			c.gridy = 1;
			ep2.add(new JLabel("Date: "), c);

			c = new GridBagConstraints();
			c.insets = new Insets(5, 0, 5, 0);
			c.gridx = 1;
			c.gridy = 1;
			ep2.add(new JLabel(CalendarUtils.toFmtDate(photo.getDateTime())), c);

			c = new GridBagConstraints();
			c.insets = new Insets(5, 0, 5, 0);
			c.gridx = 0;
			c.gridy = 2;
			ep2.add(new JLabel("Caption:  "), c);

			c = new GridBagConstraints();
			c.insets = new Insets(5, 0, 5, 0);
			c.gridx = 1;
			c.gridy = 2;
			c.ipadx = 100;

			captionField = new JTextField(photo.getCaption());
			ep2.add(captionField, c);

			c = new GridBagConstraints();
			c.insets = new Insets(5, 0, 5, 0);
			c.gridx = 0;
			c.gridy = 0;
			editPanel.add(ep2, c);

			String[] columnNames = { "Tag type", "Value" };
			Object[][] data = new Object[20][2];

			int i = 0;
			for (Entry<String, SortedSet<String>> e1 : photo.getTags()
					.entrySet()) {

				for (String e2 : e1.getValue()) {
					data[i][0] = e1.getKey();
					data[i][1] = e2;

					i++;
					if (i >= 20) {
						break;
					}
				}
				if (i >= 20) {
					break;
				}
			}

			while (i < 20) {
				data[i][0] = "";
				data[i][1] = "";
				i++;
			}

			tagTable = new JTable(data, columnNames);
			tagTable.setPreferredScrollableViewportSize(new Dimension(200, 100));
			tagTable.setFillsViewportHeight(true);
			tagTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

			JScrollPane scrollPane = new JScrollPane(tagTable);

			c = new GridBagConstraints();
			c.insets = new Insets(5, 0, 5, 0);
			c.gridx = 0;
			c.gridy = 1;
			c.ipadx = 200;
			c.ipady = 100;
			editPanel.add(scrollPane, c);

			ep2 = new JPanel(new GridBagLayout());

			c = new GridBagConstraints();
			c.insets = new Insets(5, 0, 5, 0);
			c.gridx = 0;
			c.gridy = 0;
			JLabel jLabel = new JLabel("Albums : ");
			ep2.add(jLabel, c);

			List<Integer> sels = new ArrayList<Integer>();

			Collection<IAlbum> albums = viewContainer.getUser().getAlbums();
			String listData[] = new String[albums.size()];
			i = 0;
			for (IAlbum ia : albums) {
				if (ia.getPhotoMap().containsKey(photo.getName())) {
					sels.add(i);
				}

				listData[i++] = ia.getAlbumName();
			}

			albumsList = new JList<String>(listData);
			scrollPane = new JScrollPane(albumsList);
			scrollPane.setPreferredSize(new Dimension(100, 100));
			albumsList.setSelectedIndices(toIntArray(sels));

			c = new GridBagConstraints();
			c.insets = new Insets(5, 0, 5, 0);
			c.gridx = 1;
			c.gridy = 0;
			ep2.add(scrollPane, c);

			c = new GridBagConstraints();
			c.insets = new Insets(0, 0, 0, 0);
			c.gridx = 0;
			c.gridy = 2;
			c.ipadx = 200;
			c.ipady = 100;

			editPanel.add(ep2, c);

			ep2 = new JPanel(new GridBagLayout());
			JButton button;
			button = new JButton("Save");
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					if (!captionField.getText().isEmpty()) {
						photo.setCaption(captionField.getText());
						iconButton.setText(captionField.getText());
						
						// update icons here
					} else {
						errorLabel.setText("Invalid Caption");
						errorFrame.setSize(260, 150);
						errorFrame.setVisible(true);
					}

					photo.getTags().clear();

					int nRow = tagTable.getRowCount();

					for (int i = 0; i < nRow; i++) {

						String key = tagTable.getValueAt(i, 0).toString();
						String val = tagTable.getValueAt(i, 1).toString();

						if (key != null && val != null && key.length() > 0
								&& val.length() > 0) {
							boolean b = viewContainer.photoController.addTag(
									photo.getName(), key, val,
									viewContainer.getUser());
							if (!b) {
								errorLabel.setText("Invalid Tag");
								errorFrame.setSize(260, 150);
								errorFrame.setVisible(true);
								tagTable.setValueAt(null, i, 0);
								tagTable.setValueAt(null, i, 1);
							}
						} else if((key !=null && key.length()>0)||(val!=null&&val.length()>0)){
							errorLabel.setText("Must Enter In Both Tag Type and Tag Value");
							errorFrame.setSize(260, 150);
							errorFrame.setVisible(true);
							tagTable.setValueAt(null, i, 0);
							tagTable.setValueAt(null, i, 1);
						}
					}

					List<String> sels = albumsList.getSelectedValuesList();

					for (String s : sels) {
						viewContainer.albumController.addPhoto(photo.getName(),
								photo.getCaption(), s, viewContainer.getUser());
					}

					ListModel<String> model = albumsList.getModel();

					for (int i = 0; i < model.getSize(); i++) {
						String aName = model.getElementAt(i);
						if (!sels.contains(aName)) {
							viewContainer.albumController.removePhoto(
									photo.getName(), aName,
									viewContainer.getUser());
						}
					}

					viewContainer.saveUser();

					if (viewContainer.getAlbum() != null
							&& !viewContainer.albumController.containsPhoto(
									photo.getName(), viewContainer.getAlbum()
											.getAlbumName(), viewContainer
											.getUser())) {
						dispose();
						viewContainer.setAlbum(viewContainer.getAlbum());
						new PhotoView(guiView).setVisible(true);
					}
				}
			});
			c = new GridBagConstraints();
			c.insets = new Insets(0, 10, 0, 10);
			c.gridx = 1;
			c.gridy = 0;
			ep2.add(button, c);

			button = new JButton("Reload");
			button.addActionListener(this);
			c = new GridBagConstraints();
			c.insets = new Insets(0, 10, 0, 10);
			c.gridx = 2;
			c.gridy = 0;
			ep2.add(button, c);

			c = new GridBagConstraints();
			c.insets = new Insets(0, 10, 0, 10);
			c.gridx = 0;
			c.gridy = 3;
			editPanel.add(ep2, c);

			editPanel.setVisible(true);
			validate();
			repaint();
		}

	}

	/**
	 * To int array.
	 *
	 * @param list the list
	 * @return the int[]
	 */
	int[] toIntArray(List<Integer> list) {
		int[] ret = new int[list.size()];
		int i = 0;
		for (Integer e : list)
			ret[i++] = e.intValue();
		return ret;
	}

	/**
	 * Creates the error panel.
	 */
	public void createErrorPanel() {

		errorFrame = new JFrame("Error");
		errorFrame.addWindowListener(panelListener);
		errorFrame.setAlwaysOnTop(true);
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		errorLabel = new JLabel();
		errorLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel.add(Box.createRigidArea(new Dimension(0, 30)));
		panel.add(errorLabel);
		panel.add(Box.createRigidArea(new Dimension(0, 35)));
		errorFrame.add(panel);
		errorFrame.setLocationRelativeTo(null);
		errorFrame.setResizable(false);
		errorFrame.setVisible(false);
	}
	
	/**
	 * Gets the photo dim.
	 *
	 * @param d the d
	 * @return the photo dim
	 */
	public Dimension getPhotoDim(Dimension d){
		
	    Dimension newDim = new Dimension((int)(d.getWidth() * 0.75), (int)(d.getHeight() * 0.8));
	    return newDim;	    
	}
}
