package com.aloineinc.journalapplication.localdb.model;

public class JournalModel {
    public static final String TABLE_NAME = "journals";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_JOURNAL = "journal";
    public static final String COLUMN_INTERVAL = "interval";

    private int id;
    private String journal;
    private String interval;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_JOURNAL + " TEXT,"
                    + COLUMN_INTERVAL + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public JournalModel() {
    }

    public JournalModel(int id, String journal, String interval) {
        this.id = id;
        this.journal = journal;
        this.interval = interval;
    }

    public int getId() {
        return id;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getInterval() {
        return interval;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInterval(String timestamp) {
        this.interval = interval;
    }


}
