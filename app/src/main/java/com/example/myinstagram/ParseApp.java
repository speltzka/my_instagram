package com.example.myinstagram;

import android.app.Activity;
import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.parse.Parse;
import com.parse.ParseConfig;

public class ParseApp extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("myFinalFBUApp")
                .clientKey("81811333")
                .server("http://speltzka-fbu-instagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
