package com.voicemod.codechallenge.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.voicemod.codechallenge.R;
import com.voicemod.codechallenge.model.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public void updateData(List<Video> videoList) {
        this.mVideoList.clear();
        this.mVideoList.addAll(videoList);
        notifyDataSetChanged();
    }

    public interface IVideoClickListener{
        void onClick(Video video);
    }
    public List<Video> mVideoList;
    private Context mContext;
    private IVideoClickListener mListener;

    public VideoAdapter(Context context, IVideoClickListener listener){
        this.mContext = context;
        this.mListener = listener;
        this.mVideoList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
            return new VideoViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VideoViewHolder videoHolder = (VideoViewHolder) holder;
        if (mVideoList.get(position) != null) {
            Video video = mVideoList.get(position);
            Glide.with(mContext).load("Video://" + video.getFile().getAbsolutePath())
                    .skipMemoryCache(true)
                    .into(videoHolder.ivThumbnail);

            videoHolder.tvName.setText(video.getFile().getName());

            videoHolder.cardView.setOnClickListener(v -> mListener.onClick(mVideoList.get(position)));

        }
    }


    @Override
    public int getItemCount() {
        return mVideoList == null ? 0 : mVideoList.size();
    }





    private static class VideoViewHolder extends RecyclerView.ViewHolder  {
        CardView cardView;
        ImageView ivThumbnail;
        TextView tvName;

        public VideoViewHolder(final View itemView){
            super(itemView);
            this.cardView = (CardView) itemView.findViewById(R.id.cardView);
            this.ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            this.tvName = (TextView) itemView.findViewById(R.id.tvName);
        }


    }

}
