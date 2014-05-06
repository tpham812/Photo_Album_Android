

package cs213.photoAlbum.android;

import cs213.photoAlbum.simpleview.ViewContainer;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;



/**
 * The Class SlideshowActivity.
 */
public class SlideshowActivity extends FragmentActivity {
    
    /** The pager. */
    private ViewPager pager;

    /** The pager adapter. */
    private PagerAdapter pagerAdapter;
    
    /** The startpage. */
    public static String STARTPAGE = "STARTPAGE";

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        pager = (ViewPager) findViewById(R.id.slideshow_act);
        pagerAdapter = new SlideshowPagerAdapter(getFragmentManager());
        
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(getIntent().getIntExtra(STARTPAGE, 0));
    }

    /**
     * The Class SlideshowPagerAdapter.
     */
    private class SlideshowPagerAdapter extends FragmentStatePagerAdapter {
        
        /**
         * Instantiates a new slideshow pager adapter.
         *
         * @param fm the fm
         */
        public SlideshowPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Gets the item.
         *
         * @param position the position
         * @return the item
         */
        @Override
        public Fragment getItem(int position) {
            return SlideshowFragment.create(position);
        }

        /**
         * Gets the count.
         *
         * @return the count
         */
        @Override
        public int getCount() {
            return ViewContainer.getInstance().getPhotos().size();
        }
    }
}
