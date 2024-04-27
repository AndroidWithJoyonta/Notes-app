package com.example.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import io.github.muddz.styleabletoast.StyleableToast;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    FloatingActionButton newNotesBtn;

    DatabaseHelper dbHelper;

    ArrayList<HashMap<String,String>> arrayList;
    HashMap<String ,String > hashMap;

    EditText edSearch;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        newNotesBtn=findViewById(R.id.newNotesBtn);
        edSearch=findViewById(R.id.edSearch);


        dbHelper= new DatabaseHelper(MainActivity.this);







        newNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,insertactivity.class));
            }
        });

        allData(dbHelper.getallData());

        //=========================================

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String key =edSearch.getText().toString();
                allData(dbHelper.searchNotes(key));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    //=============================================

    //=============================================

    public  void allData(Cursor cursor){




        //Cursor cursor =  dbHelper.getallData();

        if (cursor!=null && cursor.getCount()>0){

            arrayList = new ArrayList<>();
            while (cursor.moveToNext()){

                int id =cursor.getInt(0);
                String title =cursor.getString(1);
                String subTitle =cursor.getString(2);
                String notes =cursor.getString(3);
                String time =cursor.getString(4);




                hashMap = new HashMap<>();
                hashMap.put("id",""+id);
                hashMap.put("title",""+title);
                hashMap.put("subTitle",""+subTitle);
                hashMap.put("notes",""+notes);
                hashMap.put("time",""+time);


                arrayList.add(hashMap);

            }

            gridView.setAdapter(new MyAdapter());
        }else{


        }
    }




   // =============================================
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            LayoutInflater inflater =getLayoutInflater();
            View myView = inflater.inflate(R.layout.item,parent,false);


            TextView notesTitle = myView.findViewById(R.id.notesTitle);
            TextView notesSubTitle = myView.findViewById(R.id.notesSubTitle);
            TextView notesDate = myView.findViewById(R.id.notesDate);
            LinearLayout layItem = myView.findViewById(R.id.layItem);
            ImageView deleteBtn = myView.findViewById(R.id.deleteBtn);


            HashMap<String,String> myMap = arrayList.get(position);
            String id =myMap.get("id");
            String title =myMap.get("title");
            String subTitle =myMap.get("subTitle");
            String notes =myMap.get("notes");
            String time =myMap.get("time");

            notesTitle.setText(title);
            notesSubTitle.setText(subTitle);
            notesDate.setText(time);


            layItem.setOnClickListener( v -> {


            });


            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
               public void onClick(View v) {


                    final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.menus_item, null);
                    alert.setView(mView);

                    final AlertDialog alertDialog = alert.create();
                    alertDialog.setCancelable(true);






                    mView.findViewById(R.id.DeleteBtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                            final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                            View mView = getLayoutInflater().inflate(R.layout.layout_delete_note, null);
                            alert.setView(mView);

                            final AlertDialog AalertDialog = alert.create();
                            AalertDialog.setCancelable(false);

                            mView.findViewById(R.id.chancelBTN).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AalertDialog.dismiss();
                                }
                            });


                            mView.findViewById(R.id.okBTN).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                        dbHelper.deleteNotes(id);
                                        allData(dbHelper.getallData());
                                        notifyDataSetChanged();
                                        MainActivity.super.onResume();


                                    new StyleableToast
                                            .Builder(MainActivity.this)
                                            .text("Notes Delete Successful!")
                                            .textColor(Color.WHITE)
                                            .backgroundColor(Color.RED)
                                            .show();
                                    AalertDialog.dismiss();

                                }
                            });



                            AalertDialog.show();




                        }
                    });


                    mView.findViewById(R.id.editBtn).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Intent intent = new Intent(MainActivity.this,UpdateActivity.class);

                            intent.putExtra("id",id);
                            intent.putExtra("title",title);
                            intent.putExtra("subTitle",subTitle);
                            intent.putExtra("notes",notes);
                            intent.putExtra("time",time);

                            startActivity(intent);

                            alertDialog.dismiss();
                        }
                    });



                    alertDialog.show();

               }
           });


            return myView;
        }}



    @Override
    protected void onPostResume() {
        super.onPostResume();
        allData(dbHelper.getallData());
        super.onPostResume();
    }



}