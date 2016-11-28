package com.karaoke;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karaoke.entity.Song;
import com.karaoke.utils.Commons;
import com.karaoke.utils.HttpRequest;
import com.karaoke.utils.HttpRequest.HttpRequestException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SongsFragment extends Fragment {

	
	private EditText etSearch;
	private Button btSearch;
	private ListView lvSongs;
    
	public List<Song> mSongList;
	public SongListAdapter mSongListAdapter;
    
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_songs, container, false);
		
		this.etSearch = (EditText)rootView.findViewById(R.id.et_search);
		this.btSearch = (Button)rootView.findViewById(R.id.bt_search);
		this.lvSongs = (ListView)rootView.findViewById(R.id.lv_songs);

		//demo de canciones  ------------------------------------------------------------------
		this.mSongList = new ArrayList<Song>();
//		this.mSongList.add(new Song((long)1, "Rata Blanca", "Anarquia", "Metal", 1));
//		this.mSongList.add(new Song((long)2, "The Weeknd", "Can't Feel My Face", "Pop", 1));
//		this.mSongList.add(new Song((long)3, "Taylor Swift", "Bad Blood", "Metal", 1));
//		this.mSongList.add(new Song((long)4, "David Guetta", "Hey Mama", "Metal", 1));
//		this.mSongList.add(new Song((long)5, "Adele", "Hello", "Metal", 1));
//		this.mSongList.add(new Song((long)6, "Fall Out Boy", "Centuries", "Metal", 1));
//		this.mSongList.add(new Song((long)7, "Maroon 5", "Animals", "Metal", 1));
//		this.mSongList.add(new Song((long)8, "Usher", "I Don't Mind", "Metal", 1));
		//-------------------------------------------------------------------------------------
		

		
		
		mSongListAdapter = new SongListAdapter(getActivity().getApplicationContext(), mSongList);
		this.lvSongs.setAdapter(mSongListAdapter);
        this.lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Do something
            }
        });
        
		this.btSearch.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
            	String songname = etSearch.getText().toString().isEmpty()? "all" :etSearch.getText().toString(); 
            	String url = String.format(Commons.URL_SONG_GET, "all", songname);
				new SearchSongTask().execute(url);
            }
        });

		return rootView;
	}
	
	private class SearchSongTask extends AsyncTask<String, Long, String> {
		protected String doInBackground(String... urls) {
			try {
				Log.d(Commons.ADAPTER_TAG, "URL_SONG_GET:"+urls[0]);
				return HttpRequest.get(urls[0]).accept("application/json").body();		
			} catch (HttpRequestException exception) {
				return null;
			}
		}

		protected void onPostExecute(String response) {
			Log.d(Commons.ADAPTER_TAG, "Post - Response:"+response);
			Gson gson = new Gson();
			Type listType = new TypeToken<List<Song>>(){}.getType();
			mSongList = gson.fromJson(response, listType);		
			mSongListAdapter.notifyDataSetChanged();
		}
	}
}
