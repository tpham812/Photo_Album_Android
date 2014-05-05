package cs213.photoAlbum.android;

import java.io.File;
import java.util.Collection;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import cs213.photoAlbum.model.IPhoto;
import cs213.photoAlbum.simpleview.ViewContainer;

public class ViewPhotos extends Activity {

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_view_photos);

		ViewContainer viewContainer = ViewContainer.getInstance();
		Collection<IPhoto> photos = viewContainer.getPhotos();
		TableLayout tableLayout = (TableLayout) findViewById(R.id.ViewPhotosLayout);

		int counter = 0;
		TableRow row = null;

		if (photos != null) {
			for (final IPhoto p : photos) {

				if (counter % 3 == 0) {
					row = new TableRow(this);
					row.setPadding(5, 5, 5, 5);
					tableLayout.addView(row, new TableLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
				}

				counter++;

				// File imgFile = new File("file:///android_asset/" +
				// p.getName());
				File imgFile = new File(p.getName());
				if (imgFile.exists()) {
					Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
							.getAbsolutePath());

					ImageView image = new ImageView(ViewPhotos.this);
					image.setImageBitmap(resize(myBitmap, 200, 200));

					image.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent i = new Intent(ViewPhotos.this,
									MainActivity.class);
							ViewContainer.getInstance().setPhoto(p);
							startActivity(i);
						}
					});

					image.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

						@Override
						public void onCreateContextMenu(ContextMenu menu,
								View v, ContextMenuInfo menuInfo) {
							menu.add(0, 0, 0, "Move");
							menu.add(0, 1, 0, "Delete");
						}
					});

					// registerForContextMenu(image);

					row.addView(image);
					Log.e(getLocalClassName(), imgFile.getAbsolutePath());
				}
			}
		} else {
			row = new TableRow(this);
			tableLayout.addView(row, new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

			TextView tView = new TextView(ViewPhotos.this);
			tView.setText("No photos");
			row.addView(tView);
		}
	}

	/*
	 * This method is called when an item in a context menu is selected.
	 */

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			move(item.getItemId());
			break;
		case 1:
			delete(item.getItemId());
			break;
		}
		return true;
	}

	private void delete(int itemId) {

	}

	public void move(int itemId) {

	}

	public static Bitmap resize(Bitmap image, int newHeight, int newWidth) {

		int width = image.getWidth();
		int height = image.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// create a matrix for the manipulation
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// recreate the new Bitmap
		Bitmap resizedBitmap = Bitmap.createBitmap(image, 0, 0, width, height,
				matrix, false);
		return resizedBitmap;
	}
}
