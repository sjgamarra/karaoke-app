package com.karaoke;

import java.util.ArrayList;
import java.util.List;

import com.karaoke.entity.Song;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
	private ListView lvSongs;
	private Button btAdd;
    private SongListAdapter adapter;
    private List<Song> mSongList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View rootView = inflater.inflate(R.layout.fragment_songs, container, false);
		
		this.etSearch = (EditText)rootView.findViewById(R.id.et_search);
		this.lvSongs = (ListView)rootView.findViewById(R.id.lv_songs);
		this.btAdd = (Button)rootView.findViewById(R.id.bt_add);

		//demo de canciones  ------------------------------------------------------------------
		this.mSongList = new ArrayList<Song>();
		this.mSongList.add(new Song((long)1, "Rata Blanca", "Anarquia", "Metal", 1));
		this.mSongList.add(new Song((long)2, "The Weeknd", "Can't Feel My Face", "Pop", 1));
		this.mSongList.add(new Song((long)3, "Taylor Swift", "Bad Blood", "Metal", 1));
		this.mSongList.add(new Song((long)4, "David Guetta", "Hey Mama", "Metal", 1));
		this.mSongList.add(new Song((long)5, "Adele", "Hello", "Metal", 1));
		this.mSongList.add(new Song((long)6, "Fall Out Boy", "Centuries", "Metal", 1));
		this.mSongList.add(new Song((long)7, "Maroon 5", "Animals", "Metal", 1));
		this.mSongList.add(new Song((long)8, "Usher", "I Don't Mind", "Metal", 1));
		//-------------------------------------------------------------------------------------
		
		adapter = new SongListAdapter(getActivity().getApplicationContext(), mSongList);
		this.lvSongs.setAdapter(adapter);
        this.lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Do something
            }
        });
	
		return rootView;

	}
}
