package com.example.albaalza.P_MyAlba;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albaalza.P_Main.MainActivity;
import com.example.albaalza.P_Main.BusProvider;
import com.example.albaalza.R;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.albaalza.R.id.end_hour;
import static com.example.albaalza.R.id.end_minute;
import static com.example.albaalza.R.id.start_hour;
import static com.example.albaalza.R.id.start_minute;

public class MyAlba1Fragment extends Fragment {

    private TextView add_albaDay, deleteBtn, modifyBtn;
    private TextView Text_TotalPay, Text_myPay; // 일급, 당월 누적 금액, 나의 시급
    private ImageView black_layout;
    private RelativeLayout buttonLayout;

    /* fragment_my_alba1 변수 선언 */
    private ImageView prevBtn;
    private ImageView nextBtn;
    private Button today_setBtn;
    private TextView dateTitle;
    private GridView calender_grid;
    private float XLocation = 0, YLocation =0;
    private Spinner spinner;
    private boolean isCreated = false;
    private ArrayList<String> spinner_albaName;
    private ArrayAdapter<String> adapter;

    /* 캘린더 변수 선언 */
    private Calendar calendar;
    public int current_year, current_month, current_date; // 현재 연,월,일
    public int calendar_year, calendar_month, calendar_date; // 캘린더 연,월,일
    public int selected_date, selected_day; // 선택된 연,월,일,요일
    private final ArrayList<Integer> days = new ArrayList<>(); // 그리드에 연결할 각 item 배열
    int startOfweek = 0; // 한주의 일요일(시작 요일)
    private boolean isLongClicked = false;

    // 데이터베이스
    MyAlbaDbOpenHelper dbHelper;
    String albaNameInSpinner = null;
    int totalMyPay = 0;

    MyAlbaDBCalculator myAlbaDBCalculator;

