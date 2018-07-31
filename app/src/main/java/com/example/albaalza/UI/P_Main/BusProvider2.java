package com.example.albaalza.UI.P_Main;


import com.squareup.otto.Bus;



public final class BusProvider2 {
    private static final Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }

    private BusProvider2(){

    }
}