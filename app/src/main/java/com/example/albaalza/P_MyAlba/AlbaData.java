package com.example.albaalza.P_MyAlba;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SEJIN on 2017-11-02.
 */

public class AlbaData implements Parcelable{
    String AlbaName; // 근무지 이름
    int AlbaPay; //나의 시급
    boolean Insurance; //4대보험 true=> 가입 false=> 미가입
    int PayDay; //급여일

    int years=5;
    public AlbaDaysData[][][] albaDaysDatas; // 5년 동안 객체
    // [years][month][days], years: 2017(0), 2018(1), 2019(3), 2020(4), 2021(5)

    public AlbaData(String AlbaName,int AlbaPay,boolean Insurance,int PayDay){
        this.AlbaName=AlbaName;
        this.AlbaPay=AlbaPay;
        this.Insurance=Insurance;
        this.PayDay=PayDay;
        create_albaDaysDatas(); // 3차원 배열 생성
    }

    public void create_albaDaysDatas(){
        // 3차원 객체 배열 생성
        albaDaysDatas = new AlbaDaysData[years][][];
        for(int i=0; i<years; i++){
            albaDaysDatas[i] = new AlbaDaysData[12][];
            for(int j=0; j<12; j++)
                albaDaysDatas[i][j] = new AlbaDaysData[31];
        }

        // 3차원 객체 배열 초기화
        for(int i=0; i<years; i++)
            for(int j=0; j<12; j++)
                for(int k=0; k<31; k++)
                    albaDaysDatas[i][j][k] = new AlbaDaysData(AlbaPay);
    }


    /************************ Parcelable ************************/
    public AlbaData(Parcel parcel){
        readFromParcel(parcel);
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(AlbaName);
        parcel.writeInt(AlbaPay);
        parcel.writeByte((byte)(Insurance?1:0)); // Boolean 처리
        parcel.writeInt(PayDay);
    }

    public void readFromParcel(Parcel parcel){
        AlbaName = parcel.readString();
        AlbaPay = parcel.readInt();
        Insurance = parcel.readByte()!=0;
        PayDay = parcel.readInt();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        public AlbaData createFromParcel(Parcel parcel){
            return new AlbaData(parcel);
        }
        public AlbaData[] newArray(int size){
            return new AlbaData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    /************************************************************/



    /* SET FUNCTION */
    void setAlbaName(String albaName){
        AlbaName = albaName;
    }

    void setAlbaPay(int albaPay){
        AlbaPay = albaPay;
    }

    void setPayDay(int payDay){
        PayDay = payDay;
    }

    void setInsurance(boolean insurance){
        Insurance=insurance;
    }

    /* GET FUNCTION */
    String getAlbaName(){
        return AlbaName;
    }

    int getAlbaPay(){
        return AlbaPay;
    }

    int getPayDay(){
        return PayDay;
    }

    boolean getInsurance(){
        return Insurance;
    }

}
