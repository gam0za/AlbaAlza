package com.example.albaalza.UI.P_MyAlba;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.albaalza.UI.P_Main.BusProvider;
import com.example.albaalza.UI.P_Main.MainActivity;
import com.example.albaalza.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAlba2Fragment extends Fragment {

    int year, month;
    Calendar calendar;

    // 날짜 정보
    Spinner spinnerSTART_YEAR, spinnerSTART_MONTH, spinnerSTART_DAY;
    Spinner spinnerEND_YEAR, spinnerEND_MONTH, spinnerEND_DAY;
    ImageView Btn_search,sendStatement;

    private ArrayList<String> list_spinnerSTART_YEAR, list_spinnerSTART_MONTH, list_spinnerSTART_DAY;
    private ArrayList<String> list_spinnerEND_YEAR, list_spinnerEND_MONTH, list_spinnerEND_DAY;

    private ArrayAdapter<String> adapter_spinnerSTART_YEAR, adapter_spinnerSTART_MONTH, adapter_spinnerSTART_DAY;
    private ArrayAdapter<String> adapter_spinnerEND_YEAR, adapter_spinnerEND_MONTH, adapter_spinnerEND_DAY;
    private SweetAlertDialog alertDialog;

    int selectedSTART_YEAR, selectedSTART_MONTH, selectedSTART_DAY;
    int selectedEND_YEAR, selectedEND_MONTH, selectedEND_DAY;

    // 기본 급여
    TextView Text_basicPay,Text_basicTime, Text_basicTotal; // 시급, 근무 시간, 기본 급여 총
    int my_pay, totalTime; double basic_total=0; // 시급, 근무 시간, 기본 급여 총

    // 추가 급여
    TextView Text_extraWeek, Text_extraNight, Text_extraTotal; // 추가급여(주휴수당, 야간수당, 총)
    double extraWeek=0,extraNight=0,extraTotal=0;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_alba2, container, false);
        spinnerSTART_YEAR = (Spinner) view.findViewById(R.id.spinnerSTART_YEAR);
        spinnerSTART_MONTH = (Spinner) view.findViewById(R.id.spinnerSTART_MONTH);
        spinnerSTART_DAY = (Spinner) view.findViewById(R.id.spinnerSTART_DAY);
        spinnerEND_YEAR = (Spinner) view.findViewById(R.id.spinnerEND_YEAR);
        spinnerEND_MONTH = (Spinner) view.findViewById(R.id.spinnerEND_MONTH);
        spinnerEND_DAY = (Spinner) view.findViewById(R.id.spinnerEND_DAY);

        Btn_search = (ImageView) view.findViewById(R.id.Btn_search);

        Text_basicPay = (TextView) view.findViewById(R.id.Text_basicPay);
        Text_basicTime = (TextView) view.findViewById(R.id.Text_basicTime);
        Text_basicTotal = (TextView) view.findViewById(R.id.Text_basicTotal);
        Text_extraWeek = (TextView) view.findViewById(R.id.Text_extraWeek);
        Text_extraNight = (TextView) view.findViewById(R.id.Text_extraNight);
        Text_extraTotal = (TextView) view.findViewById(R.id.Text_extraTotal);
        Text_4insurance = (TextView) view.findViewById(R.id.Text_4insurance);
        Text_TotalInsurance = (TextView) view.findViewById(R.id.Text_TotalInsurance);
        Text_total = (TextView) view.findViewById(R.id.Text_total);
        sendStatement=(ImageView)view.findViewById(R.id.sendStatement);

        calendar = Calendar.getInstance(Locale.getDefault());

        SetSpinner();
        listenerFACTORY();

        sendStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog=new SweetAlertDialog(getContext(),SweetAlertDialog.SUCCESS_TYPE);
                alertDialog.setTitleText("급여명세서 전송완료:)")
                        .show();
            }
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if(isVisibleToUser){
            albaNameInSpinner = ((MainActivity)getActivity()).getAlbaNameInSpinner();

            SetSpinner();
            calculate_basicPay(); // 기본 급여 계산
            calculate_extraPay(); // 추가 급여 계산 (추후 수정)
            calculate_insurance(); // 보험
            calculate_total(); // total
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    // 스피너 초기 셋팅
    public void SetSpinner(){

        /***************** INSERT  DATA *****************/
        // insert YEAR
        list_spinnerSTART_YEAR = new ArrayList<String>();
        list_spinnerEND_YEAR = new ArrayList<String>();

        for(int i=2017; i<2022; i++){
            list_spinnerSTART_YEAR.add(String.valueOf(i));
            list_spinnerEND_YEAR.add(String.valueOf(i));
        }

        adapter_spinnerSTART_YEAR = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list_spinnerSTART_YEAR);
        spinnerSTART_YEAR.setAdapter(adapter_spinnerSTART_YEAR);
        adapter_spinnerEND_YEAR = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list_spinnerEND_YEAR);
        spinnerEND_YEAR.setAdapter(adapter_spinnerEND_YEAR);


        // insert MONTH
        list_spinnerSTART_MONTH = new ArrayList<String>();
        list_spinnerEND_MONTH = new ArrayList<String>();

        for(int i=1; i<13; i++){
            list_spinnerSTART_MONTH.add(String.valueOf(i));
            list_spinnerEND_MONTH.add(String.valueOf(i));
        }

        adapter_spinnerSTART_MONTH = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list_spinnerSTART_MONTH);
        spinnerSTART_MONTH.setAdapter(adapter_spinnerSTART_MONTH);
        adapter_spinnerEND_MONTH = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list_spinnerEND_MONTH);
        spinnerEND_MONTH.setAdapter(adapter_spinnerEND_MONTH);


        // insert DAY
        list_spinnerSTART_DAY = new ArrayList<String>();
        adapter_spinnerSTART_DAY = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list_spinnerSTART_DAY);
        list_spinnerEND_DAY = new ArrayList<String>();
        adapter_spinnerEND_DAY = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list_spinnerEND_DAY);

        calendar.set(year, month - 1, 1);
        int lastDay = calendar.getActualMaximum(Calendar.DATE); // 시작 월의 마지막 날

        list_spinnerSTART_DAY.clear();
        list_spinnerEND_DAY.clear();
        for(int i=1; i<=lastDay; i++){
            list_spinnerSTART_DAY.add(String.valueOf(i));
            list_spinnerEND_DAY.add(String.valueOf(i));
        }

        spinnerSTART_DAY.setAdapter(adapter_spinnerSTART_DAY);
        spinnerEND_DAY.setAdapter(adapter_spinnerEND_DAY);


        /***************** SET  DATA *****************/
        year = myAlba1Fragment.getCalendar_year();
        Log.d("★년", String.valueOf(year));
        month = myAlba1Fragment.getCalendar_month();
        Log.d("★월", String.valueOf(month));

        // setting YEAR
        for(int i=0; i<adapter_spinnerSTART_YEAR.getCount(); i++)
            if(adapter_spinnerSTART_YEAR.getItem(i).equals(String.valueOf(year))){
                spinnerSTART_YEAR.setSelection(i);
                spinnerEND_YEAR.setSelection(i);
            }

        // setting MONTH
        for(int i=0; i<adapter_spinnerSTART_MONTH.getCount(); i++)
            if(adapter_spinnerSTART_MONTH.getItem(i).equals(String.valueOf(month))){
                spinnerSTART_MONTH.setSelection(i);
                spinnerEND_MONTH.setSelection(i);
            }
    }

    // 리스너 팩토리
    public void listenerFACTORY(){

        // 시작 '월' 리스너
        spinnerSTART_YEAR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSTART_YEAR = Integer.parseInt(adapter_spinnerSTART_YEAR.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 종료 '월' 리스너
        spinnerEND_YEAR.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEND_YEAR = Integer.parseInt(adapter_spinnerEND_YEAR.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 시작 '달' 리스너
        spinnerSTART_MONTH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSTART_MONTH = Integer.parseInt(adapter_spinnerSTART_MONTH.getItem(position));

                calendar.set(selectedSTART_YEAR, selectedSTART_MONTH - 1, 1);
                int lastDay = calendar.getActualMaximum(Calendar.DATE); // 시작 월의 마지막 날

                list_spinnerSTART_DAY.clear();
                for(int i=1; i<=lastDay; i++){
                    list_spinnerSTART_DAY.add(String.valueOf(i));
                }
                spinnerSTART_DAY.setAdapter(adapter_spinnerSTART_DAY);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 종료 '달' 리스너
        spinnerEND_MONTH.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEND_MONTH = Integer.parseInt(adapter_spinnerEND_MONTH.getItem(position));

                calendar.set(selectedEND_YEAR, selectedEND_MONTH - 1, 1);
                int lastDay = calendar.getActualMaximum(Calendar.DATE); // 시작 월의 마지막 날

                list_spinnerEND_DAY.clear();
                for(int i=1; i<=lastDay; i++){
                    list_spinnerEND_DAY.add(String.valueOf(i));
                }
                spinnerEND_DAY.setAdapter(adapter_spinnerEND_DAY);

                spinnerEND_DAY.setSelection(adapter_spinnerEND_DAY.getCount()-1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 시작 '일' 리스너
        spinnerSTART_DAY.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSTART_DAY = Integer.parseInt(adapter_spinnerSTART_DAY.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 종료 '일' 리스너
        spinnerEND_DAY.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedEND_DAY = Integer.parseInt(adapter_spinnerEND_DAY.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // '검색' 버튼 리스너
        Btn_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                boolean isThereError = false;

                if(selectedSTART_YEAR > selectedEND_YEAR)
                    isThereError = true;
                else if(selectedSTART_YEAR == selectedEND_YEAR && selectedSTART_MONTH > selectedEND_MONTH)
                    isThereError = true;
                else if(selectedSTART_MONTH == selectedEND_MONTH && selectedSTART_DAY > selectedEND_DAY)
                    isThereError = true;

                if(isThereError) {
                    Toast.makeText(getContext(), "근무 기간을 다시 선택해주세요.", Toast.LENGTH_SHORT).show();

                    // setting YEAR
                    for(int i=0; i<adapter_spinnerSTART_YEAR.getCount(); i++)
                        if(adapter_spinnerSTART_YEAR.getItem(i).equals(String.valueOf(year))){
                            spinnerSTART_YEAR.setSelection(i);
                            spinnerEND_YEAR.setSelection(i);
                        }

                    // setting MONTH
                    for(int i=0; i<adapter_spinnerSTART_MONTH.getCount(); i++)
                        if(adapter_spinnerSTART_MONTH.getItem(i).equals(String.valueOf(month))){
                            spinnerSTART_MONTH.setSelection(i);
                            spinnerEND_MONTH.setSelection(i);
                        }
                }
                else{
                    // 화면 갱신
                    calculate_basicPay(); // 기본 급여 계산
                    calculate_extraPay(); // 추가 급여 계산 (추후 수정)
                    calculate_insurance(); // 보험
                    calculate_total(); // total

                    Toast.makeText(getContext(), "급여 내역이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 기본 급여 계산
    public void calculate_basicPay(){

        /*** 시급 ***/
        my_pay = myAlbaDBCalculator.getMyPaySet(albaNameInSpinner);
        Text_basicPay.setText(String.valueOf(my_pay));

        /*** 근무 시간, 기본 급여 총 ***/
        totalTime = myAlbaDBCalculator.calculateTimeForMonth(albaNameInSpinner, selectedSTART_YEAR, selectedSTART_MONTH, selectedSTART_DAY,
                selectedEND_YEAR, selectedEND_MONTH, selectedEND_DAY);
        Text_basicTime.setText(String.valueOf(totalTime));

        /*** 기본 급여 총 ***/
        double temp = myAlbaDBCalculator.getTotalPay(albaNameInSpinner, selectedSTART_YEAR, selectedSTART_MONTH, selectedSTART_DAY,
                selectedEND_YEAR, selectedEND_MONTH, selectedEND_DAY);
        basic_total = Double.parseDouble(String.format("%.2f",temp)); // 반올림
        Text_basicTotal.setText(String.valueOf(basic_total));
    }

    // 추가 급여 계산 (추후 수정)
    public void calculate_extraPay() {

        Double temp = myAlbaDBCalculator.getExtraPay(albaNameInSpinner, selectedSTART_YEAR, selectedSTART_MONTH, selectedSTART_DAY,
                selectedEND_YEAR, selectedEND_MONTH, selectedEND_DAY);
        extraWeek = Double.parseDouble(String.format("%.2f",temp)); // 반올림

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