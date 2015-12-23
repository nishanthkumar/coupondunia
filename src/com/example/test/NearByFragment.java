package com.example.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.test.app.AppController;
import com.example.test.utils.DataItems;
import com.example.test.utils.RequestData;
import com.example.test.utils.RequestData.updateUIListener;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NearByFragment extends Fragment implements updateUIListener {

	private static final String ARG_SECTION_NUMBER = "section_number";
	
	ListView mList;
	ListAdapter adapter;

	private List<DataItems> mDataItems = new ArrayList<DataItems>();
	private ProgressDialog pDialog;
	TextView mTitle;
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_main, container, false);
		mList = (ListView)v.findViewById(R.id.section_label);
		mTitle = (TextView)v.findViewById(R.id.header_title);
		RequestData requestData = new RequestData(this);
		pDialog = new ProgressDialog(this.getActivity());
		// Showing progress dialog before making http request
		pDialog.setMessage("Loading...");
		pDialog.show();
		requestData.request();
		return v;
	}
	
	private void hidePDialog() {
		if (pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}
	
	private TextView getTextView() {
		return mTitle;
	}
	
	public static Fragment newInstance(int number) {
		Fragment fragment = new NearByFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, number);
		fragment.setArguments(args);
		return fragment;
	}
	
	private static class ListAdapter extends BaseAdapter {

		private LayoutInflater inflater;
		private List<DataItems> mDataItems;
		private NearByFragment fragment;
		ImageLoader imageLoader = AppController.getInstance()
				.getImageLoader();
		
		static class ViewHolder {
			NetworkImageView thumbNail;
			TextView title, subtitle, locDistance;
			LinearLayout layout;
		}

		public ListAdapter(NearByFragment fragment, List<DataItems> dataItems) {
			this.fragment = fragment;
			this.mDataItems = dataItems;
		}
		
		static final Comparator<DataItems> distanceComparator = new Comparator<DataItems>() {
			@Override
			public int compare(DataItems lhs, DataItems rhs) {
				if (lhs.getDistance() < rhs.getDistance()) {
					return -1;
				} else {
					return 1;
				}
			}
		};
		
		public void sortData() {
			Collections.sort(mDataItems, distanceComparator);
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mDataItems.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mDataItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView,
				ViewGroup parent) {
			 
			ViewHolder viewHolder;
			if (inflater == null)
				inflater = (LayoutInflater) fragment.getActivity()
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.listitem, null);
			    viewHolder = new ViewHolder();
			    viewHolder.thumbNail = (NetworkImageView) convertView
						.findViewById(R.id.thumbnail);
			    viewHolder.title = (TextView) convertView
						.findViewById(R.id.title);
			    viewHolder.subtitle =  (TextView) convertView
						.findViewById(R.id.subTitile);
			    viewHolder.locDistance = (TextView) convertView
						.findViewById(R.id.locDistance);
			    viewHolder.layout = (LinearLayout) convertView
						.findViewById(R.id.categories_id);
			    convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			

			if (imageLoader == null)
				imageLoader = AppController.getInstance()
						.getImageLoader();

			
			
			
			// getting movie data for the row
			DataItems m = mDataItems.get(position);

			fragment.getTextView().setText(m.getNeighbouthoodName());
			viewHolder.thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

			
			viewHolder.title.setText(m.getTitle());

			
			viewHolder.subtitle.setText(String.valueOf(m.getCoupons())+" Offers");
			
			viewHolder.locDistance.setSingleLine();
			viewHolder.locDistance.setText(String.valueOf((int)m.getDistance())+"m   "+m.getNeighbouthoodName());
			
			TextView text = new TextView(fragment.getActivity());
			text.setEllipsize(TruncateAt.END);
			text.setSingleLine();
			text.setTextSize(16);
			StringBuilder result = new StringBuilder();
			for (int i=0 ; i <m.getCategoryName().length; i++ ){
				String[] categories = m.getCategoryName();
				result.append(Html.fromHtml("&#8226")+" " + categories[i] + "   ");
			}
			text.setText(result.toString());
			viewHolder.layout.addView(text);
			return convertView;
		}

	}

	@Override
	public void updateUI(List<DataItems> dataItems) {
		hidePDialog();
		mDataItems = dataItems;
		adapter = new ListAdapter(this, mDataItems);
		mList.setAdapter(adapter);
		adapter.sortData();	
			
	}
}