    public MyAlba1Fragment() {
        // Required empty public constructor
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isCreated) {
            setSpinner();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            dbHelper = ((MainActivity)getActivity()).getDB(); // 메인액티비티로 부터 db를 얻어옴
            myAlbaDBCalculator = ((MainActivity)getActivity()).getMyAlbaDBCalculator();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        spinner_albaName = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, spinner_albaName);
        albaNameInSpinner = ((MainActivity)getActivity()).getAlbaNameInSpinner();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_alba1, container, false);
        view = createView(view); // 뷰 객체 생성 함수

        setSpinner(); // 스피너 초기화
        showCurrentTime(); // 현재 연,월,일 구하기 함수
        updateCalendar();
        viewFunction(); // 뷰 객체 기능 구현 함수

        isCreated = true;
        return view;
    }

    @Subscribe
    public void onActivityResult(MainActivity.ActivityResultEvent activityResultEvent) {
        onActivityResult(activityResultEvent.getRequestCode(),
                activityResultEvent.getResultCode(),
                activityResultEvent.getData()); // 자신의 함수 부르기
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == 1001){
                albaNameInSpinner = ((MainActivity)getActivity()).getAlbaNameInSpinner();
                setSpinner(); // 스피너 업데이트
            }
            else if (requestCode == 2001) {
                ArrayList<Boolean> dateRepeat = new ArrayList<>(); //(0=mon ~ 6=sun / 7=week)
                dateRepeat.add(data.getBooleanExtra("monRepeat", false));
                dateRepeat.add(data.getBooleanExtra("tueRepeat", false));
                dateRepeat.add(data.getBooleanExtra("wedRepeat", false));
                dateRepeat.add(data.getBooleanExtra("thuRepeat", false));
                dateRepeat.add(data.getBooleanExtra("friRepeat", false));
                dateRepeat.add(data.getBooleanExtra("satRepeat", false));
                dateRepeat.add(data.getBooleanExtra("sunRepeat", false));
                dateRepeat.add(data.getBooleanExtra("weekRepeat", false));

                ArrayList<Integer> total_hour = new ArrayList<>(); //(0=start_hour, 1=start_minute, 2=end_hour, 3=end_minute)
                total_hour.add(data.getIntExtra("start_hour", -1));
                total_hour.add(data.getIntExtra("start_minute", -1));
                total_hour.add(data.getIntExtra("end_hour", -1));
                total_hour.add(data.getIntExtra("end_minute", -1));

                int myPAY = data.getIntExtra("my_pay", -1);

                setTotalPay(dateRepeat,total_hour, myPAY);

            } else if (requestCode == 3001) {
                int start_hour = data.getIntExtra("start_hour", -1);
                int start_minute = data.getIntExtra("start_minute", -1);
                int end_hour = data.getIntExtra("end_hour", -1);
                int end_minute = data.getIntExtra("end_minute", -1);
                int my_pay = data.getIntExtra("my_pay", -1);

                // 일급 다시 계산
                int payForDay = myAlbaDBCalculator.setPayForDay(my_pay, start_hour, start_minute, end_hour, end_minute);

                // 데이터 베이스 업그레이드
                dbHelper.updateColumn_MYALBA(my_pay, calendar_year, calendar_month, selected_day, // 조건
                        start_hour, start_minute, end_hour, end_minute, payForDay);

                // 화면 갱신
                updateCalendar();
                getTotalPay(calendar_year, calendar_month);
            }
        }
    }

    /* 뷰 객체 생성 함수 */
    private View createView(View view) {

        dateTitle = (TextView) view.findViewById(R.id.dateTitle);
        calender_grid = (GridView) view.findViewById(R.id.calendar_grid);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        prevBtn = (ImageView) view.findViewById(R.id.prevBtn);
        nextBtn = (ImageView) view.findViewById(R.id.nextBtn);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        Text_TotalPay = (TextView) view.findViewById(R.id.Text_totalPay);
        Text_myPay = (TextView) view.findViewById(R.id.Text_myPay);
        black_layout = (ImageView) view.findViewById(R.id.black_layout);
        add_albaDay = (TextView) view.findViewById(R.id.add_albaDay);
        modifyBtn = (TextView) view.findViewById(R.id.modifyBtn);
        deleteBtn = (TextView) view.findViewById(R.id.deleteBtn);
        buttonLayout = (RelativeLayout) view.findViewById(R.id.buttonLayout);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /* 스피너 설정 */
    private void setSpinner(){

        spinner_albaName.clear();

        // 스피너에 알바 이름 추가
        try {
            Cursor iCursor = dbHelper.selectColumns_MYALBANAME();
            iCursor.moveToFirst();
            while (iCursor.moveToNext()) {
                String tempMYALBANAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));
                adapter.add(tempMYALBANAME);
            }
            iCursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        adapter.add("알바 추가");
        spinner.setAdapter(adapter);


        // 스피너 선택
        try {
            for (int i = 0; i < adapter.getCount(); i++)
                if (adapter.getItem(i).equals(albaNameInSpinner))
                    spinner.setSelection(i);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        setMyPay(); // 시급 설정
    }

    /* 시급 설정 */
    public void setMyPay(){
        int tempMYPAY = myAlbaDBCalculator.getMyPaySet(albaNameInSpinner);
        String s = String.valueOf(tempMYPAY + " 원");
        Text_myPay.setText(s);
    }

    /* 뷰 객체 기능 구현 함수 */
    private void viewFunction() {

        // '<' button
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendar_year > 2016) { // 캘린더 날짜 범위 (2017/01~2021/12)
                    if (calendar_month != 1)
                        --calendar_month;
                    else if ((calendar_year - 1) > 2016) {
                        calendar_month = 12;
                        --calendar_year;
                    }
                    getTotalPay(calendar_year, calendar_month);
                    updateCalendar(); // update calendar
                }
            }
        });

        // '>' button
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendar_year < 2022) { // 캘린더 날짜 범위 (2017/01~2021/12)
                    if (calendar_month != 12)
                        ++calendar_month;
                    else if ((calendar_year + 1) < 2022) {
                        ++calendar_year;
                        calendar_month = 1;
                    }
                    getTotalPay(calendar_year, calendar_month);
                    updateCalendar(); // update calendar
                }
            }
        });

        // today_setBtn button
