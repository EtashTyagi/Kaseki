package com.example.kaseki;

import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yausername.youtubedl_android.YoutubeDL;
import com.yausername.youtubedl_android.YoutubeDLException;

import java.io.File;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private BottomBarController bottomBarController;
    private SecondarySongDisplayController secondarySongDisplayController;
    private MainDisplayFlipperController mainDisplayFlipperController;
    private PlaylistDisplayController playlistDisplayController;
    private static Download_Manager Downloader;
    private static Vector<Playlist> playlists;
    private static String SERIALIZED_FILE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SERIALIZED_FILE = getApplicationInfo().dataDir.toString() + "/playlists.ser";
        getSupportActionBar().hide();
        Utils.changeStatusBarColor(this, R.color.transparent_black);
        Utils.changeNavBarColor(this, R.color.black);
        setContentView(R.layout.activity_main);
        initialize(getApplicationInfo().dataDir.toString());
        Downloader = new Download_Manager();
        Downloader.initialize(getApplication(),getApplicationInfo().dataDir);
        playlists=Utils.deserializePlaylist(SERIALIZED_FILE);
        //Bottom Bar
        initiateBottomBar();
        //Song Display Bar
        initiateSecondarySongDisplay();
        //Main Display
        initiateMainDisplay();
    }
    private void initiateBottomBar() {
        bottomBarController =new BottomBarController(this);
    }
    private void initiateSecondarySongDisplay() {
        secondarySongDisplayController=new SecondarySongDisplayController(this);
    }
    public BottomBarController getBottomBarController() {
        return bottomBarController;
    }
    public SecondarySongDisplayController getSecondarySongDisplayController() {
        return secondarySongDisplayController;
    }

    private void initiateMainDisplay() {
        mainDisplayFlipperController=new MainDisplayFlipperController(this);
        View search = findViewById(R.id.searchDisplayInclude);
        View library= findViewById(R.id.libraryDisplayInclude);
        RecyclerView songScroller = search.findViewById(R.id.songScroller);
        RecyclerView playlistScroller = library.findViewById(R.id.playlistScroller);
        SongDisplayAdapter songAdapter = new SongDisplayAdapter(this);
        PlaylistDisplayAdapter playlistDisplayAdapter = new PlaylistDisplayAdapter(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(RecyclerView.VERTICAL);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        songScroller.setLayoutManager(layoutManager);
        playlistScroller.setLayoutManager(layoutManager1);
        songScroller.setAdapter(songAdapter);
        playlistScroller.setAdapter(playlistDisplayAdapter);
        mainDisplayFlipperController.initiateHomeDisplay();
        mainDisplayFlipperController.initiateSearchDisplay(songAdapter);
        mainDisplayFlipperController.initiateLibraryDisplay(playlistDisplayAdapter, playlists);
        playlistDisplayController=mainDisplayFlipperController.initiatePlaylistDisplay(playlists.get(0));
    }
    public void changeMainDisplayController(Class<? extends DisplayController> type) {
        mainDisplayFlipperController.flipToDisplay(type);
    }
    public static Download_Manager getDownloader() {
        return Downloader;
    }
    public static Vector<Playlist> getPlaylists() {
        return playlists;
    }
    public void initialize(String path){
        File thumbnail = new File(path+"/thumbnails");
        if(!thumbnail.exists()) {
            thumbnail.mkdir();
            Log.d("Folder","Thumbnail Folder Created");
        }
    }

    public PlaylistDisplayController getPlaylistDisplayController() {
        return playlistDisplayController;
    }

    public static String getSerializedPath(){
        return SERIALIZED_FILE;
    }

}