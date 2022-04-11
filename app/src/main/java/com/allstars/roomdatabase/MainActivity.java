package com.allstars.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.room.Index;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText t1,t2,idNo;
    AppCompatButton b1,b2,viewSingle,deleteSingle,updateSingle;
    TextView singleView;
    database db;
    UserDao userDao;
    ImageButton show,hide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        t1 = findViewById(R.id.firstName);
        t2 = findViewById(R.id.lastName);
        idNo = findViewById(R.id.idNo);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        viewSingle = findViewById(R.id.viewSingle);
        singleView = findViewById(R.id.singleView);
        deleteSingle = findViewById(R.id.deleteSingle);
        updateSingle = findViewById(R.id.updateSingle);
        show = findViewById(R.id.show);
        hide = findViewById(R.id.hide);


        idNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if(idNo.getText().toString().isEmpty()){
                        singleView.setText("");
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        db = Room.databaseBuilder(getApplicationContext(),
                database.class, "room_db").allowMainThreadQueries().build();
        userDao = db.userDao();

        b1.setOnClickListener(view -> {
            if(isValid()) {
                userDao.insert(new User(t1.getText().toString(), t2.getText().toString()));
                Toast.makeText(MainActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
            }
            t1.setText("");
            t2.setText("");
        });

        b2.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(),fetchData.class)));

        viewSingle.setOnClickListener(view -> {
            if(!isValidId()){
                Toast.makeText(MainActivity.this, "Invalid ID", Toast.LENGTH_SHORT).show();
                return;
            }
            int id = Integer.parseInt(String.valueOf(idNo.getText()));
            List<User> users = userDao.getallusers();
            String str= "";

            for(User user: users){
                if(id == user.getUid()){
                    singleView.setText("id: " + id + " username: "+user.firstName);
                    return;
                }

            }
            Toast.makeText(MainActivity.this, "Invalid ID", Toast.LENGTH_SHORT).show();
        });

        deleteSingle.setOnClickListener(view -> {

            if(!isValidId()){
                Toast.makeText(MainActivity.this, "Invalid ID", Toast.LENGTH_SHORT).show();
                return;
            }
            int id = Integer.parseInt(String.valueOf(idNo.getText()));
            List<User> users = userDao.getallusers();

            for(User user: users){
                if(id == user.getUid()){

                    userDao.deleteById(user.getUid());
                    Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    idNo.setText(" ");
                    return;

                }

            }

            Toast.makeText(MainActivity.this, "Invalid ID", Toast.LENGTH_SHORT).show();

        });

        updateSingle.setOnClickListener(view -> {


            if(!isValidId()){
                Toast.makeText(MainActivity.this, "Invalid ID", Toast.LENGTH_SHORT).show();
                return;
            }
            int id = Integer.parseInt(String.valueOf(idNo.getText()));
            List<User> users = userDao.getallusers();

            for(User user: users){
                if(id == user.getUid()){

                         if(isValid()) {
                             userDao.update(t1.getText().toString(), t2.getText().toString(), user.getUid());
                             t1.setText("");
                             t2.setText("");
                             idNo.setText("");
                             Toast.makeText(MainActivity.this, "updated Successfully", Toast.LENGTH_SHORT).show();
                             return;
                         }else{
                             return;
                         }

                }

            }
            Toast.makeText(MainActivity.this, "Invalid ID", Toast.LENGTH_SHORT).show();

        });
    }

    private boolean isValid() {
        if(t1.getText().toString().isEmpty() || t2.getText().toString().isEmpty()){

            if(t1.getText().toString().isEmpty()){
                t1.setError("Required");

            }if(t2.getText().toString().isEmpty()){
                t2.setError("Required");
            }
            return false;

        }return true;

    }

    private boolean isValidId(){
        if(idNo.getText().toString().isEmpty()){
            idNo.setError("Required");
            return false;
        }
        return true;
    }


    public void show(View view) {
        show.setVisibility(View.INVISIBLE);
        hide.setVisibility(View.VISIBLE);
        t2.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    public void hide(View view) {
        hide.setVisibility(View.INVISIBLE);
        show.setVisibility(View.VISIBLE);
        t2.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
    }
}