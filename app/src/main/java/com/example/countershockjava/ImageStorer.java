package com.example.countershockjava;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ImageStorer {

    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public ImageStorer(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(ShockUtils.SHOCK_SCARED_PREFS, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }


    public void storeImages(List<ImageModel> images){
        String key = context.getString(R.string.key_stored_offline_images);
        Gson gson = new Gson();
        editor.putString(key, gson.toJson(images));
        editor.commit();
    }

    public void addImage(ImageModel image){
        List<ImageModel> images = getStoredImages();
        images.add(image);
        storeImages(images);
    }

    private List<ImageModel> getStoredImages(){
        String imagesAsJson = preferences.getString(context.getString(R.string.key_stored_offline_images), null);
        if(imagesAsJson == null || imagesAsJson.length() == 0){
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<ImageModel>>(){}.getType();
        List<ImageModel> storedImages = gson.fromJson(imagesAsJson, type);
        return storedImages;
    }

    public List<ImageModel> getAllImages(){
        ArrayList<ImageModel> assetImages = new ArrayList<>();
        assetImages.add(new ImageModel(0, "bust_1", true));
        assetImages.add(new ImageModel(1, "bust_2", true));
        assetImages.add(new ImageModel(2, "man_1", true));

        assetImages.addAll(getStoredImages());
        return assetImages;
    }

    public ImageModel getSelectedImage(){
        List<ImageModel> images = getAllImages();

        ImageModel defaultImage = images.get(0);

        int imageId =  preferences.getInt(context.getString(R.string.key_photo_id), 0);
        for(ImageModel image: images){
            if(image.getId() == imageId){
                return image;
            }
        }

        // Fall back on defaults
        editor.putInt(context.getString(R.string.key_photo_id), 0);
        editor.commit();

        return defaultImage;
    }

}
