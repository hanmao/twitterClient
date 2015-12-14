package com.codepath.apps.myTweets;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.myTweets.models.TweetModel;
import com.codepath.apps.restclienttemplate.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by hamao on 12/13/15.
 */
public class TwitterListAdapter extends ArrayAdapter {

    private Context context;

    public TwitterListAdapter(Context appContext, List<TweetModel> objects) {

        super(appContext, android.R.layout.simple_list_item_activated_1, objects);
        this.context = appContext;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final TweetModel model = (TweetModel) getItem(position);

        if (null == convertView)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.twitter_time_line, parent, false);
        }

        ImageView Profile_pic = (ImageView) convertView.findViewById(R.id.ivProfile_pic);
        TextView Usrname = (TextView) convertView.findViewById(R.id.tvUsrname);
        TextView Screenname = (TextView) convertView.findViewById(R.id.tvScreenname);
        TextView Text = (TextView) convertView.findViewById(R.id.tvText);
        TextView Created_at = (TextView) convertView.findViewById(R.id.tvTime);
        TextView Retweet_cnt = (TextView) convertView.findViewById(R.id.tvRetweetCounter);
        TextView Fav_cnt = (TextView) convertView.findViewById(R.id.tvStarCounter);
        //    ImageView Media_pic = (ImageView) convertView.findViewById(R.id.ivMedia_pic);

        Profile_pic.setImageResource(0);
        Picasso.with(getContext())
                .load(model.getImg_url())
                .fit()
                .into(Profile_pic);

        Usrname.setText(model.getUser());
        Screenname.setText("@" + model.getScreen_name());
        Text.setText(Html.fromHtml(model.getText()));
        Created_at.setText(getRelativeTimeAgo(model.getCreated_at()));
        Retweet_cnt.setText(Integer.toString(model.getRetweet_count()));
        Fav_cnt.setText(Integer.toString(model.getFav_count()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Ganesh", "onItemClick");

                Intent sharingIntent = new Intent(context, com.codepath.apps.myTweets.TwitterDetailActivity.class);
                sharingIntent.putExtra("Item", model);
                context.startActivity(sharingIntent);
            }
        });

        return convertView;
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
