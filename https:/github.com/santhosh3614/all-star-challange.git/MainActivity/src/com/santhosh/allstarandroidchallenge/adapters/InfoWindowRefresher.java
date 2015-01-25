package com.santhosh.allstarandroidchallenge.adapters;

import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;

public class InfoWindowRefresher implements Callback {

	private Marker marker;

	public InfoWindowRefresher(Marker marker) {
		this.marker = marker;
	}

	@Override
	public void onSuccess() {
		if (marker != null) {
			marker.showInfoWindow();
		}
	}

	@Override
	public void onError() {
	}

}
