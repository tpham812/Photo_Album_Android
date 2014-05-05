package cs213.photoAlbum.android;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PhotoSelect extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	static final String 	LOG_TAG 	= "PhotoSelect";
	static final int 		idBut 		= Menu.FIRST + 1,
							idIntentID 	= Menu.FIRST + 2;
	Button m_Button;
	TextView m_Tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a linearlayout to hold a button and an image
        LinearLayout panel = new LinearLayout(this);
  		panel.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
  		panel.setOrientation(LinearLayout.VERTICAL);

  		m_Tv = new TextView(this);
  		m_Tv.setText("Press button to select image");
  		panel.addView(m_Tv);
        
  		m_Button = new Button(this);
  		m_Button.setId(idBut);
  		m_Button.setText("Select Image");
  		m_Button.setOnClickListener(this);
        panel.addView(m_Button);

        
        setContentView(panel);
    }
    
    public void onClick(View v) {
    	int id = v.getId();
		if (id == idBut) {
			//open photo picker intent
			Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
			photoPickerIntent.setType("image/*");
			startActivityForResult(photoPickerIntent, idIntentID);
		}
	}
    
    @Override
	  protected void onActivityResult(int requestCode, int resultcode, Intent intent)
	  {
		  super.onActivityResult(requestCode, resultcode, intent);
		  if (requestCode == idIntentID) {
			  if (intent != null) {
				  
				  Cursor cursor = getContentResolver().query(intent.getData(), null, null, null, null);
				  cursor.moveToFirst();  
				  int idx = cursor.getColumnIndex(ImageColumns.DATA);
				  String fileSrc = cursor.getString(idx); 
				  Log.d(LOG_TAG, "Picture:" + fileSrc);
				  m_Tv.setText("Image selected:"+fileSrc);
				  
			  } else {

				  m_Tv.setText("Image selection canceled!");
			  }
		  }
	  }

	

}