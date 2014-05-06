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
 * The Class EditAlbum.
 */
public class EditAlbum extends ActionBarActivity {

	/** The ad. */
	private AlertDialog ad;
	
	/** The apply. */
	private Button apply;
	
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
		setContentView(R.layout.activity_edit_album);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		setTitle("Edit Album");
		tf   = (EditText)findViewById(R.id.TextField);
		apply = (Button) findViewById(R.id.Apply);
		apply.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String newAlbumName = tf.getText().toString().trim();
				String oldAlbumName = getIntent().getExtras().getString("Old_Album");
				if(newAlbumName.equals("")) {
					ad.setMessage("Did not specify a name for album.");
					ad.show();
				}
				else if(newAlbumName.equals(oldAlbumName)) {
					ad.setMessage("New album name must be different than the old album name.");
					ad.show();
				}
				else if(container.isAlbumExist(newAlbumName)) {
					ad.setMessage("Album name already exists.");
					ad.show();
				}
				else {
					container.editAlbum(newAlbumName, getIntent().getExtras().getString("Old_Album"));
					MainActivity.albumChange = true;
					container.saveUser();
					finish();
				}
			}
		});
		buildAlertDialog();
		
		cancel = (Button) findViewById(R.id.Cancel);
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
		getMenuInflater().inflate(R.menu.edit_album, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_edit_album,
					container, false);
			return rootView;
		}
	}

}
