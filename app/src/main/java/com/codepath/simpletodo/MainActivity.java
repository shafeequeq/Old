package com.codepath.simpletodo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView lvItems;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private static final int REQUEST_CODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        showSupportBar();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
       // items.add("First item");
        // items.add("Second item");
        setupListViewListener();


    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {

                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

       lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           public void onItemClick (AdapterView<?> parent,
                             View view,
                             int position,
                             long id) {
                //Toast.makeText(view,"Hello", Toast.LENGTH_SHORT).show();
                // create intent to launch second activity.
               Intent i = new Intent(MainActivity.this , EditItemActivity.class);
               Object o = parent.getItemAtPosition(position);
               i.putExtra("text",o.toString());
               i.putExtra("position",position);
               startActivityForResult(i, REQUEST_CODE);

            }
        });
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir , "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch(IOException e){
            items = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

   /* public void onAddItem(View view) {
        Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show();
    }*/

    public void onAddItem(View view) {
      //  Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show();
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        if(!itemText.isEmpty()) {
            itemsAdapter.add(itemText);
            etNewItem.setText("");
            writeItems();
        }
    }

    private void showSupportBar(){
        Resources res = getResources();
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        actionBar.setTitle(res.getString(R.string.app_name)); // set the top title
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            String text = data.getStringExtra("text");

            Integer position = data.getIntExtra("position",0);
            if((text!= null) && (!text.isEmpty()) && (position >= 0)){
                items.set(position, text);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
            }
        }
        showSupportBar();
    }
}
