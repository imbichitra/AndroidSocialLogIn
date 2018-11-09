package com.bichi.volleylibrary;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

public class VolleyJsonArrayRequest {
    private static MyInterFace mInterFace;

    public static void jsonArrayRequest(Context context,String url,MyInterFace myInterFace){
        mInterFace = myInterFace;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mInterFace.finalResult(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mInterFace.error(error);
            }
        });

        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }
}
