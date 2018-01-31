package com.example.albaalza.P_MyAlba;

/**
 * Created by SEJIN on 2017-09-18.
 */

public class MyAlbaList {
    String alba_name;
    int my_pay, pay_day;
    boolean insurance_flag;

    public MyAlbaList(){

    }

    public MyAlbaList(String alba_name, int my_pay, int pay_day, boolean insurance_flag){
        this.alba_name=alba_name;
        this.my_pay=my_pay;
        this.pay_day=pay_day;
        this.insurance_flag=insurance_flag;

    }

    /* data get 함수 */
    public String get_alba_name(){
        return alba_name;
    }

    public int get_my_pay(){
        return my_pay;
    }

    public int get_pay_day(){
        return pay_day;
    }

    public boolean get_insurance_flag(){
        return insurance_flag;
    }


    /* data set 함수 */
    public void set_alba_name(String alba_name){
        this.alba_name=alba_name;
    }

    public void set_my_pay(int my_pay){
        this.my_pay=my_pay;
    }

    public void set_pay_day(int pay_day){
        this.pay_day=pay_day;
    }

    public void set_insurance_flag(boolean insurance_flag){
        this.insurance_flag=insurance_flag;
    }


}
