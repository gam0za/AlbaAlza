package com.example.albaalza.Server;


import com.example.albaalza.B_MyPlace.SchedulePost;
import com.example.albaalza.B_MyPlace.ScheduleResponse;
import com.example.albaalza.P_Home.HomePost;
import com.example.albaalza.P_Home.HomeResponse;
import com.example.albaalza.P_Login.LoginPost;
import com.example.albaalza.P_Login.LoginResponse;
import com.example.albaalza.P_MyAlba.Server.SearchBossPost;
import com.example.albaalza.P_MyAlba.Server.SearchBossResponse;
import com.example.albaalza.P_MyAlba.Server.SendRequestPost;
import com.example.albaalza.P_MyAlba.Server.SendRequestResponse;
import com.example.albaalza.P_MyAlba.Server.SendSchedulePost;
import com.example.albaalza.P_MyAlba.Server.SendScheduleResponse;
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

    // 사장님 검색
    @POST("/process/searchboss")
    Call<SearchBossResponse> searchboss(@Body SearchBossPost searchBossPost);

//    2-7 스케줄 보내기 (알바생 계정
    @POST("/process/schedule")
    Call<SendScheduleResponse> sendSchedule(@Body SendSchedulePost sendSchedulePost);

//    2-8 스케줄 받아오기 (사장님 계정에서
    @POST("/process/sendschedule")
    Call<ScheduleResponse> scheduleresponse(@Body SchedulePost schedulePost);

//    3-1 친구 신청 보내기
    @POST("process/sendrequest")
    Call<SendRequestResponse> sendrequest(@Body SendRequestPost sendRequestPost);


}





