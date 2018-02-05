package com.example.albaalza.P_MyAlba;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by SEJIN on 2018-01-12.
 */

public class MyAlbaDbOpenHelper {
    private static final String DATABASE_NAME = "MyAlba11.db";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteDatabase mDB;
    private DatabaseHelper mDbHelper;
    private Context mCtx;
    private String TAG;
    private Cursor mCursor;

    // table name
    public static final String TABLENAME_MYALBA = "MYALBA";
    public static final String TABLENAME_MYALBANAME = "MYALBANAME";

    /**** TABLENAME_MYALBA's key name ****/
    public static final String KEY_ROWID = "_id"; // primary key, 0
    // public static final String KEY_MYALBANAME = "myAlbaName"; // 근무지 이름, 1
    public static final String KEY_MYPAY = "myPay"; // 시급, 2
    public static final String KEY_YEAR = "year"; // 년, 3
    public static final String KEY_MONTH = "month"; // 월, 4
    public static final String KEY_DAY = "day"; // 일, 5
    public static final String KEY_STARTHOUR = "startHour"; // 시작 시, 6
    public static final String KEY_STARTMINUTES = "startMinutes"; // 시작 분, 7
    public static final String KEY_ENDHOUR = "endHour"; // 종료 시, 8
    public static final String KEY_ENDMINUTES = "endMinutes"; // 종료 분, 9
    public static final String KEY_PAYFORDAY = "payForDay"; // 일금, 4

    /**** TTABLENAME_MYALBANAME's key name ****/
    public static final String KEY_MYALBANAME = "myAlbaName"; // 근무지 이름, 0
    public static final String KEY_MYPAY_SET = "myPaySet"; // 시급, 1
    public static final String KEY_PAYMENT = "myPayment"; // 사대보험 적용 여부, 2
    public static final String KEY_PAYDAY = "myPayday"; // 월급 날, 3

    /************** [CLASS] DatabaseHelper **************/
    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            /***** create table *****/
            try{
                String _CREATE = "create table if not exists " + TABLENAME_MYALBA + "("
                        + KEY_ROWID + " INTEGER PRIMARY KEY autoincrement, "
                        + KEY_MYALBANAME + " text not null, "
                        + KEY_MYPAY + " integer not null, "
                        + KEY_YEAR + " integer not null, "
                        + KEY_MONTH + " integer not null, "
                        + KEY_DAY + " integer not null, "
                        + KEY_STARTHOUR + " integer not null, "
                        + KEY_STARTMINUTES + " integer not null, "
                        + KEY_ENDHOUR + " integer not null, "
                        + KEY_ENDMINUTES + " integer not null, "
                        + KEY_PAYFORDAY + " integer not null );";
                db.execSQL(_CREATE); // 테이블 생성
            } catch (Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }

