package com.example.kaseki;

import android.app.Application;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.yausername.youtubedl_android.YoutubeDL;
import com.yausername.youtubedl_android.YoutubeDLException;

import java.io.File;
import java.util.HashSet;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    private static MainActivity mainActivity;
    private BottomBarController bottomBarController;
    private SecondarySongDisplayController secondarySongDisplayController;
    private MainDisplayFlipperController mainDisplayFlipperController;
    private PlaylistDisplayController playlistDisplayController;
    private PrimarySongDisplayController primarySongDisplayController;
    private static Download_Manager Downloader;
    private static Vector<Playlist> playlists;
    private static HashSet<Song> downloadedSongs;
    private static String SERIALIZED_FILE;
    private static Player player = new Player();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity=this;
        SERIALIZED_FILE = getApplicationInfo().dataDir + "/playlists.ser";
        getSupportActionBar().hide();
        Utils.changeStatusBarColor(this, R.color.transparent_black);
        Utils.changeNavBarColor(this, R.color.black);
        setContentView(R.layout.activity_main);
        downloadedSongs=new HashSet<>();
        playlists = Utils.deserializePlaylist(SERIALIZED_FILE);
        for (Playlist p : playlists) {
            downloadedSongs.addAll(p.getSongs());
        }
        Downloader = new Download_Manager();
        Downloader.initialize(getApplication(),getApplicationInfo().dataDir);;
        initialize(getApplicationInfo().dataDir);
        GarbageCollector.collectGarbage(getApplicationInfo().dataDir);
        //Bottom Bar
         initiateBottomBar();
        //Song Display Bar
        initiateSecondarySongDisplay();
        //Main Display
        initiateMainDisplay();
        Handler mHandler = new Handler();
        //Make sure you update Seekbar on UI thread
        Runnable mRunnable = new Runnable() {
            @Override
            public void run() {
                if(Player.getPlayer() != null){
                    int mCurrentPosition = Player.getPlayer().getCurrentPosition();
                    PrimarySongDisplayController.getSeekBar().setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this,200);
            }
        };
        mRunnable.run();
    }

    public static MainActivity getCurrentInstance() {
        return mainActivity;
    }

    private void initiateBottomBar() {
        bottomBarController =new BottomBarController();
    }
    private void initiateSecondarySongDisplay() {
        secondarySongDisplayController=new SecondarySongDisplayController();
    }
    public BottomBarController getBottomBarController() {
        return bottomBarController;
    }
    public SecondarySongDisplayController getSecondarySongDisplayController() {
        return secondarySongDisplayController;
    }

    private void initiateMainDisplay() {
        mainDisplayFlipperController=new MainDisplayFlipperController();
        View search = findViewById(R.id.searchDisplayInclude);
        View library= findViewById(R.id.libraryDisplayInclude);
        RecyclerView songScroller = search.findViewById(R.id.songScroller);
        RecyclerView playlistScroller = library.findViewById(R.id.playlistScroller);
        SongDisplayAdapter songAdapter = new SongDisplayAdapter();
        PlaylistDisplayAdapter playlistDisplayAdapter = new PlaylistDisplayAdapter();
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
        primarySongDisplayController=mainDisplayFlipperController.initiatePrimarySongDisplayController();
    }

    public PrimarySongDisplayController getPrimarySongDisplayController() {
        return primarySongDisplayController;
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
    public static void addDownloaded(Song song) {
        downloadedSongs.add(song);
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

    public static boolean isDownloaded(Song song) {
        return downloadedSongs.contains(song);
    }

    public Application getActivity(){
        return this.getApplication();
    }

    public void updateSeekBar(){

    }

}