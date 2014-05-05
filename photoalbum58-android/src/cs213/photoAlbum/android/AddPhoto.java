package cs213.photoAlbum.android;

import cs213.photoAlbum.model.IAlbum;
import cs213.photoAlbum.simpleview.ViewContainer;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;
import android.provider.MediaStore.Images.ImageColumns;

public class AddPhoto extends ActionBarActivity {

	private Button fileBrowser;

	private Button cancelBtn;

	private Button saveBtn;

	private TextView pathnameVal;

	private TextView albumVal;

	private TextView captionVal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_photo);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		fileBrowser = (Button) findViewById(R.id.buttonSelectFile);
		fileBrowser.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				Intent pickIntent = new Intent(Intent.ACTION_PICK);
				pickIntent.setType("image/*");
				startActivityForResult(pickIntent, 1);
			}
		});

		pathnameVal = (TextView) findViewById(R.id.PathnameVal);
		captionVal = (TextView) findViewById(R.id.captionVal);
		albumVal = (TextView) findViewById(R.id.albumVal);

		Intent intent = getIntent();
		albumVal.setText(intent.getStringExtra("Album"));

		cancelBtn = (Button) findViewById(R.id.CancelAddPhoto);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(AddPhoto.this, ViewPhotos.class);
				startActivity(i);
			}
		});

		saveBtn = (Button) findViewById(R.id.SavePhoto);
		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(AddPhoto.this, ViewPhotos.class);

				ViewContainer viewContainer = ViewContainer.getInstance();

				viewContainer.albumController.addPhoto(pathnameVal.getText()
						.toString(), captionVal.getText().toString(), albumVal
						.getText().toString(), viewContainer.getUser());

				viewContainer.saveUser();
				
				startActivity(i);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultcode,
			Intent intent) {

		super.onActivityResult(requestCode, resultcode, intent);
		if (requestCode == 1) {
			if (intent != null) {

				Cursor cursor = getContentResolver().query(intent.getData(),
						null, null, null, null);
				cursor.moveToFirst();
				int idx = cursor.getColumnIndex(ImageColumns.DATA);
				String fileSrc = cursor.getString(idx);
				pathnameVal.setText(fileSrc);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_photo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_photo,
					container, false);
			return rootView;
		}
	}

}
