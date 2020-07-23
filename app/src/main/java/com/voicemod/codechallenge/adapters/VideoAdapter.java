package com.voicemod.codechallenge.adapters;

import android.content.Context;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.voicemod.codechallenge.R;
import com.voicemod.codechallenge.model.Video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public void updateData(List<File> videoList) {
        this.mVideoList.clear();
        this.mVideoList.addAll(videoList);
        notifyDataSetChanged();
    }

    public interface IVideoClickListener{
        void onClick(File video);
    }
    public List<File> mVideoList;
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
            Glide.with(mContext).load("file://" + mVideoList.get(position).getAbsolutePath())
                    .skipMemoryCache(true)
                    .into(videoHolder.ivThumbnail);
        }
    }


    @Override
    public int getItemCount() {
        return mVideoList == null ? 0 : mVideoList.size();
    }



    public void clearItem(Video video) {
       //TODO: Borrar de la galería y de la lista
    }



    private static class VideoViewHolder extends RecyclerView.ViewHolder {

        ImageView ivThumbnail;

        public VideoViewHolder(final View itemView){
            super(itemView);
            this.ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);

        }
    }

}
