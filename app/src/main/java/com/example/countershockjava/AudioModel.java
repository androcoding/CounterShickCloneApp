package com.example.countershockjava;

import java.io.Serializable;
import java.util.Objects;

public class AudioModel implements Serializable {

    int id;
    String audioFilename;
    String descriptionMessage;
    boolean isAsset;
    boolean isTTS;

    // For asset audio only
    public AudioModel(int id, String audioFilename, String descriptionMessage, boolean isAsset) {
        this.id = id;
        this.audioFilename = audioFilename;
        this.descriptionMessage = descriptionMessage;
        this.isAsset = isAsset;
    }

    // For TTS only
    public AudioModel(int id, String descriptionMessage) {
        this.id = id;
        this.descriptionMessage = descriptionMessage;
        this.isTTS = true;
    }

    public int getId() {
        return id;
    }

    public String getAudioFilename() {
        return audioFilename;
    }

    public String getDescriptionMessage() {
        return descriptionMessage;
    }

    public boolean isAsset() {
        return isAsset;
    }

    public boolean isTTS() {
        return isTTS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AudioModel that = (AudioModel) o;
        return id == that.id &&
                isAsset == that.isAsset &&
                isTTS == that.isTTS &&
                Objects.equals(audioFilename, that.audioFilename) &&
                Objects.equals(descriptionMessage, that.descriptionMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, audioFilename, descriptionMessage, isAsset, isTTS);
    }
}
