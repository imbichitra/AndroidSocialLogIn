package com.bichi.storeimageindb;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FoodListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Food> foodList;
    LayoutInflater inflter;
    RoundedImageView roundedImageView;

    public FoodListAdapter(Context context, ArrayList<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int position) {
        return foodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflter.inflate(R.layout.food_items, null);
        Food food = foodList.get(position);
        TextView textView= convertView.findViewById(R.id.textView);
        ImageView imageView =convertView.findViewById(R.id.imageView2);
        textView.setText(food.getName());
        byte[] foodImage = food.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage,0,foodImage.length);
        drawImage(imageView,bitmap);
        return convertView;
    }
    public void drawImage(ImageView imageView, Bitmap bm)
    {
        roundedImageView = new RoundedImageView(context);
        Bitmap conv_bm = roundedImageView.getCroppedBitmap(bm, 250, 7, Color.WHITE);
        imageView.setImageBitmap(conv_bm);
    }
}
