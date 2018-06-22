package com.example.albaalza.P_MyAlba.Server;

/**
 * Created by jinyoungkim on 2018. 6. 5..
 */

public class SendSchedulePost {
    public String id;
    public String oid;

    public String MONstart_hour;
    public String MONstart_min;
    public String MONend_hour;
    public String MONend_min;

    public String TUEstart_hour;
    public String TUEstart_min;
    public String TUEend_hour;
    public String TUEend_min;

    public String WEDstart_hour;
    public String WEDstart_min;
    public String WEDend_hour;
    public String WEDend_min;

    public String THUstart_hour;
    public String THUstart_min;
    public String THUend_hour;
    public String THUend_min;

    public String FRIstart_hour;
    public String FRIstart_min;
    public String FRIend_hour;
    public String FRIend_min;

    public String SATstart_hour;
    public String SATstart_min;
    public String SATend_hour;
    public String SATend_min;

    public String SUNstart_hour;
    public String SUNstart_min;
    public String SUNend_hour;
    public String SUNend_min;

    public SendSchedulePost(String id, String oid, String MONstart_hour, String MONstart_min, String MONend_hour, String MONend_min, String TUEstart_hour, String TUEstart_min, String TUEend_hour, String TUEend_min, String WEDstart_hour, String WEDstart_min, String WEDend_hour, String WEDend_min, String THUstart_hour, String THUstart_min, String THUend_hour, String THUend_min, String FRIstart_hour, String FRIstart_min, String FRIend_hour, String FRIend_min, String SATstart_hour, String SATstart_min, String SATend_hour, String SATend_min, String SUNstart_hour, String SUNstart_min, String SUNend_hour, String SUNend_min) {
        this.id = id;
        this.oid = oid;
        this.MONstart_hour = MONstart_hour;
        this.MONstart_min = MONstart_min;
        this.MONend_hour = MONend_hour;
        this.MONend_min = MONend_min;
        this.TUEstart_hour = TUEstart_hour;
        this.TUEstart_min = TUEstart_min;
        this.TUEend_hour = TUEend_hour;
        this.TUEend_min = TUEend_min;
        this.WEDstart_hour = WEDstart_hour;
        this.WEDstart_min = WEDstart_min;
        this.WEDend_hour = WEDend_hour;
        this.WEDend_min = WEDend_min;
        this.THUstart_hour = THUstart_hour;
        this.THUstart_min = THUstart_min;
        this.THUend_hour = THUend_hour;
        this.THUend_min = THUend_min;
        this.FRIstart_hour = FRIstart_hour;
        this.FRIstart_min = FRIstart_min;
        this.FRIend_hour = FRIend_hour;
        this.FRIend_min = FRIend_min;
        this.SATstart_hour = SATstart_hour;
        this.SATstart_min = SATstart_min;
        this.SATend_hour = SATend_hour;
        this.SATend_min = SATend_min;
        this.SUNstart_hour = SUNstart_hour;
        this.SUNstart_min = SUNstart_min;
        this.SUNend_hour = SUNend_hour;
        this.SUNend_min = SUNend_min;
    }
}
