package com.example.albaalza.P_AlbaTing;

/**
 * Created by DS on 2017-11-07.
 */

public class AlbaTingData {
    int profile;    //프로필 사진
    String name;    //작성자
    String time;    //작성된 시간
    int image;      //게시물 이미지
    String title;   //제목
    int comment_image;//댓글 말풍선 이미지
    String comment;        //댓글 수

    //생성자
    public AlbaTingData(int profile, String name, String time, int image, String title, int comment_image, String comment) {
        this.profile = profile;
        this.name = name;
        this.time = time;
        this.image = image;
        this.title = title;
        this.comment_image = comment_image;
        this.comment = comment;
    }

    //getter/setter
    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getComment_image() {
        return comment_image;
    }

    public void setComment_image(int comment_image) {
        this.comment_image = comment_image;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
