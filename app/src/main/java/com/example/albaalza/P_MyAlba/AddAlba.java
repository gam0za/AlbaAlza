package com.example.albaalza.P_MyAlba;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albaalza.P_Main.MainActivity;
import com.example.albaalza.R;


public class AddAlba extends AppCompatActivity {

    /* activity_add_alba 변수 선언 */
    private EditText alba_name,my_pay,pay_day;
    private TextView insurance, backBtn;
    private ImageView alba_name_btn,my_pay_btn,pay_day_btn,confirm,insurance_yes,insurance_no;
    private Switch insuranceSwitch;

    private int insuranceFlag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alba);

        createView(); // view 생성 함수
        viewFuction(); // view 기능(리스너) 함수
    }

    /* view 생성 함수 */
    private void createView(){
        alba_name=(EditText)findViewById(R.id.alba_name);
        my_pay=(EditText)findViewById(R.id.my_pay);
        pay_day=(EditText)findViewById(R.id.pay_day);
        backBtn=(TextView)findViewById(R.id.backBtn);
        alba_name_btn=(ImageView)findViewById(R.id.alba_name_btn);
        my_pay_btn=(ImageView)findViewById(R.id.my_pay_btn);
        pay_day_btn=(ImageView)findViewById(R.id.pay_day_btn);
        confirm=(ImageView)findViewById(R.id.confirm);
        insurance_yes=(ImageView)findViewById(R.id.insurance_yes);
        insurance_no=(ImageView)findViewById(R.id.insurance_no);
    }

    /* view 기능(리스너) 함수 */
    private void viewFuction(){

        /*
        // 뒤로가기 버튼
        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish(); // 추후 경고 다이얼로그 추가 예정
            }
        });
        */

        // 근무지 이름
        alba_name_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alba_name.setText("");
            }
        });

        // 나의 시급
        my_pay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my_pay.setText("");
            }
        });

        // 사대 보험 가입 여부
        insurance_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insuranceFlag=1;
            }
        });
        insurance_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insuranceFlag=0;
            }
        });

        // 월급 날짜
        pay_day_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pay_day.setText("");
            }
        });


        // 알바 추가
        confirm.setOnClickListener(new View.OnClickListener() { //확인 버튼을 누르면 캘린더로 화면이동, MyAlba3Fragment에 알바 정보 전송
            @Override
            public void onClick(View view) {
                if (alba_name.getText().toString().equals("") ||
                        my_pay.getText().toString().equals("") ||
                        pay_day.getText().toString().equals("")) { // 항목 미입력 시

                    Toast.makeText(getApplicationContext(),"알바 정보를 모두 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else // 모든 항목 입력 완료 시 알바 추가 완료
                {
                    // main activity로 인텐트 보내기
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED); // Intent to MainActiviy 중복 제거
                    intent.putExtra("alba_name",alba_name.getText().toString());
                    intent.putExtra("my_pay",Integer.parseInt(my_pay.getText().toString()));
                    intent.putExtra("insurance",insuranceFlag);
                    intent.putExtra("pay_day",Integer.parseInt(pay_day.getText().toString()));
                    setResult(RESULT_OK,intent);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
