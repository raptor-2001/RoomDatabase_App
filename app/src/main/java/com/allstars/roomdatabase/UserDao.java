package com.allstars.roomdatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {



    @Query("SELECT * FROM user")
    List<User> getallusers();

    @Query("DELETE FROM User WHERE uid = :uid")
    void deleteById(int uid);



    @Query("UPDATE User SET first_name = :first, last_name = :last WHERE uid =:uid")
    void update(String first, String last, int uid);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... users);



}