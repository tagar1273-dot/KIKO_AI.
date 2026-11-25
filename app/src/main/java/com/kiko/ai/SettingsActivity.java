package com.kiko.ai;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.*;

public class SettingsActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        LinearLayout l = new LinearLayout(this); l.setOrientation(1); l.setPadding(20,24,20,20);
        TextView t = new TextView(this); t.setText("KIKO Settings & Permissions"); t.setTextSize(23); l.addView(t);
        add(l,"Accessibility Service فعال کرنے کی Settings", v -> startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)));
        add(l,"App Permissions", v -> startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                android.net.Uri.parse("package:"+getPackageName()))));
        add(l,"Text-to-Speech Settings", v -> startActivity(new Intent("com.android.settings.TTS_SETTINGS")));
        add(l,"Android App Notification Settings", v -> startActivity(new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                .putExtra(Settings.EXTRA_APP_PACKAGE,getPackageName())));
        TextView note = new TextView(this);
        note.setText("\\nاہم: Accessibility صرف تب فعال کریں جب آپ اس کے اثرات سمجھتے ہوں۔ KIKO کو اسکرین پر نظر آنے والے controls تک رسائی مل سکتی ہے۔ بینکنگ، پاس ورڈ اور ادائیگی جیسے حساس کام خود انجام دیں۔");
        l.addView(note); setContentView(l);
    }
    private void add(LinearLayout l,String text, android.view.View.OnClickListener c) {
        Button b = new Button(this); b.setText(text); b.setOnClickListener(c); l.addView(b);
    }
}
