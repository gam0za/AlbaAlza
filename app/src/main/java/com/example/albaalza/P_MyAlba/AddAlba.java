package com.example.albaalza.P_MyAlba;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.albaalza.P_Main.MainActivity;
import com.example.albaalza.P_MyAlba.Server.SearchBossPost;
import com.example.albaalza.P_MyAlba.Server.SearchBossResponse;
import com.example.albaalza.P_MyAlba.Server.SendRequestPost;
import com.example.albaalza.P_MyAlba.Server.SendRequestResponse;
import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.Server.NetworkService;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddAlba extends AppCompatActivity {

    /* activity_add_alba 변수 선언 */
    private EditText alba_name,my_pay,pay_day,edit_search_boss;
    private ImageView alba_name_btn,my_pay_btn,pay_day_btn,confirm,insurance_yes,insurance_no,search_boss;
    private SweetAlertDialog alertDialog;
    private RelativeLayout send;
    private int insuranceFlag = 0;
    private NetworkService networkService;
    SearchBossPost searchBossPost;
    SendRequestPost sendRequestPost;
    SharedPreferences sharedPreferences,sharedPreferences2;
    SharedPreferences.Editor editor;
    private String oid;
    private String wid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_alba);

        alba_name=(EditText)findViewById(R.id.alba_name);
        my_pay=(EditText)findViewById(R.id.my_pay);
        pay_day=(EditText)findViewById(R.id.pay_day);
        alba_name_btn=(ImageView)findViewById(R.id.alba_name_btn);
        my_pay_btn=(ImageView)findViewById(R.id.my_pay_btn);
        pay_day_btn=(ImageView)findViewById(R.id.pay_day_btn);
        confirm=(ImageView)findViewById(R.id.confirm);
        insurance_yes=(ImageView)findViewById(R.id.insurance_yes);
        insurance_no=(ImageView)findViewById(R.id.insurance_no);
        send=(RelativeLayout)findViewById(R.id.send);

        sharedPreferences2=getSharedPreferences("boss", Activity.MODE_PRIVATE);
        editor=sharedPreferences2.edit();

        sharedPreferences=getSharedPreferences("account", Context.MODE_PRIVATE);
        wid=sharedPreferences.getString("id","doby");


//        사장님 친추
        edit_search_boss=(EditText)findViewById(R.id.edit_search_boss);
        search_boss=(ImageView)findViewById(R.id.search_boss);

        networkService= ApplicationController.getInstance().getNetworkService();

        //사장님 친추
        /**** 추후 서버 연동 필요 *****/
        search_boss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edit_search_boss.getText().toString().equals("")){
                    ApplicationController.getInstance().makeToast("사장님 아이디를 입력해주세요");
                }else{
                    searchboss();
                    send.setVisibility(View.VISIBLE);
                    search_boss.setVisibility(View.GONE);
                }


            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendrequest();
            }
        });

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
                insuranceFlag = 1; // 사대 보험 가입 O
                Toast.makeText(getApplicationContext(),"4대보험 가입",Toast.LENGTH_SHORT).show();
            }
        });
        insurance_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insuranceFlag=0;
                Toast.makeText(getApplicationContext(),"4대보험 미가입",Toast.LENGTH_SHORT).show();
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
    /********** 사장님 검색 **********/
    public void searchboss(){
        searchBossPost=new SearchBossPost(edit_search_boss.getText().toString());
        Call<SearchBossResponse> searchBossResponseCall=networkService.searchboss(searchBossPost);
        searchBossResponseCall.enqueue(new Callback<SearchBossResponse>() {
            @Override
            public void onResponse(Call<SearchBossResponse> call, Response<SearchBossResponse> response) {
                if(response.isSuccessful()){
                    alertDialog=new SweetAlertDialog(AddAlba.this,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                    alertDialog.setTitleText("사장님 친구추가")
                            .setContentText(response.body().id+"사장님이 맞으신가요?")
                            .setCustomImage(R.drawable.albabot)
                            .show();
                    oid=response.body().id;
                }
            }

            @Override
            public void onFailure(Call<SearchBossResponse> call, Throwable t) {
                ApplicationController.getInstance().makeToast("서버상태를 확인해주세요");
            }
        });
    }

    /********** 사장님에게 친구요청 **********/
    public void sendrequest(){
        sendRequestPost=new SendRequestPost(wid,oid);
        Call<SendRequestResponse> sendRequestResponseCall=networkService.sendrequest(sendRequestPost);
        sendRequestResponseCall.enqueue(new Callback<SendRequestResponse>() {
            @Override
            public void onResponse(Call<SendRequestResponse> call, Response<SendRequestResponse> response) {
                if(response.isSuccessful()){
                    ApplicationController.getInstance().makeToast("사장님에게 친구요청을 보냈습니다:)");

//                    사장님 저장
                    editor.putString("boss",oid);
                    editor.commit();
                }
            }

            @Override
            public void onFailure(Call<SendRequestResponse> call, Throwable t) {
                ApplicationController.getInstance().makeToast("서버상태를 확인해주세요");
            }
        });
    }

}
