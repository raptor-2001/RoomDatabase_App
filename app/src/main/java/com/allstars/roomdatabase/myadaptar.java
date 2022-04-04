package com.allstars.roomdatabase;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.List;

public class myadaptar extends RecyclerView.Adapter<myadaptar.viewholder> {
    List<User> users;

    myadaptar (List<User> users){
        this.users = users;
    }


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.recId.setText(String.valueOf(users.get(position).getUid()));
        holder.recFirst.setText(users.get(position).getFirstName());
        holder.recLast.setText(users.get(position).getLastName());

        holder.delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database db = Room.databaseBuilder(holder.recFirst.getContext(),
                        database.class, "room_db").allowMainThreadQueries().build();
                UserDao userDao = db.userDao();

                userDao.deleteById(users.get(position).getUid());

                users.remove(position);

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
          return users.size();
    }

    class  viewholder extends RecyclerView.ViewHolder{

        TextView recId,recFirst,recLast;
        ImageButton delbtn;
        public viewholder(@NonNull View itemView) {
            super(itemView);

            recId = itemView.findViewById(R.id.recId);
            recFirst = itemView.findViewById(R.id.recFirst);
            recLast = itemView.findViewById(R.id.recLast);
            delbtn  =itemView.findViewById(R.id.delbtn);

        }
    }
}
