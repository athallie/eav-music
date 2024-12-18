package com.eavmusic.eavmusic.Model;

import com.eavmusic.eavmusic.Controller.MediaPlayerManager;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.collections.MapChangeListener.Change;

import java.io.File;
import java.nio.file.Path;

public class Music {
    private StringProperty title = new SimpleStringProperty("Unknown"),
            artist = new SimpleStringProperty("Unknown"),
            album = new SimpleStringProperty("Unknown");
    private DoubleProperty duration = new SimpleDoubleProperty(0);
    private Media media;
    private MediaPlayer mediaPlayer;

    public Music(Path filePath) {
        this.media = new Media(filePath.toUri().toString());
        this.mediaPlayer = new MediaPlayer(this.media);
        setStringProperties(filePath);
        setDurationProperty();
    }

    private void setDurationProperty() {
        this.mediaPlayer.setOnReady(
                () ->
                {
                    this.duration.setValue(this.media.getDuration().toMinutes());
                }
        );
    }

    private void setStringProperties(Path filePath) {
        this.media.getMetadata().addListener(
                (Change<? extends String, ?> c) ->
                {
                    if (c.wasAdded()) {
                        if (c.getKey().equals("artist")) {
                            this.artist.setValue(c.getValueAdded().toString());
                        } else if (c.getKey().equals("title")) {
                            this.title.setValue(c.getValueAdded().toString());
                        } else if (c.getKey().equals("album")) {
                            this.album.setValue(c.getValueAdded().toString());
                        }
                    }
                }
        );

        Platform.runLater(
                () ->
                {
                    if (getTitle().equals("Unknown")) {
                        File file = new File(filePath.toUri());
                        this.title.setValue(file.getName().replace(".mp3", ""));
                    }
                }
        );
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public Media getMedia() {
        return media;
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public String getArtist() {
        return artist.get();
    }

    public StringProperty artistProperty() {
        return artist;
    }

    public String getAlbum() {
        return album.get();
    }

    public StringProperty albumProperty() {
        return album;
    }

    public double getDuration() {
        return duration.get();
    }

    public DoubleProperty durationProperty() {
        return duration;
    }

    @Override
    public String toString() {
        return String.format(
                "%s - %s - %s - %s",
                getTitle(), getAlbum(), getArtist(), getDuration()
        );
    }
}
