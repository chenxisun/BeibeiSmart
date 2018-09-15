package edu.buaa.beibeismart.Media;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;

public class SlackMusicPlayer {

    public final static SlackMusicPlayer instance = new SlackMusicPlayer();

    private Thread mPrepareThread = null;

    private SlackMusicPlayer() {
        //
    }

    private MediaPlayer mMediaPlayer;

    private String mLastPlayMusicUrl = "";

    private boolean mMusicIsPlaying = false;
    private final Object mPlayerLocker = new Object();


//    public  static SlackMusicPlayer getInstance(){
//     if
//    }

    public void play(@NonNull String url) {
//        play(url, false);
        play(url, true);
    }

    public void play(@NonNull String url, boolean restart) {
        synchronized (mPlayerLocker) {
            try {
                if (mMediaPlayer == null) {
                    mMediaPlayer = new MediaPlayer();
                }
                if (restart || !mLastPlayMusicUrl.equals(url)) {
                    mMediaPlayer.reset();
                    mMediaPlayer.setLooping(true);
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    mMediaPlayer.setVolume(0.5f, 0.5f);
                    mMediaPlayer.setDataSource(url);
                    mLastPlayMusicUrl = url;
                    mMusicIsPlaying = true;
                    if (mPrepareThread != null) {
                        mPrepareThread.interrupt();
                    }
                    mPrepareThread = null;
                    if (url.startsWith("/")) {// 本地音乐文件
                        mMediaPlayer.setOnPreparedListener(null);
                        mPrepareThread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    synchronized (mPlayerLocker) {
                                        if (mMediaPlayer != null && mMusicIsPlaying) {
                                            mMediaPlayer.prepare();
                                            mMediaPlayer.start();
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        mPrepareThread.start();
                    } else {
                        mMediaPlayer.prepareAsync();
                        mMediaPlayer.setOnPreparedListener(mMediaPrepareListener);

                    }
                } else {
                    mMediaPlayer.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setVolume(float left, float right) {
        synchronized (mPlayerLocker) {
            try {
                if (mMediaPlayer != null) {
                    mMediaPlayer.setVolume(left, right);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 8-3
    public void continuePlay() {
        synchronized (mPlayerLocker) {
            try {
                if (mMediaPlayer != null) {
                    mMediaPlayer.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mMusicIsPlaying = true;
        }
    }

    //8-3
    public boolean isPlaying() {
        return mMusicIsPlaying;
    }

    public void pause() {
        synchronized (mPlayerLocker) {
            try {
                if (mMediaPlayer != null) {
                    mMediaPlayer.pause();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mMusicIsPlaying = false;
        }
    }


    public void reset() {
        synchronized (mPlayerLocker) {
            try {
                if (mMediaPlayer != null) {

                    mMediaPlayer.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mMusicIsPlaying = false;
            mLastPlayMusicUrl = "";
        }
    }

    public int getCurrentPosition() {
        if (mMediaPlayer != null)
            return mMediaPlayer.getCurrentPosition();
        else {
            return 0;
        }

    }

    public void seekTo(int track) {
        mMediaPlayer.seekTo(track);
    }

    public boolean isNull() {
        return mMediaPlayer == null;
    }

    public void release() {
        synchronized (mPlayerLocker) {
            try {
                if (mMediaPlayer != null) {
                    mMediaPlayer.release();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mMusicIsPlaying = false;
            mMediaPlayer = null;
        }
    }

    private MediaPlayer.OnPreparedListener
            mMediaPrepareListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            synchronized (mPlayerLocker) {
                if (mMediaPlayer != null) {
                    if (mMusicIsPlaying) {
                        mMediaPlayer.start();
                    } else {
                        mMediaPlayer.pause();
                    }
                }
            }
        }
    };
}