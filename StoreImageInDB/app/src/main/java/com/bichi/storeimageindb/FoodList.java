package com.bichi.storeimageindb;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FoodList extends AppCompatActivity {

    ArrayList<Food> list;
    FoodListAdapter adapter = null;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        listView = findViewById(R.id.listview);
        list = new ArrayList<>();
        adapter = new FoodListAdapter(this,list);
        listView.setAdapter(adapter);

        Cursor cursor = MainActivity.databaseHandler.getData();
        Log.d("FoodList", "onCreate: "+list);
        list.clear();
        if(cursor!=null) {
            Log.d("FoodList", "if part1 : ");
            if(cursor.moveToFirst()) {
                Log.d("FoodList", "if part2 : ");
                do {
                    Log.d("FoodList", "if part 3: ");
                    int id = cursor.getInt(0);
                    String name = cursor.getString(1);
                    byte[] image = cursor.getBlob(2);
                    Log.d("FoodList", "name: " + name);
                    Log.d("FoodList", "id: " + id);
                    Toast.makeText(this, "id: "+id+"name is: "+name, Toast.LENGTH_SHORT).show();

                    list.add(new Food(id, name,image));
                }while (cursor.moveToNext());
                adapter.notifyDataSetChanged();
            }

        }else{
            Toast.makeText(this, "Cursor null", Toast.LENGTH_SHORT).show();
        }
        Log.d("FoodList", "onCreate1: "+list);
    }
}
