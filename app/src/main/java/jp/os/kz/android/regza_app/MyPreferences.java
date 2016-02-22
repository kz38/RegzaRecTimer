package jp.os.kz.android.regza_app;

import android.preference.PreferenceActivity;
import android.os.Bundle;

public class MyPreferences extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.pref);
	}
}
