package com.allstars.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import java.util.List;

public class fetchData extends AppCompatActivity {
    RecyclerView recview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);
        getroomdata();

    }

    public void getroomdata() {
        database db = Room.databaseBuilder(getApplicationContext(),
                database.class, "room_db").allowMainThreadQueries().build();
        UserDao userDao = db.userDao();

        recview = findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(this));

        List<User>  users = userDao.getallusers();
        myadaptar adaptar=new myadaptar(users);
        recview.setAdapter(adaptar);

    }
}