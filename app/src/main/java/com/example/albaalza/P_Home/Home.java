package com.example.albaalza.P_Home;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albaalza.P_Main.MainActivity;
import com.example.albaalza.P_MyAlba.MyAlbaDBCalculator;
import com.example.albaalza.P_MyAlba.MyAlbaDbOpenHelper;
import com.example.albaalza.R;
import com.github.lzyzsd.circleprogress.CircleProgress;

import java.text.DecimalFormat;
import java.util.Calendar;


public class Home extends Fragment {

    private CircleProgress circleProgress;
    private TextView d_day,user_name,albaname;
    private int progress,pay_day,more,month,date;
    private String alba_name;
    MyAlbaDbOpenHelper dbHelper;
    MyAlbaDBCalculator myAlbaDBCalculator;

    public Home(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_information, container, false);

        circleProgress=(CircleProgress)view.findViewById(R.id.circle_progress);
        d_day=(TextView)view.findViewById(R.id.d_day);
        user_name=(TextView)view.findViewById(R.id.user_name);
        albaname=(TextView)view.findViewById(R.id.albaname);


        // db is opened
        try{
            dbHelper = ((MainActivity)getActivity()).getDB(); // 메인액티비티로 부터 db를 얻어옴
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        setInformation();//급여 날짜와 알바 이름 가져오기

        albaname.setText(alba_name);//알바 이름 불러오기

        //현재 날짜 가져오기 시간 분 까지 가능
        Calendar rightNow = Calendar.getInstance();// 날짜 불러오는 함수
        int year = rightNow.get(Calendar.YEAR) % 100;// 100을 나눠서 년도표시를 2009->9지만
        // decimal포멧으로 09로 표현
        month = rightNow.get(Calendar.MONTH);// 달
        date = rightNow.get(Calendar.DATE);// 일

        more=31;

//      월별 마지막 일 계산
        if(month==2){
            more=28;
        }else if(month==4||month==6||month==9||month==11){
            more=30;
        }

        progress=pay_day-date;
        Log.d("more",String.valueOf(more));

        if(progress<0){
            progress=more+progress;
            Log.d("progress",String.valueOf(progress));
        }

        d_day.setText(String.valueOf(progress));//급여일까지 남은 일 수

        circleProgress.setProgress(date);//circleProgress 근무 일수 비율

        return view;
    }

//  db에서 불러온 값 세팅
    public void setInformation(){
        try {
            Log.d("setInfo","SUCCESS");
            Cursor iCursor = dbHelper.selectColumns_MYALBANAME();
            iCursor.moveToFirst();
            while (iCursor.moveToNext()) {

                alba_name = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));
                pay_day = iCursor.getInt(iCursor.getColumnIndex("myPayday"));


            }
            iCursor.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}