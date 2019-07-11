package com.example.myinstagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;


@ParseClassName("User")
public class User extends ParseObject {
    public static final String KEY_PROFILE_PHOTO = "profilePhoto";


    public ParseFile getProfileImage(){
        return getParseFile(KEY_PROFILE_PHOTO);
    }

    public void setProfileImage(ParseFile image){
        put(KEY_PROFILE_PHOTO, image);
    }

}
