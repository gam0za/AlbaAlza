package com.example.albaalza.P_MyAlba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.albaalza.R;

import java.util.ArrayList;

/**
 * Created by Jinyoung on 2017-11-13.
 */


/* 그리드 뷰 Adapter class*/
class gridAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Integer> days;
    LayoutInflater grid_inflater;
    int startDay, current_date, calendar_year, calendar_month;
    boolean today_flag;
    MyAlbaDBCalculator myAlbaDBCalculator;
    String albaNameInSpinner = null;

    int myPayDay = 0;
    boolean periodFlag  =false;


    public gridAdapter(Context context, ArrayList<Integer> days, int startDay,
                       int current_date, boolean today_flag,
                       int calendar_year, int calendar_month,
                       MyAlbaDBCalculator myAlbaDBCalculator,
                       String albaNameInSpinner){

        this.context = context;
        this.days = days;
        this.startDay = startDay;
        this.today_flag = today_flag;
        this.current_date = current_date;
        this.calendar_year = calendar_year;
        this.calendar_month = calendar_month;
        this.myAlbaDBCalculator = myAlbaDBCalculator;
        this.albaNameInSpinner = albaNameInSpinner;

        // 내역 기간
        myPayDay = myAlbaDBCalculator.getMyPayDay(albaNameInSpinner);

        grid_inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null && i <= this.getCount()) {
            view = grid_inflater.inflate(R.layout.fragment_my_alba1_calendar_item, null);
            TextView day_icon = (TextView) view.findViewById(R.id.day);
            day_icon.setText(String.valueOf(this.getItem(i)));

            // 1일 시작 전 cells 없애기
            if (i < startDay - 1)
                day_icon.setVisibility(View.GONE);

            // 토요일, 일요일 색상 바꾸기
            if ((i + 1) % 7 == 0)
                day_icon.setTextColor(0xff0000ff);
            else if ((i + 1) % 7 == 1)
                day_icon.setTextColor(0xffff0000);


            // today 색상 변경
            if (today_flag == true
                    && i == ((startDay - 1) + (current_date - 1))) {
                day_icon.setBackgroundColor(0xcc000000);
                day_icon.setTextColor(0xffffffff);
            }

            if(today_flag == true)
            // 돈 있는 날 색깔 표시
            if(i >= startDay - 1 && i<startDay + 32) {
                if(myAlbaDBCalculator.getPayForDay(albaNameInSpinner,calendar_year,calendar_month,i-startDay+2)!=0)
                    day_icon.setBackgroundColor(0xcc000fff);
            }
        }
        return view;
    }
    @Override

    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int i) {
        return days.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


}

