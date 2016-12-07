package com.karaoke;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karaoke.entity.Song;
import com.karaoke.utils.Commons;
import com.karaoke.utils.HttpRequest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SongsFragment extends Fragment {

	private EditText etSearch;
	//private Button btSearch;
	private ListView lvSongs;
	private SongListAdapter adapter;
	private List<Song> mSongList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(Commons.TAG, "SongsFragment  - onCreateView");

		View rootView = inflater.inflate(R.layout.fragment_songs, container, false);

		this.etSearch = (EditText) rootView.findViewById(R.id.et_search);
		//this.btSearch = (Button) rootView.findViewById(R.id.bt_search);
		this.lvSongs = (ListView) rootView.findViewById(R.id.lv_songs);
	
		this.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent arg2) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					Log.d(Commons.TAG, "SongsFragment  - onSearch");				
					String songsUrl = String.format(Commons.URL_SONG_GET, "all", etSearch.getText().toString());

					String response = HttpRequest.get(songsUrl).accept("application/json").body();
					Gson gson = new Gson();
					Type listType = new TypeToken<List<Song>>() {}.getType();
					mSongList = gson.fromJson(response, listType);
					adapter = new SongListAdapter(v.getContext(), mSongList, false, "+");
					lvSongs.setAdapter(adapter);
		            return true;
		        }
		        return false;
			}
		});

		return rootView;
	}
}
