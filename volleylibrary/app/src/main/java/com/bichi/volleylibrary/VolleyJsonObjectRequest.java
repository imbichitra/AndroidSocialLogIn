package com.bichi.volleylibrary;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class VolleyJsonObjectRequest {
    private static MyInterFace mInterFace;

    public static void jsonObjectRequest(Context context,String url,MyInterFace interFace){
       mInterFace = interFace;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mInterFace.finalResult(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mInterFace.error(error);
            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
