package com.example.kaseki;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Search {
    private String api_key;
    private String search;
    private String Description_part1;
    private String Description_part2;
    private ArrayList<String> video_ids;
    private ArrayList<String> small_thumbnail;
    private ArrayList<String> large_thumbnail;
    private ArrayList<String> title;
    private ArrayList<String> channel_Title;
    private int searchSize;

    Search(){
        api_key = "AIzaSyDBT_mj6XgfCQYXuipuhsgSAELknxWvRyg";
        Description_part1 ="https://www.googleapis.com/youtube/v3/videos?id=";
        Description_part2 = "&key="+api_key+"&part=snippet";
        search = "https://www.googleapis.com/youtube/v3/search?key="+api_key+"&q=";
    }

    public void call(Context context, String request, SearchDisplayController sdc){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = search + request;
        video_ids = new ArrayList<>();
        small_thumbnail = new ArrayList<>();
        large_thumbnail = new ArrayList<>();
        title = new ArrayList<>();
        channel_Title = new ArrayList<>();
        Search srch=this;
        // Request a string response from the provided URL.
        StringRequest searchRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject res;
                        try {
                            res = new JSONObject(response);
                            JSONArray result = res.getJSONArray("items");
                            searchSize=result.length();
                            if (searchSize == 0) {
                                sdc.populateSearchResult(srch);
                            }
                            for(int i=0;i<result.length();i++){
                                JSONObject video = result.getJSONObject(i).getJSONObject("id");
                                String video_id = video.get("videoId").toString();
                                video_ids.add(video_id);
                                Log.d("videoids",video_ids.toString());
                                add_des(context,video_id, sdc);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response","That didn't work!");
            }
        });
        queue.add(searchRequest);
    }
    public void add_des(Context context, String video_id, SearchDisplayController sdc){
        String url = Description_part1+video_id+Description_part2;
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d("queue",queue.toString());
        Search srch=this;
        StringRequest searchRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject res;
                        try {
                            res = new JSONObject(response);
                            JSONObject result = res.getJSONArray("items").getJSONObject(0);
                            JSONObject snippet = result.getJSONObject("snippet");
                            title.add(snippet.get("title").toString());
                            channel_Title.add(snippet.get("channelTitle").toString());
                            JSONObject thumbnail = snippet.getJSONObject("thumbnails");
                            JSONObject d_efault = thumbnail.getJSONObject("default");
                            JSONObject standard = thumbnail.getJSONObject("standard");
                            small_thumbnail.add(d_efault.get("url").toString());
                            large_thumbnail.add(standard.get("url").toString());
                            if (title.size()==searchSize) {
                                sdc.populateSearchResult(srch);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response","That didn't work!");
            }
        });
        queue.add(searchRequest);
    }

    @Override
    public String toString() {
        return "Search{" +
                "api_key='" + api_key + '\'' +
                ", search='" + search + '\'' +
                ", Description_part1='" + Description_part1 + '\'' +
                ", Description_part2='" + Description_part2 + '\'' +
                ", video_ids=" + video_ids +
                ", small_thumbnail=" + small_thumbnail +
                ", large_thumbnail=" + large_thumbnail +
                ", title=" + title +
                ", channel_Title=" + channel_Title +
                '}';
    }
}
