package com.example.albaalza.P_MyAlba;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.albaalza.P_Main.BusProvider;
import com.example.albaalza.P_Main.MainActivity;
import com.example.albaalza.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAlba2Fragment extends Fragment {

    int year, month;

    // 기본 급여
    TextView Text_basicPay,Text_basicTime, Text_basicTotal; // 시급, 근무 시간, 기본 급여 총
    int my_pay, totalTime, basic_total=0; // 시급, 근무 시간, 기본 급여 총

    // 추가 급여
    TextView Text_extraWeek, Text_extraNight, Text_extraTotal; // 추가급여(주휴수당, 야간수당, 총)
    int extraWeek=0,extraNight=0,extraTotal=0;

    // 보험
    TextView Text_4insurance, Text_TotalInsurance; // 세금(4대 보험, 총)
    double total_Insurance=0;

    // 최종 합
    TextView Text_total;
    double totalPay=0;

    MyAlbaDbOpenHelper dbHelper;
    String albaNameInSpinner = null;
    MyAlbaDBCalculator myAlbaDBCalculator;

    MyAlba myAlba;
    MyAlba1Fragment myAlba1Fragment;

    public MyAlba2Fragment() {
        // Required empty public constructor
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
            myAlba = ((MainActivity)getActivity()).getMyAlba();
            myAlba1Fragment = myAlba.getMyAlba1Fragment();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        year = myAlba1Fragment.getCalendar_year();
        month = myAlba1Fragment.getCalendar_month();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_alba2, container, false);
        Text_basicPay = (TextView) view.findViewById(R.id.Text_basicPay);
        Text_basicTime = (TextView) view.findViewById(R.id.Text_basicTime);
        Text_basicTotal = (TextView) view.findViewById(R.id.Text_basicTotal);
        Text_extraWeek = (TextView) view.findViewById(R.id.Text_extraWeek);
        Text_extraNight = (TextView) view.findViewById(R.id.Text_extraNight);
        Text_extraTotal = (TextView) view.findViewById(R.id.Text_extraTotal);
        Text_4insurance = (TextView) view.findViewById(R.id.Text_4insurance);
        Text_TotalInsurance = (TextView) view.findViewById(R.id.Text_TotalInsurance);
        Text_total = (TextView) view.findViewById(R.id.Text_total);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){

            calculate_basicPay(); // 기본 급여 계산
            calculate_extraPay(); // 추가 급여 계산 (추후 수정)
            calculate_insurance(true); // 보험
            calculate_total(); // total
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    // 기본 급여 계산
    public void calculate_basicPay(){

        /*** 시급 ***/
        albaNameInSpinner = ((MainActivity)getActivity()).getAlbaNameInSpinner();
        my_pay = myAlbaDBCalculator.getMyPaySet(albaNameInSpinner);
        Text_basicPay.setText(String.valueOf(my_pay));
<<<<<<< HEAD
//master
=======
//sejin
>>>>>>> origin/Sejin

        /*** 근무 시간, 기본 급여 총 ***/
        totalTime = myAlbaDBCalculator.calculateTimeForMonth(albaNameInSpinner, year, month);
        Text_basicTime.setText(String.valueOf(totalTime));


        /*** 기본 급여 총 ***/
        basic_total = myAlbaDBCalculator.getTotalPay(albaNameInSpinner, year, month);
        Text_basicTotal.setText(String.valueOf(basic_total));
    }

    // 추가 급여 계산 (추후 수정)
    public void calculate_extraPay() {

        // 추가 급여 총
        extraTotal = extraWeek + extraNight;

        // view set
        Text_extraWeek.setText(String.valueOf(extraWeek));
        Text_extraNight.setText(String.valueOf(extraNight));
        Text_extraTotal.setText(String.valueOf(extraTotal));
    }

    // 보험
    public void calculate_insurance(Boolean insurance){

        if(insurance){
            Text_4insurance.setText("가입(8.43%)");
            total_Insurance = (basic_total + extraTotal) * 0.085; // 4대보험 계산
        }
        else {
            Text_4insurance.setText("미가입");
            total_Insurance = 0;
        }

        Text_total.setText(String.valueOf(total_Insurance));
    }


    // total
    public void calculate_total(){
        totalPay = basic_total + extraTotal - total_Insurance;
        Text_total.setText(String.valueOf(totalPay));
    }
}