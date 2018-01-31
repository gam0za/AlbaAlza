package com.example.albaalza.Server;

import android.app.Application;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jinyoung on 2017-11-19.
 */

public class ApplicationController extends Application {
  private static ApplicationController applicationController;
  public static ApplicationController getInstance(){
    return applicationController;
  }
  private static String baseUrl = "http://13.59.115.89:3000";
  private NetworkService networkService;
  public NetworkService getNetworkService(){
    return networkService;
  }

  @Override
  public void onCreate() {
    super.onCreate();

    ApplicationController.applicationController = this;

      buildService();       //통신소스 완료 후 주석풀자.
  }

  public void buildService(){
    Retrofit.Builder builder = new Retrofit.Builder();
    Retrofit retrofit = builder
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    networkService = retrofit.create(NetworkService.class);
  }
  public void makeToast(String message){
    Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
  }
}