package com.karaoke;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karaoke.entity.Song;
import com.karaoke.utils.Commons;
import com.karaoke.utils.HttpRequest;
import com.karaoke.utils.HttpRequest.HttpRequestException;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

public class MySongsFragment extends Fragment {

	private ListView lvSongs;
    private SongListAdapter adapter;
    private List<Song> mMySongList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		Log.d(Commons.TAG, "MySongsFragment - onCreateView");
		 		
		View rootView = inflater.inflate(R.layout.fragment_my_songs, container, false);
		this.lvSongs = (ListView)rootView.findViewById(R.id.lv_songs);
		this.mMySongList = new ArrayList<Song>();
		
		//verificar si el teclado virtual esta deployado
		View view = getActivity().getCurrentFocus();
		if (view != null) {
			view.clearFocus();
		    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		
		String mySongsUrl = String.format(Commons.URL_REQUEST_GET,Commons.DEVICE_ID);
		
		try{
			
			
			//sincrono
			String response = HttpRequest.get(mySongsUrl).accept("application/json").body();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<Song>>(){}.getType();
			mMySongList = gson.fromJson(response, listType);
			adapter = new SongListAdapter(getActivity().getApplicationContext(), mMySongList, true,false);
			lvSongs.setAdapter(adapter);
			
			//asincrono
			//new GetMySongsTask().execute(mySongsUrl);
		}catch (Exception e){
			//log
		}
		
		return rootView;
	}
	
//	private class GetMySongsTask extends AsyncTask<String, Long, String> {
//		protected String doInBackground(String... urls) {
//			try {
//				Log.d(Commons.TAG, "GetMySongs - Request:"+urls[0]);
//				return HttpRequest.get(urls[0]).accept("application/json").body();			
//			} catch (HttpRequestException exception) {
//				return null;
//			}
//		}
//
//		protected void onPostExecute(String response) {
//			Log.d(Commons.TAG, "GetMySongs - Response:"+response);
//
//			Gson gson = new Gson();
//			Type listType = new TypeToken<List<Song>>(){}.getType();
//			mMySongList = gson.fromJson(response, listType);
//			
//			adapter = new SongListAdapter(getActivity().getApplicationContext(), mMySongList, true, "-");
//			lvSongs.setAdapter(adapter);
//			
//		}
//	}
}
