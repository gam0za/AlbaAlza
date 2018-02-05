package com.example.albaalza.P_MyAlba;

import android.database.Cursor;

import java.util.Calendar;

/**
 * Created by SEJIN on 2018-01-15.
 */

public class MyAlbaDBCalculator {

    MyAlbaDbOpenHelper dbHelper;
    private Calendar calendar;

    public MyAlbaDBCalculator(MyAlbaDbOpenHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    /* 일급 데이터 계산하기  */
    public int setPayForDay(int myPAY, int start_hour, int start_minute, int end_hour, int end_minute) {

        int timeForDay = calculateTIME(start_hour, start_minute, end_hour, end_minute);

        return myPAY * timeForDay;
    }

    /* 일급 데이터 얻어오기 */
    public int getPayForDay(String albaNameInSpinner, int year, int month, int day){

        try {
            Cursor iCursor = dbHelper.selectColumns_MYALBA();
            iCursor.moveToFirst();
            while (iCursor.moveToNext()) {
                String tempMYALBANAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));
                if (tempMYALBANAME.equals(albaNameInSpinner)) {
                    int tempYEAR = iCursor.getInt(iCursor.getColumnIndex("year"));
                    int tempMONTH = iCursor.getInt(iCursor.getColumnIndex("month"));
                    int tempDAY = iCursor.getInt(iCursor.getColumnIndex("day"));

                    if (tempYEAR == year && tempMONTH == month && tempDAY == day) {
                        int tempPAYFORDAY = iCursor.getInt(iCursor.getColumnIndex("payForDay"));
                        return tempPAYFORDAY;
                    }
                }
            }
            iCursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /* 하루 일한 시간 구하기 */
    public int calculateTIME(int tempSTARTHOUR, int tempSTARTMINUTES, int tempENDHOUR, int tempENDMINUTES){

        int totalHOUR, totalMINUTES; // 하루 일한 시간

        // hour
        if (tempENDHOUR < tempSTARTHOUR) {
            totalHOUR = 24 - tempSTARTHOUR + tempENDHOUR;
        } else
            totalHOUR = tempENDHOUR - tempSTARTHOUR;

        // minute
        if (tempENDMINUTES < tempSTARTMINUTES) {
            totalMINUTES = 60 - tempSTARTMINUTES + tempENDMINUTES;
            if (totalHOUR == 0)
                totalHOUR = 23;
            else
                totalMINUTES--;
        } else
            totalMINUTES = tempENDMINUTES - tempSTARTMINUTES;

        return totalHOUR + totalMINUTES / 60;
    }

    /* 한달 일한 시간 구하기 */
    public int calculateTimeForMonth(String albaNameInSpinner, int year, int month){

        int total_time = 0;

        int myPayDay = getMyPayDay(albaNameInSpinner);

        try {
            Cursor iCursor = dbHelper.selectColumns_MYALBA();
            iCursor.moveToFirst();

            while (iCursor.moveToNext()) {
                String tempMYALBANAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));

                if (tempMYALBANAME.equals(albaNameInSpinner)) {
                    int tempYEAR = iCursor.getInt(iCursor.getColumnIndex("year"));
                    int tempMONTH = iCursor.getInt(iCursor.getColumnIndex("month"));
                    int tempDAY = iCursor.getInt(iCursor.getColumnIndex("day"));

                    int tempYEARminus = 0;
                    int tempMONTHplus = 0;
                    if(tempMONTH == 1 && tempDAY<myPayDay){ // 1월의 경우
                        tempYEARminus = 1;
                        tempMONTHplus = 12;
                    }
                    if ((tempYEAR == year
                            && tempMONTH == month
                            && tempDAY < myPayDay)

                            || (tempYEAR == year-tempYEARminus
                            && tempMONTH == month+tempMONTHplus-1
                            && tempDAY >= myPayDay)) {

                        int tempSH = iCursor.getInt(iCursor.getColumnIndex("startHour"));
                        int tempSM = iCursor.getInt(iCursor.getColumnIndex("startMinutes"));
                        int tempEH = iCursor.getInt(iCursor.getColumnIndex("endHour"));
                        int tempEM = iCursor.getInt(iCursor.getColumnIndex("endMinutes"));

                        int temp_time = calculateTIME(tempSH, tempSM, tempEH, tempEM);
                        total_time += temp_time;
                    }
                }

            }
            iCursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return total_time;
    }

