package com.flickr.search;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity {
	EditText et;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		et = (EditText) this.findViewById(R.id.et_search);

	}

	public void startSearch(View v) {
		
		String search_string = et.getText().toString();

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		SearchResultFragment fragment = new SearchResultFragment();
		fragmentTransaction.add(R.id.fragment_container, fragment);
		fragmentTransaction.commit();
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

			return 5;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {

			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {

			View v = MainActivity.this.getLayoutInflater().inflate(
					R.layout.item_result, arg2, false);

			return v;
		}

	}
}
