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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;

public class AllSongsFragment extends Fragment {

	private ListView lvSongs;
    private SongListAdapter adapter;
    private List<Song> mSongList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		Log.d(Commons.TAG, "AllSongsFragment - onCreateView");

		View rootView = inflater.inflate(R.layout.fragment_all_songs, container, false);	
		this.lvSongs = (ListView)rootView.findViewById(R.id.lv_songs);
		this.mSongList = new ArrayList<Song>();
		
		//verificar si el teclado virtual esta deployado
		View view = getActivity().getCurrentFocus();		
		if (view != null) {  
			view.clearFocus();
		    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
		
		String allSongsUrl = String.format(Commons.URL_REQUEST_GET,"all");
		
		try{
			//sincrono
			String response = HttpRequest.get(allSongsUrl).accept("application/json").body();
			Gson gson = new Gson();
			Type listType = new TypeToken<List<Song>>(){}.getType();
			mSongList = gson.fromJson(response, listType);
			adapter = new SongListAdapter(getActivity().getApplicationContext(), mSongList, false,false);
			lvSongs.setAdapter(adapter);
			//asincrono
			//new GetAllSongsTask().execute(allSongsUrl);
		}catch(Exception e){
			//log
		}

		return rootView;
	}
	
//	private class GetAllSongsTask extends AsyncTask<String, Long, String> {
//		protected String doInBackground(String... urls) {
//			try {
//				Log.d(Commons.TAG, "GetAllSongs - Request:"+urls[0]);
//				return HttpRequest.get(urls[0]).accept("application/json").body();			
//			} catch (HttpRequestException exception) {
//				return null;
//			}
//		}
//
//		protected void onPostExecute(String response) {
//			Log.d(Commons.TAG, "GetAllSongs - Response:"+response);
//
//			Gson gson = new Gson();
//			Type listType = new TypeToken<List<Song>>(){}.getType();
//			mSongList = gson.fromJson(response, listType);
//			
//			adapter = new SongListAdapter(getActivity().getApplicationContext(), mSongList, false, false);
//			lvSongs.setAdapter(adapter);
//			
//		}
//	}
}
