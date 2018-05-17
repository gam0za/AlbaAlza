package com.example.albaalza.Server;

import com.example.albaalza.P_AlbaTing.AddMember.AddMemberPost;
import com.example.albaalza.P_AlbaTing.AddMember.AddMemberResponse;
import com.example.albaalza.P_AlbaTing.AddComment.CommentPost;
import com.example.albaalza.P_AlbaTing.AddComment.CommentResponse;
import com.example.albaalza.P_AlbaTing.CreateGroup.CreateGroupPost;
import com.example.albaalza.P_AlbaTing.CreateGroup.CreateGroupResponse;
import com.example.albaalza.P_AlbaTing.ListGroup.AllListResponse;
import com.example.albaalza.P_AlbaTing.AddComment.DetailContentPost;
import com.example.albaalza.P_AlbaTing.AddComment.DetailContentResponse;
import com.example.albaalza.P_AlbaTing.ListPost.ListPost;
import com.example.albaalza.P_AlbaTing.ListPost.ListResponse;
import com.example.albaalza.P_AlbaTing.MyListGroup.MyGroupListPost;
import com.example.albaalza.P_AlbaTing.MyListGroup.MyGroupListResponse;
import com.example.albaalza.P_AlbaTing.AddPost.WriteTingResponse;
import com.example.albaalza.P_AlbaTing.AddPost.WriteTingPost;
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

    //    3-1 그룹 생성
    @POST("/process/creategroup")
    Call<CreateGroupResponse> createGroup(@Body CreateGroupPost createGroupPost);

    //    3-2 그룹에 멤버 추가
    @POST("/process/addmember")
    Call<AddMemberResponse> addMember(@Body AddMemberPost addMemberPost);

    //    3-4 전체 그룹 리스트 불러오기
    @GET("/process/listgroup")
    Call<AllListResponse> listGroup();

    //    3-5 속해 있는 그룹 리스트 불러오기
    @POST("/process/mylistgroup")
    Call<MyGroupListResponse> myGroup(@Body MyGroupListPost myGroupListPost);

    //    4-1 글 등록
    @POST("/process/addpost")
    Call<WriteTingResponse> postTing(@Body WriteTingPost writeTingPost);

//    4-2 댓글 등록
    @POST("/process/addcomment")
    Call<CommentResponse> addcomment(@Body CommentPost commentPost);

//    4-3 게시글 리스트 가져오기
    @POST("/process/grouplistpost")
    Call<ListResponse> grouplistpost(@Body ListPost listPost);

//    4-4 게시글 상세화면
    @POST("/process/showpost")
    Call<DetailContentResponse> showpost(@Body DetailContentPost detailContentPost);

//    5 홈화면
    @POST("/process/home")
    Call<HomeResponse> gethomepage (@Body HomePost homePost);

}





