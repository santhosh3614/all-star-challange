package com.santhosh.allstarandroidchallenge;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.santhosh.allstarandroidchallenge.adapters.MapWindowInfoAdapter;
import com.santhosh.allstarandroidchallenge.adapters.PlacesParser;
import com.santhosh.allstarandroidchallenge.utillity.NetworkUtils;
import com.santhosh.allstarandroidchallenge.utillity.Utils;
import com.santhosh.allstartandroidchallenge.core.Place;

public class MapActivity extends FragmentActivity implements LocationListener {

	private GoogleMap mGoogleMap;
	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	// The minimum time between updates inFsss milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minutesss
	private String type;
	private PlacesTask placesTask;
	private ProgressBar progressbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_layout);
		progressbar = (ProgressBar) findViewById(R.id.progressBar1);
		if(NetworkUtils.isInternetConnected(this)){			
			init();
		}else{
			Toast.makeText(this,"Unable to View locations due to poor internet",Toast.LENGTH_LONG).show();
		}
	}

	private void init() {
		if (!Utils.isGooglePlayServicesAvailable(this)) {
			finish();
		}
		if (getIntent() != null) {
			type = getIntent().getStringExtra("type");
		}
		initilizeMap();// Loading map
		Location location = getCurrentLocation(this);
		if (location != null) {
			onLocationChanged(location);
		}
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		try {
			if (mGoogleMap == null) {
				mGoogleMap = ((SupportMapFragment) getSupportFragmentManager()
						.findFragmentById(R.id.map)).getMap();
				// check if map is created successfully or not
				if (mGoogleMap == null) {
					Toast.makeText(getApplicationContext(),
							"Sorry! unable to create maps", Toast.LENGTH_SHORT)
							.show();
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null && location.getLatitude() != 0
				&& location.getLongitude() != 0) {
			if (placesTask != null && placesTask.getStatus() != Status.FINISHED) {
				placesTask.cancel(true);
			}
			placesTask = new PlacesTask();
			placesTask.execute(location);
		}
	}

	class PlacesTask extends AsyncTask<Location, Void, List<Place>> {
		@Override
		protected void onPreExecute() {
			if (progressbar != null) {
				progressbar.setVisibility(View.VISIBLE);
			}
		}

		@Override
		protected List<Place> doInBackground(Location... locations) {
			if (type != null && locations != null && locations.length > 0) {
				try {
					String jsonoutput = Utils.downloadUrl(getUrl(type,
							locations[0].getLatitude(),
							locations[0].getLongitude(), 5000, true,
							"AIzaSyCNfPhyVvjoBCWYsRvhUIXpMLXWGal4TxU"));
					return new PlacesParser().parsePlaces(jsonoutput);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Place> result) {
			if (result != null && mGoogleMap != null) {
				if (progressbar != null) {
					progressbar.setVisibility(View.INVISIBLE);
					mGoogleMap.clear();
				    Marker marker = null;
					for (int i = 0; i < result.size(); i++) {
						Place place = result.get(i);
						if (place != null) {
							MarkerOptions markerOptions = new MarkerOptions();
							com.santhosh.allstartandroidchallenge.core.Place.Location location = place
									.getGeometry().getLocation();
							markerOptions.position(
									new LatLng(location.getLatitude(), location
											.getLongitude())).icon(
									BitmapDescriptorFactory
											.fromResource(R.drawable.pin48));
							marker = mGoogleMap.addMarker(markerOptions);
						}
					}
					if (marker != null) {
						mGoogleMap
								.animateCamera(CameraUpdateFactory.newLatLngZoom(
										new LatLng(
												marker.getPosition().latitude,
												marker.getPosition().longitude),
										12.0f));
					}
					final MapWindowInfoAdapter mapWindowInfoAdapter = new MapWindowInfoAdapter(
							MapActivity.this, result);
					mGoogleMap.setInfoWindowAdapter(mapWindowInfoAdapter);
					mGoogleMap.setOnMarkerClickListener(new OnMarkerClickListener() {
						@Override
						public boolean onMarkerClick(Marker arg0) {
							mapWindowInfoAdapter.playReview(type,arg0.getPosition());
							arg0.showInfoWindow();
							return false;
						}
					});
				}
			}
		}
	}

	private static String getUrl(String type, double latitude,
			double longitude, int radius, boolean sensor, String KEY) {
		final String PLACES_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
		return PLACES_URL + "type=" + type + "&location" + "=" + latitude + ","
				+ longitude + "&radius=" + radius + "&sensor=" + sensor
				+ "&key=" + KEY;
	}

	public Location getCurrentLocation(Context context) {
		Location location = null;
		try {
			LocationManager locationManager = (LocationManager) context
					.getSystemService(Activity.LOCATION_SERVICE);
			// Getting GPS status
			boolean isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
			// Getting network status
			boolean isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			if (!isGPSEnabled && !isNetworkEnabled) {
				// No network provider is enabled
			} else {
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					location = locationManager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				}
				// If GPS enabled, get latitude/longitude using GPS Services
				if (isGPSEnabled) {
					if (location == null) {
						locationManager.requestLocationUpdates(
								LocationManager.GPS_PROVIDER,
								MIN_TIME_BW_UPDATES,
								MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						location = locationManager
								.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return location;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}
}
