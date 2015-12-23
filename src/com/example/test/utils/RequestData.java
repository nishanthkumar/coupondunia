package com.example.test.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.test.app.AppController;

public class RequestData {
	public static String url = "http://staging.couponapitest.com/task.txt";
	
	private List<DataItems> mDataItems = new ArrayList<DataItems>();
	
	Fragment fragment;
	
	public RequestData(Fragment fragment) {
		this.fragment = fragment;
	}
	
	public interface updateUIListener {
		
		public void updateUI(List<DataItems> dataItems);
	}
	
	public void request() {
		// Creating volley request obj
				JsonObjectRequest dataReq = new JsonObjectRequest(
						Request.Method.GET, url, null,
						new Response.Listener<JSONObject>() {
							@Override
							public void onResponse(JSONObject response) {
								// Parsing json
								try {
									JSONArray jArray = response
											.getJSONArray("data");
									for (int i = 0; i < jArray.length(); i++) {

										JSONObject jObject = jArray
												.getJSONObject(i);

										String name = jObject
												.getString("OutletName");
										String neighbour = jObject
												.getString("NeighbourhoodName");
										int coupons = jObject
												.getInt("NumCoupons");
										DataItems data = new DataItems();
										data.setTitle(jObject
												.getString("OutletName"));
										data.setSubtitle(jObject
												.getString("NeighbourhoodName"));
										data.setThumbnailUrl(jObject
												.getString("LogoURL"));
										data.setCoupons((int) ((Number) jObject
												.get("NumCoupons")).doubleValue());
										data.setDistance((double) jObject
												.get("Distance"));
										data.setNeighbouthoodName(jObject.getString("NeighbourhoodName"));
										JSONArray jsonArray = jObject.getJSONArray("Categories");
										String[] categories = new String[jsonArray.length()];
										for (int j = 0; j < jsonArray.length(); j++) {
											JSONObject jObj = jsonArray.getJSONObject(j);
											
											categories[j] =  jObj.getString("Name");
										}
										data.setCategoryName(categories);

										mDataItems.add(data);
										System.out.println("@Nishanth name = "
												+ name + " neighbourhood = "
												+ neighbour + " coupons = "
												+ coupons);

									} // End Loop

								} catch (JSONException e) {
									Log.e("JSONException",
											"Error: " + e.toString());
								} // catch (JSONException e)
									// notifying list adapter about data changes
									// so that it renders the list view with updated
									// data.
								((updateUIListener)fragment).updateUI(mDataItems);
							}
						}, new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								VolleyLog.d("@Nishanth",
										"Error: " + error.getMessage());
								((updateUIListener)fragment).updateUI(mDataItems);

							}
						});

				// Adding request to request queue
				AppController.getInstance().addToRequestQueue(dataReq);
	}

}
