package com.example.countershockjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.HashMap;

public class SurpriseActivity extends AppCompatActivity {

    ImageView mImageView;

    Uri mPhotoUri;
    Uri mSoundUri;

    TextToSpeech mTTS;
    MediaPlayer mMediaPlayer;

    AudioModel mAudioModel;
    ImageModel mImageModel;

    boolean acceptingTouches = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surprise);

        mImageView = findViewById(R.id.imageView);

        mAudioModel = new AudioStorer(this).getSelectedAudio();
        mImageModel= new ImageStorer(this).getSelectedImage();

        if(mImageModel.isAsset()){
            mPhotoUri = ShockUtils.getDrawableUri(this, mImageModel.getImgFilename());

        }else{
            mPhotoUri = Uri.fromFile(new File(mImageModel.getImgFilename()));
        }

        if(!mAudioModel.isTTS()){
            if(mAudioModel.isAsset()){
                mSoundUri = ShockUtils.getRawUri(this, mAudioModel.getAudioFilename());
            }else{
                mSoundUri = Uri.fromFile(new File(mAudioModel.getAudioFilename()));
            }
        }

        Toast.makeText(this, "Ready", Toast.LENGTH_SHORT).show();

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void showImage(){
        Glide.with(this)
                .load(mPhotoUri)
                .into(mImageView);

        mImageView.setVisibility(View.VISIBLE);
    }

    private void playSoundClip(){
        mMediaPlayer = MediaPlayer.create(this, mSoundUri);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                finish();
            }
        });
        mMediaPlayer.start();
    }

    private void handleTTS(){
        final String toSpeak = mAudioModel.getDescriptionMessage();
        mTTS= new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS){
                    HashMap<String, String> params =  new HashMap<>();

                    params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "stringId");

                    if(i == TextToSpeech.SUCCESS){
                        mTTS.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
                            @Override
                            public void onUtteranceCompleted(String s) {
                                finish();
                            }
                        });
                        mTTS.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, params);
                    }else{
                        finish();
                    }
                }
            }
        });
    }

    private void userTriggeredActions(){
        if(!acceptingTouches){
            return;
        }
        acceptingTouches = false;

        showImage();


        if(mAudioModel.isTTS()){
            handleTTS();
        }else{
            playSoundClip();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        userTriggeredActions();

        return super.onTouchEvent(event);

    }
}
