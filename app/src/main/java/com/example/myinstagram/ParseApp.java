package com.example.myinstagram;

import android.app.Application;

import com.example.myinstagram.model.Post;
import com.example.myinstagram.model.User;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApp extends Application {

    @Override
    public void onCreate() {

        super.onCreate();
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(User.class);
        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("myFinalFBUApp")
                .clientKey("81811333")
                .server("http://speltzka-fbu-instagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
