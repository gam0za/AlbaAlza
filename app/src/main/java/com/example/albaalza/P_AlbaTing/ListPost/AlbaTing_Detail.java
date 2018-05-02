package com.example.albaalza.P_AlbaTing.ListPost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albaalza.P_AlbaTing.AddMember.AddMemberPost;
import com.example.albaalza.P_AlbaTing.AddMember.AddMemberResponse;
import com.example.albaalza.P_AlbaTing.AddPost.AlbaTing_Write;
import com.example.albaalza.P_AlbaTing.ShowPost.AlbaTingData;
import com.example.albaalza.R;
import com.example.albaalza.Server.ApplicationController;
import com.example.albaalza.Server.NetworkService;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbaTing_Detail extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton floating1;
    private TextView AlbaTingName;
    private ImageView add_member;
    private String member_id,gname; //그룹에 멤버 추가 (id, gname)
    private NetworkService networkService;
    private AddMemberPost addMemberPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alba_ting_detail);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);
        floating1= (FloatingActionButton) findViewById(R.id.floating1);
        floating1.attachToRecyclerView(recyclerView);
        AlbaTingName=(TextView)findViewById(R.id.AlbaTingName);
        add_member=(ImageView)findViewById(R.id.add_member);
        gname= AlbaTingName.getText().toString();

        networkService= ApplicationController.getInstance().getNetworkService();


    /***********************멤버추가***********************/
        add_member.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            member_id="abaz";
            addmember("TESTGROUP3",member_id);
        }
    });


//        Adapter_AlbaTingList에서 넘겨준 알바팅 이름을 가져와서 붙여준다.
        Intent intent=getIntent();
        String albating_name=intent.getStringExtra("albating_name");
        AlbaTingName.setText(albating_name);

//        플로팅 버튼을 눌렀을 경우 글쓰기 페이지로 넘어간다.
        floating1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AlbaTing_Write.class);
                i.putExtra("gname",gname);
                startActivity(i);
            }
        });

        ArrayList<AlbaTingData> albaTingDataList=new ArrayList<>();

        //리사이클러뷰에 뷰가 추가되는 순간
        //서버에서 가져온걸 붙이던가, 아니면 AlbaTingData 클래스에 서버에서 가져온 데이터를 추가해야 할 것 같다.

//        int profile, String name, String time, int image, String title, int comment_image, String comment

        albaTingDataList.add(new AlbaTingData(R.drawable.user1,"사장님","2017.03.13.07:38",R.drawable.coffee,
                "5월 황금연휴 대타 구합니다~",R.drawable.tab_albating,"3"));

        albaTingDataList.add(new AlbaTingData(R.drawable.user1,"전지현","2017.05.13.07:38",R.drawable.misojigi,
                "6월 7일 저녁 6시!!!",R.drawable.tab_albating,"5"));

        albaTingDataList.add(new AlbaTingData(R.drawable.user1,"강동원","2017.07.16.07:38",R.drawable.dongwon,
                "동원찡이 대타가 급해요ㅠㅠ",R.drawable.tab_albating,"3"));

        albaTingDataList.add(new AlbaTingData(R.drawable.user1,"김태희","2017.05.13.07:38",R.drawable.puppy,
                "대타 급구!!!!!!!",R.drawable.tab_albating,"3"));

        albaTingDataList.add(new AlbaTingData(R.drawable.user1,"송중기","2017.02.13.07:38",R.drawable.cat,
                "3월 대타 필요하신분~",R.drawable.tab_albating,"3"));

        albaTingDataList.add(new AlbaTingData(R.drawable.user1,"사장님","2017.08.13.07:38",R.drawable.boss,
                "나는야 주휴수당 챙겨주는 멋쟁이~",R.drawable.tab_albating,"3"));

        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter_Detail(albaTingDataList,this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        int index= recyclerView.getChildAdapterPosition(view);
        Log.d("Item Click","CLICK");
        Toast.makeText(this, index, Toast.LENGTH_SHORT).show();

    }

    public void addmember(final String gname, final String member_id){
        addMemberPost=new AddMemberPost(gname,member_id);
        Call<AddMemberResponse> addMemberResponseCall=networkService.addMember(addMemberPost);
        addMemberResponseCall.enqueue(new Callback<AddMemberResponse>() {
            @Override
            public void onResponse(Call<AddMemberResponse> call, Response<AddMemberResponse> response) {
                if(response.isSuccessful()){
                    Log.d("addMember","SUCCESS");
                    ApplicationController.getInstance().makeToast(member_id+"님이 "+gname+"에 추가 되었습니다.");

                }else{
                    ApplicationController.getInstance().makeToast(response.body().result+": "+ response.body().message);
                }
            }

            @Override
            public void onFailure(Call<AddMemberResponse> call, Throwable t) {
                    Log.d("addMember","FAIL");
                    Log.d("gname,member_id",gname+","+member_id);
                    ApplicationController.getInstance().makeToast("서버 상태를 확인해주세요.");
            }
        });
    }
}
