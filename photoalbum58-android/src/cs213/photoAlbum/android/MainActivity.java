package cs213.photoAlbum.android;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.os.Build;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		populateList();
	}
	
	private void populateList() {
		
		String[] list = {"Red", "Blue", "Yellow", "Green", "Purple", "Orange", "Violet", "This", "Is", "Only", "A", "Test", "And", "It's", "Cool", "Who", "Are", "You", "Right", "Now", "Am", "I"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.color_list, list);
		final ListView list2 = (ListView) findViewById(R.id.listView1);
		list2.setAdapter(adapter);
		list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Object o = list2.getItemAtPosition(position);
				String s = (String)o;
				if(s.equals("Red")) {
					Intent myIntent = new Intent(MainActivity.this, SecondActivity.class);
					startActivity(myIntent);
				}
			}
		});
		
		list2.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int position, long id) {
				Object o = list2.getItemAtPosition(position);
				String s = (String)o;
				if(s.equals("Blue")) {
					Intent myIntent = new Intent(MainActivity.this, SecondActivity.class);
					startActivity(myIntent);
				}
				return true;
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
