package com.example.albaalza.UI.P_Labor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by SEJIN on 2017-12-16.
 */

public class AdviceDbOpenHelper{

    private static final String DATABASE_NAME = "adviceCenterInfo12.db";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteDatabase mDB;
    private DatabaseHelper mDbHelper;
    private Context mCtx;
    private String TAG;
    private Cursor mCursor;

    // table name
    public static final String TABLENAME_CENTER = "CENTER";
    public static final String TABLENAME_CENTERINFO = "CENTERINFO";
    // key name
    public static final String KEY_ROWID = "_id"; // primary key, 0
    public static final String KEY_CITY = "city"; // 시도구, 1
    public static final String KEY_CENTER = "center"; // 관할고용센터, 2
    public static final String KEY_DEPARTMENT = "department"; // 관서명, 3
    public static final String KEY_STATE = "state"; // 시도, 4
    public static final String KEY_TEL = "tel"; // 전화번호
    public static final String KEY_FAX = "fax"; // 팩스번호
    public static final String KEY_ADDRESS= "address"; // 주소
    public static final String KEY_lATITUDE = "latitude"; // 위도
    public static final String KEY_LONGITUDE = "longitude"; // 경도


    /************** [CLASS] DatabaseHelper **************/
    private class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            /***** create table *****/
            try{
                String _CREATE = "create table if not exists " + TABLENAME_CENTER + "("
                        + KEY_ROWID + " INTEGER PRIMARY KEY autoincrement, "
                        + KEY_CITY + " text not null unique, "
                        + KEY_CENTER + " text not null, "
                        + KEY_DEPARTMENT + " text not null, "
                        + KEY_STATE + " text not null );";
                db.execSQL(_CREATE); // 테이블 생성
            } catch (Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }

