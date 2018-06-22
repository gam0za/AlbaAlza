package com.example.albaalza.B_MyPlace.Server;

/**
 * Created by jinyoungkim on 2018. 6. 22..
 */
//4-2 나에게 도착한 명세서 확인

public class StatementPost {
    public String oid;
    public String wid;

    public StatementPost(String oid, String wid) {
        this.oid = oid;
        this.wid = wid;
    }
}
