package com.karaoke;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.karaoke.entity.Song;
import com.karaoke.utils.Commons;
import com.karaoke.utils.HttpRequest;
import com.karaoke.utils.HttpRequest.HttpRequestException;

	public class SongListAdapter extends BaseAdapter{

	private Context mContext;
	private List<Song> mSongList;

	private boolean mButtonShow;
	private boolean mButtonAdd;
	
	private int mSongSelectedPos;
	private Song mSongSelected;

	// Constructor
	public SongListAdapter(Context mContext, List<Song> mSongList, boolean mButtonHidden, boolean mButtonAdd) {
		this.mContext = mContext;
		this.mSongList = mSongList;
		this.mButtonShow = mButtonHidden;
		this.mButtonAdd = mButtonAdd;
	}

	@Override
	public int getCount() {
		return mSongList.size();
	}

	@Override
	public Song getItem(int position) {
		return mSongList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mSongList.get(position).getId();
	}
	
	public void setSongList(List<Song> songs){
		this.mSongList = songs;
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.item_song_list, null);
			holder.tvArtist = (TextView) convertView.findViewById(R.id.tv_artist);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tvGenre = (TextView) convertView.findViewById(R.id.tv_genre);
			holder.btAction = (Button) convertView.findViewById(R.id.bt_action);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvArtist.setText(mSongList.get(position).getArtist());
		holder.tvName.setText(mSongList.get(position).getTitle());
		holder.tvGenre.setText(mSongList.get(position).getGenre());
		
		//controlando el boton.
		holder.btAction.setVisibility(mButtonShow ? View.VISIBLE : View.GONE);
		holder.btAction.setText(mButtonAdd ? "+" : "-");

		holder.btAction.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mSongSelectedPos = position;
				mSongSelected = mSongList.get(mSongSelectedPos);			
				
				AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
				builder
					.setMessage(mButtonAdd?"¿Añadir canción?":"¿Eliminar canción?")
					.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							if(mButtonAdd){
								new MakeRequestTask().execute(String.format(Commons.URL_REQUEST_POST,Commons.DEVICE_ID, mSongSelected.getId()));								
							}else{
								new CancelRequestTask().execute(String.format(Commons.URL_REQUEST_PUT,Commons.DEVICE_ID, mSongSelected.getId()));
							}							
							mSongList.remove(mSongSelectedPos);
							
							notifyDataSetChanged();
						}})
					.setNegativeButton("No", null)
					.show();
			}
		});

		return convertView;
	}
	
	private class MakeRequestTask extends AsyncTask<String, Long, String> {
		protected String doInBackground(String... urls) {
			try {
				Log.d(Commons.TAG, "Post - Request:"+urls[0]);
				return HttpRequest.post(urls[0]).accept("application/json").body();
			} catch (HttpRequestException exception) {
				return null;
			}
		}

		protected void onPostExecute(String response) {
			Log.d(Commons.TAG, "Post - Response:"+response);
		}
	}
	
	private class CancelRequestTask extends AsyncTask<String, Long, String> {
		protected String doInBackground(String... urls) {
			try {
				Log.d(Commons.TAG, "Put - Request:"+urls[0]);
				return HttpRequest.put(urls[0]).accept("application/json").body();
			} catch (HttpRequestException exception) {
				return null;
			}
		}

		protected void onPostExecute(String response) {
			Log.d(Commons.TAG, "Put - Response:"+response);
		}
	}

	class ViewHolder {
		TextView tvName;
		TextView tvArtist;
		TextView tvGenre;
		Button btAction;
		Button btRemove;
	}

}
