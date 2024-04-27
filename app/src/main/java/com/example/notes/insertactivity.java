package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.github.muddz.styleabletoast.StyleableToast;

public class insertactivity extends AppCompatActivity {

    FloatingActionButton DoneNotesBtn;
    EditText edTitle,edSubTitle,edNotes;

    TextView create_textDeteTime;

    DatabaseHelper dbHelper;

    MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insertactivity);

        DoneNotesBtn = findViewById(R.id.DoneNotesBtn);
        edTitle = findViewById(R.id.edTitle);
        edSubTitle = findViewById(R.id.edSubTitle);
        edNotes = findViewById(R.id.edNotes);
        toolbar = findViewById(R.id.toolbar);
        create_textDeteTime = findViewById(R.id.create_textDeteTime);


        create_textDeteTime.setText(
                new SimpleDateFormat("EEEE , dd MMMM yyyy HH:mm a", Locale.getDefault())
                        .format(new Date())
        );

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        dbHelper= new DatabaseHelper(this);




        DoneNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String sTitle = edTitle.getText().toString();
                String sSubTitle = edSubTitle.getText().toString();
                String sNotes = edNotes.getText().toString();

                if (sTitle.length()>0 && sSubTitle.length()>0 && sNotes.length()>0){

                    dbHelper.addNotes(sTitle,sSubTitle,sNotes);

                    new StyleableToast
                            .Builder(insertactivity.this)
                            .text("Notes Update Successful!")
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.GREEN)
                            .show();
                    startActivity(new Intent(insertactivity.this,MainActivity.class));
                }else
                    new StyleableToast
                            .Builder(insertactivity.this)
                            .text("Note title can't be empty!")
                            .textColor(Color.WHITE)
                            .backgroundColor(Color.RED)
                            .show();
            }
        });





    }



}