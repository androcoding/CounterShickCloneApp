package com.example.countershockjava;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AudioPickerDialogFragment extends DialogFragment implements AudioPickerAdapter.Callback {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    AudioPickerAdapter adapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getContext().getSharedPreferences(ShockUtils.SHOCK_SCARED_PREFS,
                Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_media_picker, container, false);

        List<AudioModel> items = new AudioStorer(getContext()).getAllAudios();

        adapter = new AudioPickerAdapter(items, this);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if(dialog != null){
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void itemSelected(AudioModel item) {
        editor.putInt(getString(R.string.key_audio_id), item.getId());
        editor.commit();
        dismiss();
        LocalBroadcastManager.getInstance(getContext()).
                sendBroadcast(new Intent(ShockUtils.MEDIA_UPDATED_ACTION));
    }
}
