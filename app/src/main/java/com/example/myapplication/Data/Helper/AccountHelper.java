package com.example.myapplication.Data.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.Data.Account;
import com.example.myapplication.Data.Enums.AccountEnum;

import java.util.ArrayList;
import java.util.List;

public class AccountHelper extends SQLiteOpenHelper {
    private static AccountHelper instance;

    public AccountHelper(@Nullable Context context) {
        super(context, "db_Inventory", null, 1);
    }

    public static AccountHelper instance(Context context){
        if(instance == null)
            instance = new AccountHelper(context);
        return instance;
    }

    private final String TABLENAME = "tbl_Accounts";
    private final SQLiteDatabase db = getWritableDatabase();
    private final SQLiteDatabase dbr = getReadableDatabase();

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLENAME + "(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "fullName TEXT," +
                    "email TEXT," +
                    "type TEXT," +
                    "credentialID INTEGER," +
                    "archived INTEGER)");
            Log.i(TABLENAME + " Logger", "Successfully created the table");
        }catch (SQLiteException e){
            Log.e(TABLENAME + " Logger", "Unable to create the table", e.getCause());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL("DROP TABLE " + TABLENAME);
            Log.i(TABLENAME + " Logger", "Successfully inserted into the table");
            onCreate(db);
        }catch (SQLiteException e){
            Log.e("DBHelper", "Unable to upgrade the table", e.getCause());
        }
    }

    public void insert(Account data){
        try{
            db.insert(TABLENAME, null, prepareData(data));
            Log.i(TABLENAME + " Logger", "Successfully inserted into the table");
        }catch (SQLiteException e){
            Log.e(TABLENAME + " Logger", "Unable to insert into the table", e.getCause());
        }
    }

    public List<Account> get(){
        List<Account> list = new ArrayList<>();
        try{
            Cursor cursor = dbr.rawQuery("SELECT ID, fullName, email, type, credentialID FROM " + TABLENAME + " WHERE archived = 0",
                    null);
            while (cursor.moveToNext())
                list.add(prepareData(cursor));
            Log.i(TABLENAME + " Logger", "Successfully retrieved data from the table");
        }catch (SQLiteException e){
            Log.e(TABLENAME + " Logger", "Unable to retrieved data from the table", e.getCause());
        }
        return list;
    }

    public Account get(int ID){
        Account data = null;
        try{
            Cursor cursor = dbr.rawQuery("SELECT ID, fullName, email, type, credentialID FROM " + TABLENAME + " WHERE ID = " + ID,
                    null);
            while (cursor.moveToNext())
                data = prepareData(cursor);
            Log.i(TABLENAME + " Logger", "Successfully retrieved data from the table");
        }catch (SQLiteException e){
            Log.e(TABLENAME + " Logger", "Unable to retrieved data from the table", e.getCause());
        }
        return data;
    }

    public void update(Account data){
        try{
            db.update(TABLENAME, prepareData(data), "ID = ?", new String[]{String.valueOf(data.getID())});
            Log.i(TABLENAME + " Logger", "Successfully update data from the table");
        }catch (SQLiteException e){
            Log.e(TABLENAME + " Logger", "Unable to update data from the table", e.getCause());
        }
    }

    public void remove(Account data){
        try{
            ContentValues values = prepareData(data);
            values.put("archived", 1);
            db.update(TABLENAME, values, "ID = ?", new String[]{String.valueOf(data.getID())});
            Log.i(TABLENAME + " Logger", "Successfully removed data from the table");
        }catch (SQLiteException e){
            Log.e(TABLENAME + " Logger", "Unable to removed data from the table", e.getCause());
        }
    }

    public int getNextID(){
        int ID = 0;
        try{
            Cursor cursor = dbr.rawQuery("SELECT MAX(ID) FROM " + TABLENAME,
                    null);
            while (cursor.moveToNext())
                ID = cursor.getInt(0);
            ID++;
            Log.i(TABLENAME + " Logger", "Successfully retrieved data from the table");
        }catch (SQLiteException e){
            Log.e(TABLENAME + " Logger", "Unable to retrieved data from the table", e.getCause());
        }
        return ID;
    }

    private ContentValues prepareData(Account data){
        ContentValues contentValues = new ContentValues();
        if(data.getFullName() != null)
            contentValues.put("fullName", data.getFullName());
        if(data.getEmail() != null)
            contentValues.put("email", data.getEmail());
        if(data.getType() != null)
            contentValues.put("type", data.getType().toString());
        if(data.getCredentialID() != 0)
            contentValues.put("credentialID", data.getCredentialID());
        contentValues.put("archived", 0);
        return contentValues;
    }

    private Account prepareData(Cursor cursor){
        Account account = new Account();
        account.setID(cursor.getInt(0));
        account.setFullName(cursor.getString(1));
        account.setEmail(cursor.getString(2));
        if(cursor.getString(3).equalsIgnoreCase("USER"))
            account.setType(AccountEnum.USER);
        else
            account.setType(AccountEnum.ADMIN);
        account.setCredentialID(cursor.getInt(4));
        return account;
    }
}
