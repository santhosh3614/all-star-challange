package com.santhosh.allstarandroidchallenge.utillity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkUtils {
	/**
	 * check internet connection
	 * @param context
	 * @return
	 */
	public static boolean isInternetConnected(Context context) {
		return isWIFIConnected(context) || isMobileDataConneted(context);
	}
	/**
	 * active Network type
	 */
	public static int getActiveNetwork(Context context) {
		ConnectivityManager conMan = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final android.net.NetworkInfo network = conMan.getActiveNetworkInfo();
		return network != null? network.getType():-1;
	}
	
	public boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			Log.e("Network Testing", "***Available***");
			return true;
		}
		Log.e("Network Testing", "***Not Available***");
		return false;
	}

	/**
	 * wifi connection
	 * @param context
	 * @return
	 */
	public static boolean isWIFIConnected(Context context) {
		ConnectivityManager conMan = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final android.net.NetworkInfo wifi = conMan
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wifi.isAvailable() && wifi.isConnectedOrConnecting();
	}
	/**
	 * is Mobiledata is conneted
	 * @param context
	 * @return
	 */
	public static boolean isMobileDataConneted(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final android.net.NetworkInfo mobile = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return mobile.isAvailable() && mobile.isConnectedOrConnecting();
	}
}
