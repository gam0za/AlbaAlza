package com.example.albaalza.P_MyAlba;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albaalza.P_Main.BusProvider;
import com.example.albaalza.P_Main.MainActivity;
import com.example.albaalza.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAlba2Fragment extends Fragment {

    int year, month;

    // 날짜 정보
    Spinner spinnerYEAR, spinnerMONTH;
    TextView dayInfo;
    Button Btn_search;
    private ArrayList<String> list_spinnerYEAR, list_spinnerMONTH;
    private ArrayAdapter<String> adapter_spinnerYEAR, adapter_spinnerMONTH;
    int selectedYEAR, selectedMONTH;

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
            albaNameInSpinner = ((MainActivity)getActivity()).getAlbaNameInSpinner();
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
        spinnerYEAR = (Spinner) view.findViewById(R.id.spinnerYEAR);
        spinnerMONTH = (Spinner) view.findViewById(R.id.spinnerMONTH);
        dayInfo = (TextView) view.findViewById(R.id.dayInfo);
        Btn_search = (Button) view.findViewById(R.id.Btn_search);

        Text_basicPay = (TextView) view.findViewById(R.id.Text_basicPay);
        Text_basicTime = (TextView) view.findViewById(R.id.Text_basicTime);
        Text_basicTotal = (TextView) view.findViewById(R.id.Text_basicTotal);
        Text_extraWeek = (TextView) view.findViewById(R.id.Text_extraWeek);
        Text_extraNight = (TextView) view.findViewById(R.id.Text_extraNight);
        Text_extraTotal = (TextView) view.findViewById(R.id.Text_extraTotal);
        Text_4insurance = (TextView) view.findViewById(R.id.Text_4insurance);
        Text_TotalInsurance = (TextView) view.findViewById(R.id.Text_TotalInsurance);
        Text_total = (TextView) view.findViewById(R.id.Text_total);

        SetSpinner();
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            albaNameInSpinner = ((MainActivity)getActivity()).getAlbaNameInSpinner();
            setDayInfo();
            calculate_basicPay(); // 기본 급여 계산
            calculate_extraPay(); // 추가 급여 계산 (추후 수정)
            calculate_insurance(); // 보험
            calculate_total(); // total
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    // 스피너
    public void SetSpinner(){

        // insert year
        list_spinnerYEAR = new ArrayList<String>();
        list_spinnerYEAR.add("2017");
        list_spinnerYEAR.add("2018");
        list_spinnerYEAR.add("2019");
        list_spinnerYEAR.add("2020");
        list_spinnerYEAR.add("2021");
        adapter_spinnerYEAR = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list_spinnerYEAR);
        spinnerYEAR.setAdapter(adapter_spinnerYEAR);

        // insert month
        list_spinnerMONTH = new ArrayList<String>();
        list_spinnerMONTH.add("1");
        list_spinnerMONTH.add("2");
        list_spinnerMONTH.add("3");
        list_spinnerMONTH.add("4");
        list_spinnerMONTH.add("5");
        list_spinnerMONTH.add("6");
        list_spinnerMONTH.add("7");
        list_spinnerMONTH.add("8");
        list_spinnerMONTH.add("9");
        list_spinnerMONTH.add("10");
        list_spinnerMONTH.add("11");
        list_spinnerMONTH.add("12");
        adapter_spinnerMONTH = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list_spinnerMONTH);
        spinnerMONTH.setAdapter(adapter_spinnerMONTH);

        // Listener - spinnerYEAR
        spinnerYEAR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedYEAR = Integer.parseInt(adapter_spinnerYEAR.getItem(i));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // Listener - spinnerMONTH
        spinnerMONTH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedMONTH = Integer.parseInt(adapter_spinnerMONTH.getItem(i));
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // Listener - Btn_search
        Btn_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                year = selectedYEAR;
                month = selectedMONTH;

                Toast.makeText(getContext(), "내역이 변경되었습니다.", Toast.LENGTH_SHORT).show();

                setDayInfo();
                calculate_basicPay(); // 기본 급여 계산
                calculate_extraPay(); // 추가 급여 계산 (추후 수정)
                calculate_insurance(); // 보험
                calculate_total(); // total
            }
        });

    }

    public void setDayInfo(){

        // 스피너 초기값
        spinnerYEAR.setSelection(0);
        spinnerMONTH.setSelection(0);

        for(int i=0; i<adapter_spinnerYEAR.getCount(); i++)
            if(adapter_spinnerYEAR.getItem(i).equals(String.valueOf(year)))
                spinnerYEAR.setSelection(i);

        for(int i=0; i<adapter_spinnerMONTH.getCount(); i++)
            if(adapter_spinnerMONTH.getItem(i).equals(String.valueOf(month)))
                spinnerMONTH.setSelection(i);

        // 내역 기간
        int myPayDay = myAlbaDBCalculator.getMyPayDay(albaNameInSpinner);
        String s;
        if(month == 1){
            s = "임금 계산 기간 : " +
                    String.valueOf(year-1) + "년 " + 12 + "월 " + String.valueOf(myPayDay) + "일 ~ " +
                    String.valueOf(year) + "년 " + month + "월 " + String.valueOf(myPayDay-1) + "일";
        }
        else{
            s = "임금 계산 기간 : " +
                    String.valueOf(year) + "년 " + String.valueOf(month-1) + "월 " + String.valueOf(myPayDay) + "일 ~ " +
                    String.valueOf(year) + "년 " + month + "월 " + String.valueOf(myPayDay-1) + "일";
        }
        dayInfo.setText(s);
    }

    // 기본 급여 계산
    public void calculate_basicPay(){

        /*** 시급 ***/
        my_pay = myAlbaDBCalculator.getMyPaySet(albaNameInSpinner);
        Text_basicPay.setText(String.valueOf(my_pay));


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
    public void calculate_insurance(){

        int insurance = myAlbaDBCalculator.getInsuranceFlag(albaNameInSpinner);

        if(insurance == 1){
            Text_4insurance.setText("가입(8.43%)");
            Double temp = (basic_total + extraTotal) * 0.0843; // 4대보험 계산
            total_Insurance = Double.parseDouble(String.format("%.2f",temp)); // 반올림
        }
        else {
            Text_4insurance.setText("미가입");
            total_Insurance = 0;
        }

        Text_TotalInsurance.setText(String.valueOf(total_Insurance));
    }


    // total
    public void calculate_total(){
        totalPay = basic_total + extraTotal - total_Insurance;
        Text_total.setText(String.valueOf(totalPay));
    }
}