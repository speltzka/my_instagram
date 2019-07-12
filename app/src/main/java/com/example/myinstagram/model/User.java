package com.example.myinstagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


@ParseClassName("_User")
public class User extends ParseUser{
    public static final String KEY_PROFILE_PHOTO = "profilePhoto";
    public static final String KEY_EMAIL = "email";


    public User(){}

    public ParseFile getProfileImage(){
        return getParseFile(KEY_PROFILE_PHOTO);
    }

    public void setProfileImage(ParseFile image){
        put(KEY_PROFILE_PHOTO, image);
    }

    public void email(String email){
        put(KEY_EMAIL, email);
    }

    public static class Query extends ParseQuery<User> {
        public Query() {
            super(User.class);

        }
    }
}
