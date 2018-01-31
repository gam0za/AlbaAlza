package com.example.albaalza.P_Main;


import com.squareup.otto.Bus;

/**
 * Created by SEJIN on 2017-10-04.
 */

public final class BusProvider2 {
    private static final Bus BUS = new Bus();

    public static Bus getInstance(){
        return BUS;
    }

    private BusProvider2(){

    }
}