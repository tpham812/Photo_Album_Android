package cs213.photoAlbum.android;

import java.io.File;
import java.util.Iterator;
import java.util.SortedSet;

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
import cs213.photoAlbum.model.IUser;
import cs213.photoAlbum.simpleview.ViewContainer;
import cs213.photoAlbum.util.Utils;

public class SlideshowFragment extends Fragment {

	public static final String CUR_PAGE = "curPage";

	private int pageNum;

	private IPhoto photo;

	private ViewContainer ctr;

	private TextView loc1, per1, per2, per3;

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
	public View onCreateView(LayoutInflater inflater,
			final ViewGroup container, Bundle savedInstanceState) {

		final ViewGroup view = (ViewGroup) inflater.inflate(
				R.layout.fragment_slideshow, container, false);

		TableRow row = (TableRow) view.findViewById(R.id.tableRow1);
		ctr = ViewContainer.getInstance();
		photo = ctr.getPhotos().get(pageNum);

		File imgFile = new File(photo.getName());
		if (imgFile.exists()) {
			Bitmap myBitmap = BitmapFactory.decodeFile(imgFile
					.getAbsolutePath());

			ImageView image = new ImageView(row.getContext());
			image.setImageBitmap(Utils.resize(myBitmap, 600, 600));
			row.addView(image);
		}

		final TextView captionVal = (TextView) view
				.findViewById(R.id.captionVal);
		captionVal.setText(photo.getCaption());

		loc1 = (TextView) view.findViewById(R.id.locVal);

		SortedSet<String> tags = photo.getTags().get("location");
		if (tags != null && !tags.isEmpty()) {
			loc1.setText(tags.iterator().next());
		}

		per1 = (TextView) view.findViewById(R.id.perVal1);
		per2 = (TextView) view.findViewById(R.id.perVal2);
		per3 = (TextView) view.findViewById(R.id.perVal3);

		tags = photo.getTags().get("person");

		if (tags != null && !tags.isEmpty()) {
			Iterator<String> itr = tags.iterator();
			if (itr.hasNext()) {
				per1.setText(itr.next());
			}
			if (itr.hasNext()) {
				per2.setText(itr.next());
			}
			if (itr.hasNext()) {
				per3.setText(itr.next());
			}
		}

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

				photo.getTags().clear();

				IUser u = ctr.getUser();

				ctr.photoController.addTag(photo.getName(), "location", loc1
						.getText().toString(), u);

				if (per1.getText().length() > 0) {
					ctr.photoController.addTag(photo.getName(), "person", per1
							.getText().toString(), u);
				}

				if (per2.getText().length() > 0) {
					ctr.photoController.addTag(photo.getName(), "person", per2
							.getText().toString(), u);
				}

				if (per3.getText().length() > 0) {
					ctr.photoController.addTag(photo.getName(), "person", per3
							.getText().toString(), u);
				}

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
