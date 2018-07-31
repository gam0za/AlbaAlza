package com.example.albaalza.UI.P_Labor;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albaalza.R;

public class Advice1Activity extends AppCompatActivity {

    private TextView title, body;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice1);

        title=(TextView)findViewById(R.id.title);
        body=(TextView)findViewById(R.id.body);
        image=(ImageView)findViewById(R.id.image);

        // 인텐트 받기
        Intent intent = new Intent();
        intent = getIntent();
        int code=intent.getIntExtra("code",-1);
        selectCode(code);

    }

    public void selectCode(int code){
        switch (code){
            case 1:
                title.setText("고용노동부");
                image.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.case1));
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                body.setText("1. 고용노동부란?\n" +
                        "대한민국 고용노동부는 고용과 노동에 관한 사무를 관장하는 중앙행정기관입니다.\n\n" +

                        "2. 조직\n" +
                        "고용노동 행정업무와 관련하여 전화 및 인터넷 상담 등을 제공하는 고객 상담센터, 서울,중부,부산,대구,광주,대전 지역을 관할하는 6개의 지방 고용노동청과 그 산하의 40개의 지청 및 1개의 출장소 등이 소속되어 있습니다.\n\n" +

                        "3. 업무 \n" +
                        "고용노동부의 주요업무는 다음과 같습니다.\n" +
                        "(1) 고용과 관련한 정책 총괄\n" +
                        "(2) 고용보험 정책 수립 및 총괄\n" +
                        "(3) 직업능력개발훈련에 관한 정책 총괄 \n" +
                        "(4) 근로자의 복지후생\n" +
                        "(5) 노사관계의 조정\n" +
                        "(6) 노사협력의 증진\n" +
                        "(7) 산업 안전보건 기준의 설정 등\n\n\n" +


                        "이 외 자세한 내용은 고용노동부 홈페이지(www.moel.go.kr)에서 확인할 수 있습니다.");
                break;
            case 2:
                title.setText("최저시급");
                image.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.case2));
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                body.setText("1. 최저임금제란?\n" +
                        "최저임금제란 저임금 근로자를 보호하기 위하여 국가가 고용자와 노동자 간의 임금 결정 과정에 개입하여 임금의 최저 수준을 정하고, 고용자에게 이 수준 이상의 임금을 지급하도록 법으로 강제한 것을 말합니다.\n\n" +

                        "2. 대한민국 최저임금\n" +
                        "대한민국의 최저임금(2018년 기준)은 7,530원으로, 전년 대비 16.4% 인상된 금액입니다.\n\n" +

                        "3. 최저임금 이하의 임금을 지급 받았다면?\n" +
                        "사장님이 알바생에게 최저임금 이상의 금액을 지급하지 않았을 경우 3년 이하의 징역 또는 2천만원 이하의 벌금을 부과할 수 있습니다. 신고는 고용노동부 홈페이지 내 '임금체불 신고' 탭을 이용해주세요.\n\n" +

                        "4. 청소년도 최저임금을 받을 수 있나요?\n" +
                        "대한민국 근로기준법 및 최저임금법은 성인은 물론 청소년에게도 적용됩니다.\n\n" +

                        "5. 수습기간이라며 최저임금 이하로 월급을 주세요.\n" +
                        "근로계약기간이 1년 이상인 근로자에 한하여 수습기간이 존재할 수 있습니다. 이 때 3개월 이내의 기간동안 최저 임금액의 10%를 감액하여 임금을 지급할 수 있습니다.");
                break;
            case 3:
                title.setText("4대보험");
                image.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.case3));
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                body.setText("1. 4대보험이란?\n" +
                        "4대보험은 '4대사회보험제도'로 국민연금, 건강보험, 고용보험, 산재보험을 통틀어 말하는 보험제도입니다.\n\n" +

                        "2.4대보험료는 얼마이며 누가 내나요?\n" +
                        "4대보험료는 임금의 8.3%로 세부 사항은 다음과 같습니다. \n" +
                        "(1) 산재보험: 고용주가 전액 부담, 모든 알바생이 가입해야 함\n" +
                        "(2) 국민연금, 건강보험, 고용보험: 사장님과 알바생이 반반 부담");
                break;
            case 4:
                title.setText("근로계약서");
                image.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.case4));
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                body.setText("1. 근로계약서란?\n" +
                        "근로계약서는 사용자가 근로자의 인력을 채용하고 근로자는 일의 대가를 지급받기 위해서 작성하는 근로 계약 문서입니다. 아르바이트 채용 시에도 필수로 작성해야 합니다:)\n\n" +

                        "2. 근로계약서 미작성 시 대처방법은?\n" +
                        "근로계약서를 작성하지 않았을 경우, 사용자에게 500만원 이하의 벌금을 부과할 수 있습니다. 사장님과 대화 후에도 근로계약서를 써주지 않는다면 노동청에 신고하세요:)\n\n");
                break;
            case 5:
                title.setText("주휴수당");
                image.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.case5));
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                body.setText("1. 주휴수당이란?\n" +
                        "주휴수당이란 일주일 동안 규정된 근무 일수를 다 채운 근로자에게 하루 임금을 추가로 지급하는 제도를 말합니다.\n\n" +

                        "2. 저도 주휴수당을 받을 수 있나요?\n" +
                        "주휴수당을 지급받기 위해서는 다음 두 개의 조건을 만족해야 합니다.\n" +
                        "(1) 일주일 동안 총 근무 시간이 15시간 이상\n" +
                        "(2) 약속된 근무일에 모두 출근했을 경우(지각, 조퇴는 상관 없음)\n\n" +

                        "3. 주휴수당 미지급 시 대처 방법은?\n" +
                        "주휴수당 지급 조건(주 15시간 근무, 약속한 근로 일에 모두 출근)을 만족했는데도 불구하고 주휴수당을 지급받지 못했다면 이는 명백한 임금체불 행위에 해당합니다. 임금체불 신고는 노동청 홈페이지 내 임금체불 신고 탭을 이용해 주세요.");
                break;
            case 6:
                title.setText("야간수당");
                image.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.case6));
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                body.setText("1. 야간수당이란?\n" +
                        "야간수당은 야간근로 시 임금의 50%, 즉 1.5배의 임금을 받을 수 있는 것을 말합니다. \n\n" +

                        "2. 저도 야간수당을 받을 수 있나요?\n" +
                        "야간수당을 지급받기 위해서는 다음 두 개의 조건을 만족해야 합니다.\n\n" +
                        "(1) 오후 10시~오전 6시 사이의 근무\n" +
                        "(2) 상시 근로하는 근로자 수가 5명 이상(5명 이하의 근로자가 있는 사업장)\n\n" +

                        "3. 야간수당 미지급 시 대처 방법은?\n" +
                        "야간수당 조건(오후10~오전6시 근무, 5명 이상의 근로자가 존재하는 사업장)을 만족했음에도 불구하고 지급받지 못했다면 이는 명백한 임금체불 입니다. 임금체불 신고는 노동청 내 임금체불 탭을 이용해 주세요");
                break;
            case 7:
                title.setText("퇴직금");
                image.setBackgroundDrawable(ContextCompat.getDrawable(this,R.drawable.case7));
                image.setScaleType(ImageView.ScaleType.FIT_CENTER);
                body.setText("1. 퇴직금제도란?\n" +
                        "퇴직금제도란 연속으로 일한 기간이 1년 이상인 노동자에 대하여 고용주가 30일 분 이상의 평균임금을 지급하는 제도입니다.\n\n" +

                        "2. 저도 퇴직금을 받을 수 있나요?\n" +
                        "연속으로 일한 기간이 1년 이상일 경우 30일분의 평균 임금을 퇴직금으로 받을 수 있습니다.\n\n" +

                        "3. 퇴직금 미지급 시 대처 방법은?\n" +
                        "1년 이상 계속 근무한 노동자에게 퇴직 14일 이내 퇴직금을 지급하지 않았을 경우, 이는 명백한 임금체불에 해당합니다. 신고는 고용노동부 홈페이지 내 '임금체불 신고' 탭을 이용해주세요.");
                break;
        }
    }
}