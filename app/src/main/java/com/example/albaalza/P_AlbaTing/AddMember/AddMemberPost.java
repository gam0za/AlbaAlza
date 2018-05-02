package com.example.albaalza.P_AlbaTing.AddMember;

/**
 * Created by jinyoungkim on 2018. 4. 30..
 */

public class AddMemberPost {
    public String gname; //그룹 이름
    public String id;//사용자 아이디

    public AddMemberPost(String gname, String id) {
        this.gname = gname;
        this.id = id;
    }
}
