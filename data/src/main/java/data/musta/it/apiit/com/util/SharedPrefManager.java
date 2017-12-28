package data.musta.it.apiit.com.util;

import android.content.SharedPreferences;

/**
 * Created by musta on 28-Dec-17.
 */

public class SharedPrefManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static SharedPrefManager sharedPrefManager;

    private SharedPrefManager(SharedPreferences sharedPreferences) {
        sharedPrefManager = new SharedPrefManager(sharedPreferences);
        this.sharedPreferences = sharedPreferences;
        this.editor =sharedPreferences.edit();
    }

    public static SharedPrefManager getInstance(SharedPreferences sharedPreferences){
        if(sharedPrefManager != null) return sharedPrefManager;
        sharedPrefManager = new SharedPrefManager(sharedPreferences);
        return sharedPrefManager;
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

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }
}
