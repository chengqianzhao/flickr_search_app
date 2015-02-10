package com.flickr.search;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.googlecode.flickrjandroid.Flickr;
import com.googlecode.flickrjandroid.FlickrException;
import com.googlecode.flickrjandroid.photos.Photo;
import com.googlecode.flickrjandroid.photos.PhotoList;
import com.googlecode.flickrjandroid.photos.SearchParameters;
import com.squareup.picasso.Picasso;

public class MainActivity extends Activity {
	/**
	 *  This demo only supports best case search. 
	 *  Erros handling, Security, UX are not considered. 
	 */
	
	static String apiKey = "bd29e274e9626faabb31ab494ba6c84b";
	static int perPage = 10;
	static int page = 1;

	String search_string;
	PhotoList photoList;

	EditText et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et = (EditText) this.findViewById(R.id.et_search);

	}

	public void startSearch(View v) {

		search_string = et.getText().toString();
		
		/**
		 * check whether the string is null
		 */
		if (search_string.length() > 0) {
			new SearchTask().execute("");
		}

	}

	class SearchTask extends AsyncTask {

		@Override
		protected Object doInBackground(Object... arg0) {
			searchFlickr();
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {

			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();
			SearchResultFragment fragment = new SearchResultFragment();
			fragmentTransaction.replace(R.id.fragment_container, fragment);
			fragmentTransaction.commit();

		}

	}

	public void searchFlickr() {

	
		Flickr f = new Flickr(apiKey);
		SearchParameters params = new SearchParameters();
		params.setText(search_string);

		try {
			photoList = f.getPhotosInterface().search(params, perPage, page);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FlickrException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	class SearchResultFragment extends Fragment {

		@Override
		public void onCreate(Bundle savedInstanceState) {

			super.onCreate(savedInstanceState);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View v = inflater.inflate(R.layout.fragment_research_result, null);
			ListView result_list = (ListView) v.findViewById(R.id.result_list);
			ResultAdapter resultAdapter = new ResultAdapter();
			result_list.setAdapter(resultAdapter);

			return v;
		}

		@Override
		public void onPause() {

			super.onPause();
		}

	}

	class ResultAdapter extends BaseAdapter {
		/**
		 * this is the list adpater to search result
		 */
		@Override
		public int getCount() {

			return photoList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public long getItemId(int arg0) {

			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			/**
			 * each item of list is customerized here;
			 * Picasso lib is used to load photos from internet
			 * 
			 */
			Photo p = photoList.get(arg0);
			
			View v = MainActivity.this.getLayoutInflater().inflate(
					R.layout.item_result, arg2, false);
			
			ImageView iv = (ImageView) v.findViewById(R.id.iv_item);
			Picasso.with(MainActivity.this).load(p.getSmallSquareUrl()).into(iv);
			
			TextView tv = (TextView) v.findViewById(R.id.tv_item);
			tv.setText(p.getTitle());
			
			return v;
		}

	}
}
