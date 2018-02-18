package data.musta.it.apiit.com.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.securepreferences.SecurePreferences;

/**
 * Created by musta on 28-Dec-17.
 */

public class SharedPrefManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static SharedPrefManager sharedPrefManager;

    public SharedPrefManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.editor = sharedPreferences.edit();
        this.editor.apply();
    }

    public static SharedPrefManager getInstance(SharedPreferences sharedPreferences){
        if(sharedPrefManager != null) return sharedPrefManager;
        else {
            sharedPrefManager = new SharedPrefManager(sharedPreferences);
            return sharedPrefManager;
        }
    }

    public static SharedPrefManager getInstance(Context context) {
        if (sharedPrefManager != null) return sharedPrefManager;
        else {
            sharedPrefManager = new SharedPrefManager(new SecurePreferences(context, "SIMPLY_SHARE&aaAA12!@", "simply_share_shared_pref.xml"));
            return sharedPrefManager;
        }
    }

    public void put(String key, String value){
        editor.putString(key, value);
        editor.apply();
        editor.commit();
    }

    public String get(String key, String defVal){
        return sharedPreferences.getString(key, defVal);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void clear(){
        sharedPreferences.edit().clear().apply();
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }
}
