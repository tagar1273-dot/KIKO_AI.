package com.kiko.ai;

import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;

public class CodeBuilderActivity extends Activity {
    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        LinearLayout l = new LinearLayout(this); l.setOrientation(1); l.setPadding(20,24,20,20);
        TextView title = new TextView(this); title.setText("KIKO Code Builder & Self-Upgrade"); title.setTextSize(22); l.addView(title);
        TextView warning = new TextView(this);
        warning.setText("یہ ورژن آپ کی فیچر درخواست محفوظ کرنے اور منصوبہ بنانے کا ابتدائی مرکز ہے۔ یہ خود سے ایپ کا سورس تبدیل، کوڈ execute یا APK انسٹال نہیں کرتا۔");
        l.addView(warning);
        EditText request = new EditText(this); request.setMinLines(4); request.setHint("مثلاً: KIKO میں فائل سرچ فیچر شامل کرو..."); l.addView(request);
        Button save = new Button(this); save.setText("فیچر درخواست محفوظ کریں");
        save.setOnClickListener(v -> {
            String s = request.getText().toString().trim();
            if (s.isEmpty()) { Toast.makeText(this,"پہلے اپنی درخواست لکھیں۔",0).show(); return; }
            getSharedPreferences("kiko",0).edit().putString("last_feature_request",s).apply();
            new android.app.AlertDialog.Builder(this).setTitle("درخواست محفوظ")
                .setMessage("درخواست محفوظ ہو گئی۔\\nاگلا محفوظ عمل: تقاضے، کوڈ ریویو، ٹیسٹ، بیک اپ، پھر آپ کی منظوری۔")
                .setPositiveButton("ٹھیک ہے",null).show();
        }); l.addView(save);
        Button history = new Button(this); history.setText("آخری محفوظ درخواست دکھائیں");
        history.setOnClickListener(v -> new android.app.AlertDialog.Builder(this).setTitle("Feature History")
            .setMessage(getSharedPreferences("kiko",0).getString("last_feature_request","ابھی کوئی درخواست نہیں۔"))
            .setPositiveButton("OK",null).show()); l.addView(history);
        Button backup = new Button(this); backup.setText("Settings / Backup ہدایات");
        backup.setOnClickListener(v -> Toast.makeText(this,"ایپ کی settings کا Android backup استعمال کریں؛ source-code backup GitHub پر رکھیں۔",1).show());
        l.addView(backup); setContentView(l);
    }
}
