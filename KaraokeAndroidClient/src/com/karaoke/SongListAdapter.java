package com.karaoke;

import android.content.Context;
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

	// Constructor
	public SongListAdapter(Context mContext, List<Song> mSongList) {
		this.mContext = mContext;
		this.mSongList = mSongList;
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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(mContext, R.layout.item_song_list, null);
			holder.tvArtist = (TextView) convertView.findViewById(R.id.tv_artist);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			holder.btAdd = (Button) convertView.findViewById(R.id.bt_add);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.tvArtist.setText(mSongList.get(position).getArtist());
		holder.tvName.setText(mSongList.get(position).getName());	

		holder.btAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d(Commons.ADAPTER_TAG, "Posicion:"+position+" Song:"+mSongList.get(position).getName());
				
				Song song = mSongList.get(position);			
				mSongList.remove(position);
				
				String url = String.format(Commons.URL_REQUEST_POST, Commons.DEVICE_ID, song.getId().toString());
				new AddSongTask().execute(url);
				
				notifyDataSetChanged();
			}
		});

		return convertView;
	}
	
	private class AddSongTask extends AsyncTask<String, Long, String> {
		protected String doInBackground(String... urls) {
			try {
				Log.d(Commons.ADAPTER_TAG, "Post - Request:"+urls[0]);
				return HttpRequest.post(urls[0]).accept("application/json").body();			
			} catch (HttpRequestException exception) {
				return null;
			}
		}

		protected void onPostExecute(String response) {
			Log.d(Commons.ADAPTER_TAG, "Post - Response:"+response);
		}
	}


	class ViewHolder {
		TextView tvName;
		TextView tvArtist;
		Button btAdd;
		Button btRemove;
	}

}
