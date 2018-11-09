package com.bichi.volleylibrary;

import android.graphics.Bitmap;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

public interface MyInterFace {
    public void finalResult(String data);
    public void finalResult(JSONObject data);
    public void finalResult(JSONArray data);
    public void error(VolleyError error);
    public void finalResult(Bitmap data);
}
