package cs213.photoAlbum.android;

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

public class EditAlbum extends ActionBarActivity {

	private AlertDialog ad;
	private Button apply;
	private Button cancel;
	private EditText tf;
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
				String albumName = tf.getText().toString().trim();
				if(albumName.equals("")) {
					ad.setMessage("Did not specify  a name for album.");
					ad.show();
				}
				if(MainActivity.container.isAlbumExist(albumName)) {
					ad.setMessage("Album name already exists.");
					ad.show();
				}
				else {
					
					MainActivity.container.editAlbum(albumName, getIntent().getExtras().getString("Old_Album"));
					MainActivity.albumChange = true;
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

	@SuppressWarnings("deprecation")
	public void buildAlertDialog() {
		ad = new AlertDialog.Builder(this).create();
		ad.setTitle("Error!");
		ad.setMessage("Album name already exists.");
		ad.setButton("Close", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				tf.setText("");
				ad.cancel();
				
			}
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_album, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_edit_album,
					container, false);
			return rootView;
		}
	}

}
