package com.supercwn.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.supercwn.player.fragments.AlbumListFragment;
import com.supercwn.player.model.SCChannel;
import com.supercwn.player.model.SCChannelFilter;
import com.supercwn.player.siteapi.SiteApi;
import com.supercwn.player.uiutils.BaseToolbarActivity;
import com.supercwn.player.uiutils.SlidingTabLayout;

import java.util.HashMap;

public class AlbumListActivity extends BaseToolbarActivity
        implements AlbumFilterDialog.OnAlbumFilterDialogAction
{
    ViewPager mViewPager;
    private SlidingTabLayout mSlidingTabLayout;
    private SitePagerAdapter mPagerAdapter;
    private int mChannelID;

    private static final String EXTRA_CHANNEL_ID = "channelID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mChannelID = intent.getIntExtra(EXTRA_CHANNEL_ID,0);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mPagerAdapter = new SitePagerAdapter(getSupportFragmentManager(), this, mChannelID);
        mViewPager.setAdapter(mPagerAdapter);
        mSlidingTabLayout.setViewPager(mViewPager);

        SCChannel channel = new SCChannel(mChannelID);
        setTitle(channel.toString());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_album_list;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void showAlbumFilterDialog() {
        AlbumListFragment fragment = mPagerAdapter.getFragment(mViewPager.getCurrentItem());
        if(fragment!= null)
            fragment.showAlbumFilterDialog(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    public static void launch(Activity activity, int mChannelID) {
        Intent mpdIntent = new Intent(activity, AlbumListActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .putExtra(AlbumListActivity.EXTRA_CHANNEL_ID, mChannelID);
        activity.startActivity(mpdIntent);
    }

    @Override
    public void onAlbumFilterSelected(SCChannelFilter filter) {

        AlbumListFragment fragment = mPagerAdapter.getFragment(mViewPager.getCurrentItem());
        if(fragment!= null)
            fragment.setChannelFilter(filter);
    }



    private class SitePagerAdapter extends FragmentPagerAdapter {

        private Context mContext;
        private int mChannelID;
        private HashMap<Integer,AlbumListFragment> mPageReferenceMap;

        public SitePagerAdapter(FragmentManager fm, Context context, int mChannelID) {
            super(fm);
            this.mContext = context;
            this.mChannelID = mChannelID;
            mPageReferenceMap = new HashMap<>();
        }

        private int positionToSiteID(int position) {
            int mSiteID = position;
            return mSiteID;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            if(obj instanceof AlbumListFragment) {
                mPageReferenceMap.put(position, (AlbumListFragment) obj);
            }
            return obj;
        }

        @Override
        public AlbumListFragment getItem(int position) {
            AlbumListFragment fragment  = AlbumListFragment.newInstance(positionToSiteID(position),mChannelID);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            mPageReferenceMap.remove(position);
        }

        public AlbumListFragment getFragment(int position) {
            return mPageReferenceMap.get(position);
        }

        @Override
        public int getCount() {
            return SiteApi.getSupportSiteNumber();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return SiteApi.getSiteName(positionToSiteID(position), mContext);
        }
    }
}