            try{
                String _CREATE = "create table if not exists " + TABLENAME_CENTERINFO + "("
                        + KEY_ROWID + " INTEGER PRIMARY KEY autoincrement, "
                        + KEY_CENTER + " text not null unique, "
                        + KEY_DEPARTMENT + " text not null, "
                        + KEY_TEL + " text not null, "
                        + KEY_FAX + " text not null, "
                        + KEY_ADDRESS + " text not null, "
                        + KEY_lATITUDE + " text not null, "
                        + KEY_LONGITUDE + " text not null );";
                db.execSQL(_CREATE); // 테이블 생성
            } catch (Exception ex) {
                Log.e(TAG, "Exception in CREATE_SQL", ex);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLENAME_CENTER);
            db.execSQL("DROP TABLE IF EXISTS " + TABLENAME_CENTERINFO);
            onCreate(db); // 테이블 다시 생성
        }
    }

    /************** AdviceDbOpenHelper 함수 **************/
    public AdviceDbOpenHelper(Context context){
        this.mCtx = context;
    }

    public AdviceDbOpenHelper open() throws SQLException{
        mDbHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDbHelper.getWritableDatabase();

        /***** insert data *****/
        try {
            mDB.delete(TABLENAME_CENTER,null,null); // 테이블 내용 전체 삭제
            mDB.delete(TABLENAME_CENTERINFO,null,null); // 테이블 내용 전체 삭제
            centerList();
            centerInfoList();
        } catch (Exception ex) {
            Log.e(TAG, "Exception in insert SQL", ex);
        }

        return this;
    }

    /***** [SQL] 데이터 삽입 함수 (center table) *****/
    public void insertColumn(String city, String center, String department, String state){
        ContentValues values;
        values = new ContentValues();
        values.put(KEY_CITY, city);
        values.put(KEY_CENTER, center);
        values.put(KEY_DEPARTMENT, department);
        values.put(KEY_STATE, state);
        mDB.insert(TABLENAME_CENTER, null, values);
    }

    /***** [SQL] 데이터 삽입 함수 (center info table) *****/
    public void insertColumn2(String center, String department, String tel, String fax, String address, String latitude, String longitude){
        ContentValues values;
        values = new ContentValues();
        values.put(KEY_CENTER, center);
        values.put(KEY_DEPARTMENT, department);
        values.put(KEY_TEL, tel);
        values.put(KEY_FAX, fax);
        values.put(KEY_ADDRESS, address);
        values.put(KEY_lATITUDE, latitude);
        values.put(KEY_LONGITUDE, longitude);
        mDB.insert(TABLENAME_CENTERINFO, null, values);
    }

    public void centerList(){
        /*** 서울 ***/
        insertColumn("강남구", "서울강남고용센터", "서울강남지청", "서울");
        insertColumn("강동구", "서울동부고용센터", "서울동부지청", "서울");
        insertColumn("강북구", "서울북부고용센터", "서울북부지청", "서울");
        insertColumn("강서구", "서울강서고용센터", "서울남부지청", "서울");
        insertColumn("관악구", "서울관악고용센터", "서울관악지청", "서울");
        insertColumn("광진구", "서울동부고용센터", "서울동부지청", "서울");
        insertColumn("구로구", "서울관악고용센터", "서울관악지청", "서울");
        insertColumn("금천구", "서울관악고용센터", "서울관악지청", "서울");
        insertColumn("노원구", "서울북부고용센터", "서울북부지청", "서울");
        insertColumn("도봉구", "서울북부고용센터", "서울북부지청", "서울");
        insertColumn("동대문구", "서울고용센터", "서울지방고용노동지청", "서울");
        insertColumn("동작구", "서울관악고용센터", "서울관악지청", "서울");
        insertColumn("마포구", "서울서부고용센터", "서울서부지청", "서울");
        insertColumn("서대문구", "서울서부고용센터", "서울서부지청", "서울");
        insertColumn("서초구", "서초고용센터", "서울지방고용노동지청", "서울");
        insertColumn("성동구", "서울동부고용센터", "서울동부지청", "서울");
        insertColumn("성북구", "서울북부고용센터", "서울북부지청", "서울");
        insertColumn("송파구", "서울동부고용센터", "서울동부지청", "서울");
        insertColumn("양천구", "서울남부고용센터", "서울남부지청", "서울");
        insertColumn("영등포구", "서울남부고용센터", "서울남부지청", "서울");
        insertColumn("용산구", "서울서부고용센터", "서울서부지청", "서울");
        insertColumn("은평구", "서울서부고용센터", "서울서부지청", "서울");
        insertColumn("종로구", "서울고용센터", "서울지방고용노동지청", "서울");
        insertColumn("중구", "서울고용센터", "서울지방고용노동지청", "서울");
        insertColumn("중랑구", "서울북부고용센터", "서울북부지청", "서울");

        /*** 부산 ***/
        insertColumn("강서구", "부산북부고용센터", "부산북부지청", "부산");
        insertColumn("금정구", "부산동부고용센터", "부산동부지청", "부산");
        insertColumn("기장군", "부산동부고용센터", "부산동부지청", "부산");
        insertColumn("남구", "부산고용센터", "부산지방고용노동청", "부산");
        insertColumn("동구", "부산고용센터", "부산지방고용노동청", "부산");
        insertColumn("동래구", "부산동부고용센터", "부산동부지청", "부산");
        insertColumn("부산진구", "부산고용센터", "부산지방고용노동청", "부산");
        insertColumn("북구", "부산북부고용센터", "부산북부지청", "부산");
        insertColumn("사상구", "부산북부고용센터", "부산북부지청", "부산");
        insertColumn("사하구", "부산고용센터", "부산지방고용노동청", "부산");
        insertColumn("서구", "부산고용센터", "부산지방고용노동청", "부산");
        insertColumn("수영구", "부산동부고용센터", "부산동부지청", "부산");
        insertColumn("영도구", "부산고용센터", "부산지방고용노동청", "부산");
        insertColumn("중구", "부산고용센터", "부산지방고용노동청", "부산");
        insertColumn("연제구", "부산고용센터", "부산지방고용노동청", "부산");
        insertColumn("해운대구", "부산동부고용센터", "부산동부지청", "부산");
    }

    public void centerInfoList(){
        /*** 서울 ***/
        insertColumn2("서울고용센터", "서울지방고용노동청", "02-2004-7301", "02-6915-4015", "서울특별시 중구 장교동 삼일대로 363","37.5671460","126.9869960"); //
        insertColumn2("서초고용센터", "서울지방고용노동청", "02-580-4900", "null", "서울특별시 중구 장교동 삼일대로 363","37.5671460", "126.9869960");
        insertColumn2("서울강남고용센터", "서울강남지청", "02-3468-4794", "02-6915-4028", "서울특별시 강남구 대치동 889-13","37.4966550","127.0550760");
        insertColumn2("서울동부고용센터", "서울동부지청", "02-2142-8924", "02-6915-4049", "서울특별시 송파구 가락동 78","37.4950180","127.1226240");
        insertColumn2("서울서부고용센터", "서울서부지청", "02-2077-6000", "null", "서울특별시 마포구 도화동 마포대로 63-8","37.5422040","126.9478070");
        insertColumn2("서울남부고용센터", "서울남부지청", "02-2639-2300", "null", "서울특별시 영등포구 양평동1가 115","37.5244550","126.8915350");
        insertColumn2("서울강서고용센터", "서울남부지청", "02-2063-6700", "null", "서울특별시 강서구 가양동 1480-6","37.5628040","126.8525880");
        insertColumn2("서울북부고용센터", "서울북부지청", "02-3282-9200", "null", "서울특별시 노원구 상계동 734-2","37.6534420","127.0581820");
        insertColumn2("서울관악고용센터", "서울관악지청", "02-3282-9200", "02-6915-4101", "서울특별시 구로구 구로동 222-30","37.4833210","126.8972580");

        /*** 부산 ***/
        insertColumn2("부산고용센터", "부산지방고용노동청", "051-860-1919", "051-719-4501", "부산광역시 부산진구 양정2동 중앙대로 993","35.1780270","129.0745580");
        insertColumn2("부산동부고용센터", "부산동부지청", "051-760-7100", "null", "부산광역시 수영구 광안3동 89-18","35.1660230","129.1152040");
        insertColumn2("부산북부고용센터", "부산북부지청", "051-330-9900", "null", "대한민국 부산광역시 북구 화명3동 2270-3","35.2356020","129.0093680");
    }

    public Cursor selectColumns_TABLENAME_CENTER(){
        return mDB.query(TABLENAME_CENTER, null, null, null, null, null, null,null);
    }

    public Cursor selectColumns_TABLENAME_CENTERINFO(){
        return mDB.query(TABLENAME_CENTERINFO, null, null, null, null, null, null,null);
    }

    public void close(){
        mDB.close();
    }
}
