package com.allstars.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Index;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText t1,t2,idNo;
    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.firstName);
        t2 = findViewById(R.id.lastName);
        idNo = findViewById(R.id.idNo);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database db = Room.databaseBuilder(getApplicationContext(),
                        database.class, "room_db").allowMainThreadQueries().build();
                UserDao userDao = db.userDao();

                userDao.insert(new User(t1.getText().toString(),t2.getText().toString()));
                t1.setText("");
                t2.setText("");
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),fetchData.class));
            }
//            @Override
//            public void onClick(View view) {
//                database db = Room.databaseBuilder(getApplicationContext(),
//                        database.class, "room_db").allowMainThreadQueries().build();
//                UserDao userDao = db.userDao();
//
//                List<User> users = userDao.getallusers();
//                String str= "";
//
//                for(User user: users){
//                    str=str+"\t"+user.firstName+" "+user.lastName+"\n\n";
//                    data.setText(str);
//                }
//            }
        });
    }

//    class bgThread extends Thread{
//        public void run(){
//            super.run();
//            database db = Room.databaseBuilder(getApplicationContext(),
//                    database.class, "room_db").build();
//            UserDao userDao = db.userDao();
//            userDao.insert(new User(t1.getText().toString(),t2.getText().toString()));
//            t1.setText("");
//            t2.setText("");
//
//        }
//    }

}