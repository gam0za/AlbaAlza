package com.example.albaalza.UI.B_MyPlace.Server;

/**
 * Created by jinyoungkim on 2018. 6. 22..
 */
//4-2 나에게 도착한 명세서 확인
public class StatementResponse {
    public String _id; //명세서의 objectid
    public String wid; //알바생 objectid
    public String oid; //사장님 objectid

    public String updated_at; //명세서 생성 날짜

//    총 급여
    public String total4; //총 급여

//    세금
    public String total3; //총 세금
    public String fourP; //4대보험 가입 여부

//    추가급여
    public String total2; //총 추가 급여
    public String nightA; //야간 수당
    public String weeklyA; //주휴 수당

//    기본급여
    public String total1; //총 기본 급여
    public String hours; //근무 시간
    public String wage; //시급


//    근무 시작종료 일자
    public String end_day; //마감 일
    public String end_month; //마감 달
    public String end_year; //마감 년

    public String start_day;//시작 일
    public String start_month;//시작 달
    public String start_year;//시작 년

//    호출 결과
    public String result;
    public String message;


}
