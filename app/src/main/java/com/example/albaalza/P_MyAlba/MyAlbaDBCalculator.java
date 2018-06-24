package com.example.albaalza.P_MyAlba;

        import android.content.Intent;
        import android.database.Cursor;
        import android.util.Log;

        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.Locale;

        import android.content.Intent;
        import android.database.Cursor;
        import android.util.Log;

        import java.text.DateFormat;
        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.Locale;

/**
 * Created by SEJIN on 2018-01-15.
 */

public class MyAlbaDBCalculator {

    MyAlbaDbOpenHelper dbHelper;
    private Calendar calendar;

    // 스케줄 데이터
    String startHOUR[] = new String[7]; //1~6:월~토, 0:일
    String startMIN[] = new String[7]; //1~6:월~토, 0:일
    String endHOUR[] = new String[7]; //1~6:월~토, 0:일
    String endMIN[] = new String[7]; //1~6:월~토, 0:일

    // 하루 스케줄 데이터
    int start_hour_for_a_day, start_min_for_a_day, end_hour_for_a_day, end_min_for_a_day;

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

    /*****************************************  한주의 스케줄 ******************************************/
    public void getMySchedule(String albaNameInSpinner) {

        // 초기화
        for(int i=0; i<7; i++){
            startHOUR[i] = "0";
            startMIN[i] = "0";
            endHOUR[i] = "0";
            endMIN[i] = "0";
        }

        // 일주일 범위 구하기
        Calendar calendar = Calendar.getInstance();
        int current_week = calendar.get(Calendar.WEEK_OF_YEAR);
        int week_start_day = calendar.getFirstDayOfWeek();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String startDate = "", endDate = "";
        startDate = df.format(calendar.getTime());
        calendar.add(Calendar.DATE, 6);
        endDate = df.format(calendar.getTime());

        // Date Split
        String date[] = startDate.split("-");
        int StartYEAR = Integer.parseInt(date[0]);
        int StartMONTH = Integer.parseInt(date[1]);
        int StartDAY = Integer.parseInt(date[2]);

        String date2[] = endDate.split("-");
        int EndYEAR = Integer.parseInt(date2[0]);
        int EndMONTH = Integer.parseInt(date2[1]);
        int EndDAY = Integer.parseInt(date2[2]);

        Log.d("START ", String.valueOf(StartYEAR));
        Log.d("START ", String.valueOf(StartMONTH));
        Log.d("START ", String.valueOf(StartDAY));
        Log.d("END ", String.valueOf(EndYEAR));
        Log.d("END ", String.valueOf(EndMONTH));
        Log.d("END ", String.valueOf(EndDAY));

        // 내부디비 불러오기
        try {
            Cursor iCursor = dbHelper.selectColumns_MYALBA();
            iCursor.moveToFirst();
            while (iCursor.moveToNext()) {
                String tempMYALBANAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));

                if (tempMYALBANAME.equals(albaNameInSpinner)) {
                    int tempYEAR = iCursor.getInt(iCursor.getColumnIndex("year"));
                    int tempMONTH = iCursor.getInt(iCursor.getColumnIndex("month"));
                    int tempDAY = iCursor.getInt(iCursor.getColumnIndex("day"));

                    if ((tempYEAR == StartYEAR && tempMONTH == StartMONTH && tempDAY >= StartDAY)
                            && (tempYEAR == EndYEAR && tempMONTH == EndMONTH && tempDAY < EndDAY)) {
                        calendar.set(Calendar.YEAR, tempYEAR);
                        calendar.set(Calendar.MONTH, tempMONTH-1);
                        calendar.set(Calendar.DATE, tempDAY);
                        Log.d("캘린더", String.valueOf(tempYEAR+"," + tempMONTH+"," + tempDAY));
                        Log.d("날짜숫자", String.valueOf(calendar.get(Calendar.DAY_OF_WEEK)));
                        switch (calendar.get(Calendar.DAY_OF_WEEK)){
                            case 1: //일요일
                                startHOUR[0] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("startHour")));
                                startMIN[0] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("startMinutes")));
                                endHOUR[0] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("endHour")));
                                endMIN[0] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("endMinutes")));
                                break;
                            case 2: //월요일
                                startHOUR[1] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("startHour")));
                                startMIN[1] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("startMinutes")));
                                endHOUR[1] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("endHour")));
                                endMIN[1] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("endMinutes")));
                                break;
                            case 3: //화요일
                                startHOUR[2] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("startHour")));
                                startMIN[2] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("startMinutes")));
                                endHOUR[2] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("endHour")));
                                endMIN[2] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("endMinutes")));
                                break;
                            case 4: //수요일
                                startHOUR[3] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("startHour")));
                                startMIN[3] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("startMinutes")));
                                endHOUR[3] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("endHour")));
                                endMIN[3] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("endMinutes")));
                                break;
                            case 5: //목요일
                                startHOUR[4] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("startHour")));
                                startMIN[4] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("startMinutes")));
                                endHOUR[4] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("endHour")));
                                endMIN[4] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("endMinutes")));
                                break;
                            case 6: //금요일
                                startHOUR[5] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("startHour")));
                                startMIN[5] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("startMinutes")));
                                endHOUR[5] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("endHour")));
                                endMIN[5] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("endMinutes")));
                                break;
                            case 7: //토요일
                                startHOUR[6] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("startHour")));
                                startMIN[6] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("startMinutes")));
                                endHOUR[6] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("endHour")));
                                endMIN[6] = String.valueOf(iCursor.getInt(iCursor.getColumnIndex("endMinutes")));
                                break;
                        }
                    }
                }
            }
            Log.d("startHOUR ", startHOUR[1]);
            Log.d("startMIN ", startMIN[1]);
            Log.d("endHOUR ", endHOUR[1]);
            Log.d("endHOUR ", endMIN[1]);
            iCursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* 한주의 스케줄 - 시작 시 얻기 */
    public String[] getSTART_HOUR(){
        return startHOUR;
    }

    /* 한주의 스케줄 - 시작 분 얻기 */
    public String[] getSTART_MIN(){
        return startMIN;
    }

    /* 한주의 스케줄 - 종료 시 얻기 */
    public String[] getEND_HOUR(){
        return endHOUR;
    }

    /* 한주의 스케줄 - 종료 분 얻기 */
    public String[] getEND_MIN(){
        return endMIN;
    }
    /*****************************************  한주의 스케줄 ******************************************/


    /*****************************************  하루의 스케줄 ******************************************/
    public void getMyScheduleForDay(String albaNameInSpinner, int year, int month, int day) {

        // 데이터 초기화
        start_hour_for_a_day = start_min_for_a_day = end_hour_for_a_day = end_min_for_a_day = 0;

        // 내부디비 불러오기
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
                        start_hour_for_a_day = iCursor.getInt(iCursor.getColumnIndex("startHour"));
                        start_min_for_a_day = iCursor.getInt(iCursor.getColumnIndex("startMinutes"));
                        end_hour_for_a_day = iCursor.getInt(iCursor.getColumnIndex("endHour"));
                        end_min_for_a_day = iCursor.getInt(iCursor.getColumnIndex("endMinutes"));
                    }
                }
            }
            iCursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /* 한주의 스케줄 - 시작 시 얻기 */
    public int getSTART_HOUR_ForDay(){
        return start_hour_for_a_day;
    }

    /* 한주의 스케줄 - 시작 분 얻기 */
    public int getSTART_MIN_ForDay(){ return start_min_for_a_day;
    }

    /* 한주의 스케줄 - 종료 시 얻기 */
    public int getEND_HOUR_ForDay(){
        return end_hour_for_a_day;
    }

    /* 한주의 스케줄 - 종료 분 얻기 */
    public int getEND_MIN_ForDay(){
        return end_min_for_a_day;
    }
    /*****************************************  하루의 스케줄 ******************************************/
}
