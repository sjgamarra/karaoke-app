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

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.Toast;

public class AllSongsFragment extends Fragment {

	private ListView lvSongs;
	private List<Song> mSongList;
    private SongListAdapter adapter;
    private Handler handler = null;
    private String allSongsUrl;
    private String mDeviceName;
    private BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(Commons.APP_TAG, "AllSongsFragment - onCreateView");

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
		
		try{
			mDeviceName = myDevice.getName();
			allSongsUrl = String.format(Commons.URL_REQUEST_GET,"all");

			//asincrono
			new GetAllSongsTask().execute(allSongsUrl);
			
			if(handler == null){
				scheduleGetSongs();
			}
		}catch(Exception e){
			Log.d(Commons.APP_TAG, "GetAllSongs - Exception: "+e.getMessage());
		}

		return rootView;
	}
	
	public void scheduleGetSongs() {
		handler = new Handler();
	    handler.postDelayed(new Runnable() {
	        public void run() {
	            new GetAllSongsTask().execute(allSongsUrl);
	            handler.postDelayed(this, Commons.APP_RECALL);
	        }
	    }, Commons.APP_RECALL);
	}
	
	private class GetAllSongsTask extends AsyncTask<String, Long, String> {
		protected String doInBackground(String... urls) {
			try {
				Log.d(Commons.APP_TAG, "GetAllSongs - Request:"+urls[0]);
				return HttpRequest.get(urls[0]).accept("application/json").body();			
			} catch (HttpRequestException exception) {
				return null;
			}
		}

		protected void onPostExecute(String response) {
			//Log.d(Commons.APP_TAG, "GetAllSongs - Response:"+response);
			if(response != null){
				Gson gson = new Gson();
				Type listType = new TypeToken<List<Song>>(){}.getType();
				mSongList = gson.fromJson(response, listType);
				
				adapter = new SongListAdapter(getActivity().getApplicationContext(), mSongList, false, false);
				lvSongs.setAdapter(adapter);
				
				if(mSongList.get(0).getDevice().equals(mDeviceName)){
					Toast.makeText(getActivity().getApplicationContext(), Commons.MSG_TOAST_REQUEST_PLAYING, Toast.LENGTH_SHORT ).show();
				}
			}			
		}
	}
}