    /* 당월 누적 금액 구하기 */
    public int getTotalPay(String albaNameInSpinner, int year, int month) {

        int totalPAY = 0;
        int myPayDay = getMyPayDay(albaNameInSpinner);

        try {
            Cursor iCursor = dbHelper.selectColumns_MYALBA();
            iCursor.moveToFirst();

            while (iCursor.moveToNext()) {
                String tempMYALBANAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));
                if (tempMYALBANAME.equals(albaNameInSpinner)) {
                    int tempYEAR = iCursor.getInt(iCursor.getColumnIndex("year"));
                    int tempMONTH = iCursor.getInt(iCursor.getColumnIndex("month"));
                    int tempDAY = iCursor.getInt(iCursor.getColumnIndex("day"));

                    int tempYEARminus = 0;
                    int tempMONTHplus = 0;
                    if(tempMONTH == 1 && tempDAY<myPayDay){ // 1월의 경우
                        tempYEARminus = 1;
                        tempMONTHplus = 12;
                    }
                    if ((tempYEAR == year
                            && tempMONTH == month
                            && tempDAY < myPayDay)

                            || (tempYEAR == year-tempYEARminus
                            && tempMONTH == month+tempMONTHplus-1
                            && tempDAY >= myPayDay)) {
                        int tempPAYFORDAY = iCursor.getInt(iCursor.getColumnIndex("payForDay"));
                        totalPAY += tempPAYFORDAY;
                    }
                }
            }
            iCursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return totalPAY;
    }

    /* 시급 데이터 얻기 */
    public int getMyPaySet(String albaNameInSpinner){
        try {
            Cursor iCursor = dbHelper.selectColumns_MYALBANAME();
            iCursor.moveToFirst();
            while (iCursor.moveToNext()) {
                String tempMYALBANAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));

                if(tempMYALBANAME.equals(albaNameInSpinner)) {
                    int tempMYPAY = iCursor.getInt(iCursor.getColumnIndex("myPaySet"));
                    return tempMYPAY;
                }
            }
            iCursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /* 시급 데이터 얻기2 */
    public int getMyPaySet2(String albaNameInSpinner, int year, int month, int day){
        try {
            Cursor iCursor = dbHelper.selectColumns_MYALBA();
            iCursor.moveToFirst();
            while (iCursor.moveToNext()) {
                String tempMYALBANAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));
                int tempYEAR = iCursor.getInt(iCursor.getColumnIndex("year"));
                int tempMONTH = iCursor.getInt(iCursor.getColumnIndex("month"));
                int tempDAY = iCursor.getInt(iCursor.getColumnIndex("day"));

                if(tempMYALBANAME.equals(albaNameInSpinner)
                        && tempYEAR == year
                        && tempMONTH == month
                        && tempDAY == day) {
                    int tempMYPAY = iCursor.getInt(iCursor.getColumnIndex("myPay"));
                    return tempMYPAY;
                }
            }
            iCursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /* 사대보험 가입 여부 */
    public int getInsuranceFlag(String albaNameInSpinner){

        try {
            Cursor iCursor = dbHelper.selectColumns_MYALBANAME();
            iCursor.moveToFirst();
            while (iCursor.moveToNext()) {
                String tempMYALBANAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));

                if(tempMYALBANAME.equals(albaNameInSpinner)) {
                    int tempINSURANCE = iCursor.getInt(iCursor.getColumnIndex("myPayment"));
                    return tempINSURANCE;
                }
            }
            iCursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    /* 주휴수당 계산 */
    public Double getExtraPay(String albaNameInSpinner, int year, int month){

        int myPayDay = getMyPayDay(albaNameInSpinner);

        int startYEAR, startMONTH, startDay=myPayDay;
        int endYEAR=year, endMONTH=month, endDay=myPayDay-1;

        if(month == 1){ // 1월의 경우
            startYEAR = year-1;
            startMONTH = 12;
        }
        else{
            startYEAR = year;
            startMONTH = month-1;
        }

        calendar = Calendar.getInstance();
        calendar.set(startYEAR, startMONTH - 1, startDay);
        int startDATE = calendar.get(Calendar.DAY_OF_WEEK); // 1(일) ~ 7(토), 시작일의 요일
        int lastDay = calendar.getActualMaximum(Calendar.DATE); // 시작 월의 마지막 날

        int currentDay = startDay;

        double temp=0, total=0;

        /*
        for(){
            if(temp = getExtraPayDB() != 0){
                // 주휴수당 계산 : (일주일 총 근로시간) / 40 * 8 * 시급
                total += temp / 40 * 8 * getMyPaySet(albaNameInSpinner);
            }
        }
        */
        return total;
    }


    public double getExtraPayDB(String albaNameInSpinner, int startYEAR,int startMONTH, int startDAY,
                                int endYEAT, int endMONTH, int endDAY){

        double total_time = 0;


        try {
            Cursor iCursor = dbHelper.selectColumns_MYALBA();
            iCursor.moveToFirst();

            while (iCursor.moveToNext()) {
                String tempMYALBANAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));

                if (tempMYALBANAME.equals(albaNameInSpinner)) {
                    int tempYEAR = iCursor.getInt(iCursor.getColumnIndex("year"));
                    int tempMONTH = iCursor.getInt(iCursor.getColumnIndex("month"));
                    int tempDAY = iCursor.getInt(iCursor.getColumnIndex("day"));

                    if ((tempYEAR == startYEAR && tempMONTH == startMONTH && tempDAY >= startDAY )
                            || tempYEAR == endYEAT && tempMONTH == endMONTH && tempDAY < endDAY) {

                        int tempSH = iCursor.getInt(iCursor.getColumnIndex("startHour"));
                        int tempSM = iCursor.getInt(iCursor.getColumnIndex("startMinutes"));
                        int tempEH = iCursor.getInt(iCursor.getColumnIndex("endHour"));
                        int tempEM = iCursor.getInt(iCursor.getColumnIndex("endMinutes"));

                        int temp_time = calculateTIME(tempSH, tempSM, tempEH, tempEM);
                        total_time += temp_time;
                    }
                }
            }
            iCursor.close();
            if(total_time >= 15) // 주 15시간 이상일 경우
                return total_time;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0.0;
    }

    /* 월급날 데이터 받기 */
    public int getMyPayDay(String albaNameInSpinner){
        try {
            Cursor iCursor = dbHelper.selectColumns_MYALBANAME();
            iCursor.moveToFirst();
            while (iCursor.moveToNext()) {
                String tempMYALBANAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));
                if(tempMYALBANAME.equals(albaNameInSpinner)) {
                    int tempMYPAYDAY = iCursor.getInt(iCursor.getColumnIndex("myPayday"));
                    return tempMYPAYDAY;
                }
            }
            iCursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }


}
