package com.example.finalexam07590548;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class UserManager extends SQLiteOpenHelper implements UserManagerHelper {

    public static final String TAG = UserManager.class.getSimpleName();
    private SQLiteDatabase mDatabase;

    public UserManager(Context context) {
        super(context, UserManagerHelper.DATABASE_NAME, null, UserManagerHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_USER = String.format("CREATE TABLE %s " +
                        "(%s TEXT, %s TEXT)" ,
                User.TABLE,
                User.Column.USERNAME,
                User.Column.PASSWORD
        );

        db.execSQL(CREATE_TABLE_USER);

        Log.i(TAG, CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_USER = "DROP TABLE IF EXISTS " + UserManagerHelper.DATABASE_VERSION;

        db.execSQL(DROP_USER);

        Log.i(TAG, DROP_USER);
        onCreate(mDatabase);
    }

    @Override
    public long registerUser(User user) {

        mDatabase = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(User.Column.USERNAME, user.getUsername());
        values.put(User.Column.PASSWORD, user.getPassword());

        long result = mDatabase.insert(User.TABLE, null, values);
        mDatabase.close();

        return result;
    }

    @Override
    public User checkUserLogin(User user) {
        mDatabase = this.getReadableDatabase();

        Cursor cursor = mDatabase.query(User.TABLE,
                null,
                User.Column.USERNAME + " = ? AND " +
                        User.Column.PASSWORD + " = ?",
                new String[]{user.getUsername(), user.getPassword()},
                null,
                null,
                null);

        User currentUser = new User();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                currentUser.setId((int) cursor.getLong(0));
                currentUser.setUsername(cursor.getString(1));
                currentUser.setPassword(cursor.getString(2));
                mDatabase.close();
                return currentUser;
            }
        }

        return null;
    }
}