package com.example.albaalza.B_MyPlace.Server;

/**
 * Created by jinyoungkim on 2018. 6. 22..
 */

//3-3. 친구 신청 수락

public class AcceptResponse {
    public String _id;//친구 신청의 objectid
    public String worker;//알바생 아이디
    public String owner;//사장님 아이디
    public int __v;//
    public String updated_at;//친구 신청 보낸 날짜
    public Boolean status;//수락 상태

}
