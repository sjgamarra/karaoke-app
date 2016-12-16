package com.karaoke;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
//import com.karaoke.SongListAdapter.CancelSongTask;
import com.karaoke.entity.Song;
import com.karaoke.utils.Commons;
import com.karaoke.utils.HttpRequest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class SongsFragment extends Fragment {

	private EditText etSearch;
	private ListView lvSongs;
	private Spinner spGenre;
	private SongListAdapter adapter;
	private List<Song> mSongList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(Commons.TAG, "SongsFragment  - onCreateView");

		View rootView = inflater.inflate(R.layout.fragment_songs, container, false);

		this.etSearch = (EditText) rootView.findViewById(R.id.et_search);
		this.lvSongs = (ListView) rootView.findViewById(R.id.lv_songs);
		this.spGenre = (Spinner) rootView.findViewById(R.id.sp_genre);
		
		//Cargar lista de generos
        List<String> list = new ArrayList<String>();
        list.add("TODOS");
        list.add("ROCK");
        list.add("SALSA");
        list.add("BALADA");
        list.add("HIPHOP");
        list.add("VARIADO");
         
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,list);  
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spGenre.setAdapter(dataAdapter);
		
		//Mostrar teclado virtual.
		//this.etSearch.requestFocus();
//		InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//		imm.showSoftInput(this.etSearch, InputMethodManager.SHOW_IMPLICIT);
	
		this.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent arg2) {
				try{
					if (actionId == EditorInfo.IME_ACTION_SEARCH) {
						Log.d(Commons.TAG, "SongsFragment  - onSearch");
						
						//construir url
						String genre = URLEncoder.encode(spGenre.getSelectedItem().toString(), "UTF-8");
						String name = URLEncoder.encode(etSearch.getText().toString(), "UTF-8");				
						String songsUrl = String.format(Commons.URL_SONG_GET, genre, name);

						Log.d(Commons.TAG, "SongsFragment  - onSearch - " + songsUrl);

						//realizar llamada.
						String response = HttpRequest.get(songsUrl).accept("application/json").body();
						Gson gson = new Gson();
						Type listType = new TypeToken<List<Song>>() {}.getType();
						mSongList = gson.fromJson(response, listType);
						
						if(mSongList.isEmpty()){
							//Mostrar alerta.
							AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
							builder
								.setMessage("No se encontraron resultados.")
								.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int id) {
										
									}})
								.show();
						}else{
							adapter = new SongListAdapter(v.getContext(), mSongList, true,true);
							lvSongs.setAdapter(adapter);
						}
			            return true;
			        }
					return false;
				}catch(Exception e){
					return false;
				}		        
			}
		});

		return rootView;
	}
}
