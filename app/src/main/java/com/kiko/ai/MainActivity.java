package com.kiko.ai;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.*;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {
    private TextToSpeech tts;
    private TextView status, transcript;
    private EditText command;
    private static final int SPEECH_REQUEST = 101;
    private static final int AUDIO_PERMISSION = 102;

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(24, 32, 24, 24);
        layout.setBackgroundColor(0xFFF4F6FA);

        TextView title = new TextView(this);
        title.setText("KIKO AI  •  Jarvis Assistant");
        title.setTextSize(25); title.setTextColor(0xFF172554);
        layout.addView(title);

        status = new TextView(this);
        status.setText("تیار ہوں۔ مائیک کے بٹن سے حکم دیں۔");
        status.setTextSize(16); status.setPadding(0,18,0,12);
        layout.addView(status);

        transcript = new TextView(this);
        transcript.setText("آپ کا حکم یہاں دکھائی دے گا۔");
        layout.addView(transcript);

        command = new EditText(this);
        command.setHint("مثلاً: کیمرہ کھولو، الارم لگاؤ، یوٹیوب کھولو");
        layout.addView(command);

        addButton(layout, "🎙 آواز سے حکم دیں", v -> startListening());
        addButton(layout, "حکم چلائیں", v -> runCommand(command.getText().toString()));
        addButton(layout, "KIKO AI Teacher", v -> startActivity(new Intent(this, TeacherActivity.class)));
        addButton(layout, "Code Builder / Self-Upgrade", v -> startActivity(new Intent(this, CodeBuilderActivity.class)));
        addButton(layout, "Settings & Permissions", v -> startActivity(new Intent(this, SettingsActivity.class)));

        ScrollView scroll = new ScrollView(this);
        scroll.addView(layout);
        setContentView(scroll);

        tts = new TextToSpeech(this, result -> {
            if (result == TextToSpeech.SUCCESS) tts.setLanguage(new Locale("ur", "PK"));
        });
        requestRuntimePermissions();
    }

    private void addButton(LinearLayout box, String text, View.OnClickListener click) {
        Button b = new Button(this); b.setText(text); b.setOnClickListener(click); box.addView(b);
    }

    private void requestRuntimePermissions() {
        ArrayList<String> needed = new ArrayList<>();
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
            needed.add(Manifest.permission.RECORD_AUDIO);
        if (android.os.Build.VERSION.SDK_INT >= 33 &&
            checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            needed.add(Manifest.permission.POST_NOTIFICATIONS);
        if (!needed.isEmpty()) requestPermissions(needed.toArray(new String[0]), AUDIO_PERMISSION);
    }

    private void startListening() {
        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO}, AUDIO_PERMISSION); return;
        }
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ur-PK");
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "KIKO کو اپنا حکم بتائیں");
        try { startActivityForResult(i, SPEECH_REQUEST); }
        catch (Exception e) { speak("اس فون میں وائس ریکگنیشن دستیاب نہیں ہے۔"); }
    }

    @Override protected void onActivityResult(int req, int result, Intent data) {
        super.onActivityResult(req, result, data);
        if (req == SPEECH_REQUEST && result == RESULT_OK && data != null) {
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (results != null && !results.isEmpty()) {
                command.setText(results.get(0)); transcript.setText("آپ نے کہا: " + results.get(0));
                runCommand(results.get(0));
            }
        }
    }

    private void runCommand(String raw) {
        String c = raw == null ? "" : raw.toLowerCase(Locale.ROOT).trim();
        transcript.setText("حکم: " + raw);
        try {
            if (contains(c, "camera", "کیمرہ", "کیمرا")) {
                startActivity(new Intent(MediaStore.ACTION_IMAGE_CAPTURE)); speak("کیمرہ کھول رہا ہوں۔");
            } else if (contains(c, "youtube", "یوٹیوب")) {
                openUrl("https://www.youtube.com");
            } else if (contains(c, "browser", "انٹرنیٹ", "براؤزر", "گوگل")) {
                openUrl("https://www.google.com");
            } else if (contains(c, "music", "میوزک", "گانا")) {
                Intent m = new Intent(Intent.ACTION_MAIN); m.addCategory(Intent.CATEGORY_APP_MUSIC);
                if (m.resolveActivity(getPackageManager()) != null) startActivity(m);
                else speak("میوزک ایپ نہیں ملی۔");
            } else if (contains(c, "alarm", "الارم")) {
                Intent a = new Intent(AlarmClock.ACTION_SET_ALARM);
                a.putExtra(AlarmClock.EXTRA_HOUR, 7); a.putExtra(AlarmClock.EXTRA_MINUTES, 0);
                a.putExtra(AlarmClock.EXTRA_MESSAGE, "KIKO reminder");
                startActivity(a); speak("الارم سیٹ کرنے کی اسکرین کھول دی ہے۔");
            } else if (contains(c, "teacher", "استاد", "ٹیچر")) {
                startActivity(new Intent(this, TeacherActivity.class));
            } else if (contains(c, "settings", "سیٹنگ")) {
                startActivity(new Intent(this, SettingsActivity.class));
            } else if (contains(c, "back", "واپس")) {
                KikoAccessibilityService.performBack();
                speak("واپس جانے کی کوشش کی۔");
            } else if (contains(c, "home", "ہوم")) {
                KikoAccessibilityService.performHome();
                speak("ہوم اسکرین کھولنے کی کوشش کی۔");
            } else if (contains(c, "time", "وقت")) {
                String now = java.text.DateFormat.getTimeInstance().format(new java.util.Date());
                speak("اب وقت ہے " + now);
            } else if (contains(c, "message", "پیغام", "میسج")) {
                Intent sms = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"));
                sms.putExtra("sms_body", ""); startActivity(sms);
                speak("پیغام لکھنے کی اسکرین کھول دی ہے۔ بھیجنے سے پہلے خود چیک کریں۔");
            } else {
                speak("یہ حکم ابھی بنیادی ورژن میں شامل نہیں۔ Code Builder میں اس فیچر کی درخواست لکھ سکتے ہیں۔");
            }
        } catch (Exception e) { speak("یہ کام مکمل نہیں ہو سکا۔ براہ کرم اجازت اور ایپ چیک کریں۔"); }
    }

    private boolean contains(String s, String... words) {
        for (String w : words) if (s.contains(w)) return true;
        return false;
    }
    private void openUrl(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        speak("کھول رہا ہوں۔");
    }
    private void speak(String text) {
        status.setText(text);
        if (tts != null) tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "kiko");
    }
    @Override protected void onDestroy() {
        if (tts != null) { tts.stop(); tts.shutdown(); }
        super.onDestroy();
    }
}
