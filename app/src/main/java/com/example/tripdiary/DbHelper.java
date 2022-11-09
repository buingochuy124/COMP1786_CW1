package com.example.tripdiary;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DbHelper extends SQLiteOpenHelper {

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

        String query = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT, " + COLUMN_DESTINATION + " TEXT, " + COLUMN_DATE + " DATETIME," +
                "" + COLUMN_REQUIRE_ASSESSEMENT + " BOOL, " + COLUMN_DESCRIPTION + " TEXT )";
        String query2 = "CREATE TABLE expenses_table (id INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                "expenses_type TEXT, " +
                "expenses_amount TEXT,  " +
                "expenses_time DATETIME,     tripId INTEGER,     FOREIGN KEY(tripId)  REFERENCES trip_table(id) )";

        db.execSQL(query);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        db.execSQL("DROP TABLE IF EXISTS expenses_table");

        onCreate(db);
    }

    public void addTrip(String name, String destination, String date, Boolean require_assessement, String description) {
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
            String a = "";

            if (require_assessement.equals(true)) {
                a = "yes";
            } else {
                a = "no";
            }
            new AlertDialog.Builder(context).setMessage("Details entered: " +
                    "\nName: " + name +
                    "\nDestination: " + destination +
                    "\nDate: " + date +
                    "\nRisk Assessement: " + a +
                    "\nDescription: " + description
            ).setNeutralButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            }).show();
        }
    }


    public void deleteAllTrip() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
        db.close();


    }

    public void deleteOneTrip(String rowId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id = ?", new String[]{rowId});
        if (result == -1) {
            Toast.makeText(context, "Delete failed !!!", Toast.LENGTH_SHORT).show();
        } else {
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

    public void AddExpenseTripToDb(String type, String amount, String time, int tripId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("expenses_type", type);
        cv.put("expenses_amount", amount);
        cv.put("expenses_time", time);
        cv.put("tripId", tripId);

        long result = db.insert("expenses_table", null, cv);
        if (result == -1) {
            Toast.makeText(context, "Insert failed !!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Insert Successfully !!!", Toast.LENGTH_SHORT).show();
        }
    }


    public Cursor readAllExpensesWithTripID(int tripId) {

        SQLiteDatabase db = this.getReadableDatabase();
//        String query = "SELECT * FROM expenses_table "  ;
//        String query = "SELECT * FROM expenses_table  where tripId = " +tripId ;


        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery("SELECT * FROM expenses_table  where tripId = '" + tripId + "'", null);
        }


        return cursor;
    }


    public void updateTrip(String rowId, String name, String destination, String date, Boolean require_assessement, String description) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DESTINATION, destination);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_REQUIRE_ASSESSEMENT, require_assessement);
        cv.put(COLUMN_DESCRIPTION, description);

        long result = db.update(TABLE_NAME, cv, "id = ?", new String[]{rowId});
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

    public Cursor SearchTrip(String tripName) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NAME + " Like '%" + tripName + "%'";


//        String query = "select * from trip_table  where trip_name like ?";
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }
//    public List<Contact> search(String keyword) {
//        List<Contact> contacts = null;
//        try {
//            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
//            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + contactTable + " where " + nameColumn + " like ?", new String[] { "%" + keyword + "%" });
//            if (cursor.moveToFirst()) {
//                contacts = new ArrayList<Contact>();
//                do {
//                    Contact contact = new Contact();
//                    contact.setId(cursor.getInt(0));
//                    contact.setName(cursor.getString(1));
//                    contact.setPhone(cursor.getString(2));
//                    contact.setAddress(cursor.getString(3));
//                    contact.setEmail(cursor.getString(4));
//                    contact.setDescription(cursor.getString(5));
//                    contacts.add(contact);
//                } while (cursor.moveToNext());
//            }
//        } catch (Exception e) {
//            contacts = null;
//        }
//        return contacts;
//    }


    public void updateExpense(String rowId, String tripId, String expenseType, String expenseAmount, String expenseTime) {


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("expenses_type", expenseType);
        cv.put("expenses_amount", expenseAmount);
        cv.put("expenses_time", expenseTime);
        cv.put("tripId", tripId);

        long result = db.update("expenses_table", cv, "id = ?", new String[]{rowId});
        if (result == -1) {
            Toast.makeText(context, "UPDATE FAIL. SOME THING WRONG !!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "EDIT SUCESSFULLY ", Toast.LENGTH_SHORT).show();
        }


    }
}
