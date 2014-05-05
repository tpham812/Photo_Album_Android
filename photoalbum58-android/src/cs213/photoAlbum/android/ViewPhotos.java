package cs213.photoAlbum.android;

import java.io.File;
import java.util.Collection;

import cs213.photoAlbum.model.IPhoto;
import cs213.photoAlbum.simpleview.ViewContainer;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class ViewPhotos extends Activity {

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_view_photos);

		ViewContainer viewContainer = ViewContainer.getInstance();

		Collection<IPhoto> photos = viewContainer.getPhotos();

		TableLayout tableLayout = (TableLayout) findViewById(R.id.ViewPhotosLayout);
		
		// for (int x = 0; x < 3; x++) {
		// ImageView image = new ImageView(ViewPhotos.this);
		// image.setBackgroundResource(R.drawable.ic_launcher);
		// linearLayout1.addView(image);
		// }
		int counter = 0;
		TableRow row = null;
		
		for (IPhoto p : photos) {
			
			if(counter % 3 == 0){
				row = new TableRow(this);
				row.setPadding(5, 5, 5, 5);
				tableLayout.addView(row,new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
			
			counter++;
			
			// File imgFile = new File("file:///android_asset/" + p.getName());
			File imgFile = new File(p.getName());
			if (imgFile.exists()) {
				Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
						.getAbsolutePath());

				ImageView image = new ImageView(ViewPhotos.this);
				image.setImageBitmap(resize(myBitmap,200,200));

				
				row.addView(image);
				Log.e(getLocalClassName(), imgFile.getAbsolutePath());
			}
		}
	}

	public static Bitmap resize(Bitmap image, int newHeight,
			int newWidth) {
		
		
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
