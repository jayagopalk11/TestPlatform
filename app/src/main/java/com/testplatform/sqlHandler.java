package com.testplatform;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Jai on 4/16/2017.
 */

public class sqlHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1 ;
    private static final String DATABASE_NAME = "Playlist.db";
    public static final String TABLE_SONGS = "SONGS_TABLE";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_ARTIST = "artist";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_ALBUMART = "albumArt";
    public static final String COLUMN_ALBUMID = "albumId";

    public sqlHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String Query = "CREATE TABLE "+ TABLE_SONGS + " (" +
                COLUMN_ID + " TEXT PRIMARY KEY ," +
                COLUMN_TITLE + " TEXT ," +
                COLUMN_ARTIST + " TEXT ," +
                COLUMN_DURATION + " TEXT ," +
                COLUMN_ALBUMART + " TEXT ," +
                COLUMN_ALBUMID + " TEXT" +
                ");";

        sqLiteDatabase.execSQL(Query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_SONGS);
        onCreate(sqLiteDatabase);
    }

    public void addSongItem(ListItem songItem){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,songItem.getId());
        values.put(COLUMN_TITLE,songItem.getTitle());
        values.put(COLUMN_ARTIST,songItem.getArtist());
        values.put(COLUMN_DURATION,songItem.getDuration());
        values.put(COLUMN_ALBUMART,songItem.getAlbumArt());
        values.put(COLUMN_ALBUMID,songItem.getAlbumId());
        SQLiteDatabase db = getWritableDatabase();
        db.insertOrThrow(TABLE_SONGS, null, values);

        db.close();
    }

    public void deleteSongItem(ListItem songItem){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+ TABLE_SONGS + " WHERE "+ COLUMN_ID + "=\""+ songItem.getId() + "\"" + " AND "+
                            COLUMN_TITLE + "=\""+ songItem.getTitle() + "\"" );
    }

    public String databasePrint(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String Query = "SELECT * FROM "+ TABLE_SONGS + " WHERE 1 ";
        Log.i("in database print step:",Query);
        Log.i("in database print step:","DB Print");
        Cursor c = db.rawQuery(Query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            Log.i("in database print step:","inside while loop");
            if(c.getString(c.getColumnIndex("title"))!= null){
                dbString += c.getString(c.getColumnIndex("_id"));
                dbString += "  ";
                dbString += c.getString(c.getColumnIndex("title"));
                dbString += "  ";
                dbString += c.getString(c.getColumnIndex("artist"));
                dbString += "  ";
                dbString += c.getString(c.getColumnIndex("duration"));
                dbString += "  ";
                dbString += c.getString(c.getColumnIndex("albumArt"));
                dbString += "  ";
                dbString += c.getString(c.getColumnIndex("albumId"));
                dbString += "  ";
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

    public String getAlbumList(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String Query = "SELECT DISTINCT "+ COLUMN_ALBUMID +" FROM "+ TABLE_SONGS + " WHERE 1 ";

        Cursor c = db.rawQuery(Query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            Log.i("in database print step:","inside while loop");
            if(c.getString(c.getColumnIndex("albumId"))!= null){
                dbString += c.getString(c.getColumnIndex("albumId"));
                dbString += "  ";

                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }


}