            try{
                String _CREATE = "create table if not exists " + TABLENAME_MYALBANAME + "("
                        + KEY_ROWID + " INTEGER PRIMARY KEY autoincrement, "
                        + KEY_MYALBANAME + " text not null, "
                        + KEY_MYPAY_SET + " integer not null, "
                        + KEY_PAYMENT + " integer not null, " // 0=false, 1=true
                        + KEY_PAYDAY + " integer not null );";
                db.execSQL(_CREATE); // 테이블 생성
            } catch (Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLENAME_MYALBA);
            db.execSQL("DROP TABLE IF EXISTS " + TABLENAME_MYALBANAME);
            onCreate(db); // 테이블 다시 생성
        }
    }

    /************** AdviceDbOpenHelper 함수 **************/
    public MyAlbaDbOpenHelper(Context context){
        this.mCtx = context;
    }

    public MyAlbaDbOpenHelper open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDbHelper.getWritableDatabase();

        return this;
    }


   /************************* MYALBANAME **************************/
   // 삽입
   public void insertColumn_myAlba(String myAlbaName, int myPaySet, int payment, int payDay){
       ContentValues values;
       values = new ContentValues();
       values.put(KEY_MYALBANAME, myAlbaName);
       values.put(KEY_MYPAY_SET, myPaySet);
       values.put(KEY_PAYMENT, payment);
       values.put(KEY_PAYDAY, payDay);
       mDB.insert(TABLENAME_MYALBANAME, null, values);
   }

   // 선택
   public Cursor selectColumns_MYALBANAME(){
       return mDB.query(TABLENAME_MYALBANAME, null, null, null, null, null, null,null);
   }

   // 삭제
   public void deleteColumn_myAlbaname(long id){
       mDB.delete(TABLENAME_MYALBANAME,KEY_ROWID + "=" + id, null);
    }

   // 수정
    public void updateColumn_MYALBANAME(long id, String myAlbaName, int myPaySet, int insurance, int payDay){
        String sql = "update " + TABLENAME_MYALBANAME + " set " + KEY_MYALBANAME + " = '" + myAlbaName + "' where " + KEY_ROWID + " = " + id + ";";
        mDB.execSQL(sql);
        sql = "update " + TABLENAME_MYALBANAME + " set " + KEY_MYPAY_SET + " = '" + myPaySet + "' where " + KEY_ROWID + " = " + id + ";";
        mDB.execSQL(sql);
        sql = "update " + TABLENAME_MYALBANAME + " set " + KEY_PAYMENT + " = '" + insurance + "' where " + KEY_ROWID + " = " + id + ";";
        mDB.execSQL(sql);
        sql = "update " + TABLENAME_MYALBANAME + " set " + KEY_PAYDAY + " = '" + payDay + "' where " + KEY_ROWID + " = " + id + ";";
        mDB.execSQL(sql);
    }





    /************************* MYALBA **************************/
    // 삽입
    public void insertColumn(String myAlbaName, int myPay, int year, int month, int day,
                             int startHour, int startMinutes, int endHour, int endMinutes, int payforday){
        ContentValues values;
        values = new ContentValues();
        values.put(KEY_MYALBANAME, myAlbaName);
        values.put(KEY_MYPAY, myPay);
        values.put(KEY_YEAR, year);
        values.put(KEY_MONTH, month);
        values.put(KEY_DAY, day);
        values.put(KEY_STARTHOUR, startHour);
        values.put(KEY_STARTMINUTES, startMinutes);
        values.put(KEY_ENDHOUR, endHour);
        values.put(KEY_ENDMINUTES, endMinutes);
        values.put(KEY_PAYFORDAY, payforday);
        mDB.insert(TABLENAME_MYALBA, null, values);
    }

    // 선택
    public Cursor selectColumns_MYALBA(){
        return mDB.query(TABLENAME_MYALBA, null, null, null, null, null, null,null);
    }

    // 삭제
    public void deleteColumn_myAlba(String myAlbaName, int year, int month, int day){
        mDB.delete(TABLENAME_MYALBA,
                KEY_MYALBANAME + "='" + myAlbaName + "' AND "
                        + KEY_YEAR + "='" + year + "' AND "
                        + KEY_MONTH + "='" + month + "' AND "
                        + KEY_DAY + "='" + day + "'", null);
    }

    // 삭제 (all)
    public void deleteAllColumn_myAlba(String myAlbaName){
        mDB.delete(TABLENAME_MYALBA,KEY_MYALBANAME + "=" + myAlbaName, null);
    }

    // 수정 (알바 이름)
    public void updateColumn_NameOfMYALBA(String newMyAlbaName, String oldMyAlbaName){
        String sql = "update " + TABLENAME_MYALBA + " set " + KEY_MYALBANAME + " = '" + newMyAlbaName + "' where " + KEY_MYALBANAME + " = " + oldMyAlbaName + ";";
        mDB.execSQL(sql);
    }

    // 수정 (all)
    public void updateColumn_MYALBA(int myPay, int year, int month, int day, // 조건
                                    int startHour, int startMinutes, int endHour, int endMinutes, int payforday){ // 바꿀 거

       ContentValues values = new ContentValues();
       values.put(KEY_MYPAY, myPay);
       values.put(KEY_STARTHOUR, startHour);
       values.put(KEY_STARTMINUTES, startMinutes);
       values.put(KEY_ENDHOUR, endHour);
       values.put(KEY_ENDMINUTES, endMinutes);
       values.put(KEY_PAYFORDAY, payforday);

       mDB.update(TABLENAME_MYALBA, values, "WHERE "
               + KEY_YEAR + "='" + year + "' and "
                + KEY_MONTH + "='" + month + "' and "
               + KEY_DAY + "='" + day + "'",  null);
    }

    public void close(){
        mDB.close();
    }
}
