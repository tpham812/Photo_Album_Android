package cs213.photoAlbum.android;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import cs213.photoAlbum.model.IAlbum;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


/**
 * The Class MainActivity.
 */
public class MainActivity extends ActionBarActivity {

	/** The adapter. */
	private ArrayAdapter<String> adapter;
	
	/** The search. */
	private Button search;
	
	/** The create. */
	private Button create;
	
	/** The list. */
	private ListView list;
	
	/** The container. */
	private ViewContainer container;
	
	/** The album change. */
	public static boolean albumChange = false;

	/**
	 * On create.
	 *
	 * @param savedInstanceState the saved instance state
	 */
	protected void onCreate(Bundle savedInstanceState) {

		container = ViewContainer.getInstance();
		if (container == null) {
			ViewContainer.init(getApplicationContext().getFilesDir()
					.getAbsolutePath());
			container = ViewContainer.getInstance();
		}
		container.setAlbum(null);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		setTitle("Photo Album");
		create = (Button) findViewById(R.id.Create);
		create.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, CreateAlbum.class);
				startActivity(i);
			}
		});
		search = (Button) findViewById(R.id.Search);
		search.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, SearchPhotos.class);
				startActivity(i);
			}
		});
		populateList();
	}

	/**
	 * Populate list.
	 */
	public void populateList() {

		Collection<IAlbum> albums = container.listAlbums();
		List<String> albumList = new ArrayList<String>();
		toArrayList(albums, albumList);
		list = (ListView) findViewById(R.id.listView1);
		adapter = new ArrayAdapter<String>(this, R.layout.album_list, albumList);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(MainActivity.this, ViewPhotos.class);
				Object o = list.getItemAtPosition(position);
				i.putExtra("Album", (String) o);
				container.setAlbum(container.getAlbum((String) o));
				startActivity(i);
			}
		});
		registerForContextMenu(list);
	}

	/**
	 * To array list.
	 *
	 * @param albums the albums
	 * @param albumList the album list
	 */
	public void toArrayList(Collection<IAlbum> albums, List<String> albumList) {
		Iterator<IAlbum> iterator = albums.iterator();
		while (iterator.hasNext()) {
			albumList.add(iterator.next().getAlbumName());
		}
	}

	/**
	 * On restart.
	 */
	public void onRestart() {
		super.onRestart();
		if (albumChange) {
			populateList();
			albumChange = false;
		}
	}

	/**
	 * On create context menu.
	 *
	 * @param menu the menu
	 * @param v the v
	 * @param menuInfo the menu info
	 */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add("Rename");
		menu.add("Delete");
	}

	/**
	 * On context item selected.
	 *
	 * @param item the item
	 * @return true, if successful
	 */
	@Override
	public boolean onContextItemSelected(MenuItem item) {

		super.onContextItemSelected(item);
		if ("Rename".equals(item.getTitle())) {
			Intent i = new Intent(this, EditAlbum.class);
			AdapterContextMenuInfo adapterMenuInfo = (AdapterContextMenuInfo) item
					.getMenuInfo();
			Object o = list.getItemAtPosition(adapterMenuInfo.position);
			i.putExtra("Old_Album", (String) o);
			startActivity(i);
		} else {
			AdapterContextMenuInfo adapterMenuInfo = (AdapterContextMenuInfo) item
					.getMenuInfo();
			Object o = list.getItemAtPosition(adapterMenuInfo.position);
			container.deleteAlbum((String) o);
			container.saveUser();
			populateList();
		}
		return true;
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
		getMenuInflater().inflate(R.menu.main, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
}