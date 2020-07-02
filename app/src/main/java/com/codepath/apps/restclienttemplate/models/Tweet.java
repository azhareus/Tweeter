package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;
import org.parceler.Transient;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    public long id;
    @Transient
    JSONObject entities;
    //public String source;

    public List <String> tweetImageUrls = new ArrayList<>();
    //empty constructor
    public Tweet(){}
    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.id = jsonObject.getLong("id");
        //tweet.source = jsonObject.getString("")

        tweet.entities = jsonObject.getJSONObject("entities");
        if(tweet.entities.has("media")){
            JSONArray mediaEntities = tweet.entities.getJSONArray("media");
            for (int i = 0; i < mediaEntities.length(); i++){
                tweet.tweetImageUrls.add(mediaEntities.getJSONObject(i).getString("media_url_https"));
            }

            Log.i("TweetMedia", "Media! Found: " + tweet.tweetImageUrls.toString());
        }
        else
            Log.i("TweetMedia", "No media on tweet");

        return tweet;
    }

    public static List <Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for (int i=0;i<jsonArray.length();i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}
