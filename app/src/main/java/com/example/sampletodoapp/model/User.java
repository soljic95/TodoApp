package com.example.sampletodoapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static com.example.sampletodoapp.model.User.USER_TABLE_NAME;

@Entity(tableName = USER_TABLE_NAME)
public class User {

    public final static String USER_TABLE_NAME = "user_table";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_SURNAME = "user_surname";
    public static final String COLUMN_USER_DATE_OF_BIRTH = "user_date_of_birth";

    @ColumnInfo(name = COLUMN_USER_ID)
    @PrimaryKey(autoGenerate = true)
    long userId;

    @ColumnInfo(name = COLUMN_USER_NAME)
    String name;

    @ColumnInfo(name = COLUMN_USER_SURNAME)
    String surname;

    @ColumnInfo(name = COLUMN_USER_DATE_OF_BIRTH)
    long dateOfBirth;

    public User(String name, String surname, long dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getDateOfBirth() {
        return dateOfBirth;
    }

}
