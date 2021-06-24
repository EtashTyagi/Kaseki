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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;

public class Search {
    private String api_key;
    private String search;
    private String Description_part1;
    private String Description_part2;
    private AtomicInteger searchSize;

    Search(){
        api_key = "AIzaSyDBT_mj6XgfCQYXuipuhsgSAELknxWvRyg";
        Description_part1 ="https://www.googleapis.com/youtube/v3/videos?id=";
        Description_part2 = "&key="+api_key+"&part=snippet";
        search = "https://www.googleapis.com/youtube/v3/search?key="+api_key+"&q=";
    }

    public void call(Context context, String request, SearchDisplayController sdc){
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = search + request;
        Hashtable<String, Boolean> done=new Hashtable<>();
        // Request a string response from the provided URL.
        StringRequest searchRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    JSONObject res;
                    try {
                        res = new JSONObject(response);
                        JSONArray result = res.getJSONArray("items");
                        searchSize=new AtomicInteger(result.length());
                        for(int i=0;i<result.length();i++){
                            JSONObject video = result.getJSONObject(i).getJSONObject("id");
                            String video_id = video.get("videoId").toString();
                            Song toAdd=(new Song());
                            toAdd.setVideoID((video_id));
                            if (!done.containsKey(video_id)) {
                                add_des(context,video_id, sdc, toAdd);
                                Log.d("CONTAINS", done.toString());
                                done.put(video_id, Boolean.TRUE);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("response","That didn't work!");
            }
        });
        queue.add(searchRequest);
    }
    private void add_des(Context context, String video_id, SearchDisplayController sdc, Song toAdd){
        String url = Description_part1+video_id+Description_part2;
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d("queue",queue.toString());
        StringRequest searchRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject res;
                        try {
                            res = new JSONObject(response);
                            JSONObject result = res.getJSONArray("items").getJSONObject(0);
                            JSONObject snippet = result.getJSONObject("snippet");
                            toAdd.setSongName(snippet.get("title").toString());
                            toAdd.setArtist(snippet.get("channelTitle").toString());
                            JSONObject thumbnail = snippet.getJSONObject("thumbnails");
                            JSONObject d_efault = thumbnail.getJSONObject("default");
                            JSONObject standard = thumbnail.getJSONObject("standard");
                            toAdd.setThumbnailPath(d_efault.get("url").toString());
                            sdc.populateSong(toAdd);
                            Log.d("SEARCH_SIZE", ""+searchSize.get());

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
}