//        today_setBtn.setText("today");
//        today_setBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showCurrentTime();
//                updateCalendar();
//            }
//        });

        // black_layout_click
        black_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show black layout
                show_Black_layout(false);
                add_albaDay.setVisibility(View.INVISIBLE);
                buttonLayout.setVisibility(View.INVISIBLE);
            }
        });


        // calendar_grid touch
        calender_grid.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                XLocation = motionEvent.getX();
                YLocation = motionEvent.getY();
                return false;
            }
        });


        // calendar_grid gridView_LongClick
        calender_grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                // show black layout
                show_Black_layout(true);

                isLongClicked = true;
                selected_date = days.get(i);
                selected_day = i % 7;

                int tempPAYFORDAY = getPayForDay(calendar_year, calendar_month, days.get(i));

                // 데이터가 없는 날 >> 추가 버튼
                if(tempPAYFORDAY == 0
                        && add_albaDay.getVisibility() == View.INVISIBLE){
                    add_albaDay.setVisibility(View.VISIBLE);
                    add_albaDay.setX(view.getX());
                    add_albaDay.setY(YLocation + 50);
                }

                // 데이터가 있는 날 >> 수정, 삭제 버튼
                else{
                    buttonLayout.setVisibility(View.VISIBLE);
                    buttonLayout.setX(view.getX());
                    buttonLayout.setY(YLocation + 50);
                }

                return false;
            }
        });

        // calendar_grid gridView_Click
        calender_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(!isLongClicked){
                    int tempPAYFORDAY = getPayForDay(calendar_year, calendar_month, days.get(i));

                    String s = String.valueOf(calendar_year + "년 " +
                            calendar_month + "월 " +
                            days.get(i) + "일: " +
                            tempPAYFORDAY + "원");

                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                }
            }
        });


        // 알바 추가 버튼
        add_albaDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tempMYPAY = myAlbaDBCalculator.getMyPaySet(albaNameInSpinner);
                Intent intent = new Intent(getActivity(), AddAlbaDay.class);
                intent.putExtra("selected_day", selected_day);
                intent.putExtra("my_pay", tempMYPAY);
                getActivity().startActivityForResult(intent, 2001);
                add_albaDay.setVisibility(View.INVISIBLE);
                show_Black_layout(false);
            }
        });

        // 알바 day 삭제 버튼
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                try{
                    dbHelper.deleteColumn_myAlba(albaNameInSpinner, calendar_year, calendar_month, selected_date);
                    Toast.makeText(getContext(), "삭제 완료", Toast.LENGTH_SHORT).show();
                    updateCalendar();
                    getTotalPay(calendar_year,calendar_month);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                    Toast.makeText(getContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
                }

                buttonLayout.setVisibility(View.INVISIBLE);
                show_Black_layout(false);
            }
        });

        // 알바 day 수정 버튼
        modifyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int tempMYPAY = myAlbaDBCalculator.getMyPaySet2(albaNameInSpinner, calendar_year, calendar_month, selected_date);
                Intent intent = new Intent(getActivity(), ModifyAlbaDay.class);

                intent.putExtra("start_hour", start_hour);
                intent.putExtra("start_minute", start_minute);
                intent.putExtra("end_hour", end_hour);
                intent.putExtra("end_minute", end_minute);
                intent.putExtra("my_pay", tempMYPAY);
                getActivity().startActivityForResult(intent, 3001);
                buttonLayout.setVisibility(View.INVISIBLE);
                show_Black_layout(false);
            }
        });

        // spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == adapter.getCount() - 1) { // 리스트의 마지막 == "알바 추가" 선택 시
                    Intent intent = new Intent(getActivity(), AddAlba.class);
                    getActivity().startActivityForResult(intent, 1001);
                    spinner.setSelection(adapter.getCount()-2);
                } else {
                    ((MainActivity)getActivity()).setAlbaNameInSpinner(adapter.getItem(i));
                    albaNameInSpinner = ((MainActivity)getActivity()).getAlbaNameInSpinner();

                    setMyPay(); // 시급 설정

                    // set 당일 누적 금액
                    getTotalPay(calendar_year, calendar_month);
                    showCurrentTime();
                    updateCalendar();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void show_Black_layout(boolean state){
        if(state){ // 보일 때
            black_layout.setVisibility(View.VISIBLE);
            calender_grid.setEnabled(false);
            prevBtn.setEnabled(false);
            nextBtn.setEnabled(false);
            spinner.setEnabled(false);
            today_setBtn.setEnabled(false);
        }
        else{ // 안 보일 때
            black_layout.setVisibility(View.INVISIBLE);
            calender_grid.setEnabled(true);
            prevBtn.setEnabled(true);
            nextBtn.setEnabled(true);
            spinner.setEnabled(true);
            today_setBtn.setEnabled(true);
            isLongClicked = false;
            // calender_grid 포커스 없애야 함 (추후 수정)
        }

    }

    /* 현재 연,월,일 구하기 함수 */
    private void showCurrentTime() {

        // 기본 캘린더에서 현재 년(current_year), 달(current_month), 일(current_date), 요일(current_day) 구하기
        calendar = Calendar.getInstance(Locale.getDefault());
        current_month = calendar.get(Calendar.MONTH) + 1;
        current_year = calendar.get(Calendar.YEAR);
        current_date = calendar.get(Calendar.DATE);

        // 캘린더 상 year, month, date 값 대입
        calendar_year = current_year;
        calendar_month = current_month;
        calendar_date = current_date;
    }

    /* 캘린더 업데이트 함수 */
    private void updateCalendar() {

        // 캘린더 상단에 년, 달 표시
        dateTitle.setText(String.valueOf(calendar_year) + "년 " + String.valueOf(calendar_month) + "월");

        // 매달 1일 요일을 구하기 위해 강제로 오늘 날짜 1일로 set
        calendar.set(calendar_year, calendar_month - 1, 1);

        int startDay = calendar.get(Calendar.DAY_OF_WEEK);
        int lastDay = calendar.getActualMaximum(Calendar.DATE);

        days.clear();

        for (int i = 0; i < lastDay + startDay - 1; i++) {
            if (i < (startDay - 1)) {
                days.add(i, 0);
            } else
                days.add(i, (i - startDay + 2));
        }

        boolean today_flag = false;
        if (current_year == calendar_year &&
                current_month == calendar_month)
            today_flag = true;


        gridAdapter gridAdapter = new gridAdapter(getContext(), days, startDay,
                current_date, today_flag, calendar_year, calendar_month, myAlbaDBCalculator, albaNameInSpinner);
        calender_grid.setAdapter(gridAdapter);
    }


    /* 당월 누적 금액 표시 */
    private void setTotalPay(ArrayList<Boolean> dateRepeat,
                             ArrayList<Integer> total_hour,
                             int myPAY) {

        startOfweek = selected_date - selected_day;

        int year = calendar_year;
        int month = calendar_month;
        int date = startOfweek;

        int start_hour = total_hour.get(0);
        int start_minute = total_hour.get(1);
        int end_hour = total_hour.get(2);
        int end_minute = total_hour.get(3);

        // 일급 계산
        int payForDay = myAlbaDBCalculator.setPayForDay(myPAY, start_hour, start_minute, end_hour, end_minute);

        try {
            Cursor iCursor = dbHelper.selectColumns_MYALBA();
            Toast.makeText(getContext(),String.valueOf(iCursor.getCount()), Toast.LENGTH_SHORT).show();
            iCursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // 요일 반복
        for (int i = 0; i < 7; i++)
            if (dateRepeat.get(i) && (date + i < calendar.getActualMaximum(Calendar.DATE))) {
                dbHelper.insertColumn(albaNameInSpinner, myPAY, year, month, date + 1 + i,
                        start_hour, start_minute, end_hour, end_minute, payForDay);
            }

            /*
        // 주별 반복
        if(dateRepeat.get(7))
            for(int i = 0; i < 7; i++)
                if(dateRepeat.get(i))
                    for(int j = date + i; j < calendar.getActualMaximum(Calendar.DATE); j+=7)
                        dbHelper.insertColumn(albaNameInSpinner, myPAY, year, month, date +j,
                                start_hour, start_minute, end_hour, end_minute, payForDay);
*/
        updateCalendar();

        getTotalPay(year, month);
    }

    private void getTotalPay(int year, int month){

        Text_TotalPay.setText(String.valueOf(totalMyPay) + "0 원");

        try{
            int monthTotal = myAlbaDBCalculator.getTotalPay(albaNameInSpinner, year, month);
            String s= String.valueOf(monthTotal)+" 원";
            Text_TotalPay.setText(s);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    /* 일급 데이터 얻어오기 */
    public int getPayForDay(int year, int month, int day){
        return myAlbaDBCalculator.getPayForDay(albaNameInSpinner, year, month, day);
    }

    public int getCalendar_year(){
        return calendar_year;
    }

    public int getCalendar_month() {
        return calendar_month;
    }
}
