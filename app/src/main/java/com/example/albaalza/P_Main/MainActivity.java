package com.example.albaalza.P_Main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.albaalza.P_AlbaTing.AlbaTing;
import com.example.albaalza.P_Home.Information;
import com.example.albaalza.P_Labor.Advice;
import com.example.albaalza.P_MyAlba.MyAlba;
import com.example.albaalza.P_MyAlba.MyAlbaAddFragment;
import com.example.albaalza.P_MyAlba.MyAlbaDBCalculator;
import com.example.albaalza.P_MyAlba.MyAlbaDbOpenHelper;
import com.example.albaalza.P_MyPage.MyPage;
import com.example.albaalza.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int previousButton = 1;
    ImageView tab_home, tab_myalba, tab_albating, tab_advice, tab_mypage;

    Information information;
    MyAlba myAlba;
    MyAlbaAddFragment myAlbaAddFragment;
    Advice advice;
    AlbaTing albaTing;
    MyPage myPage;

    private String albaNameInSpinner = null;

    // 데이터베이스
    private MyAlbaDbOpenHelper dbHelper;
    private MyAlbaDBCalculator myAlbaDBCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createView(); // 뷰 생성 함수

        // db is opened
        dbHelper = new MyAlbaDbOpenHelper(getApplicationContext());
        dbHelper.open();

        myAlbaDBCalculator = new MyAlbaDBCalculator(dbHelper);
    }

    public void createView() {

        myAlba = new MyAlba();
        myAlbaAddFragment = new MyAlbaAddFragment();
        advice = new Advice();
        albaTing = new AlbaTing();
        myPage = new MyPage();
        information = new Information();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, information).commit();

        tab_home = (ImageView) findViewById(R.id.tab_home);
        tab_myalba = (ImageView) findViewById(R.id.tab_myalba);
        tab_albating = (ImageView) findViewById(R.id.tab_albating);
        tab_advice = (ImageView) findViewById(R.id.tab_advice);
        tab_mypage = (ImageView) findViewById(R.id.tab_mypage);

        tab_home.setOnClickListener(this);
        tab_myalba.setOnClickListener(this);
        tab_albating.setOnClickListener(this);
        tab_advice.setOnClickListener(this);
        tab_mypage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, information).commit();
                previousButton = 1;
                break;

            case R.id.tab_myalba:
                if (getAlbaNameNumber()) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, myAlba).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment, myAlbaAddFragment).commit();
                }
                previousButton = 2;
                break;

            case R.id.tab_albating:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, albaTing).commit();
                previousButton = 3;
                break;

            case R.id.tab_advice:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, advice).commit();
                previousButton = 4;
                break;

            case R.id.tab_mypage:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, myPage).commit();
                previousButton = 5;
                break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1001) { // from AddAlba
                String alba_name = data.getStringExtra("alba_name");
                int my_pay = data.getIntExtra("my_pay", -1);
                int pay_day = data.getIntExtra("pay_day", -1);
                int payment = data.getIntExtra("insuranceFlag", -1);

                // 데이터베이스 insert
                dbHelper.insertColumn_myAlba(alba_name, my_pay, payment, pay_day);

                setAlbaNameInSpinner(alba_name);

                // sending signal to MyAlba1Fragment
                BusProvider.getInstance().postQueue(new ActivityResultEvent(requestCode, resultCode, data));

                // 화면 전환
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, myAlba).commit();

            } else if (requestCode == 2001) { // from AddAlbaDay
                // sending data to MyAlba1Fragment (data: 알바 시작-종료 시간, 요일 반복 체크박스, 주별 반복 스위치)
                BusProvider.getInstance().postQueue(new ActivityResultEvent(requestCode, resultCode, data));
            } else if (requestCode == 3001) { // from ModifyAlbaDay
                // sending data to MyAlba1Fragment (data: 알바 시작-종료 시간, 요일 반복 체크박스, 주별 반복 스위치)
                BusProvider.getInstance().postQueue(new ActivityResultEvent(requestCode, resultCode, data));
            }
        }
    }

    /* Send Data Into Event Bus */
    public class ActivityResultEvent {
        private int requestCode, resultCode;
        private Intent data;

        public ActivityResultEvent(int requestCode, int resultCode, Intent data) {
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.data = data;
        }

        public int getRequestCode() {
            return requestCode;
        }

        public int getResultCode() {
            return resultCode;
        }

        public Intent getData() {
            return data;
        }
    }

    public MyAlbaDbOpenHelper getDB(){
        return dbHelper;
    }

    public MyAlbaDBCalculator getMyAlbaDBCalculator(){
        return myAlbaDBCalculator;
    }

    public String getAlbaNameInSpinner (){
        return albaNameInSpinner;
    }

    public void setAlbaNameInSpinner(String albaNameInSpinner){
        this.albaNameInSpinner = albaNameInSpinner;
    }

    public boolean getAlbaNameNumber(){

        try {
            Cursor iCursor = dbHelper.selectColumns_MYALBANAME();
            if(iCursor.getCount() > 1) {
                iCursor.moveToLast();
                String tempMYALBANAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));
                Toast.makeText(getApplicationContext(),String.valueOf(iCursor.getCount()), Toast.LENGTH_SHORT).show();
                setAlbaNameInSpinner(tempMYALBANAME);
                return true;
            }
            iCursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public MyAlba getMyAlba(){
        return myAlba;
    }

    public MyAlbaAddFragment getMyAlbaAddFragment(){
        return myAlbaAddFragment;
    }

    @Override
    public void onDestroy() {
        dbHelper.close(); // db is closed
        Toast.makeText(getApplicationContext(),"db is closed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}

