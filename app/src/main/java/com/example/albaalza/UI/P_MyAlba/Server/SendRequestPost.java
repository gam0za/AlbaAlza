package com.example.albaalza.UI.P_MyAlba.Server;

/**
 * Created by jinyoungkim on 2018. 6. 5..
 */

public class SendRequestPost {
    public String wid; //알바생 아이디
    public String oid; //사장님 아이디

    public SendRequestPost(String wid, String oid) {
        this.wid = wid;
        this.oid = oid;
    }
}
