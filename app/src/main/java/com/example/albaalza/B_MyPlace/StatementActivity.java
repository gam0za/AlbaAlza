package com.example.albaalza.B_MyPlace;

//급여 명세서 페이지

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albaalza.B_MyPlace.Server.StatementPost;
import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.Server.NetworkService;

public class StatementActivity extends AppCompatActivity {

    String wid; //알바생 이름
    String oid; //사장님 이름

    //    총 급여
    public TextView total4; //총 급여

    //    세금
    public TextView total3; //총 세금
    public TextView fourP; //4대보험 가입 여부

    //    추가급여
    public TextView total2; //총 추가 급여
    public TextView nightA; //야간 수당
    public TextView weeklyA; //주휴 수당

    //    기본급여
    public TextView total1; //총 기본 급여
    public TextView hours; //근무 시간
    public TextView wage; //시급


    //    근무 시작종료 일자
    public TextView end_day; //마감 일
    public TextView end_month; //마감 달
    public TextView end_year; //마감 년

    public TextView start_day;//시작 일
    public TextView start_month;//시작 달
    public TextView start_year;//시작 년


    SharedPreferences sharedPreferences;
    NetworkService networkService;
    StatementPost statementPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statement);
        networkService= ApplicationController.getInstance().getNetworkService();

        total4=(TextView)findViewById(R.id.total4);
        total3=(TextView)findViewById(R.id.total3);
        fourP=(TextView)findViewById(R.id.fourP);
        total2=(TextView)findViewById(R.id.total2);
        nightA=(TextView)findViewById(R.id.nightA);
        weeklyA=(TextView)findViewById(R.id.weeklyA);
        total1=(TextView)findViewById(R.id.total1);
        hours=(TextView)findViewById(R.id.hours);
        wage=(TextView)findViewById(R.id.wage);
        end_day=(TextView)findViewById(R.id.end_day);
        end_month=(TextView)findViewById(R.id.end_month);
        end_year=(TextView)findViewById(R.id.end_year);
        start_day=(TextView)findViewById(R.id.start_day);
        start_month=(TextView)findViewById(R.id.start_month);
        start_year=(TextView)findViewById(R.id.start_year);

        sharedPreferences=getSharedPreferences("account", Context.MODE_PRIVATE);
        oid=sharedPreferences.getString("id","도비"); //사장님 아이디

        Intent intent= getIntent();
        wid=intent.getStringExtra("albaname"); //알바생 아이디

        ApplicationController.getInstance().makeToast(oid+", "+wid);

    }

    public void myliststatement(String oid, String wid){

    }

}
