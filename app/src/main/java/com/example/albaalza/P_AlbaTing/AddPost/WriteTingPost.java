package com.example.albaalza.P_AlbaTing.AddPost;

/**
 * Created by jinyoungkim on 2018. 2. 28..
 */

//글등록

public class WriteTingPost {

    public String title;
//    public String type;
    public String contents;
    public String writer;
    public String gname;

    public WriteTingPost(String title, String contents, String writer,String gname){
        this.title=title;
        this.contents=contents;
        this.writer=writer;
        this.gname=gname;
    }


}
