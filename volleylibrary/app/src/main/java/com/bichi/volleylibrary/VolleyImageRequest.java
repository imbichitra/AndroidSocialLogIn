package com.bichi.volleylibrary;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

public class VolleyImageRequest {
    private static MyInterFace mInterFace;

    public static void imageRequst(Context context,String url,MyInterFace myInterFace){
        mInterFace = myInterFace;
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
              mInterFace.finalResult(response);
            }
        }, 0, 0, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mInterFace.error(error);
            }
        });

        VolleySingleton.getInstance(context).addToRequestQueue(imageRequest);
    }
}
