package com.eavmusic.eavmusic.Controller;

import com.eavmusic.eavmusic.Model.Music;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class MediaPlayerManager {

    public static MediaPlayer lastMediaPlayer;
    public static Music lastMusic;

    private static void setUpMediaPlayer(Music music) {
        lastMusic = music;
        lastMediaPlayer = music.getMediaPlayer();
    }

    public static void play(Music music) {
        setUpMediaPlayer(music);
        if (lastMediaPlayer.getStatus() == MediaPlayer.Status.READY) {
            lastMediaPlayer.play();
        } else {
            lastMediaPlayer.setOnReady(() -> lastMediaPlayer.play());
        }
    }

    public static void play() {
        if (lastMediaPlayer != null && isPaused()) {
            lastMediaPlayer.play();
        }
    }

    public static void pause() {
        if (lastMediaPlayer != null && isPlaying()) {
            lastMediaPlayer.pause();
        }
    }

    public static void stop() {
        if (lastMediaPlayer != null) {
            lastMediaPlayer.stop();
            dispose();
            setMediaPlayer(lastMusic);
            lastMediaPlayer = null;
        }
    }

    public static void replay() {
        if (lastMediaPlayer != null && isPlaying()) {
            lastMediaPlayer.seek(Duration.ZERO);
        }
    }

    public static void setMediaPlayer(Music music) {
        music.setMediaPlayer(new MediaPlayer(music.getMedia()));
    }

    public static MediaPlayer getLastMediaPlayer() {
        return lastMediaPlayer;
    }

    public static void dispose() {
        if (lastMediaPlayer != null) {
            lastMediaPlayer.dispose();
        }
    }

    public static String getLastMediaPlayerStatus() {
        return lastMediaPlayer.getStatus().toString();
    }

    public static boolean isReady() {
        return lastMediaPlayer.getStatus() == MediaPlayer.Status.READY;
    }

    public static boolean isNull() {
        return lastMediaPlayer == null;
    }

    public static boolean isPlaying() {
        return lastMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public static boolean isPaused() {
        return lastMediaPlayer.getStatus() == MediaPlayer.Status.PAUSED;
    }

    public static boolean isStopped() {
        return lastMediaPlayer.getStatus() == MediaPlayer.Status.STOPPED;
    }
}
