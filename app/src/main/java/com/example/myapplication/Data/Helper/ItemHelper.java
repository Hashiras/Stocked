package com.example.myapplication.Data.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.Data.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemHelper extends SQLiteOpenHelper {
    private static ItemHelper instance;

    public ItemHelper(@Nullable Context context) {
        super(context, "db_Inventory", null, 1);
    }

    public static ItemHelper instance(Context context) {
        if (instance == null)
            instance = new ItemHelper(context);
        return instance;
    }

    private final String TABLENAME = "tbl_Items";
    private final SQLiteDatabase db = getWritableDatabase();
    private final SQLiteDatabase dbr = getReadableDatabase();

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLENAME + "(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "description TEXT," +
                    "quantity INTEGER," +
                    "ownerID INTEGER," +
                    "archived INTEGER)");
            Log.i(TABLENAME + " Logger", "Successfully created the table");
        } catch (SQLiteException e) {
            Log.e(TABLENAME + " Logger", "Unable to create the table", e.getCause());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL("DROP TABLE " + TABLENAME);
            Log.i(TABLENAME + " Logger", "Successfully inserted into the table");
            onCreate(db);
        } catch (SQLiteException e) {
            Log.e("DBHelper", "Unable to upgrade the table", e.getCause());
        }
    }

    public void insert(Item data) {
        try {
            db.insert(TABLENAME, null, prepareData(data));
            Log.i(TABLENAME + " Logger", "Successfully inserted into the table");
        } catch (SQLiteException e) {
            Log.e(TABLENAME + " Logger", "Unable to insert into the table", e.getCause());
        }
    }

    public List<Item> get() {
        List<Item> list = new ArrayList<>();
        try {
            Cursor cursor = dbr.rawQuery("SELECT ID, name, description, quantity, ownerID FROM " + TABLENAME + " WHERE archived = 0",
                    null);
            while (cursor.moveToNext())
                list.add(prepareData(cursor));
            Log.i(TABLENAME + " Logger", "Successfully retrieved data from the table");
        } catch (SQLiteException e) {
            Log.e(TABLENAME + " Logger", "Unable to retrieved data from the table", e.getCause());
        }
        return list;
    }

    public Item get(int ID) {
        Item data = null;
        try {
            Cursor cursor = dbr.rawQuery("SELECT ID, name, description, quantity, ownerID FROM " + TABLENAME + " WHERE ID = ?",
                    new String[]{String.valueOf(ID)});
            while (cursor.moveToNext())
                data = prepareData(cursor);
            Log.i(TABLENAME + " Logger", "Successfully retrieved data from the table");
        } catch (SQLiteException e) {
            Log.e(TABLENAME + " Logger", "Unable to retrieved data from the table", e.getCause());
        }
        return data;
    }

    public void update(Item data) {
        try {
            db.update(TABLENAME, prepareData(data), "ID = ?", new String[]{String.valueOf(data.getID())});
            Log.i(TABLENAME + " Logger", "Successfully update data from the table");
        } catch (SQLiteException e) {
            Log.e(TABLENAME + " Logger", "Unable to update data from the table", e.getCause());
        }
    }

    public void remove(Item data) {
        try {
            ContentValues values = prepareData(data);
            values.put("archived", 1);
            db.update(TABLENAME, values, "ID = ?", new String[]{String.valueOf(data.getID())});
            Log.i(TABLENAME + " Logger", "Successfully removed data from the table");
        } catch (SQLiteException e) {
            Log.e(TABLENAME + " Logger", "Unable to removed data from the table", e.getCause());
        }
    }

    public int getNextID() {
        int ID = 0;
        try {
            Cursor cursor = dbr.rawQuery("SELECT MAX(ID) FROM " + TABLENAME,
                    null);
            while (cursor.moveToNext())
                ID = cursor.getInt(0);
            ID++;
            Log.i(TABLENAME + " Logger", "Successfully retrieved data from the table");
        } catch (SQLiteException e) {
            Log.e(TABLENAME + " Logger", "Unable to retrieved data from the table", e.getCause());
        }
        return ID;
    }

    private ContentValues prepareData(Item data) {
        ContentValues contentValues = new ContentValues();
        if (data.getName() != null)
            contentValues.put("name", data.getName());
        if (data.getDescription() != null)
            contentValues.put("description", data.getDescription());
        if (data.getQuantity() != 0)
            contentValues.put("quantity", data.getQuantity());
        if (data.getOwnerID() != 0)
            contentValues.put("ownerID", data.getOwnerID());
        contentValues.put("archived", 0);
        return contentValues;
    }

    private Item prepareData(Cursor cursor) {
        Item item = new Item();
        item.setID(cursor.getInt(0));
        item.setName(cursor.getString(1));
        item.setDescription(cursor.getString(2));
        item.setQuantity(cursor.getInt(3));
        item.setOwnerID(cursor.getInt(4));
        return item;
    }
}
