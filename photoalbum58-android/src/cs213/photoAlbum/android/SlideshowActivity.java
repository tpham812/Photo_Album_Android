

package cs213.photoAlbum.android;

import cs213.photoAlbum.simpleview.ViewContainer;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;


public class SlideshowActivity extends FragmentActivity {
    
    private ViewPager pager;

    private PagerAdapter pagerAdapter;
    
    public static String STARTPAGE = "STARTPAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        pager = (ViewPager) findViewById(R.id.slideshow_act);
        pagerAdapter = new SlideshowPagerAdapter(getFragmentManager());
        
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(getIntent().getIntExtra(STARTPAGE, 0));
    }

    private class SlideshowPagerAdapter extends FragmentStatePagerAdapter {
        public SlideshowPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return SlideshowFragment.create(position);
        }

        @Override
        public int getCount() {
            return ViewContainer.getInstance().getPhotos().size();
        }
    }
}
