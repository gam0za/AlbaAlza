package com.example.albaalza.UI.P_Labor;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.albaalza.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;


public class Advice2Fragment extends Fragment {

    TextView result;
    EditText edit_pageNo;
    ImageView move_btn;
//    인증키, 페이지번호, 한페이지 결과 수 post

    //    TextView mainCategory, middleCategory, smallCategory, articleNo, question,answer;
    boolean  mainCategory, middleCategory, smallCategory, articleNo, question,answer=false;
    String  mainCategory_s, middleCategory_s, smallCategory_s, articleNo_s, question_s,answer_s=null;
    String pageNo="1";
    public Advice2Fragment() {
        // Required empty public constructor
    }

    public static Advice2Fragment newInstance() {
        Advice2Fragment fragment = new Advice2Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_advice2, container, false);

        StrictMode.enableDefaults();
        result=(TextView)view.findViewById(R.id.result);
        edit_pageNo=(EditText)view.findViewById(R.id.edit_pageNo);
        move_btn=(ImageView)view.findViewById(R.id.move_btn);


        move_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageNo=edit_pageNo.getText().toString();
                getLabor(pageNo);
            }
        });

        return view;
    }




    public void getLabor(String pageNo){
        this.pageNo=pageNo;
        try{
            URL url=new URL("http://apis.data.go.kr/1270000/lawedu/lawqna?"+"ServiceKey=bZ4h7I13jwBTJgcBTUJvRYlXBh9TKp7Nepu5eCPWz7bBFKEcaVR0cH9oQlmMlJ5O4FJjuCwCnd1EdpZvw1%2BwSg%3D%3D&"
                    +"numOfRows=10&pageNo="+pageNo);

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(),null);

            int parserEvent = parser.getEventType();
            Log.d("parsing","start");

            while(parserEvent != XmlPullParser.END_DOCUMENT){
                switch (parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("middleCategory")){
                            Log.d("item","middleCategory");
                            middleCategory=true;
                        }

                        if(parser.getName().equals("articleNo")){
                            Log.d("item","articleNo");
                            articleNo=true;
                        }

                        if(parser.getName().equals("articleNo")){
                            Log.d("item","articleNo");
                            articleNo=true;
                        }

                        if(parser.getName().equals("answer")){
                            Log.d("item","answer");
                            answer=true;
                        }

                        if(parser.getName().equals("mainCategory")){
                            Log.d("item","mainCategory");
                            mainCategory=true;
                        }

                        if(parser.getName().equals("question")){
                            Log.d("item","question");
                            question=true;
                        }

                        if(parser.getName().equals("smallCategory")){
                            Log.d("item","smallCategory");
                            smallCategory=true;
                        }

                        if(parser.getName().equals("message")){
                            Log.d("item","message");
                            result.setText("error");
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을 때
                        if(middleCategory){
                            middleCategory_s=parser.getText();
                            middleCategory=false;
                        }

                        if(articleNo){
                            articleNo_s=parser.getText();
                            articleNo=false;
                        }

                        if(answer){
                            answer_s=parser.getText();
                            answer=false;
                        }

                        if(mainCategory){
                            mainCategory_s=parser.getText();
                            mainCategory=false;
                        }

                        if(question){
                            question_s=parser.getText();
                            question=false;
                        }

                        if(smallCategory){
                            smallCategory_s=parser.getText();
                            smallCategory=false;
                        }
                        break;

                    case  XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            result.setText(result.getText()+question_s+"\n\n"+answer_s+"\n\n"+articleNo_s+"\n\n"+mainCategory_s+"\n\n"+middleCategory_s+"\n\n"+smallCategory_s+"\n\n");
                        }
                        break;

                }
                parserEvent=parser.next();
            }


        }catch (Exception e){

        }
    }


}

























