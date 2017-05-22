package com.mobile.emojis.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.emojis.R;
import com.mobile.emojis.listener.OnEmojiClickListener;
import com.mobile.emojis.model.Data;
import com.mobile.emojis.util.Utils;

import java.util.List;

public class SlidePagerAdapter extends PagerAdapter {

    private OnEmojiClickListener listener;
    private List<Data> dataSet;
    private Context context;

    public SlidePagerAdapter(final Context context, final List<Data> dataSet, final OnEmojiClickListener listener) {
        super();
        this.context = context;
        this.dataSet = dataSet;
        this.listener = listener;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.emoji_grid, container, false);
        RecyclerView grid = (RecyclerView)rootView.findViewById(R.id.grid);
        GridLayoutManager layoutManager = new GridLayoutManager(context, Utils.calculateNoOfColumns(context));
        grid.setLayoutManager(layoutManager);
        grid.setAdapter(new EmojiAdapter(context, dataSet.get(position).getIcons(), listener));
        container.addView(rootView);
        return rootView;
    }

    @Override
    public int getCount() {
        return dataSet == null ? 0: dataSet.size();
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view == object;
    }
}
