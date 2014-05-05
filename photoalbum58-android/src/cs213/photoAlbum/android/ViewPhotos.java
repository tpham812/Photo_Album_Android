package cs213.photoAlbum.android;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import cs213.photoAlbum.model.IAlbum;
import cs213.photoAlbum.model.IPhoto;
import cs213.photoAlbum.simpleview.ViewContainer;
import cs213.photoAlbum.util.Utils;


public class ViewPhotos extends Activity {
	
	private ViewContainer viewContainer;
	private AlertDialog.Builder ab;
	private AlertDialog ad;
	
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_view_photos);

		viewContainer = ViewContainer.getInstance();		
		if(viewContainer.getAlbum() != null) {
			setTitle(viewContainer.getAlbum().getAlbumName());	
		} else {
			setTitle("Search results");
		}
		populatePhotos();
	}
	public void populatePhotos() {
		Collection<IPhoto> photos = viewContainer.getPhotos();
		TableLayout tableLayout = (TableLayout) findViewById(R.id.ViewPhotosLayout);

		int counter = 0;
		TableRow row = null;

		row = new TableRow(this);
		tableLayout.addView(row, new TableLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		Button backBtn = new Button(this);
		backBtn.setText("Home");
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ViewPhotos.this,
						MainActivity.class);				
				startActivity(i);
			}
		});
		
		row.addView(backBtn);
		
		if(viewContainer.getAlbum() != null) {
			Button addPhotoBtn = new Button(this);
			addPhotoBtn.setText("Add Photo");
			addPhotoBtn.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					Intent i = new Intent(ViewPhotos.this, AddPhoto.class);	
					i.putExtra("Album", viewContainer.getAlbum().getAlbumName());
					startActivity(i);
				}
			});
			
			row.addView(addPhotoBtn);
		}
		if (photos != null && !photos.isEmpty()) {
			for (final IPhoto p : photos) {

				if (counter % 3 == 0) {
					row = new TableRow(this);
					row.setPadding(5, 5, 5, 5);
					tableLayout.addView(row, new TableLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
				}

				File imgFile = new File(p.getName());
				if (imgFile.exists()) {
					Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
							.getAbsolutePath());

					ImageView image = new ImageView(ViewPhotos.this);
					image.setImageBitmap(Utils.resize(myBitmap, 200, 200));


					final int c = counter;
					
					image.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent i = new Intent(ViewPhotos.this,
									SlideshowActivity.class);
							i.putExtra(SlideshowActivity.STARTPAGE, c);
							
							ViewContainer.getInstance().setPhoto(p);

							startActivity(i);
						}
					});

					if(viewContainer.getAlbum() != null) {
						image.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
	
							@Override
							public void onCreateContextMenu(ContextMenu menu,
									View v, ContextMenuInfo menuInfo) {
								menu.add(c, 0, 0, "Move");
								menu.add(c, 1, 0, "Delete");
							}
						});
					}


					row.addView(image);
					Log.e(getLocalClassName(), imgFile.getAbsolutePath());
				}

				counter++;
			}
		} 
	}

	/*
	 * This method is called when an item in a context menu is selected.
	 */

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			move(item.getGroupId());
			
			break;
		case 1:
			delete(item.getGroupId());
			break;
		}
		return true;
	}

	private void delete(int groupId) {
		
		IPhoto photo = viewContainer.getPhotos().get(groupId);		
		viewContainer.albumController.removePhoto(photo.getName(), viewContainer.getAlbum().getAlbumName(), viewContainer.getUser());
		viewContainer.saveUser();
		
		Intent i = new Intent(ViewPhotos.this, ViewPhotos.class);
		startActivity(i);
	}

	@SuppressWarnings("deprecation")
	public void move(int groupId) {
		
		final String[] albumList;
		final int id = groupId;
		final String albumToMoveFrom = getIntent().getExtras().getString("Album");
		albumList = getAlbumList(viewContainer.listAlbums(), albumToMoveFrom);
		ab = new AlertDialog.Builder(this);
		ab.setTitle("Choose album to move to.");
		ab.setSingleChoiceItems(albumList, -1, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				IPhoto photo = viewContainer.getPhotos().get(id);
				viewContainer.movePhoto(photo.getName(), albumToMoveFrom, albumList[which]);
				viewContainer.saveUser();
				Intent i = new Intent(ViewPhotos.this, ViewPhotos.class);
				startActivity(i);
			}
		});
		ad = ab.create();
		ad.show();
	}
	
	public String[] getAlbumList(Collection<IAlbum> albums, String albumToMoveFrom) {
		
		String[] albumList = new String[albums.size() - 1];
		Iterator<IAlbum> iterator = albums.iterator();
		int i = 0;
		IAlbum al;
		while(iterator.hasNext()) {
			if(!(al = iterator.next()).getAlbumName().equals(albumToMoveFrom)) {
				albumList[i] = al.getAlbumName();
				i++;
			}
		}
		return albumList;
	}
}
