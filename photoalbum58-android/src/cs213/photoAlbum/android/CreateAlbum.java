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

public class CreateAlbum extends ActionBarActivity {

	private Button create;
	private AlertDialog ad;
	private Button cancel;
	private EditText tf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_album);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		setTitle("Edit Album");
		setTitle("Edit Album");
		tf   = (EditText)findViewById(R.id.TextField2);
		create = (Button) findViewById(R.id.Create2);
		create.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				ad.show();
			}
		});
		buildAlertDialog();
		
		cancel = (Button) findViewById(R.id.Cancel2);
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
		getMenuInflater().inflate(R.menu.create_album, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_create_album,
					container, false);
			return rootView;
		}
	}

}
