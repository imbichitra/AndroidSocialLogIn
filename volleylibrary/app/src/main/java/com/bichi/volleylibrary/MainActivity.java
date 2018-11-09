package com.bichi.volleylibrary;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MyInterFace{

    String stringUrl = "http://www.json-generator.com/api/json/get/caNCZhuKMi?indent=2";
    String jsonArrayUrl = "http://www.json-generator.com/api/json/get/cgxLHmvNCG?indent=2";
    String jsonObjectUrl = "http://www.json-generator.com/api/json/get/bOSWesUFyW?indent=2";
    String postUrl = "http://marketzoo.000webhostapp.com/demo.php";
    String imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/71/Sun_Wiki.svg/320px-Sun_Wiki.svg.png";
    TextView t;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t= findViewById(R.id.bichi);
        imageView = findViewById(R.id.image);

        //VolleyStringRequest.stringRequest(getApplication(),stringUrl,this);
        //VolleyJsonArrayRequest.jsonArrayRequest(getApplication(),jsonArrayUrl,this);
        //VolleyJsonObjectRequest.jsonObjectRequest(getApplication(),jsonObjectUrl,this);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("name", "Volley Demo");
            jsonBody.put("address", "BNK");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final String requestBody = jsonBody.toString();
       // VolleyStringRequest.postRequest(getApplication(),postUrl,this,requestBody);
        VolleyImageRequest.imageRequst(getApplication(),imageUrl,this);
    }

    @Override
    public void finalResult(String data) {
        t.setText(data);
    }

    @Override
    public void finalResult(JSONObject data) {
        try {
            t.setText(data.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finalResult(JSONArray data) {
        try {
            JSONObject obj =  data.getJSONObject(0);
            t.setText(obj.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finalResult(Bitmap data) {
        imageView.setImageBitmap(data);
    }

    @Override
    public void error(VolleyError error) {
        t.setText(error.toString());
    }

}
