package cs213.photoAlbum.android;

import java.util.ArrayList;
import java.util.List;

import cs213.photoAlbum.simpleview.ViewContainer;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	public static int i = 0;
	
	public static ArrayAdapter<String> adapter; 
	
	public static List <String> list = new ArrayList<String>();
	
	private ViewContainer container;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		container = ViewContainer.getInstance();
		if(container == null) {
			ViewContainer.init(getApplicationContext().getFilesDir().getAbsolutePath());
		}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		setTitle("Photo Album");
		populateList();
	}
	
	public void populateList() {
		
		list.add("Red"); 
		list.add("Blue");
		list.add("Yellow");
		
		adapter = new ArrayAdapter<String>(this, R.layout.color_list, list);
		final ListView list2 = (ListView) findViewById(R.id.listView1);
		list2.setAdapter(adapter);
		registerForContextMenu(list2);
	}
	

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add("Delete");
		menu.add("Edit");
	}
	
	

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onContextItemSelected(item);
		if(item.getTitle() == "Edit") {
			Intent i = new Intent(this, EditAlbum.class);
			startActivity(i);
		}
		
		return true;
	
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
