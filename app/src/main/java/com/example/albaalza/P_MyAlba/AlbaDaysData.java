package com.example.albaalza.P_MyAlba;

/**
 * Created by SEJIN on 2017-11-03.
 */

public class AlbaDaysData {

    public int AlbaPay, TotalPay=0; // 알바 시급, 일수당
    public int start_hour, start_minute, end_hour, end_minute;
    public int total_hour, total_minute;

    public AlbaDaysData(int AlbaPay){
        this.AlbaPay = AlbaPay;
    }

    // 근무 시간 set
    public void setTimeOfWorking(int start_hour, int start_minute,
                                 int end_hour, int end_minute) {
        this.start_hour = start_hour;
        this.start_minute = start_minute;
        this.end_hour = end_hour;
        this.end_minute = end_minute;
        TotalPay = returnPayOfEachDay(start_hour, start_minute,
                end_hour, end_minute);
    }

    // 일당 구하기
    public int returnPayOfEachDay(int start_hour, int start_minute,
                                  int end_hour, int end_minute){

        // hour
        if(end_hour < start_hour){
            total_hour = 24-end_hour+start_hour;
        } else
            total_hour = end_hour-start_hour;

        // minute
        if(end_minute > start_minute){
            total_minute = 60 + end_minute - start_minute;
            total_hour--;
        } else
            total_minute = end_minute - start_minute;

        return AlbaPay*(total_hour+total_minute/60);
    }
}
