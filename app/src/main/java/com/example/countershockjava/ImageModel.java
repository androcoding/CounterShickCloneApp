package com.example.countershockjava;

import java.io.Serializable;
import java.util.Objects;

public class ImageModel implements Serializable {

    int id;
    String imgFilename;
    boolean isAsset;

    public ImageModel(int id, String imgFilename, boolean isAsset) {
        this.id = id;
        this.imgFilename = imgFilename;
        this.isAsset = isAsset;
    }

    public int getId() {
        return id;
    }

    public String getImgFilename() {
        return imgFilename;
    }

    public boolean isAsset() {
        return isAsset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageModel that = (ImageModel) o;
        return id == that.id &&
                isAsset == that.isAsset &&
                Objects.equals(imgFilename, that.imgFilename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imgFilename, isAsset);
    }
}
