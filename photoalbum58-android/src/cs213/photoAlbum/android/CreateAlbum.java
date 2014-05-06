package cs213.photoAlbum.android;

import cs213.photoAlbum.simpleview.ViewContainer;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.os.Build;


/**
 * The Class CreateAlbum.
 */
public class CreateAlbum extends ActionBarActivity {

	/** The create. */
	private Button create;
	
	/** The ad. */
	private AlertDialog ad;
	
	/** The cancel. */
	private Button cancel;
	
	/** The tf. */
	private EditText tf;
	
	/** The container. */
	private ViewContainer container = ViewContainer.getInstance();
	
	/**
	 * On create.
	 *
	 * @param savedInstanceState the saved instance state
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_album);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		buildAlertDialog();
		setTitle("Create Album");
		tf   = (EditText)findViewById(R.id.TextField2);
		create = (Button) findViewById(R.id.Create2);
		create.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String albumName = tf.getText().toString().trim();
				if(albumName.equals("")) {
					ad.setMessage("Did not specify a name for album.");
					ad.show();
				}
				else if(container.isAlbumExist(albumName)) {
					ad.setMessage("Album name already exists.");
					ad.show();
				}
				else {
					container.createAlbum(albumName);
					MainActivity.albumChange = true;
					container.saveUser();
					finish();
				}
			}
		});
		cancel = (Button) findViewById(R.id.Cancel2);
		cancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
	}
	
	/**
	 * Builds the alert dialog.
	 */
	@SuppressWarnings("deprecation")
	public void buildAlertDialog() {
		ad = new AlertDialog.Builder(this).create();
		ad.setTitle("Error!");
		ad.setButton("Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				tf.setText("");
				ad.cancel();
			}
		});
	}

	/**
	 * On create options menu.
	 *
	 * @param menu the menu
	 * @return true, if successful
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_album, menu);
		return true;
	}

	/**
	 * On options item selected.
	 *
	 * @param item the item
	 * @return true, if successful
	 */
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

		/**
		 * Instantiates a new placeholder fragment.
		 */
		public PlaceholderFragment() {
		}

		/**
		 * On create view.
		 *
		 * @param inflater the inflater
		 * @param container the container
		 * @param savedInstanceState the saved instance state
		 * @return the view
		 */
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_create_album,
					container, false);
			return rootView;
		}
	}

}
