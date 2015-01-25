package com.santhosh.allstartandroidchallenge.core;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable {

	private String id;
	private String name;
	private String reference;
	private String icon;
	private String vicinity;
	private Geometry geometry;
	private String formatted_address;
	private String formatted_phone_number;
	private float rating;

	public Place() {
		id = icon = name = reference = vicinity = "NA";
		rating = -1.0f;
	}

	@Override
	public String toString() {
		return getId();
	}

	public static class Geometry implements Parcelable {

		public Geometry() {
			// TODO Auto-generated constructor stub
		}

		private Location location;

		@Override
		public int describeContents() {
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {

		}

		public Location getLocation() {
			return location;
		}

		public void setLocation(Location location) {
			this.location = location;
		}
	}

	public static class Location implements Parcelable {
		private double lat;
		private double lng;

		public Location(double latitude, double longitude) {
			this.setLatitude(latitude);
			this.setLongitude(longitude);
		}

		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void writeToParcel(Parcel dest, int flags) {
			// TODO Auto-generated method stub
		}

		public double getLatitude() {
			return lat;
		}

		public void setLatitude(double lat) {
			this.lat = lat;
		}

		public double getLongitude() {
			return lng;
		}

		public void setLongitude(double lng) {
			this.lng = lng;
		}
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public Geometry getGeometry() {
		return geometry;
	}

	public void setGeometry(Geometry geometry) {
		this.geometry = geometry;
	}

	public String getFormatted_address() {
		return formatted_address;
	}

	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}

	public String getFormatted_phone_number() {
		return formatted_phone_number;
	}

	public void setFormatted_phone_number(String formatted_phone_number) {
		this.formatted_phone_number = formatted_phone_number;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
}
