package com.studeofin_educaofinanceira.data.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.studeofin_educaofinanceira.conta;

public class YourPreference {
    private static YourPreference yourPreference;
    private SharedPreferences sharedPreferences;

    public static YourPreference getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new YourPreference(context);
        }
        return yourPreference;
    }

    private YourPreference(Context context) {
        sharedPreferences = context.getSharedPreferences("YourCustomNamedPreference",Context.MODE_PRIVATE);
    }

    public void saveData(String key,String value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }
    public void saveData(String key,Boolean value) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();

    }

    public void removeData(String key) {
        try{
            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
            prefsEditor.remove(key);
            prefsEditor.commit();
        }
        catch (Exception ex){

        }
    }

    public String getData(String key) {
        try{
            if (sharedPreferences!= null) {
                return sharedPreferences.getString(key, "");

            }
            return "";
        }
        catch (Exception ex)
        {
            return "";
        }
    }
    public Boolean getDataBoolean(String key) {
        try{
            if (sharedPreferences!= null) {
                return sharedPreferences.getBoolean(key, false);
            }
            return false;
        }
        catch (Exception ex){
            return false;
        }

    }
}
