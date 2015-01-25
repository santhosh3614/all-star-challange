package com.santhosh.allstarandroidchallenge.adapters;

import java.util.List;

import com.santhosh.allstarandroidchallenge.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SpinnerAdapter<T> extends BaseAdapter {
	/**
	 * String type vibration,sound and etc...
	 */
	private List<T> objects;
	private Context context;

	/**
	 * This is the default constructor for spinner adapter
	 * 
	 * @param context
	 * @param objects
	 */
	public SpinnerAdapter(Context context, List<T> objects) {
		this.context = context;
		this.objects = objects;
	}

	/**
	 * This Method is used to get the count of no of strings
	 */
	@Override
	public int getCount() {
		return objects.size();
	}

	/**
	 * 
	 * This Method is used to get String
	 */
	@Override
	public T getItem(int position) {
		return objects.get(position);
	}
	
	public void removeItem(int position){
		objects.remove(position);
		notifyDataSetChanged();
	}

	/**
	 * This Method is used to get position
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * This Method is used to get the view
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){			
			convertView = LayoutInflater.from(context).inflate(
					R.layout.custom_spinner_layout, null);
		}
		T t = getItem(position);
		((TextView) convertView.findViewById(R.id.spinner_item_textView))
				.setText(t != null ? t.toString() : "");
		return convertView;
	}

	/**
	 * This Method is used to get drop down view
	 */
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){			
			convertView = LayoutInflater.from(context).inflate(R.layout.custom_spinner_dropdown_layout,
					null);
		}
		T t = getItem(position);
		((TextView) convertView.findViewById(R.id.spinner_item_textView))
				.setText(t!=null?t.toString():"");
		return convertView;
	}
}
