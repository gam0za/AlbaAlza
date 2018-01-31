package com.example.albaalza.P_Main;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Created by SEJIN on 2017-10-04.
 */

public final class BusProvider extends Bus{

    private static BusProvider instance;

    public static BusProvider getInstance(){
        if(instance == null)
            instance = new BusProvider();
        return instance;
    }

    private Handler handler = new Handler(Looper.getMainLooper());
    public void postQueue(final Object obj){
        handler.post(new Runnable(){
            @Override
            public void run() {
                BusProvider.getInstance().post(obj);
            }
        });
    }
}
