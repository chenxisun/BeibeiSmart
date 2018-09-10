package edu.buaa.beibeismart.Media;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.LinkedList;

public class OnlineMediaPlayer extends MediaPlayer implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private static MediaPlayer mediaPlayer;
    private static OnlineMediaPlayer onlineMediaPlayer;
    private MyPreparedListener myPreparedListener;

    LinkedList<String> playUrlsList;

    public void clearVedioUrl(){
        playUrlsList.clear();
    }
    public void stop(){
        if(playUrlsList.size() > 0){
            playUrlsList.removeFirst();
        }
        mediaPlayer.release();
    }

    public void stopAll(){
        playUrlsList.clear();
        mediaPlayer.release();
    }

    public OnlineMediaPlayer() {
        initMediaPlayer();
        playUrlsList = new LinkedList();
    }

    public void PlayLocalMusic(String uri){
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(uri);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static OnlineMediaPlayer getInstance(){
        if (onlineMediaPlayer == null){
            onlineMediaPlayer = new OnlineMediaPlayer();
        }
        return onlineMediaPlayer;
    }

    public static OnlineMediaPlayer RefreshPlayer() {


        onlineMediaPlayer=new OnlineMediaPlayer();

        return onlineMediaPlayer;
    }

    private void initMediaPlayer(){
        mediaPlayer = new MediaPlayer();
        if(myPreparedListener == null){
            myPreparedListener = new MyPreparedListener(0);
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnPreparedListener(myPreparedListener);
        mediaPlayer.setOnCompletionListener(this);

    }

    private void addPlayUrl(String url){
        Log.e("OnlineMediaPlayer: addPlayUrl",url);
        playUrlsList.addLast(url);

        Log.e("OnlineMediaPlayer: playUrlsList.size():",""+playUrlsList.size());
        if (playUrlsList.size() <= 1){
            playNet(0,playUrlsList.getFirst());
        }
    }

    public void play(String url){
        Log.e("OnlineMediaPlayer",url);
        initMediaPlayer();
        addPlayUrl(url);
    }
    public void playAppend(String url){
        if (mediaPlayer == null){
            initMediaPlayer();
        }
        addPlayUrl(url);
    }

    private void playNet(int playPosition,String videoUrl) {
        Log.e("OnlineMediaPlayer: playNet",videoUrl);
        initMediaPlayer();
        try {
            mediaPlayer.reset();// 把各项参数恢复到初始状态
            mediaPlayer.setDataSource(videoUrl);
            mediaPlayer.prepare();// 进行缓冲
        } catch (Exception e) {
            e.printStackTrace();
            playUrlsList.removeFirst();
            mediaPlayer.release();
            return;
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {

    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {

        Log.e("OnlineMediaPlayer on prepared11:","prepare1");
    }


    @Override
    public boolean isPlaying() {
        return super.isPlaying();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.e("OnlineMediaPlayer onCompletion:","completion");
        //从播放list中删除第一个播放url
        if (playUrlsList.size() > 0){
            playUrlsList.removeFirst();
        }
        if (playUrlsList.size()>0){
            playNet(0,playUrlsList.getFirst());
        }
        else{
            mediaPlayer.release();
        }
    }


    private final class MyPreparedListener implements
            android.media.MediaPlayer.OnPreparedListener {
        private int playPosition;

        public MyPreparedListener(int playPosition) {
            this.playPosition = playPosition;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            Log.e("OnlineMediaPlayer on prepared:","prepare");
            mediaPlayer.start();// 开始播放
            if (playPosition > 0) {
                mediaPlayer.seekTo(playPosition);
            }
        }
    }
}
