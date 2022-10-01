package com.example.tripdiary;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Date;

public class DbHelper extends SQLiteOpenHelper  {

    private static final String DATABASE_NAME = "trip_diary.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "trip_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "trip_name";
    private static final String COLUMN_DESTINATION = "trip_destination";
    private static final String COLUMN_DATE = "trip_date";
    private static final String COLUMN_REQUIRE_ASSESSEMENT = "trip_require_assessement";
    private static final String COLUMN_DESCRIPTION = "trip_description";


    private Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableStament = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT, " + COLUMN_DESTINATION + " TEXT, " + COLUMN_DATE + " DATETIME," +
                "" + COLUMN_REQUIRE_ASSESSEMENT + " BOOL, " + COLUMN_DESCRIPTION + " TEXT )";
        db.execSQL(createTableStament);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);


        onCreate(db);
    }

    public void addTrip(String name, String destination,String date,Boolean require_assessement,String description ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESTINATION, destination);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_REQUIRE_ASSESSEMENT, require_assessement);
        cv.put(COLUMN_DESCRIPTION, description);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) {
            Toast.makeText(context, "INSERT FAIL. SOME THING WRONG !!", Toast.LENGTH_SHORT).show();
        } else {
            String a ="";

            if (require_assessement.equals(true)  ){
                a = "yes";
            }
            else {
                a = "no";
            }
            new AlertDialog.Builder(context).setMessage("Details entered: " +
                    "\nName: " + name +
                    "\nDestination: " + destination +
                    "\nDate: " + date +
                    "\nRisk Assessement: " +  a +
                    "\nDescription: " + description
            ).setNeutralButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
        }
    }


    public void deleteAllTrip(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM "+TABLE_NAME;
        db.execSQL(query);
        db.close();


    }
    public void deleteOneTrip(String rowId){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id = ?", new String[]{rowId});
        if (result == -1){
            Toast.makeText(context, "Delete failed !!!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Deleted Successfully !!!", Toast.LENGTH_SHORT).show();
        }

    }



    public Cursor readAllTrip() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void updateTrip(String rowId, String name, String destination,String date,Boolean require_assessement,String description ) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESTINATION, destination);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_REQUIRE_ASSESSEMENT, require_assessement);
        cv.put(COLUMN_DESCRIPTION, description);

        long result = db.update(TABLE_NAME,cv,"id = ?", new String[]{rowId});
        if (result == -1) {
        Toast.makeText(context, "UPDATE FAIL. SOME THING WRONG !!", Toast.LENGTH_SHORT).show();
        } else {
        Toast.makeText(context, "EDIT SUCESSFULLY ", Toast.LENGTH_SHORT).show();
        }

//        SQLiteDatabase db = this.getWritableDatabase();
//        String query = "UPDATE "+TABLE_NAME+"SET"+ COLUMN_NAME +"="+name +"WHERE "+COLUMN_ID +" = " + rowId ;
//
//        db.execSQL(query);
//        db.close();
//
//        Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show();


    }
}
