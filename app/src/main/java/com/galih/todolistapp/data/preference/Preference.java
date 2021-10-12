package com.galih.todolistapp.data.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context ctx;

    public Preference(Context ctx){
        this.ctx = ctx;
        prefs = ctx.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void setLoggedin(boolean logggedin){
        editor.putBoolean("loggedInmode",logggedin);
        editor.commit();
    }

    public boolean loggedin(){
        return prefs.getBoolean("loggedInmode", false);
    }

    public void setUserId(int userId){
        editor.putInt("userId", userId);
        editor.commit();
    }

    public int getUserId(){
        return prefs.getInt("userId", 0);
    }

    public void setUserName(String userName){
        editor.putString("userName", userName);
        editor.commit();
    }

    public String getUserName(){
        return prefs.getString("userName", "");
    }
}
