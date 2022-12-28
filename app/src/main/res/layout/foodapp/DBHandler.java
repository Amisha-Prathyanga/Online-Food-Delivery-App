
package com.example.foodapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DB_NAME = "Instania.db";
    private static final String TABLE_NAME = "Cdmodel";

    //Columns Names
    private static final String ID = "id";
    private static final String CARDNUMBER = " CardNumber";
    private static final String CARDNAME = "CardName";
    private static final String MONTH = "Month";
    private static final String YEAR = "Year";
    private static final String CVV = "cvv";
    private static final String USERID = "userid";


    Context context;

    public DBHandler(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

       //User Register Table
        String DbEntries =
                "CREATE TABLE " +UserTable.Users.USER_TABLE_NAME+ " (" +
                        UserTable.Users.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        UserTable.Users.COLUMN_FULL_NAME + " TEXT," +
                        UserTable.Users.COLUMN_EMAIL + " TEXT,"  +
                        UserTable.Users.COLUMN_BIRTHDATE + " DATE," +
                        UserTable.Users.COLUMN_MOBILE + " TEXT," +
                        UserTable.Users.COLUMN_PASSWORD + " TEXT," +
                        UserTable.Users.COLUMN_REG_DATE + " DATE, " +
                        UserTable.Users.COLUMN_TYPE + " TEXT)";

        sqLiteDatabase.execSQL(DbEntries);

        //User Card Details Table
        String CardDbEntries =
                "CREATE TABLE " +TABLE_NAME+ " ("
                    +ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +CARDNUMBER+ " TEXT,"
                    +CARDNAME+ " TEXT,"
                    +MONTH+ " TEXT,"
                    +YEAR+ " TEXT,"
                    +CVV+ " TEXT,"
                        +USERID+ " TEXT)";

        sqLiteDatabase.execSQL(CardDbEntries);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean registerUser(UserModel userModel){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(UserTable.Users.COLUMN_FULL_NAME, userModel.getFullName());
        contentValues.put(UserTable.Users.COLUMN_EMAIL, userModel.getEmail());
        contentValues.put(UserTable.Users.COLUMN_MOBILE, userModel.getMobile());
        contentValues.put(UserTable.Users.COLUMN_MOBILE, userModel.getMobile());
        contentValues.put(UserTable.Users.COLUMN_BIRTHDATE, userModel.getBrithDate());
        contentValues.put(UserTable.Users.COLUMN_REG_DATE, userModel.getRegDate());
        contentValues.put(UserTable.Users.COLUMN_PASSWORD, userModel.getPassword());
        contentValues.put(UserTable.Users.COLUMN_TYPE, userModel.getType());

        long result = sqLiteDatabase.insert(UserTable.Users.USER_TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();

        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean checkUsername(String username){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Users where "+UserTable.Users.COLUMN_EMAIL+" = ?", new String[] {username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean login(String username, String password){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Users where Email = ? and Password = ?", new String[] {username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public UserModel getLoggedUser(String username){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(UserTable.Users.USER_TABLE_NAME, new String[]{UserTable.Users.ID, UserTable.Users.COLUMN_FULL_NAME, UserTable.Users.COLUMN_EMAIL, UserTable.Users.COLUMN_MOBILE, UserTable.Users.COLUMN_BIRTHDATE, UserTable.Users.COLUMN_PASSWORD, UserTable.Users.COLUMN_TYPE}, UserTable.Users.COLUMN_EMAIL + "= ?", new String[]{username}, null, null, null);
        UserModel userModel;

        if(cursor != null) {
            cursor.moveToFirst();
            userModel = new UserModel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6));
            return userModel;
        }
        else
            return null;
    }


    public int updateUser(UserModel userModel){
       SQLiteDatabase sqLiteDatabase = getWritableDatabase();
       ContentValues contentValues = new ContentValues();

       contentValues.put(UserTable.Users.COLUMN_FULL_NAME, userModel.getFullName());
       contentValues.put(UserTable.Users.COLUMN_EMAIL, userModel.getEmail());
       contentValues.put(UserTable.Users.COLUMN_BIRTHDATE, userModel.getBrithDate());
       contentValues.put(UserTable.Users.COLUMN_MOBILE, userModel.getMobile());

       int res = sqLiteDatabase.update(UserTable.Users.USER_TABLE_NAME, contentValues, UserTable.Users.ID +"= ?",new String[]{String.valueOf(userModel.getId())});
       sqLiteDatabase.close();

       return res;
    }

    public boolean checkPasswordWithID(int id, String currentPassword){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String id1  = Integer.toString(id);
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Users where ID = ? and Password = ?", new String[]{id1, currentPassword});
       // sqLiteDatabase.close();

        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean changePassword(int id, String newPassword){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String id1  = Integer.toString(id);
        Cursor cursor = sqLiteDatabase.rawQuery("update Users set Password = ? where ID = ?", new String[]{newPassword, id1});
        sqLiteDatabase.close();

        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean deleteUser(int id){
        String id1  = Integer.toString(id);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("delete from Users where ID = ?", new String[]{id1});
        //sqLiteDatabase.close();

        if (cursor.getCount()>0)
            return true;
        else
            return false;


    }

    //insert card details
    public boolean cardsDetail(Cdmodel cdmodel){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(CARDNUMBER,cdmodel.getCardNumber());
        contentValues.put(CARDNAME,cdmodel.getCardName());
        contentValues.put(MONTH,cdmodel.getMonth());
        contentValues.put(YEAR,cdmodel.getYear());
        contentValues.put(CVV,cdmodel.getCvv());
        contentValues.put(USERID, cdmodel.getUserid());

    //save to table
        long res =  sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
    //close database
        sqLiteDatabase.close();

        if (res == 1)
            return true;
        else
            return false;
    }


    public Cdmodel getCardDetails(int id){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String id1 = String.valueOf(id);

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{ID, CARDNUMBER,CARDNAME,MONTH,YEAR,CVV}, USERID + " = ?", new String[]{id1}, null,null, null);
        Cdmodel cdmodel;

        if(cursor != null){
            cursor.moveToFirst();
            cdmodel = new Cdmodel(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5));
            return cdmodel;
        }
        else
            return null;
    }

    public int updateCardDetails(Cdmodel cdmodel){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(CARDNUMBER, cdmodel.getCardNumber());
        contentValues.put(CARDNAME, cdmodel.getCardName());
        contentValues.put(MONTH, cdmodel.getMonth());
        contentValues.put(YEAR, cdmodel.getYear());
        contentValues.put(CVV, cdmodel.getCvv());

        int res = sqLiteDatabase.update(TABLE_NAME,contentValues,ID+"= ?",new String[]{String.valueOf(cdmodel.getId())});
        sqLiteDatabase.close();

        return res;
    }

    public boolean deleteCardDetails(int id){
        String id1  = Integer.toString(id);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("delete from Cdmodel where ID = ?", new String[]{id1});

        if (cursor.getCount()>0)
            return true;
        else
            return false;

    }



}