package com.example.albaalza.P_Labor;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albaalza.R;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class Advice3Fragment extends Fragment {

    TextView text_myCenter1, text_myCenter2;
    TextView text_myCenterAddress;
    TextView text_myCenterTel, text_myCenterFax;
    FloatingActionButton Btn_Call;
    Spinner spinner_state, spinner_city;
    LinearLayout adviceInfoFrame;

    // 데이터 베이스
    AdviceDbOpenHelper dbHelper;
    private ArrayList<String> list_spinner_state, list_spinner_city;
    private ArrayAdapter<String> adapter_spinner_state, adapter_spinner_city;
    String selectedSTATE = null;
    String selectedCITY = null;
    String selectedCENTER = null;
    String selectedTEL = null;
    String selectedFAX = null;
    String selectedADDRESS = null;

    public Advice3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new AdviceDbOpenHelper(getContext());
        dbHelper.open(); // open database
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_advice3, container, false);
        text_myCenter1 = view.findViewById(R.id.text_myCenter1);
        text_myCenter2 = view.findViewById(R.id.text_myCenter2);
        text_myCenterAddress = view.findViewById(R.id.text_myCenterAddress);
        text_myCenterTel = view.findViewById(R.id.text_myCenterTel);
        text_myCenterFax = view.findViewById(R.id.text_myCenterFax);
        spinner_state = view.findViewById(R.id.spinner_state);
        spinner_city = view.findViewById(R.id.spinner_city);
        adviceInfoFrame = view.findViewById(R.id.adviceInfoFrame);
        Btn_Call = view.findViewById(R.id.Btn_Call);

        // call
        Btn_Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedTEL != null){
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + selectedTEL));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getContext(),"근무지의 위치를 선택해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        // state spinner setting
        setSpinner_state();

        // city spinner setting
        list_spinner_city = new ArrayList<String>();
        adapter_spinner_city = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list_spinner_city);
        spinner_city.setAdapter(adapter_spinner_city);

        // selectedListener
        selectedSpinner_city();
        selectedSpinner_state();

        return view;
    }

    public void setSpinner_state() {
        list_spinner_state = new ArrayList<String>();
        list_spinner_state.add(" ");
        list_spinner_state.add("서울"); // 1
        list_spinner_state.add("부산"); // 2
        list_spinner_state.add("대구"); // 3
        list_spinner_state.add("광주"); // 4
        list_spinner_state.add("대전"); // 5
        list_spinner_state.add("울산"); // 6
        list_spinner_state.add("경기"); // 7
        list_spinner_state.add("충청"); // 8
        list_spinner_state.add("전라"); // 9
        list_spinner_state.add("경상"); // 10
        list_spinner_state.add("제주"); // 11
        list_spinner_state.add("세종"); // 12
        adapter_spinner_state = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list_spinner_state);
        spinner_state.setAdapter(adapter_spinner_state);
        spinner_state.setSelection(0);
    }

    public void selectedSpinner_state() {
        spinner_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    selectedSTATE = null;
                    spinner_city.setEnabled(false);
                    text_myCenter1.setText("근무지의 위치를 선택해주세요.");
                    adviceInfoFrame.setVisibility(View.GONE);
                } else {
                    selectedSTATE = spinner_state.getSelectedItem().toString();
                    spinner_city.setEnabled(true);
                    list_spinner_city.clear();
                    list_spinner_city.add(" ");
                    spinner_city.setSelection(0);

                    try {
                        Cursor iCursor = dbHelper.selectColumns_TABLENAME_CENTER();
                        iCursor.moveToFirst();

                        while (iCursor.moveToNext()) {
                            String tempCITY = iCursor.getString(iCursor.getColumnIndex("city"));
                            String tempSTATE = iCursor.getString(iCursor.getColumnIndex("state"));
                            if (tempSTATE.equals(selectedSTATE)) {
                                list_spinner_city.add(tempCITY);
                                spinner_city.setAdapter(adapter_spinner_city);
                            }
                        }
                        iCursor.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public void selectedSpinner_city(){
        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    adviceInfoFrame.setVisibility(View.GONE);
                    text_myCenter1.setText("근무지의 위치를 선택해주세요.");
                }
                else{
                    selectedCITY = spinner_city.getSelectedItem().toString();
                    adviceInfoFrame.setVisibility(View.VISIBLE);

                    try {
                        Cursor iCursor = dbHelper.selectColumns_TABLENAME_CENTER();
                        iCursor.moveToFirst();
                        while (iCursor.moveToNext()) {
                            String tempCITY = iCursor.getString(iCursor.getColumnIndex("city"));
                            String tempCENTER = iCursor.getString(iCursor.getColumnIndex("center"));
                            if (tempCITY.equals(selectedCITY)) {
                                selectedCENTER = tempCENTER;
                                text_myCenter1.setText("관할 노동청은 '" + selectedCENTER + "' 입니다.");
                                text_myCenter2.setText(selectedCENTER);
                            }
                        }
                        iCursor.close();

                        Cursor iCursor2 = dbHelper.selectColumns_TABLENAME_CENTERINFO();
                      //  iCursor2.moveToFirst();
                        while (iCursor2.moveToNext()) {
                            String tempCENTER = iCursor2.getString(iCursor2.getColumnIndex("center"));
                            String tempTEL = iCursor2.getString(iCursor2.getColumnIndex("tel"));
                            String tempADDRESS = iCursor2.getString(iCursor2.getColumnIndex("address"));
                            String tempFAX = iCursor2.getString(iCursor2.getColumnIndex("fax"));
                            if (tempCENTER.equals(selectedCENTER)) {
                                selectedTEL = tempTEL;
                                selectedADDRESS = tempADDRESS;
                                selectedFAX = tempFAX;
                                text_myCenterTel.setText(selectedTEL);
                                text_myCenterAddress.setText(selectedADDRESS);
                                text_myCenterFax.setText(selectedFAX);
                            }
                        }
                        iCursor2.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}