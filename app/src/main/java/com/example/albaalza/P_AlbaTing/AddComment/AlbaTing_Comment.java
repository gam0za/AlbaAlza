package com.example.albaalza.P_AlbaTing.AddComment;



   //게시글 상세화면
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albaalza.P_AlbaTing.MyListGroup.AlbaTing;
import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.Server.NetworkService;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbaTing_Comment extends AppCompatActivity {

    private ImageView writer_profile,content_image,comment_profile,send_comment;
    private TextView writer,contents,title,other_comment,updated_at,created_at;
    private EditText edit_comment;
    private NetworkService networkService;
    private CommentPost commentPost;
    private DetailContentPost detailContentPost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alba_ting__comment);

        writer_profile=(ImageView)findViewById(R.id.writer_profile);//글쓴이 프사
        content_image=(ImageView)findViewById(R.id.content_image);//게시글 이미지 (x)
//        comment_profile=
        send_comment=(ImageView)findViewById(R.id.send_comment);//댓글 보내기 버튼
        writer=(TextView)findViewById(R.id.writer_id);//글쓴이 아이디
        title=(TextView)findViewById(R.id.contents_title);//글 제목
        contents=(TextView)findViewById(R.id.contents);//글 내용
        created_at=(TextView)findViewById(R.id.created_at);//작성 시간
//        other_comment=
        edit_comment=(EditText)findViewById(R.id.edit_comment);//댓글 (현재 사용자가 쓴 댓글

        networkService= ApplicationController.getInstance().getNetworkService();


        getDetail();
        send_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send();
            }
        });

    }

//    댓글 등록
    public void send(){
        commentPost=new CommentPost("abaz","1",edit_comment.getText().toString());//일단 아이디하고 pid는 임의값 넣음
        Call<CommentResponse> commentResponseCall=networkService.addcomment(commentPost);
        commentResponseCall.enqueue(new Callback<CommentResponse>() {
            @Override
            public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                if(response.isSuccessful()){
                    Log.d("tag","SUCCESS");
                    ApplicationController.getInstance().makeToast("댓글 등록 완료");
                    Intent intent = new Intent(AlbaTing_Comment.this,AlbaTing.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CommentResponse> call, Throwable t) {
                Log.d("tag","FAIL");
                ApplicationController.getInstance().makeToast("서버 상태를 확인해주세요.");
            }
        });
    }


//     페이지 조회
    public void getDetail(){
        detailContentPost=new DetailContentPost("1");
        Call<DetailContentResponse> detailContentResponseCall=networkService.showpost(detailContentPost);
        detailContentResponseCall.enqueue(new Callback<DetailContentResponse>() {
            @Override
            public void onResponse(Call<DetailContentResponse> call, Response<DetailContentResponse> response) {
                if(response.isSuccessful()){
                    writer.setText(response.body().writer);
                    created_at.setText(response.body().created_at);
                    title.setText(response.body().title);
                    contents.setText(response.body().contents);
                }
            }

            @Override
            public void onFailure(Call<DetailContentResponse> call, Throwable t) {
                ApplicationController.getInstance().makeToast("서버 상태를 확인해주세요");
            }
        });
    }
}