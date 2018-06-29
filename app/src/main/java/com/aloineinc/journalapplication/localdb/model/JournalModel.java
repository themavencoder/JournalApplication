package com.aloineinc.journalapplication.localdb.model;

public class JournalModel {
    public static final String TABLE_NAME = "journals";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_JOURNAL = "journal";
    public static final String COLUMN_INTERVAL = "timestamp";

    private int mId;
    private String mJournal;
    private String mTimestamp;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_JOURNAL + " TEXT,"
                    + COLUMN_INTERVAL + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public JournalModel() {
    }

    public JournalModel(int id, String journal, String timestamp) {
        this.mId = id;
        this.mJournal = journal;
        this.mTimestamp = timestamp;
    }

    public int getId() {
        return mId;
    }

    public String getJournal() {
        return mJournal;
    }

    public void setJournal(String journal) {
        this.mJournal = journal;
    }

    public String getInterval() {
        return mTimestamp;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public void setInterval(String timestamp) {
        this.mTimestamp = timestamp;
    }
}
