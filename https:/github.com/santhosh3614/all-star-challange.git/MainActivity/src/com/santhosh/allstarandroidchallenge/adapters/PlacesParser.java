package com.santhosh.allstarandroidchallenge.adapters;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.santhosh.allstartandroidchallenge.core.Place;
import com.santhosh.allstartandroidchallenge.core.Place.Geometry;
import com.santhosh.allstartandroidchallenge.core.Place.Location;

public class PlacesParser {

	public List<Place> parsePlaces(String json) {
		List<Place> places = null;
		if (json != null) {
			try {
				places = new ArrayList<Place>();
				JSONObject jsonObject = new JSONObject(json);
				JSONArray jsonArray = jsonObject.getJSONArray("results");
				for (int i = 0; i < jsonArray.length(); i++) {
					Place place = getPlace(jsonObject = jsonArray
							.getJSONObject(i));
					if (place != null) {
						places.add(place);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return places;
	}

	private Place getPlace(JSONObject jsonObject) {
		Place place = null;
		if (jsonObject != null) {
			try {
				place = new Place();
				place.setId(jsonObject.getString("id"));
				place.setIcon(jsonObject.getString("icon"));
				place.setName(jsonObject.getString("name"));
				place.setReference(jsonObject.getString("reference"));
				try{					
					place.setRating((float) jsonObject.getDouble("rating"));
				}catch(JSONException jsonException){}
				place.setVicinity(jsonObject.getString("vicinity"));
				jsonObject = jsonObject.getJSONObject("geometry")
						.getJSONObject("location");
				Geometry geometry = new Geometry();
				geometry.setLocation(new Location(jsonObject.getDouble("lat"),
						jsonObject.getDouble("lng")));
				place.setGeometry(geometry);
			} catch (JSONException e) {
				e.printStackTrace();
				place = null;
			}
		}
		return place;
	}

}
