package com.mobile.emojis.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mobile.emojis.R;
import com.mobile.emojis.listener.OnEmojiClickListener;
import com.mobile.emojis.model.Emoji;

import java.io.File;
import java.util.List;

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.ViewHolder>{

    private List<Emoji> mDataset;
    private Context context;
    private OnEmojiClickListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ViewHolder(final View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.icon);
        }
    }

    public EmojiAdapter(final Context context, final List<Emoji> myDataset, final OnEmojiClickListener listener) {
        this.context = context;
        mDataset = myDataset;
        this.listener = listener;
    }

    @Override
    public EmojiAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.emoji_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        File file = new File(mDataset.get(position).getLocalCahcedPath());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEmojiClicked(mDataset.get(position));
            }
        });
        Glide.with(context).load(file).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0: mDataset.size();
    }
}
