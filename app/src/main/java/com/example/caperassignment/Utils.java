package com.example.caperassignment;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class Utils {
    public static Context context;

    public Utils(Context context){
        context = context;
    }
    public static String getAssetJsonData() {
        String json;
        try {
            InputStream is = Utils.context.getAssets().open("caper.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        Log.e("data", json);
        return json;
    }


}
