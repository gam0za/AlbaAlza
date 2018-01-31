package com.example.albaalza.P_MyAlba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.albaalza.P_Main.MainActivity;
import com.example.albaalza.R;

public class ModifyAlbaDay extends AppCompatActivity {

    // view 객체
    Button backBtn, sendBtn;
    TimePicker timePicker; // false=시작시간, true=종료시간
    boolean timePicker_flag;
    LinearLayout startTimeLayout, endTimeLayout;
    TextView Text_start_hour, Text_start_minute, Text_start_amORpm;
    TextView Text_end_hour, Text_end_minute, Text_end_amORpm;
    EditText edit_my_pay;

    // Received Intent 변수
    Intent intentFromMyAlbaCalender;
    int start_hour, start_minute, end_hour, end_minute; // 24시 기준
    int my_pay;

    // 데이터베이스
    MyAlbaDbOpenHelper dbHelper;
    String albaNameInSpinner = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_alba_day);

        createView(); // 뷰 객체 생성 함수
        functionView(); // 뷰 객체 기능 함수
    }

    private void createView(){

        //view 객체 생성
        backBtn = (Button)findViewById(R.id.backBtn);
        sendBtn = (Button)findViewById(R.id.sendBtn);

        timePicker = (TimePicker)findViewById(R.id.timePicker);
        startTimeLayout = (LinearLayout)findViewById(R.id.startTimeLayout);
        endTimeLayout = (LinearLayout)findViewById(R.id.endTimeLayout);
        Text_start_hour = (TextView)findViewById(R.id.start_hour);
        Text_start_minute = (TextView)findViewById(R.id.start_minute);
        Text_start_amORpm = (TextView)findViewById(R.id.start_amORpm);
        Text_end_hour = (TextView)findViewById(R.id.end_hour);
        Text_end_minute = (TextView)findViewById(R.id.end_minute);
        Text_end_amORpm = (TextView)findViewById(R.id.end_amORpm);

        edit_my_pay = (EditText)findViewById(R.id.edit_my_pay);

        // 인텐트 받기
        intentFromMyAlbaCalender = new Intent();
        intentFromMyAlbaCalender = getIntent();

        start_hour=intentFromMyAlbaCalender.getIntExtra("start_hour",-1);
        start_minute=intentFromMyAlbaCalender.getIntExtra("start_minute",-1);
        end_hour=intentFromMyAlbaCalender.getIntExtra("end_hour",-1);
        end_minute=intentFromMyAlbaCalender.getIntExtra("end_minute",-1);

        my_pay=intentFromMyAlbaCalender.getIntExtra("my_pay",-1);
        edit_my_pay.setText(String.valueOf(my_pay));
    }

    private void functionView(){

        //back button
        backBtn.setText("<");
        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //timepicker 보이기
        startTimeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(timePicker.getVisibility()==View.GONE) {
                    timePicker_flag = false;
                    timePicker.setVisibility(View.VISIBLE);
                }
                else
                    timePicker.setVisibility(View.GONE);
            }
        });

        endTimeLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(timePicker.getVisibility()==View.GONE) {
                    timePicker_flag = true;
                    timePicker.setVisibility(View.VISIBLE);
                }
                else
                    timePicker.setVisibility(View.GONE);
            }
        });

        //timepicker
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener(){
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int min) {

                if(timePicker_flag == false){ // 시작시간 timepicker일 때
                    start_hour=hour; start_minute=min;
                    if(start_hour<12){
                        Text_start_hour.setText(String.valueOf(start_hour));
                        Text_start_amORpm.setText("AM");
                    }
                    else{
                        Text_start_hour.setText(String.valueOf(start_hour-12));
                        Text_start_amORpm.setText("PM");
                    }
                    Text_start_minute.setText(String.valueOf(start_minute));
                }
                else { // 종료시간 timepicker일 때
                    end_hour=hour; end_minute=min;
                    if(end_hour<12){
                        Text_end_hour.setText(String.valueOf(end_hour));
                        Text_end_amORpm.setText("AM");
                    }
                    else{
                        Text_end_hour.setText(String.valueOf(end_hour-12));
                        Text_end_amORpm.setText("PM");
                    }
                    Text_end_minute.setText(String.valueOf(end_minute));
                }
            }
        });

        //send button
        sendBtn.setText("send");
        sendBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                // main activity로 인텐트 보내기
                Intent intent = new Intent(getApplication(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED); // Intent to MainActiviy 중복 제거

                int myPay = Integer.parseInt(edit_my_pay.getText().toString());

                intent.putExtra("start_hour",start_hour);
                intent.putExtra("start_minute",start_minute);
                intent.putExtra("end_hour",end_hour);
                intent.putExtra("end_minute",end_minute);
                intent.putExtra("my_pay",Integer.parseInt(edit_my_pay.getText().toString()));

                setResult(RESULT_OK,intent);
                startActivity(intent);
                finish();
            }
        });
    }

}
