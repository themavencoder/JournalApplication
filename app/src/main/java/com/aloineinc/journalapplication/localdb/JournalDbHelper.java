package com.aloineinc.journalapplication.localdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aloineinc.journalapplication.localdb.model.JournalModel;

import java.util.ArrayList;
import java.util.List;

public class JournalDbHelper extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "journals_db";


    public JournalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create journals table
        db.execSQL(JournalModel.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + JournalModel.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertJournal(String journal) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(JournalModel.COLUMN_JOURNAL, journal);

        // insert row
        long id = db.insert(JournalModel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public JournalModel getJournal(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(JournalModel.TABLE_NAME,
                new String[]{JournalModel.COLUMN_ID, JournalModel.COLUMN_JOURNAL, JournalModel.COLUMN_INTERVAL},
                JournalModel.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare journal object
        JournalModel journal = new JournalModel(
                cursor.getInt(cursor.getColumnIndex(JournalModel.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(JournalModel.COLUMN_JOURNAL)),
                cursor.getString(cursor.getColumnIndex(JournalModel.COLUMN_INTERVAL)));

        // close the db connection
        cursor.close();

        return journal;
    }

    public List<JournalModel> getAllJournals() {
        List<JournalModel> journals = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + JournalModel.TABLE_NAME + " ORDER BY " +
                JournalModel.COLUMN_INTERVAL + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                JournalModel journal = new JournalModel();
                journal.setId(cursor.getInt(cursor.getColumnIndex(JournalModel.COLUMN_ID)));
                journal.setJournal(cursor.getString(cursor.getColumnIndex(JournalModel.COLUMN_JOURNAL)));
                journal.setInterval(cursor.getString(cursor.getColumnIndex(JournalModel.COLUMN_INTERVAL)));

                journals.add(journal);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return journals list
        return journals;
    }

    public int getJournalsCount() {
        String countQuery = "SELECT  * FROM " + JournalModel.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateJournal(JournalModel journal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(JournalModel.COLUMN_JOURNAL, journal.getJournal());

        // updating row
        return db.update(JournalModel.TABLE_NAME, values, JournalModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(journal.getId())});
    }

    public void deleteJournal(JournalModel journal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(JournalModel.TABLE_NAME, JournalModel.COLUMN_ID + " = ?",
                new String[]{String.valueOf(journal.getId())});
        db.close();
    }


}
