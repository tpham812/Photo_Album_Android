package cs213.photoAlbum.android;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import cs213.photoAlbum.model.IPhoto;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;

public class SearchPhotos extends ActionBarActivity {

	private Button search;
	private Button cancel;
	private AlertDialog ad;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_photos);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		buildAlertDialog();
		search = (Button)findViewById(R.id.Search2);
		search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				List<String> tagType = new ArrayList<String>();
				List<String> tagValue = new ArrayList<String>();
				getTags(tagType, tagValue);
				SortedSet<IPhoto> photos = MainActivity.container.getPhotosByTag(tagType, tagValue);
				if(photos.isEmpty()) {
					ad.setTitle("No Result");
					ad.setMessage("No photos match your search.");
					ad.show();
				}
			}
			public void getTags(List<String> tagType, List<String> tagValue) {	
				tagType.add(((EditText) findViewById(R.id.tf1_1)).getText().toString());
				tagType.add(((EditText) findViewById(R.id.tf2_1)).getText().toString());
				tagType.add(((EditText) findViewById(R.id.tf3_1)).getText().toString());
				tagType.add(((EditText) findViewById(R.id.tf4_1)).getText().toString());
				tagType.add(((EditText) findViewById(R.id.tf5_1)).getText().toString());
				tagValue.add(((EditText) findViewById(R.id.tf1_2)).getText().toString());
				tagValue.add(((EditText) findViewById(R.id.tf2_2)).getText().toString());
				tagValue.add(((EditText) findViewById(R.id.tf3_2)).getText().toString());
				tagValue.add(((EditText) findViewById(R.id.tf4_2)).getText().toString());
				tagValue.add(((EditText) findViewById(R.id.tf5_2)).getText().toString());
			}
		});
		cancel = (Button)findViewById(R.id.Cancel3);
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	public void buildAlertDialog() {
		ad = new AlertDialog.Builder(this).create();
		ad.setButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				ad.cancel();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_photos, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_search_photos,
					container, false);
			return rootView;
		}
	}

}
