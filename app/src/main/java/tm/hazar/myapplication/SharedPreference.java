package tm.hazar.myapplication;

import android.content.Context;

public class SharedPreference {

    private final android.content.SharedPreferences pref;
    public android.content.SharedPreferences.Editor editor;

    private final Context _context;
    private static SharedPreference sharedPreferences;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "COURSE_WORK";
    private static final String KEY_THEME = "KEY_THEME";
    private static final String KEY_LANG = "KEY_LANG";


    public static SharedPreference newInstance(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = new SharedPreference(context);
        }
        return sharedPreferences;
    }

    public SharedPreference(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    public void setTheme(Boolean theme) {
        editor.putBoolean(KEY_THEME, theme);
        editor.commit();
    }
    public void setLang(String lang){
        editor.putString(KEY_LANG, lang);
        editor.commit();
    }
    public String getLang(){ return pref.getString(KEY_LANG,"tk");}

    public Boolean getTheme() {
        return pref.getBoolean(KEY_THEME, false);
    }
}

