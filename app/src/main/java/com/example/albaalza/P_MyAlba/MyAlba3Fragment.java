package com.example.albaalza.P_MyAlba;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albaalza.P_Main.MainActivity;
import com.example.albaalza.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAlba3Fragment extends Fragment{

    TextView edit_my_pay, edit_payday, edit_albaName;
    Button btn_edit, btn_delete;
    Switch insuranceSwitch;

    String alba_name;
    int currentID;
    int switchFlag = 0;

    MyAlba myAlba;
    MyAlbaAddFragment myAlbaAddFragment;

    // 데이터베이스
    MyAlbaDbOpenHelper dbHelper;

    public MyAlba3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            dbHelper = ((MainActivity)getActivity()).getDB(); // 메인액티비티로 부터 db를 얻어옴
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_alba3, container, false);
        view = createView(view);
        viewFunction();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if(isVisibleToUser){
            setInformation();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public View createView(View view){
        edit_albaName = (EditText) view.findViewById(R.id.edit_albaname);
        edit_my_pay = (EditText) view.findViewById(R.id.edit_my_pay);
        edit_payday = (EditText) view.findViewById(R.id.edit_payday);

        btn_edit = (Button) view.findViewById(R.id.btn_edit);
        btn_delete = (Button) view.findViewById(R.id.btn_delete);


        return view;
    }

    public void viewFunction(){

        // 사대 보험 가입 여부
        insuranceSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    switchFlag = 1; // 사대 보험 가입 O
                }
                else{
                    switchFlag = 0; // 사대 보험 가입 X
                }
            }
        });

        // 알바 정보 수정
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String tempNAME = String.valueOf(edit_albaName.getText());
                    int tempMYPAY = Integer.parseInt(String.valueOf(edit_my_pay.getText()));
                    int tempPAYDAY = Integer.parseInt(String.valueOf(edit_payday.getText()));

                    // db update
                    dbHelper.updateColumn_MYALBANAME(currentID, tempNAME, tempMYPAY, switchFlag, tempPAYDAY);
                    dbHelper.updateColumn_NameOfMYALBA(tempNAME, alba_name);

                    setInformation();
                    ((MainActivity)getActivity()).setAlbaNameInSpinner(tempNAME);
                    alba_name = ((MainActivity)getActivity()).getAlbaNameInSpinner();
                    Toast.makeText(getContext(), "수정이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(getContext(), "수정 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 알바 삭제
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    dbHelper.deleteColumn_myAlbaname(currentID); // db 삭제
                    dbHelper.deleteAllColumn_myAlba(alba_name); // db 삭제

                    // 근무지 1개 이상
                    if(((MainActivity)getActivity()).getAlbaNameNumber()){
                        Cursor iCursor = dbHelper.selectColumns_MYALBANAME();
                        iCursor.moveToLast();
                        String tempMYALBANAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));
                        ((MainActivity) getActivity()).setAlbaNameInSpinner(tempMYALBANAME);

                        // 화면 전환
                        myAlba = ((MainActivity) getActivity()).getMyAlba();
                        myAlba.getViewPager().setCurrentItem(0); // myAlba1Fragment
                    }
                    // 근무지 없을 때
                    else {
                        // 화면 전환
                        myAlba = ((MainActivity) getActivity()).getMyAlba();
                        myAlba.getViewPager().setCurrentItem(0); // myAlba1Fragment

                        myAlbaAddFragment = ((MainActivity) getActivity()).getMyAlbaAddFragment();
                        ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment, myAlbaAddFragment).commit();
                    }
                }
                catch (Exception ex){
                    Toast.makeText(getContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
                    ex.printStackTrace();
                }
                Toast.makeText(getContext(), "정상적으로 삭제 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setInformation(){
        try {
            alba_name = ((MainActivity)getActivity()).getAlbaNameInSpinner();

            Cursor iCursor = dbHelper.selectColumns_MYALBANAME();
            iCursor.moveToFirst();
            while (iCursor.moveToNext()) {
                String tempNAME = iCursor.getString(iCursor.getColumnIndex("myAlbaName"));
                int tempID = iCursor.getInt(iCursor.getColumnIndex("_id"));
                int tempMYPAY = iCursor.getInt(iCursor.getColumnIndex("myPaySet"));
                int tempPAYDAY = iCursor.getInt(iCursor.getColumnIndex("myPayday"));
                int tempINSURANCE = iCursor.getInt(iCursor.getColumnIndex("myPayment"));

                if(tempNAME.equals(alba_name)) {
                    currentID = tempID;
                    edit_albaName.setText(tempNAME);
                    edit_my_pay.setText(String.valueOf(tempMYPAY));
                    edit_payday.setText(String.valueOf(tempPAYDAY));
                    setInsuranceSwitch(tempINSURANCE);
                }
            }
            iCursor.close();
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setInsuranceSwitch(int tempINSURANCE){
        if(tempINSURANCE == 1){
            insuranceSwitch.setChecked(true);
            switchFlag = 1;
        }
    }
}
