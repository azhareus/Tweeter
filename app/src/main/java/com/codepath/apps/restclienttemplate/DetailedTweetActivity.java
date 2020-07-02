package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DetailedTweetActivity extends AppCompatActivity {

    //Tweet to display
    Tweet tweet;
    //the view objects
    ImageView ivProfileImage;
    TextView tvName;
    TextView tvScreenName;
    TextView tvBody;
    ImageView ivTweetImage;
    TextView tvTime;
    //TextView tvSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_tweet);
        ivProfileImage = findViewById(R.id.ivProfileImage);
        tvName = findViewById(R.id.tvName);
        tvScreenName = findViewById(R.id.tvScreenName);
        tvBody = findViewById(R.id.tvBody);
        ivTweetImage = findViewById(R.id.ivTweetImage);
        tvTime = findViewById(R.id.tvTime);
        //TextView tvSource;

        // unwrap the movie passed in via intent, using its simple name as a key
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        //Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        tvBody.setText(tweet.body);
        tvName.setText(tweet.user.name);
        tvScreenName.setText("@" + tweet.user.screenName);
        tvTime.setText(tweet.createdAt.substring(0,19)+ "  · ");
        //Profile picture
        Glide.with(this)
                .load(tweet.user.profileImageUrl)
                .transform(new CircleCrop())
                .into(ivProfileImage);

        //If there's an image
        if(tweet.tweetImageUrls.size() > 0){
            //Set media
            Glide.with(this)
                    .load(tweet.tweetImageUrls.get(0))
                    .transform(new CenterCrop(), new RoundedCorners(30))
                    .into(ivTweetImage);

            //Recovers visibility on a recycled item after it had been toggled off
            ivTweetImage.setVisibility(View.VISIBLE);
        }
        else{
            //No image? Hide the view.
            ivTweetImage.setVisibility(View.GONE);
        }



    }
    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}