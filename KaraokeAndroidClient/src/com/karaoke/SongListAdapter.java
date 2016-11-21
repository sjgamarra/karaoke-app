package com.karaoke;

import android.content.Context;
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

import com.karaoke.entity.Song;

//public class SongListAdapter extends ArrayAdapter<Song>{
	public class SongListAdapter extends BaseAdapter{

	private Context mContext;
	private List<Song> mSongList;
	private List<Song> mRequestList;
	private int layoutResourceId;


//	public SongListAdapter(Context context, int layoutResourceId, ArrayList<Song> data) {
//		super(context, layoutResourceId, data);
//		this.layoutResourceId = layoutResourceId;
//		this.mContext = context;
//		this.mSongList = data;
//	}

	// Constructor
	public SongListAdapter(Context mContext, List<Song> mSongList) {
		this.mContext = mContext;
		this.mSongList = mSongList;
		this.mRequestList = new ArrayList<Song>();
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

		//final int pos = position;
		holder.btAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {	
				Log.d("KARAOKE", "Boton Actual------------");		
				Log.d("KARAOKE", "Posicion:"+position+" Song:"+mSongList.get(position).getName());
				
				
				mSongList.remove(position);
				notifyDataSetChanged();
			}
		});

		return convertView;
	}

	class ViewHolder {
		TextView tvName;
		TextView tvArtist;
		Button btAdd;
		Button btRemove;
	}

}
