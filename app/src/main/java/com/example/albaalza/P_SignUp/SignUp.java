package com.example.albaalza.P_SignUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albaalza.P_Login.Login;
import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.Server.NetworkService;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUp extends AppCompatActivity {
   private ImageView signup_check,signup_actor;
   private EditText signup_name,signup_id,signup_password,signup_password_check;
   String type;
   NetworkService networkService;
   SignPost signPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        type=getIntent().getStringExtra("actor");  //SelectActor 로 부터 받은 계정 종류
        Log.d("type",type);
        signup_check=(ImageView)findViewById(R.id.signup_check);
        signup_actor=(ImageView)findViewById(R.id.signup_actor);
        signup_name=(EditText)findViewById(R.id.signup_name);
        signup_id=(EditText)findViewById(R.id.signup_id);
        signup_password=(EditText)findViewById(R.id.signup_password);
        signup_password_check=(EditText)findViewById(R.id.signup_password_check);


        networkService= ApplicationController.getInstance().getNetworkService();

//         계정 종류 이미지 변경
        if(type.equals("boss")){
            signup_actor.setImageResource(R.drawable.signup_boss_button);
        }else if(type.equals("alba")){
            signup_actor.setImageResource(R.drawable.signup_alba_button);
        }


//        회원가입 버튼
        signup_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //유효성 검사
                if(signup_id.length()==0){
                    Toast.makeText(getApplicationContext(),"아이디를 입력해주세요.",Toast.LENGTH_SHORT).show();
                    signup_id.requestFocus();
                    return;
                }else if(signup_name.length()==0) {
                    Toast.makeText(getApplicationContext(), "이름을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    signup_name.requestFocus();
                    return;
                }else if(signup_password.length()==0){
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    signup_password.requestFocus();
                    return;
                }else if(!signup_password.getText().toString().equals(signup_password_check.getText().toString())){
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    signup_password_check.requestFocus();
                    return;
                }else if (!Pattern.matches("^[a-zA-Z0-9]*$$", signup_id.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "아이디를 영문과 숫자로만 입력해주세요.", Toast.LENGTH_SHORT).show();
                    signup_id.requestFocus();
                    return;
                }else if (!Pattern.matches("^[a-zA-Z0-9]*$", signup_password.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 영문과 숫자로만 입력해주세요.", Toast.LENGTH_SHORT).show();
                    signup_password.requestFocus();
                    return;
                }else{
                    signup();
                }



            }
        });

    }

    public void signup(){

        Log.d("signup","in");
        Log.d("signup",signup_id.getText().toString()+" "+signup_password.getText().toString()+" "+signup_name.getText().toString()+" "+type);

        signPost=new SignPost(signup_id.getText().toString(),signup_password.getText().toString(),signup_name.getText().toString(),type);
        Call<SignResponse> signResponseCall=networkService.signup(signPost);
        signResponseCall.enqueue(new Callback<SignResponse>() {
            @Override
            public void onResponse(Call<SignResponse> call, Response<SignResponse> response) {

                Log.d("onResponse","in");

                if(response.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"회원가입 성공",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),Login.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<SignResponse> call, Throwable t) {

                Log.d("onFailure","in");

                Toast.makeText(getApplicationContext(),"서버 상태를 확인해주세요.",Toast.LENGTH_SHORT);
            }
        });
    }
}
