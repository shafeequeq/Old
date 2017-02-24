package com.codepath.simpletodo;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private EditText editItem;
    private Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String text = getIntent().getStringExtra("text");
        position = getIntent().getIntExtra("position",0);

        Resources res = getResources();
        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        actionBar.setTitle(res.getString(R.string.app_name_edit)); // set the top title
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        editItem = (EditText)findViewById(R.id.editItem);
        if(editItem != null) {
            editItem.setText(text);
            editItem.setSelection(text.length());
        }
    }

    public void OnSave(View v){
        Intent data = new Intent();
        editItem = (EditText)findViewById(R.id.editItem);
         if( editItem != null){
             String text = editItem.getText().toString();
             data.putExtra("text", text);
             data.putExtra("position",position);
             setResult(RESULT_OK, data);
         }
        else{
             data.putExtra("text","");
             data.putExtra("position",-1);
             setResult(RESULT_OK, data);
         }

        finish();
    }
}
