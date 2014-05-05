package cs213.photoAlbum.android;

import java.io.File;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import cs213.photoAlbum.model.IPhoto;
import cs213.photoAlbum.simpleview.ViewContainer;

public class SlideshowFragment extends Fragment {

    public static final String CUR_PAGE = "curPage";

    private int pageNum;
    
    private IPhoto photo;

    public static SlideshowFragment create(int pageNumber) {
        SlideshowFragment fragment = new SlideshowFragment();
        Bundle args = new Bundle();
        args.putInt(CUR_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public SlideshowFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNum = getArguments().getInt(CUR_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
            Bundle savedInstanceState) {

    	ViewGroup view = (ViewGroup) inflater
                .inflate(R.layout.fragment_slideshow, container, false);

        
        TableRow row = (TableRow)view.findViewById(R.id.tableRow1);        
        final ViewContainer ctr = ViewContainer.getInstance();
        photo = ctr.getPhotos().get(pageNum);
        
        
		File imgFile = new File(photo.getName());
		if (imgFile.exists()) {
			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
					.getAbsolutePath());

			ImageView image = new ImageView(row.getContext());
			image.setImageBitmap(myBitmap);			
			row.addView(image);
		}
        
        final TextView captionVal = (TextView) view.findViewById(R.id.captionVal);
		captionVal.setText(photo.getCaption());

		
//		String[] FRUITS = new String[] { "Apple", "Avocado", "Banana",
//				"Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
//				"Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple" };
//		
//		ListView albumList = (ListView) view.findViewById(R.id.albumList);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(container.getContext(), R.layout.albumrow,FRUITS);
//		albumList.setAdapter(adapter);
		
		Button cancelBtn = (Button) view.findViewById(R.id.CancelAddPhoto);
		cancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(container.getContext(), ViewPhotos.class);
				startActivity(i);
			}
		});

		Button saveBtn = (Button) view.findViewById(R.id.SavePhoto);
		saveBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				photo.setCaption(captionVal.getText().toString());
				ctr.saveUser();

			}
		});
		saveBtn.requestFocus();
		
        return view;
    }

    public int getPageNumber() {
        return pageNum;
    }
}
