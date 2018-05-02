package com.example.albaalza.P_AlbaTing.CreateGroup;

/**
 * Created by jinyoungkim on 2018. 4. 30..
 */

public class CreateGroupPost {
    public String gname;
    public String gtype;
    public String admin;

    public CreateGroupPost(String gname, String gtype, String admin) {
        this.gname = gname;
        this.gtype = gtype;
        this.admin = admin;
    }
}
