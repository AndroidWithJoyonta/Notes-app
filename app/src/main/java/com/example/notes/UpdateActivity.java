package com.example.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.muddz.styleabletoast.StyleableToast;

public class UpdateActivity extends AppCompatActivity {

    FloatingActionButton UpdateNotesBtn;
    EditText edUpTitle,edUpSubTitle,edUpNotes;

    DatabaseHelper dbHelper;
    String id = "";
    MaterialToolbar toolbarUpdate;

    TextView create_textDeteTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        UpdateNotesBtn = findViewById(R.id.UpdateNotesBtn);
        edUpTitle = findViewById(R.id.edUpTitle);
        edUpSubTitle = findViewById(R.id.edUpSubTitle);
        edUpNotes = findViewById(R.id.edUpNotes);
        toolbarUpdate = findViewById(R.id.toolbarUpdate);
        create_textDeteTime = findViewById(R.id.create_textDeteTime);



        dbHelper= new DatabaseHelper(this);

        toolbarUpdate.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        id = getIntent().getExtras().getString("id");
        String title =getIntent().getExtras().getString("title");
        String subTitle =getIntent().getExtras().getString("subTitle");
        String notes =getIntent().getExtras().getString("notes");
        String time =getIntent().getExtras().getString("time");



        edUpTitle.setText(title);
        edUpSubTitle.setText(subTitle);
        edUpNotes.setText(notes);
        create_textDeteTime.setText(time);


        UpdateNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sTitle = edUpTitle.getText().toString();
                String sSubTitle = edUpSubTitle.getText().toString();
                String sNotes = edUpNotes.getText().toString();

                if (sTitle.length()>0 && sSubTitle.length()>0 && sNotes.length()>0){
                    dbHelper.edit(id,sTitle, sSubTitle, sNotes,time);

                    new StyleableToast
                            .Builder(UpdateActivity.this)
                            .text("Notes Update Successful!")
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.GREEN)
                            .show();

                    startActivity(new Intent(UpdateActivity.this,MainActivity.class));

                }else
                    new StyleableToast
                            .Builder(UpdateActivity.this)
                            .text("Note title can't be empty!")
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.RED)
                            .show();


            }
        });



    }


}