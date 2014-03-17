
package com.blstream.myguide;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;


public class SightseeingActivity extends Activity implements OnCameraChangeListener {

	private ImageView mImgvSlidingMenu;
	private ImageView mImgvShowRoute;
	private SearchView mSearchView;
	private ImageView mSearchViewClose;
	private ActionBar mActionBar;

	private String[] mDrawerMenuItems;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private GoogleMap mMap;
	private float mMinZoom;
	private float mMaxZoom;
	private float mStartZoom;
	private double mStartCenterLat;
	private double mStartCenterLon;

	/**
	 * Called when the activity is first created. Sets up ActionBar and
	 * NavigationDrawer for the Activity.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_sightseeing);

		mActionBar = getActionBar();

		if (mActionBar != null) {
			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
			mActionBar.setCustomView(R.layout.action_bar_sightseeing);
			mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

			View actionBarCustomView = mActionBar.getCustomView();
			setUpActionBar(actionBarCustomView);
			setUpActionBarListeners();
		}

		setUpDrawerListView();

		mMinZoom = 14.5f;
		mMaxZoom = 19.0f;
		mStartZoom = 15.5f;
		mStartCenterLat = 51.1052072;
		mStartCenterLon = 17.0754498;

		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mStartCenterLat,mStartCenterLon), mStartZoom));
		mMap.setOnCameraChangeListener(this);
	}

	/** Sets up custom ActionBar. */
	private void setUpActionBar(View v) {
		mSearchView = (SearchView) v.findViewById(R.id.svSightseeing);
		mImgvShowRoute = (ImageView) v.findViewById(R.id.imgvShowRoute);
		mImgvSlidingMenu = (ImageView) v.findViewById(R.id.imgvSlidingMenu);

		mSearchView.setQueryHint(getString(R.string.search_sightseeing));
		mSearchView.setIconified(false);
		mSearchView.clearFocus();

		int searchPlateId = mSearchView.getContext().getResources()
				.getIdentifier("android:id/search_plate", null, null);
		View searchPlate = mSearchView.findViewById(searchPlateId);

		if (searchPlate != null) {
			searchPlate.setBackgroundResource(R.drawable.rounded_edittext);

			int searchTextId = searchPlate.getContext().getResources()
					.getIdentifier("android:id/search_src_text", null, null);
			TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
			if (searchText != null) {
				searchText.setGravity(Gravity.CENTER);
			}

			int search = searchPlate.getContext().getResources()
					.getIdentifier("android:id/search_close_btn", null, null);
			mSearchViewClose = (ImageView) searchPlate.findViewById(search);
			if (mSearchViewClose != null) {
				mSearchViewClose.setVisibility(View.GONE);
			}
		}
	}

	/** Sets up listeners for ActionBar views. */
	private void setUpActionBarListeners() {
		mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
			@Override
			public boolean onClose() {
				mSearchView.clearFocus();
				mSearchViewClose.setVisibility(View.GONE);
				return true;
			}
		});

		mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String s) {
				// TODO finding text

				mSearchView.clearFocus();
				return false;
			}

			@Override
			public boolean onQueryTextChange(String s) {
				// TODO we can crete database for parsed data, then we can use
				// AutoCompleText of animals when search is use

				return false;
			}
		});

		mImgvShowRoute.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// TODO handle route
			}
		});

		mImgvSlidingMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT)) mDrawerLayout
						.openDrawer(Gravity.LEFT);
				else mDrawerLayout.closeDrawer(Gravity.LEFT);
			}
		});
	}

	/** Sets up NavigationDrawer. */
	public void setUpDrawerListView() {

		mDrawerMenuItems = getResources().getStringArray(R.array.nav_drawer_items);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.lvMenuSightseeing);

		mDrawerList.setAdapter(new ArrayAdapter<String>(this,
				R.layout.sliding_menu_item, mDrawerMenuItems));

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close);

		mDrawerLayout.setDrawerListener(mDrawerToggle);

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	public void onCameraChange(CameraPosition camera) {
		if (camera.zoom < mMinZoom) {
			mMap.animateCamera(CameraUpdateFactory.zoomTo(mMinZoom));
		}
		else if (camera.zoom > mMaxZoom) {
			mMap.animateCamera(CameraUpdateFactory.zoomTo(mMaxZoom));
		}
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
