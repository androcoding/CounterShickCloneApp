package com.example.countershockjava;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AudioStorer {

    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public AudioStorer(Context context){
        this.context = context;
        preferences = context.getSharedPreferences(ShockUtils.SHOCK_SCARED_PREFS, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void addAudio(AudioModel audioModel){
        List<AudioModel> audios = getStoredAudios();
        audios.add(audioModel);
        storeAudios(audios);
    }

    public void storeAudios(List<AudioModel> audios){
        String key = context.getString(R.string.key_stored_audios);
        Gson gson = new Gson();
        editor.putString(key, gson.toJson(audios));
        editor.commit();
    }

    private List<AudioModel> getStoredAudios(){
        String audiosAsString = preferences.getString(context.getString(R.string.key_stored_audios), null);
        if(audiosAsString == null || audiosAsString.length() == 0){
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<AudioModel>>(){}.getType();
        return gson.fromJson(audiosAsString, type);
    }

    public List<AudioModel> getAllAudios(){
        List<AudioModel> audios = new ArrayList<>();
        audios.add(new AudioModel(0, "scream2", "Scream 2", true));
        audios.add(new AudioModel(1, "behind_you", "Behind You", true));
        audios.add(new AudioModel(2, "watching_you", "I'm Watching You", true));

        audios.addAll(getStoredAudios());
        return audios;
    }

    public AudioModel getSelectedAudio(){
        List<AudioModel> audios = getAllAudios();

        AudioModel defaultAudio =  audios.get(0);

        int audioId = preferences.getInt(context.getString(R.string.key_audio_id), 0);
        for(AudioModel audio : audios){
            if(audio.getId() == audioId){
                return audio;
            }
        }

        // Fall back on default
        editor.putInt(context.getString(R.string.key_audio_id), 0);
        editor.commit();

        return defaultAudio;
    }

}
