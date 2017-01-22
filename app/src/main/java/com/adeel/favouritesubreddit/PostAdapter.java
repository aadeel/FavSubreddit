package com.adeel.favouritesubreddit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Adeel on 17-Jan-17.
 */

public class PostAdapter extends BaseAdapter {

    private Context mContext;
    private List<PostModel> mList;
    private SimpleDateFormat mDateFormat;

    public PostAdapter(Context context, List<PostModel> list) {
        mContext = context;
        mList = list;
        mDateFormat = new SimpleDateFormat("HH:mm' 'MMM' 'dd", Locale.US);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        PostViewHolder holder;
        PostModel post = mList.get(position);
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            holder = new PostViewHolder(view);
            view.setTag(holder);
        } else
            holder = (PostViewHolder) view.getTag();

        PostModel pm = mList.get(position);
        holder.titleTextView.setText(pm.getTitle());
        holder.votesTextView.setText(pm.getScore() + "");
        holder.detailTextView.setText("By: " + pm.getAuthor() + " at " + mDateFormat.format(new Date(pm.getCreated_utc())));
        Picasso.with(mContext).load(pm.getThumbnail()).fit().into(holder.thumbnail);
        return view;
    }

    private static class PostViewHolder {
        final TextView titleTextView, detailTextView, votesTextView;
        final ImageView thumbnail;

        PostViewHolder(View view) {
            titleTextView = (TextView) view.findViewById(R.id.list_item_title);
            detailTextView = (TextView) view.findViewById(R.id.list_item_description);
            votesTextView = (TextView) view.findViewById(R.id.list_item_score);
            thumbnail = (ImageView) view.findViewById(R.id.list_item_thumbnail);
        }
    }
}
