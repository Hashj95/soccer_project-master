package com.example.batool2.soccerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "teamManager",
    KEY_ID = "id",
    TABLE_TEAMS = "teams",
    KEY_TEAM_NAME = "name",
    KEY_ESTABLISH_YEAR = "established",
    KEY_MANAGER_NAME = "manager";

    public DBHandler(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_TEAMS + "(" + KEY_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_TEAM_NAME + " TEXT," + KEY_ESTABLISH_YEAR + " TEXT," + KEY_MANAGER_NAME + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        onCreate(db);

    }

    public void createTeam(TeamDatabase team)
    {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(KEY_TEAM_NAME,team.getTeamName());
        values.put(KEY_ESTABLISH_YEAR,team.getYearEstablished());
        values.put(KEY_MANAGER_NAME,team.getManagerName());

        db.insert(TABLE_TEAMS, null, values);
        db.close();

    }

    public TeamDatabase getTeam(int id)
    {

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_TEAMS,new String[] {KEY_ID,KEY_TEAM_NAME,KEY_ESTABLISH_YEAR,KEY_MANAGER_NAME},KEY_ID + "=?",new String[] {String.valueOf(id)},null,null,null,null);

        if(cursor != null) {

            cursor.moveToFirst();
        }

        TeamDatabase team = new TeamDatabase(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2) ,cursor.getString(3));
            db.close();
            cursor.close();
            return team;

    }

    public void deleteTeam(TeamDatabase team)
    {

        SQLiteDatabase db = getWritableDatabase();

        String[] selectionArg = {team.getTeamName()};

        db.delete(TABLE_TEAMS ,KEY_TEAM_NAME + " LIKE ?", selectionArg);

        db.close();

    }

    public int getTeamCount()
    {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TEAMS, null);

        int count = cursor.getCount();

        db.close();
        cursor.close();

        return count;
    }

    public int updateTeam(TeamDatabase team)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_TEAM_NAME,team.getTeamName());
        values.put(KEY_ESTABLISH_YEAR,team.getYearEstablished());
        values.put(KEY_MANAGER_NAME, team.getManagerName());

        return db.update(TABLE_TEAMS, values, KEY_ID + "=?", new String[]{ String.valueOf(team.getId()) });
    }

    public List<TeamDatabase> getAllTeams()
    {
        List<TeamDatabase> teams = new ArrayList<TeamDatabase>();

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TEAMS,null);

        if(cursor.moveToFirst())
        {
            do{
                TeamDatabase team = new TeamDatabase(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2) ,cursor.getString(3));
                teams.add(team);
            }
            while(cursor.moveToNext());

        }

        return teams;
    }
}
