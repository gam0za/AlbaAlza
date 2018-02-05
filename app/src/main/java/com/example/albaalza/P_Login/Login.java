package com.example.albaalza.P_Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albaalza.P_SignUp.SelectActor;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.P_FindPassword.FindPassword;
import com.example.albaalza.P_Main.MainActivity;
import com.example.albaalza.Server.NetworkService;
import com.example.albaalza.R;
import com.example.albaalza.P_SignUp.SignUp;

import retrofit2.*;

public class Login extends AppCompatActivity {

    EditText edit_name, edit_password;
    CheckBox checkbox;
    ImageView button_login;
    TextView find_password, goto_signup;
    String loginId, loginPw;
    private NetworkService networkService;
    private UserData userData;
    private LoginPost loginPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_password = (EditText) findViewById(R.id.edit_password);
        checkbox = (CheckBox) findViewById(R.id.checkBox);//자동로그인
        button_login = (ImageView) findViewById(R.id.button_login);
        find_password = (TextView) findViewById(R.id.find_password);
        goto_signup = (TextView) findViewById(R.id.goto_signup);

        loginId = edit_name.getText().toString();
        loginPw = edit_password.getText().toString();

        networkService = ApplicationController.getInstance().getNetworkService();
        userData = new UserData();

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();
//                login();
            }

        });

        find_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FindPassword.class);
                startActivity(intent);
            }
        });

        goto_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectActor.class);
                startActivity(intent);
            }
        });

    }

    public void login() {
        Log.d("get_into_method", "메소드 들어옴~~~~~~~");
        loginPost = new LoginPost(edit_name.getText().toString(), edit_password.getText().toString());
        Log.d("post", loginPost.id);
        Log.d("post", loginPost.password);
        retrofit2.Call<LoginResponse> loginResponseCall = networkService.getLoginResult(loginPost);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("response", "Response 들어옴~~~~~~~");
                if (response.isSuccessful()) {
                    Log.d("success", "isSuccessful 들어옴~~~~~~~");
                    userData.id = response.body().id;
                    userData.password = response.body().password;
//                        userData.pwd=response.body().loginData.upwd;
                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.d("check_id_pw", "else 들어옴~~~~~~~");
//                    Log.d("pw:",response.body().loginData.upwd.toString());
                    ApplicationController.getInstance().makeToast("아이디와 비밀번호를 다시 확인해 주세요");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<LoginResponse> call, Throwable t) {
                Log.d("fail", "로그인 실패 Failure 들어옴");
                ApplicationController.getInstance().makeToast("서버 상태를 연결해주세요~");
            }
        });
    }
}