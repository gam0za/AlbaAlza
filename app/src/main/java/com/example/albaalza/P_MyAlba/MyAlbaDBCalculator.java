package com.example.albaalza.P_MyAlba;

import android.database.Cursor;
import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

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

    /* 선택된 근무 기간동안 일한 시간 구하기 */
    public int calculateTimeForMonth(String albaNameInSpinner, int selectedSTART_YEAR, int selectedSTART_MONTH, int selectedSTART_DAY,
                                     int selectedEND_YEAR, int selectedEND_MONTH, int selectedEND_DAY){

        int total_time = 0;
        try {
            Cursor iCursor = dbHelper.selectColumns_MYALBA();
            iCursor.moveToFirst();

            while (iCursor.moveToNext()) {
                String tempMYALBANAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));

                if (tempMYALBANAME.equals(albaNameInSpinner)) {
                    int tempYEAR = iCursor.getInt(iCursor.getColumnIndex("year"));
                    int tempMONTH = iCursor.getInt(iCursor.getColumnIndex("month"));
                    int tempDAY = iCursor.getInt(iCursor.getColumnIndex("day"));

                    if ( (tempYEAR == selectedSTART_YEAR && tempMONTH == selectedSTART_MONTH && tempDAY >= selectedSTART_DAY)
                            || (tempYEAR == selectedEND_YEAR && tempMONTH == selectedEND_MONTH && tempDAY <= selectedEND_DAY) )
                    {

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

    /* 선택된 근무 기간동안 일한 급여 구하기 */
    public int getTotalPay(String albaNameInSpinner, int selectedSTART_YEAR, int selectedSTART_MONTH, int selectedSTART_DAY,
                           int selectedEND_YEAR, int selectedEND_MONTH, int selectedEND_DAY) {

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
                    if ( (tempYEAR == selectedSTART_YEAR && tempMONTH == selectedSTART_MONTH && tempDAY >= selectedSTART_DAY)
                            || (tempYEAR == selectedEND_YEAR && tempMONTH == selectedEND_MONTH && tempDAY <= selectedEND_DAY) )
                    {
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

    /* 당월 누적 금액 구하기 */
    public int getTotalPayForMonth(String albaNameInSpinner, int year, int month) {

        int totalPAY = 0;

        try {
            Cursor iCursor = dbHelper.selectColumns_MYALBA();
            iCursor.moveToFirst();

            while (iCursor.moveToNext()) {
                String tempMYALBANAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));
                if (tempMYALBANAME.equals(albaNameInSpinner)) {
                    int tempYEAR = iCursor.getInt(iCursor.getColumnIndex("year"));
                    int tempMONTH = iCursor.getInt(iCursor.getColumnIndex("month"));

                    if (tempYEAR == year && tempMONTH == month) {
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
    public Double getExtraPay(String albaNameInSpinner, int selectedSTART_YEAR, int selectedSTART_MONTH, int selectedSTART_DAY,
                              int selectedEND_YEAR, int selectedEND_MONTH, int selectedEND_DAY){

        Calendar calendar;
        calendar = Calendar.getInstance(Locale.getDefault());
        calendar.set(selectedSTART_YEAR, selectedSTART_MONTH - 1, 1);
        int lastDay = calendar.getActualMaximum(Calendar.DATE); // 해당 월의 마지막 날

        int currentCursorYEAR = selectedSTART_YEAR;
        int currentCursorMONTH = selectedSTART_MONTH;
        int currentCursorDAY = selectedSTART_DAY;

        double totalExtraPay = 0;

        while((currentCursorYEAR < selectedEND_YEAR)
                || (currentCursorMONTH <= selectedEND_MONTH
                && currentCursorDAY <= selectedEND_DAY)) {

            // 한 달 안에 일주일이 해결될 경우
            if (currentCursorDAY + 6 <= lastDay) {

                double tempExtraTime = getExtraPayDB(albaNameInSpinner, currentCursorYEAR, currentCursorMONTH, currentCursorDAY,
                        currentCursorYEAR, currentCursorMONTH, currentCursorDAY + 6);
                //
                String logText = String.valueOf(currentCursorYEAR) + String.valueOf(currentCursorMONTH) + String.valueOf(currentCursorDAY);
                String logText2 = String.valueOf(currentCursorYEAR) + String.valueOf(currentCursorMONTH) + String.valueOf(currentCursorDAY + 6);
                Log.v("태그", logText);
                Log.v("태그2", logText2);

                if (tempExtraTime > 0) {
                    // 주휴수당 계산 : (일주일 총 근로시간) / 40 * 8 * 시급
                    totalExtraPay += (tempExtraTime / 40) * 8 * getMyPaySet(albaNameInSpinner);
                    Log.v("주휴수당", String.valueOf(tempExtraTime));
                }
                currentCursorDAY += 6;

                 // 현재 일이 월의 끝 일일 경우
                if (currentCursorDAY == lastDay) {
                    if (currentCursorMONTH == 12) { // 12월의 경우
                        currentCursorYEAR++;
                        currentCursorMONTH = 1;
                    } else {
                        currentCursorMONTH++;
                    }
                    currentCursorDAY = 1;
                }
                else{
                    currentCursorDAY++;
                }
            }


            // 한 달 안에 일주일이 해결되지 못할 경우
            else {

                // 다음 달을 검색하지 않고, 일주일을 채우지 못했을 경우
                if(currentCursorYEAR == selectedEND_YEAR
                        && currentCursorMONTH == selectedEND_MONTH
                        && selectedEND_DAY < currentCursorDAY + 6) {
                    double tempExtraTime = getExtraPayDB(albaNameInSpinner, currentCursorYEAR, currentCursorMONTH, currentCursorDAY,
                            currentCursorYEAR, currentCursorMONTH, selectedEND_DAY);

                    //
                    String logText = String.valueOf(currentCursorYEAR) + String.valueOf(currentCursorMONTH) + String.valueOf(currentCursorDAY);
                    String logText2 = String.valueOf(currentCursorYEAR) + String.valueOf(currentCursorMONTH) + String.valueOf(selectedEND_DAY);
                    Log.v("태그", logText);
                    Log.v("태그2", logText2);

                    if (tempExtraTime > 0) {
                        // 주휴수당 계산 : (일주일 총 근로시간) / 40 * 8 * 시급
                        totalExtraPay += (tempExtraTime / 40) * 8 * getMyPaySet(albaNameInSpinner);
                        Log.v("주휴수당", "2");
                    }
                    break;
                }

                // 다음 달까지 검색할 경우
                else{
                    int addDay = lastDay - currentCursorDAY;

                    if (currentCursorMONTH == 12) { // 12월의 경우
                        double tempExtraTime = getExtraPayDB(albaNameInSpinner, currentCursorYEAR, currentCursorMONTH, currentCursorDAY,
                                currentCursorYEAR + 1, 1, 6 - addDay);

                        //
                        String logText =  String.valueOf(currentCursorYEAR) +  String.valueOf(currentCursorMONTH) + String.valueOf(currentCursorDAY);
                        String logText2 =  String.valueOf(currentCursorYEAR + 1) +  String.valueOf(1) + String.valueOf(6 - addDay);
                        Log.v("태그", logText);
                        Log.v("태그2", logText2);

                        if(tempExtraTime > 0){
                            // 주휴수당 계산 : (일주일 총 근로시간) / 40 * 8 * 시급
                            totalExtraPay += (tempExtraTime / 40) * 8 * getMyPaySet(albaNameInSpinner);
                            Log.v("주휴수당", "3");
                        }
                        currentCursorYEAR++;
                        currentCursorMONTH = 1;
                        currentCursorDAY = 7 - addDay;
                    } else {
                        double tempExtraTime = getExtraPayDB(albaNameInSpinner, currentCursorYEAR, currentCursorMONTH, currentCursorDAY,
                                currentCursorYEAR, currentCursorMONTH + 1, 6 - addDay);

                        //
                        String logText =  String.valueOf(currentCursorYEAR) +  String.valueOf(currentCursorMONTH) + String.valueOf(currentCursorDAY);
                        String logText2 =  String.valueOf(currentCursorYEAR) +  String.valueOf(currentCursorMONTH + 1) + String.valueOf(6 - addDay);
                        Log.v("태그", logText);
                        Log.v("태그2", logText2);

                        if(tempExtraTime > 0){
                            // 주휴수당 계산 : (일주일 총 근로시간) / 40 * 8 * 시급
                            totalExtraPay += (tempExtraTime / 40) * 8 * getMyPaySet(albaNameInSpinner);
                            Log.v("주휴수당", "4");
                        }
                        currentCursorMONTH++;
                        currentCursorDAY = 7 - addDay;
                    }
                }
            }
        }

        return totalExtraPay;
    }


    public double getExtraPayDB(String albaNameInSpinner, int selectedSTART_YEAR, int selectedSTART_MONTH, int selectedSTART_DAY,
                                int selectedEND_YEAR, int selectedEND_MONTH, int selectedEND_DAY){

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

                    if ((tempYEAR == selectedSTART_YEAR && tempMONTH == selectedSTART_MONTH && tempDAY >= selectedSTART_DAY )
                            && (tempYEAR == selectedEND_YEAR && tempMONTH == selectedEND_MONTH && tempDAY < selectedEND_DAY) ) {

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
