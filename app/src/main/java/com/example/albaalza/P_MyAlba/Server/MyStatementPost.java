package com.example.albaalza.P_MyAlba.Server;

/**
 * Created by jinyoungkim on 2018. 6. 22..
 */

//4-1 명세서 보내기
public class MyStatementPost {

    public String wid;
    public String oid;
    public String start_y;
    public String start_m;
    public String start_d;
    public String end_y;
    public String end_m;
    public String end_d;
    public String wage;
    public String hours;
    public String tota1;
    public String weekly_a;
    public String night_a;
    public String total2;
    public String four_P;
    public String total3;
    public String total4;

    public MyStatementPost(String wid, String oid, String start_y, String start_m, String start_d, String end_y, String end_m, String end_d, String wage, String hours, String tota1, String weekly_a, String night_a, String total2, String four_P, String total3, String total4) {
        this.wid = wid;
        this.oid = oid;
        this.start_y = start_y;
        this.start_m = start_m;
        this.start_d = start_d;
        this.end_y = end_y;
        this.end_m = end_m;
        this.end_d = end_d;
        this.wage = wage;
        this.hours = hours;
        this.tota1 = tota1;
        this.weekly_a = weekly_a;
        this.night_a = night_a;
        this.total2 = total2;
        this.four_P = four_P;
        this.total3 = total3;
        this.total4 = total4;
    }
}
