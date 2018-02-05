package com.example.albaalza.P_AlbaTing;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albaalza.R;

/**
 * Created by DS on 2017-11-07.
 */

public class ViewHolder_Detail extends RecyclerView.ViewHolder {
    public ImageView profile,image,comment_image;
    public TextView name,time,title,comment;

    public ViewHolder_Detail(View itemView) {
        super(itemView);

        profile=(ImageView)itemView.findViewById(R.id.profile); //프로필 사진
        name=(TextView)itemView.findViewById(R.id.name);    //작성자
        time=(TextView)itemView.findViewById(R.id.time);    //작성된 시간
        image=(ImageView)itemView.findViewById(R.id.image); //게시물 이미지
        title=(TextView)itemView.findViewById(R.id.title);  //제목
        comment_image=(ImageView)itemView.findViewById(R.id.comment_image); //댓글 말풍선 이미지
        comment=(TextView)itemView.findViewById(R.id.comment);      //댓글 수



    }


}
