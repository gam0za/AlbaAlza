package com.example.albaalza.Network;


import com.example.albaalza.UI.B_MyPlace.Server.AcceptPost;
import com.example.albaalza.UI.B_MyPlace.Server.AcceptResponse;
import com.example.albaalza.UI.B_MyPlace.Server.AlbaListPost;
import com.example.albaalza.UI.B_MyPlace.Server.AlbaListResponse;
import com.example.albaalza.UI.B_MyPlace.Server.SchedulePost;
import com.example.albaalza.UI.B_MyPlace.Server.ScheduleResponse;
import com.example.albaalza.UI.B_MyPlace.Server.StatementPost;
import com.example.albaalza.UI.B_MyPlace.Server.StatementResponse;
import com.example.albaalza.UI.P_Login.LoginPost;
import com.example.albaalza.UI.P_Login.LoginResponse;
import com.example.albaalza.UI.P_MyAlba.Server.SearchBossPost;
import com.example.albaalza.UI.P_MyAlba.Server.SearchBossResponse;
import com.example.albaalza.UI.P_MyAlba.Server.SendRequestPost;
import com.example.albaalza.UI.P_MyAlba.Server.SendRequestResponse;
import com.example.albaalza.UI.P_MyAlba.Server.SendSchedulePost;
import com.example.albaalza.UI.P_MyAlba.Server.SendScheduleResponse;
import com.example.albaalza.UI.P_MyAlba.Server.MyStatementPost;
import com.example.albaalza.UI.P_MyAlba.Server.MyStatementResponse;
import com.example.albaalza.UI.P_SignUp.SignPost;
import com.example.albaalza.UI.P_SignUp.SignResponse;

import retrofit2.Call;
import retrofit2.http.Body;
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

    // 2-6 사장님 검색 (O)
    @POST("/process/searchboss")
    Call<SearchBossResponse> searchboss(@Body SearchBossPost searchBossPost);

//    2-7 스케줄 보내기 (알바생 계정)
    @POST("/process/schedule")
    Call<SendScheduleResponse> sendSchedule(@Body SendSchedulePost sendSchedulePost);

//    2-8 스케줄 받아오기 (사장님 계정에서)
    @POST("/process/sendschedule")
    Call<ScheduleResponse> scheduleresponse(@Body SchedulePost schedulePost);

//    3-1 친구 신청 보내기(O)
    @POST("process/sendrequest")
    Call<SendRequestResponse> sendrequest(@Body SendRequestPost sendRequestPost);

//    3-2 받은 친구 신청 확인(0)
    @POST("process/mylistrequest")
    Call<AlbaListResponse> mylistrequest(@Body AlbaListPost albaListPost);

//    3-3 친구신청 수락
    @POST("process/acceptrequest")
    Call<AcceptResponse> acceptrequest(@Body AcceptPost acceptPost);

//    4-1 명세서 보내기
    @POST("process/sendstatement")
    Call<MyStatementResponse> sendstatement (@Body MyStatementPost myStatementPost);

//    4-2 나에게 도착한 명세서 확인(0)
    @POST("process/myliststatement")
    Call<StatementResponse> myliststatement (@Body StatementPost statementPost);




}





