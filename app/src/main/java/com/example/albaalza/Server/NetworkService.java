package com.example.albaalza.Server;


import com.example.albaalza.P_Home.HomePost;
import com.example.albaalza.P_Home.HomeResponse;
import com.example.albaalza.P_Login.LoginPost;
import com.example.albaalza.P_Login.LoginResponse;
import com.example.albaalza.P_SignUp.SignPost;
import com.example.albaalza.P_SignUp.SignResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Jinyoung on 2017-11-21.
 */

public interface NetworkService {

    //    로그인
    @POST("/process/login")
    Call<LoginResponse> getLoginResult(@Body LoginPost loginPost);

    //  회원가입
    @POST("/process/adduser")
    Call<SignResponse> signup(@Body SignPost signPost);


//    5 홈화면
    @POST("/process/home")
    Call<HomeResponse> gethomepage (@Body HomePost homePost);

}





