package com.example.lauramarra.checkboxsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "fluxogramaDB.db";
    public static final String TABLE_FLUXOGRAMA = "fluxograma";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_CODIGO = "codigo";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_STATUS = "status";


    //We need to pass database information along to superclass
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_FLUXOGRAMA + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CODIGO + " TEXT, " +
                COLUMN_NOME + " TEXT, " +
                COLUMN_STATUS + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLUXOGRAMA);
        onCreate(db);
    }



    //Add a new row to the database
    public void addMateria(MateriaDB materia){
        ContentValues values = new ContentValues();

        values.put(COLUMN_CODIGO, materia.get_codigo());
        values.put(COLUMN_NOME, materia.get_nome());
        values.put(COLUMN_STATUS, materia.get_status());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_FLUXOGRAMA, null, values);
        db.close();
    }
    //Delete a product from the database
    public void delMateria(String codigo){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FLUXOGRAMA + " WHERE " + COLUMN_CODIGO + "=\"" + codigo + "\";");
    }

    public void update(String codigo, String status){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        //Cursor c = db.rawQuery("SELECT * FROM " + TABLE_FLUXOGRAMA+ " WHERE " + COLUMN_CODIGO + "= '" + codigo + "'", null);

        values.put(COLUMN_STATUS, status);

        //db = getWritableDatabase();
       // db.update(TABLE_FLUXOGRAMA, values, COLUMN_ID + c.getColumnIndex("codigo") , null);
        db.close();
    }

    public boolean itemExists(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FLUXOGRAMA + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);

        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("codigo")) != null) {
                return true;
            }
            c.moveToNext();
        }
        db.close();
        return false;
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FLUXOGRAMA + " WHERE 1";


        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);

        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("codigo")) != null) {
                dbString += "Codigo: ";
                dbString += c.getString(c.getColumnIndex("codigo"));
                dbString += "\n";

                dbString += "Nome: ";
                dbString += c.getString(c.getColumnIndex("nome"));
                dbString += "\n";

                dbString += "Status: ";
                dbString += c.getString(c.getColumnIndex("status"));
                dbString += "\n";
            }
            c.moveToNext();
        }
        db.close();
        return dbString;
    }

}
