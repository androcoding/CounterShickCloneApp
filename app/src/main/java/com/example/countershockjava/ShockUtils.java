package com.example.countershockjava;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;

import java.io.File;

public class ShockUtils {

    public static String SHOCK_SCARED_PREFS = "shock_shared_prefs";

    public static int STARTING_ID = 1000;

    public static String MEDIA_UPDATED_ACTION = "MEDIA_UPDATED_ACTION";

    public static Uri getRawUri(Context context, String assetName){
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + File.pathSeparator + File.separator +
                File.separator + context.getPackageName() + "/raw/" + assetName);
    }

    public static Uri getDrawableUri(Context context, String assetName){
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + File.pathSeparator + File.separator +
                File.separator + context.getPackageName() + "/drawable/" + assetName);
    }

}
