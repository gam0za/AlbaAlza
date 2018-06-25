package com.example.albaalza.B_MyPlace.Server;

import java.util.ArrayList;

/**
 * Created by jinyoungkim on 2018. 6. 22..
 */

//3-2. 받은 친구신청 확인
public class AlbaListResponse {
    public String objectid; //친구 신청의 objectid
    public String worker; //알바생 아이디
    public String owner;//사장님 아이디
    public Boolean status;//수락상태
    public String updated_at;//친구신청 보낸 날짜
}
