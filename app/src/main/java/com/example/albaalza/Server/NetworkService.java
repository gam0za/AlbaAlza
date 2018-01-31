package com.example.albaalza.Server;

import com.example.albaalza.P_Login.LoginPost;
import com.example.albaalza.P_Login.LoginResponse;
import com.example.albaalza.P_SignUp.SignPost;
import com.example.albaalza.P_SignUp.SignResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Jinyoung on 2017-11-21.
 */

public interface NetworkService {

//    로그인
    @POST("/process/login")
    Call<LoginResponse> getLoginResult(@Body LoginPost loginPost);

    @POST("/process/adduser")
    Call<SignResponse> signup(@Body SignPost signPost);

}
