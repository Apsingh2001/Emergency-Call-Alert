package com.example.admn.blockthem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public DataBaseHelper(Context context) {

        //super(context, name, factory, version);
        super(context, "contact.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table contacttable (id integer primary key not null , number text not null , count integer)");
        this.db = db;
        Log.d("database","tableCreated");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query ="Drop table if exists "+"contacttable";
        db.execSQL(query);
        this.onCreate(db);
    }



    public void insertContact(Contact c) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "Select * from " + "contacttable";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put("id",count);
        values.put("number",c.getNumber());
        values.put("count",c.getCounter());

        long id= db.insert("contacttable",null,values);
        db.close();
        Log.d("database","numberAdded"+id);

    }

    public  void updateCounter(String number)
    {
        db=this.getReadableDatabase();
        ContentValues values= new ContentValues();
        String searchQuery ="Select id,number,count from contacttable";
        Cursor cursor = db.rawQuery(searchQuery,null);
        int dbID=0;
        String dbCount="",id="";
        if (cursor.moveToFirst())
        {
            do
            {
                String num=cursor.getString(1);
                boolean check=number.equalsIgnoreCase(num);
                Log.d("lakhan",""+check);
                if(number.equalsIgnoreCase(num))
                {
                    dbCount=cursor.getString(2);
                    dbID=cursor.getInt(0);
                    Log.d("lakhandbId",""+dbID);
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        db.close();
        db=this.getWritableDatabase();
        int updateCount=Integer.parseInt(dbCount);
        updateCount++;
        Log.d("lakhanupdatecount",""+updateCount);
        dbCount= String.valueOf(updateCount);
        Log.d("lakhandbcount",""+dbCount);
        id=String.valueOf(dbID);
        values.put("count",dbCount);
        final int rowsUpdated;
        rowsUpdated = db.update("contacttable",values,"id="+id, null);
        db.close();
        Log.d("lakhanDatabase","updated rows"+rowsUpdated);
    }



    public String returnCount(String number) {
        String counter = "";
        db = this.getReadableDatabase();
        String query = "select number,count from contacttable";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                String num = cursor.getString(0);
                if (number.equalsIgnoreCase(num)) {
                    counter = cursor.getString(1);
                    break;
                }
            } while (cursor.moveToNext());
        }
        return counter;
    }
    public boolean validateCheck(String number)
    {
        db= this.getReadableDatabase();
        String Equery = "Select number from contacttable";
        Cursor c = db.rawQuery(Equery,null);
        boolean valid =true;
        String a;
        if(c.moveToFirst())
        {
            do
            {

                String num=c.getString(0);
                if((number.trim()).equalsIgnoreCase(num.trim()))
                {
                    valid= false;
                    break;
                }
            }
            while(c.moveToNext());
        }
        return valid;
    }
    public void resetCount(String number)
    {
        db=this.getReadableDatabase();
        Log.d("lakhaninresetfn","hello");
        ContentValues values= new ContentValues();
        String searchQuery ="Select id,number,count from contacttable";
        Cursor cursor = db.rawQuery(searchQuery,null);
        int dbID=0;
        String id="";
        if (cursor.moveToFirst())
        {
            do
            {
                String num=cursor.getString(1);
                boolean check=number.equalsIgnoreCase(num);
                Log.d("lakhanresetinfn",""+check);
                if(number.equalsIgnoreCase(num))
                {
                    dbID=cursor.getInt(0);
                    Log.d("lakhanresetdbIdinfn",""+dbID);
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        db.close();
        db=this.getWritableDatabase();
        id=String.valueOf(dbID);
        values.put("count","0");
        final int rowsUpdated;
        rowsUpdated = db.update("contacttable",values,"id="+id, null);
        db.close();
        Log.d("lakhanresetDatabase","updated rows"+rowsUpdated);
    }

    public void deleteNumber(String number)
    {
        db=this.getReadableDatabase();
        ContentValues values= new ContentValues();
        String searchQuery ="Select id,number from contacttable";
        Cursor cursor = db.rawQuery(searchQuery,null);
        int dbID=0;
        String id="";
        if (cursor.moveToFirst())
        {
            do
            {
                String num=cursor.getString(1);
                boolean check=number.equalsIgnoreCase(num);
                Log.d("lakhan",""+check);
                if(number.equalsIgnoreCase(num))
                {
                    dbID=cursor.getInt(0);
                    Log.d("lakhandbId",""+dbID);
                    break;
                }
            }
            while(cursor.moveToNext());
        }
        db.close();
        Log.d("deleteNumber","inside deleteNumber");
        db=this.getWritableDatabase();
        Log.d("deleteNumber",""+number);
        int deletedItems=db.delete("contacttable","id="+String.valueOf(dbID),null);
        Log.d("deleteNumber",deletedItems+"rows deleted");
        db.close();

    }

    public List<String> returnNumberFromDatabase(){
        List<String> list = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  * FROM contacttable";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);//selectQuery,selectedArguments

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(1));//adding 2nd column data
            } while (cursor.moveToNext());
        }
        // closing connection
        cursor.close();
        db.close();
        // returning lables
        return list;
    }
}
