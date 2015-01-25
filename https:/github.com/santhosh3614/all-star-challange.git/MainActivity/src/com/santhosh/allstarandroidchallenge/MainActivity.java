package com.santhosh.allstarandroidchallenge;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.santhosh.allstarandroidchallenge.adapters.SpinnerAdapter;

public class MainActivity extends Activity implements OnItemSelectedListener,
		OnClickListener, TextWatcher {

	private Spinner destSpinner;
	private List<String> destinationList;
	private List<String> destinationListNames;
	private final String SPINNER_DEFAULT_TAG = "Choose your Destination";
	private Button nxtbtn;
	private EditText searchPlaceEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		searchPlaceEditText = (EditText) findViewById(R.id.search_place_editText);
		searchPlaceEditText.addTextChangedListener(this);
		destinationList = Arrays.asList(getResources().getStringArray(
				R.array.dest_list));
		destinationListNames = Arrays.asList(getResources().getStringArray(
				R.array.dest_list_name));
		destSpinner = (Spinner) findViewById(R.id.choose_dest_spinner);
		nxtbtn = (Button) findViewById(R.id.nxt_btn);
		nxtbtn.setOnClickListener(this);
		destSpinner.setAdapter(new SpinnerAdapter<String>(this,
				destinationListNames));
		destSpinner.post(new Runnable() {
			public void run() {
				destSpinner.setOnItemSelectedListener(MainActivity.this);
			}
		});
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent.getId() == R.id.choose_dest_spinner) {
			startMapScreen(position);
		}
	}

	private void startMapScreen(int position) {
		Intent intent = new Intent(this, MapActivity.class);
		intent.putExtra("type", destinationList.get(position));
		startActivity(intent);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.nxt_btn) {
			startMapScreen(destSpinner.getSelectedItemPosition());
		}
	}

	@Override
	public void afterTextChanged(Editable arg0) {
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		if (arg0 != null) {

		}
	}
}
