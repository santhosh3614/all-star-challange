package com.santhosh.allstarandroidchallenge.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.santhosh.allstarandroidchallenge.R;
import com.santhosh.allstarandroidchallenge.utillity.TextToSpeechUtils;
import com.santhosh.allstartandroidchallenge.core.Place;
import com.squareup.picasso.Picasso;

public class MapWindowInfoAdapter implements InfoWindowAdapter {

	private Context context;
	private List<Place> places;
	private boolean not_first_time_showing_info_window;

	public MapWindowInfoAdapter(Context context, List<Place> places) {
		this.context = context;
		this.places = places;
	}

	@Override
	public View getInfoContents(Marker marker) {
		View view = null;
		Place place = getPlace(marker.getPosition());
		if (marker != null && place != null) {
			view = LayoutInflater.from(context.getApplicationContext())
					.inflate(R.layout.map_window_info, null);
			StringBuilder desc = new StringBuilder("Place : " + place.getName()
					+ "\n");
			desc.append("Address:" + place.getVicinity() + "\n");
			// desc.append("Rating:" + place.getRating());
			((TextView) view.findViewById(R.id.desc_txtView)).setText(desc);
			// set image view like this:
			if (not_first_time_showing_info_window) {
				Picasso.with(context.getApplicationContext())
						.load(place.getIcon())
						.into((ImageView) view.findViewById(R.id.icon));
			} else { // if it's the first time, load the image with the callback
						// set
				not_first_time_showing_info_window = true;
				Picasso.with(context.getApplicationContext())
						.load(place.getIcon())
						.into((ImageView) view.findViewById(R.id.icon),
								new InfoWindowRefresher(marker));
			}
		}
		return view;
	}

	private Place getPlace(LatLng latLng) {
		if (latLng != null) {
			for (Place plc : places) {
				if (plc.getGeometry().getLocation().getLatitude() == latLng.latitude
						&& plc.getGeometry().getLocation().getLongitude() == latLng.longitude) {
					return plc;
				}
			}
		}
		return null;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}

	public void playReview(String type, LatLng latLng) {
		String speech = null;
		Place place = getPlace(latLng);
		if (type.equals("airport") || type.equals("atm") || type.equals("bank")
				|| type.equals("bus_station") || type.equals("church")
				|| type.equals("doctor") || type.equals("hospital")
				|| type.equals("mosque") || type.equals("movie_theater")
				|| type.equals("hindu_temple") || type.equals("restaurant")) {
			speech = place.getVicinity()
					+ " having rating "
					+ ((place.getRating() > 0) ? "" + place.getRating()
							: " not defined.");
		}
		if (speech != null) {
			TextToSpeechUtils.speak(context, speech);
		}
	}
}
