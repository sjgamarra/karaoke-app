package com.karaoke;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karaoke.entity.Song;
import com.karaoke.utils.Commons;
import com.karaoke.utils.HttpRequest;
import com.karaoke.utils.HttpRequest.HttpRequestException;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class SongsFragment extends Fragment {

	private EditText etSearch;
	private ListView lvSongs;
	private Spinner spGenre;
	
	private List<Song> mSongList;
	private SongListAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.d(Commons.APP_TAG, "SongsFragment  - onCreateView");

		View rootView = inflater.inflate(R.layout.fragment_songs, container, false);

		this.etSearch = (EditText) rootView.findViewById(R.id.et_search);
		this.lvSongs = (ListView) rootView.findViewById(R.id.lv_songs);
		this.spGenre = (Spinner) rootView.findViewById(R.id.sp_genre);
		
        if(mSongList == null){
        	String songsUrl = String.format(Commons.URL_SONG_GET, "all", "all");
        	new GetSongsTask().execute(songsUrl);
        }
        	
		//Cargar lista de generos
//        String genreResponse = HttpRequest.get(Commons.URL_GENRE_GET).accept("application/json").body();
//        Gson gson = new Gson();
//		Type listType = new TypeToken<List<String>>() {}.getType();
//		List<String> genres = gson.fromJson(genreResponse, listType);        
        
        //ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,genres);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_item,this.getGenres());
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
						//TODO: perder foco para guardar teclado.
						
						//construir url
						String auxGenre = spGenre.getSelectedItem().toString()=="Todos"?"all":spGenre.getSelectedItem().toString();
						String auxName = etSearch.getText().toString().isEmpty()?"all":etSearch.getText().toString();
						String genre = URLEncoder.encode(auxGenre, "UTF-8");
						String name = URLEncoder.encode(auxName, "UTF-8");
						String songsUrl = String.format(Commons.URL_SONG_GET, genre, name);

						Log.d(Commons.APP_TAG, "SongsFragment  - onSearch - " + songsUrl);

						//realizar llamada.
						String response = HttpRequest.get(songsUrl).accept("application/json").body();
						if(response != null){
							Gson gson = new Gson();
							Type listType = new TypeToken<List<Song>>() {}.getType();
							mSongList = gson.fromJson(response, listType);
							
							if(!mSongList.isEmpty()){
								//actualizar lista
								adapter = new SongListAdapter(v.getContext(), mSongList, true,true);
								lvSongs.setAdapter(adapter);
							}else{
								//Mostrar alerta (podria ser un toast)
								AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
								builder
									.setMessage(Commons.MSG_ALERT_NOT_FOUND)
									.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
										@Override
										public void onClick(DialogInterface dialog, int id) {
										}})
									.show();
							}
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
	
	private class GetSongsTask extends AsyncTask<String, Long, String> {
		protected String doInBackground(String... urls) {
			try {
				Log.d(Commons.APP_TAG, "GetSongs - Request:"+urls[0]);
				return HttpRequest.get(urls[0]).accept("application/json").body();			
			} catch (HttpRequestException exception) {
				return null;
			}
		}

		protected void onPostExecute(String response) {
			Log.d(Commons.APP_TAG, "GetSongs - Response:"+response);
			
			if (response != null) {
				Gson gson = new Gson();
				Type listType = new TypeToken<List<Song>>() {}.getType();
				mSongList = gson.fromJson(response, listType);
				adapter = new SongListAdapter(getActivity().getApplicationContext(), mSongList, true, true);
				lvSongs.setAdapter(adapter);
			}
			
		}
	}
	
	private List<String> getGenres(){
		List<String> genres = new ArrayList<String>();
		try{
			String genreResponse = HttpRequest.get(Commons.URL_GENRE_GET).accept("application/json").body();
	        Gson gson = new Gson();
			Type listType = new TypeToken<List<String>>() {}.getType();
			genres = gson.fromJson(genreResponse, listType);
		}catch(Exception e){
			
		}
		genres.add(0, "Todos");
		return genres;
	}
}
