package com.example.listviewapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> mylist;
    ListView myListView;
    ArrayAdapter<String> arrayAdapter ;

    public void onAdding(View view){
        EditText itemView= findViewById(R.id.newItem);
        String item=itemView.getText().toString();
        if(item.isEmpty()){
            Toast.makeText(this,"Empty TextField Please Enter something",Toast.LENGTH_SHORT).show();
        }else {
            Log.i("button", item);
            mylist.add(item);
            itemView.getText().clear();
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mylist);
            myListView.setAdapter(arrayAdapter);
            myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    mylist.remove(i);
                    arrayAdapter.notifyDataSetChanged();
                }
            });
            Button newList = findViewById(R.id.newList);
            newList.animate().alpha(1);
            TextView note=(TextView) findViewById(R.id.note);
            note.animate().alpha(1);
        }
    }
    public void getnewList(View view){
        mylist.clear();
        arrayAdapter.notifyDataSetChanged();
        Button newList = findViewById(R.id.newList);
        newList.animate().alpha(0);
        TextView note=(TextView) findViewById(R.id.note);
        note.animate().alpha(0);
    }

 @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences sharedPreferences = getSharedPreferences("myList", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Clear existing list items and item count
        editor.clear();

        // Store the number of items in the list
        editor.putInt("itemsCount", mylist.size());

        // Put each list item in SharedPreferences
        for (int i = 0; i < mylist.size(); i++) {
            editor.putString("item" + i, mylist.get(i));
        }

        editor.apply();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         myListView=findViewById(R.id.myListView);
         mylist = new ArrayList<String>();

        // Get SharedPreferences instance
        SharedPreferences sharedPreferences = getSharedPreferences("myList", MODE_PRIVATE);

        // Retrieve list items from SharedPreferences
        int itemsCount = sharedPreferences.getInt("itemsCount", 0);
        for (int i = 0; i < itemsCount; i++) {
            String item = sharedPreferences.getString("item" + i, "");
            mylist.add(item);
        }
        if(!mylist.isEmpty()){
            Button newList = findViewById(R.id.newList);
            newList.animate().alpha(1);
            TextView note=(TextView) findViewById(R.id.note);
            note.animate().alpha(1);
        }

        // Create and set adapter for the list view
       arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        myListView.setAdapter(arrayAdapter);

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mylist.remove(i);
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }}