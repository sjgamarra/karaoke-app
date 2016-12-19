package com.karaoke;

import java.net.URLEncoder;
import java.util.List;

import com.karaoke.entity.Song;
import com.karaoke.utils.Commons;
import com.karaoke.utils.HttpRequest;
import com.karaoke.utils.HttpRequest.HttpRequestException;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

	public class SongListAdapter extends BaseAdapter{

	private Context mContext;
	private List<Song> mSongList;

	private String mDeviceName;
	private boolean mButtonShow;
	private boolean mButtonAdd;
	
	private int mSongSelectedPos;
	private Song mSongSelected;
	
	private boolean mResponse;

	// Constructor
	public SongListAdapter(Context mContext, List<Song> mSongList, boolean mButtonHidden, boolean mButtonAdd) {
		this.mContext = mContext;
		this.mSongList = mSongList;
		this.mButtonShow = mButtonHidden;
		this.mButtonAdd = mButtonAdd;
		
        //obtener nombre del dispositvo
        BluetoothAdapter myDevice = BluetoothAdapter.getDefaultAdapter();
        mDeviceName = myDevice.getName();
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
			holder.tvDevice = (TextView) convertView.findViewById(R.id.tv_device);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tvArtist = (TextView) convertView.findViewById(R.id.tv_artist);
			holder.tvGenre = (TextView) convertView.findViewById(R.id.tv_genre);
			holder.btAction = (Button) convertView.findViewById(R.id.bt_action);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvDevice.setText(mSongList.get(position).getDevice());
		holder.tvName.setText(mSongList.get(position).getTitle());
		holder.tvArtist.setText(mSongList.get(position).getArtist());
		holder.tvGenre.setText(mSongList.get(position).getGenre());
		
		holder.tvDevice.setVisibility(mSongList.get(position).getDevice().isEmpty()?View.GONE:View.VISIBLE);
		
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
					.setMessage(mButtonAdd?Commons.MSG_ALERT_ADD_SONG:Commons.MSG_ALERT_DEL_SONG)
					.setPositiveButton(Commons.MSG_ALERT_YES, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							try{
								if(mButtonAdd){
									new MakeRequestTask().execute (String.format(Commons.URL_REQUEST_POST,URLEncoder.encode(mDeviceName, "UTF-8"), mSongSelected.getId()));
								}else{
									new CancelRequestTask().execute(String.format(Commons.URL_REQUEST_PUT,URLEncoder.encode(mDeviceName, "UTF-8"), mSongSelected.getRequest()));
								}
							}catch(Exception e){
								
							}
						}})
					.setNegativeButton(Commons.MSG_ALERT_NO, null)
					.show();
			}
		});
		return convertView;
	}
	
	private class MakeRequestTask extends AsyncTask<String, Long, String> {
		protected String doInBackground(String... urls) {
			try {
				Log.d(Commons.APP_TAG, "Post - Request:"+urls[0]);
				return HttpRequest.post(urls[0]).accept("application/json").body();
			} catch (HttpRequestException exception) {
				return null;
			}
		}

		protected void onPostExecute(String response) {
			Log.d(Commons.APP_TAG, "Post - Response:"+response);
			
			mResponse = Boolean.valueOf(response);
			if(mResponse){
				//Toast.makeText(mContext, "La canci\u00F3n ha sido agregada.", Toast.LENGTH_SHORT ).show();
				Toast.makeText(mContext, Commons.MSG_TOAST_SONG_ADDED_SUCCESS, Toast.LENGTH_SHORT ).show();
				mSongList.remove(mSongSelectedPos);
				notifyDataSetChanged();
			}else{
				Toast.makeText(mContext, Commons.MSG_TOAST_SONG_ADDED_FAIL, Toast.LENGTH_SHORT ).show();
			}
		}
	}
	
	private class CancelRequestTask extends AsyncTask<String, Long, String> {
		protected String doInBackground(String... urls) {
			try {
				Log.d(Commons.APP_TAG, "Put - Request:"+urls[0]);
				return HttpRequest.put(urls[0]).accept("application/json").body();
			} catch (HttpRequestException exception) {
				return null;
			}
		}

		protected void onPostExecute(String response) {
			Log.d(Commons.APP_TAG, "Put - Response:"+response);
			mResponse = Boolean.valueOf(response);
			if(mResponse){
				Toast.makeText(mContext, Commons.MSG_TOAST_SONG_CANCELED_SUCCESS, Toast.LENGTH_SHORT ).show();
				mSongList.remove(mSongSelectedPos);
				notifyDataSetChanged();
			}else{
				Toast.makeText(mContext, Commons.MSG_TOAST_SONG_CANCELED_FAIL, Toast.LENGTH_SHORT ).show();
			}
		}
	}

	class ViewHolder {
		TextView tvDevice;
		TextView tvName;
		TextView tvArtist;
		TextView tvGenre;
		Button btAction;
		Button btRemove;
	}

}
